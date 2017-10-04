/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.ebean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.premiumminds.billy.core.persistence.dao.ebean.DAOTaxImpl;
import com.premiumminds.billy.core.persistence.entities.ebean.JPAContextEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPATaxEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.query.QJPAContextEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.query.QJPATaxEntity;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.JPAPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.ebean.JPAPTTaxEntity;

public class DAOPTTaxImpl extends DAOTaxImpl implements DAOPTTax {

    @Override
    public PTTaxEntity getEntityInstance() {
        return new JPAPTTaxEntity();
    }

    @Override
    protected Class<JPAPTTaxEntity> getEntityClass() {
        return JPAPTTaxEntity.class;
    }

    @Override
    public List<JPAPTTaxEntity> getTaxesForSAFTPT(PTRegionContextEntity context, Date validFrom, Date validTo) {
        QJPATaxEntity query = this.queryTax().active.eq(true).context.equalTo((JPAContextEntity) context);
        if (validFrom != null) {
            query.validFrom.eq(validFrom);
        }
        if (validTo != null) {
            query.validTo.eq(validTo);
        }

        List<JPATaxEntity> taxes = query.findList();
        List<JPAPTTaxEntity> ptTaxes = new ArrayList<>();
        for (JPATaxEntity tax : taxes) {
            if (tax instanceof JPAPTTaxEntity) {
                ptTaxes.add((JPAPTTaxEntity) tax);
            }
        }

        List<JPAPTRegionContextEntity> childContexts = null;
        List<JPAPTTaxEntity> taxResult = null;
        List<JPAPTTaxEntity> taxContextResult = new ArrayList<>();

        for (JPAPTTaxEntity tax : ptTaxes) {
            childContexts = this.getChildContexts((PTRegionContextEntity) tax.getContext());
            for (JPAPTRegionContextEntity childContext : childContexts) {
                taxResult = this.getTaxesForSAFTPT(childContext, validFrom, validTo);
                if (taxResult != null) {
                    taxContextResult.addAll(taxResult);
                }
            }
        }
        if (taxContextResult != null) {
            ptTaxes.addAll(taxContextResult);
        }

        return ptTaxes;
    }

    private List<JPAPTRegionContextEntity> getChildContexts(PTRegionContextEntity parentContext) {
        List<JPAContextEntity> childContexts = this.queryContext(parentContext).findList();

        List<JPAPTRegionContextEntity> childPTContexts = new ArrayList<>();
        for (JPAContextEntity childContext : childContexts) {
            if (childContext instanceof JPAPTRegionContextEntity) {
                childPTContexts.add((JPAPTRegionContextEntity) childContext);
            }
        }
        return childPTContexts;
    }

    private QJPAContextEntity queryContext(PTRegionContextEntity parentContext) {
        return new QJPAContextEntity().parent.equalTo((JPAContextEntity) parentContext);
    }

    @Override
    public List<JPAPTTaxEntity> getTaxes(PTRegionContextEntity context, Date validFrom, Date validTo) {
        List<JPATaxEntity> taxes =
                this.queryTax().validFrom.eq(validFrom).validTo.eq(validTo).validTo.lessOrEqualTo(validFrom).active
                        .eq(true).context.equalTo((JPAContextEntity) context).findList();
        List<JPAPTTaxEntity> ptTaxes = new ArrayList<>();
        for (JPATaxEntity tax : taxes) {
            if (tax instanceof JPAPTTaxEntity) {
                ptTaxes.add((JPAPTTaxEntity) tax);
            }
        }

        if (context.getParentContext() != null) {
            ptTaxes.addAll(this.getTaxes((PTRegionContextEntity) context.getParentContext(), validFrom, validTo));
        }
        return ptTaxes;
    }

    private QJPATaxEntity queryTax() {
        return new QJPATaxEntity();
    }
}
