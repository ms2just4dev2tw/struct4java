package org.self.queue;

import org.self.list.LinkedList;

public class LinkedQueue<E> implements Queue<E> {

	private LinkedList<E> list;

	@Override
	public boolean enter(E e) {
		return false;
	}

	@Override
	public E poll() {
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
