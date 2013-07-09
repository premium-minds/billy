/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.persistence.dao;

/**
 * @author Francisco Vargas
 * 
 * A class that ensures that ONE transaction is active.
 * If a transaction is not already active, then a new one is created.
 * Transaction management is ignored otherwise.
 * @param <T> The transaction return type
 */
public abstract class TransactionWrapper<T> {

	private DAO<?> dao;
	private boolean wasActive;

	/**
	 * The TransactionWrapper constructor
	 * @param dao The {@link DAO} managing the transaction.
	 */
	public TransactionWrapper(DAO<?> dao) {
		this.dao = dao;
	} 

	private void setupTransaction() {
		this.wasActive = dao.isTransactionActive();
		if(!wasActive) {
			dao.beginTransaction();
		}
	}

	/**
	 * Runs the transaction instructions
	 * @return The transaction return value
	 * @throws Exception an exception wrapping all thrown exceptions in the runTransaction block
	 */
	public abstract T runTransaction() throws Exception;

	/**
	 * Executes the transaction wrapping steps
	 * @return The transaction return value
	 * @throws Exception 
	 */
	public T execute() throws Exception {
		setupTransaction();
		T result = null;
		try {
			result = runTransaction();
		} catch(Exception e) {
			rollback();
			finalizeTransaction();
			throw e;
		}
		finalizeTransaction();
		return result;
	}

	/**
	 * Sets the transaction to be commited
	 */
	public void commit() {
		//Do nothing
	}

	/**
	 * Sets the transaction for rollback
	 */
	public void rollback() {
		dao.setForRollback();
	}

	private void finalizeTransaction() {
		if(!wasActive) {
			if(dao.isSetForRollback()) {
				dao.rollback();
			} else {
				dao.commit();
			}
		}
	}

}
