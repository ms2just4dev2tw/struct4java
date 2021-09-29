package org.self.queue;

import java.util.Iterator;

import org.self.list.ArrayList;

public class ArrayQueue<E> implements Queue<E>, Iterable<E> {

	// 队首
	protected int front;
	// 队尾
	protected int rear;
	// 存储数据
	private ArrayList<E> list;

	public ArrayQueue() {
		this.rear = 0;
		this.front = 0;
		this.list = new ArrayList<>();
	}

	@Override
	public boolean enter(E e) {
		list.add(rear++, e);
		return true;
	}

	@Override
	public E poll() {
		if (!isEmpty())
			return list.valueOf(front++);
		else
			throw new IllegalCallerException("the queue is empty, you can not caller");
	}

	@Override
	public E peek() {
		if (!isEmpty())
			return list.valueOf(front);
		else
			throw new IllegalCallerException("the queue is empty, you can not caller");
	}

	@Override
	public boolean isEmpty() {
		return front == rear;
	}

	@Override
	public Iterator<E> iterator() {
		return new HeapIterato();
	}

	private class HeapIterato implements Iterator<E> {
		private int count;
		private ArrayList<E> copy;

		public HeapIterato() {
			this.count = 0;
			this.copy = new ArrayList<>();
			for (int index = 0; index < list.size(); index++)
				copy.add(list.valueOf(index));
		}

		@Override
		public boolean hasNext() {
			return count < copy.size();
		}

		@Override
		public E next() {
			return copy.valueOf(count++);
		}
	}

}
