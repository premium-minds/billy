/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.export;

import java.util.ArrayList;
import java.util.List;

public class ParamsTree<K, V> {

	private Node<K, V>	root;

	public ParamsTree(K rootKey) {
		this(rootKey, null);
	}

	public ParamsTree(K rootKey, V rootValue) {
		this.root = new Node<K, V>(rootKey, rootValue, null);
		this.root.value = rootValue;
		this.root.children = new ArrayList<Node<K, V>>();
	}

	public Node<K, V> getRoot() {
		return this.root;
	}

	@Override
	public String toString() {
		return this.root.toString();
	}

	public static class Node<K, V> {

		private K					key;
		private V					value;
		private Node<K, V>			parent;
		private List<Node<K, V>>	children;

		public Node(K key, Node<K, V> parent) {
			this(key, null, parent);
		}

		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.children = new ArrayList<Node<K, V>>();
		}

		public Node<K, V> addChild(K key) {
			return this.addChild(key, null);
		}

		public Node<K, V> addChild(K key, V value) {
			Node<K, V> newBorn = new Node<K, V>(key, value, this);
			this.children.add(newBorn);
			return newBorn;
		}

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}

		public Node<K, V> getParent() {
			return this.parent;
		}

		public List<Node<K, V>> getChildren() {
			return this.children;
		}

		@Override
		public String toString() {
			return this.toString("");
		}

		public boolean hasChildren() {
			return !this.children.isEmpty();
		}

		private String toString(String indentation) {
			String rval = indentation + "[" + this.key.toString() + "]"
					+ (null != this.value ? " - " + this.value.toString() : "")
					+ "\n";
			for (Node<K, V> child : this.children) {
				rval += indentation + child.toString(indentation + " ");
			}
			return rval;
		}
	}
}
