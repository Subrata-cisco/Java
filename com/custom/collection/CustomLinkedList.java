package com.custom.collection;


public class CustomLinkedList<E> {
	
	transient Node<E> first;
	transient Node<E> last;

	public CustomLinkedList() {
		
	}

	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

}
