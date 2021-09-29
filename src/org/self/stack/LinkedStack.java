package org.self.stack;

import org.self.list.LinkedList;

public class LinkedStack<E> implements Stack<E> {

	private LinkedList<E> list;

	@Override
	public E pop() {
		return null;
	}

	@Override
	public E push(E e) {
		return null;
	}

	@Override
	public E peek() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

}
