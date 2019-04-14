package com.custom.collection;

import java.util.HashMap;
import java.util.Map;

public class CustomLRUCache {

	private Node head;
	private Node tail;
	private Map<Integer, Node> map = new HashMap<Integer, Node>();
	private int size = 0;

	public CustomLRUCache(int size) {
		this.size = size;
	}

	public void addIntoCache(int data) {
		size++;
		if (head == null) {
			head = Node.newNode(data);
			tail = head;
			return;
		}
	}

	static class Node {
		int data;
		Node next;
		Node before;
		Node child;
		Object obj;

		static Node newNode(int data, Object... obj) {
			Node n = new Node();
			n.data = data;
			n.next = null;
			n.before = null;
			if (obj.length > 0) {
				n.obj = obj[0];
			}
			return n;
		}
	}

}
