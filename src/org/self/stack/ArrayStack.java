package org.self.stack;

import org.self.list.ArrayList;

public class ArrayStack<E> implements Stack<E> {

	private ArrayList<E> list;

	public ArrayStack() {
		list = new ArrayList<>();
	}

	@Override
	public E pop() {
		int len = list.size();
		return list.remove(len - 1);
	}

	@Override
	public E push(E e) {
		if (list.add(e))
			return e;
		else
			throw new IllegalArgumentException(e.toString());
	}

	@Override
	public E peek() {
		int len = list.size();
		return list.valueOf(len - 1);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

}
