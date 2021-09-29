package org.self.list;

/**
 * 链接列表
 * <p>
 * 使用尾插法添加元素
 * 
 * @author TungWang
 * @param <E>
 */
public class LinkedList<E> implements List<E> {

	// 双向链表节点元素
	private class Element {
		E data;
		private Element pre;
		private Element next;

		public Element(E data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return data.toString();
		}
	}

	private int count;
	private Element head;
	private Element tail;

	public LinkedList() {
	}

	@Override
	public int size() {
		return count;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean add(E e) {
		if (tail == null) {
			tail = new Element(e);
			head = tail;
		} else {
			Element tmp = new Element(e);
			tail.next = tmp;
			tmp.pre = tail;
			//
			tail = tmp;
		}
		count++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		Element tmp = head;
		for (int index = 0; index < count; index++) {
			if (tmp.data == o) {
				Element pre = tmp.pre, next = tmp.next;
				pre.next = next;
				next.pre = pre;
				return true;
			} else
				tmp = tmp.next;
		}
		return false;
	}

	@Override
	public E remove(int index) {
		Element el = search(index);
		Element pre = el.pre, next = el.next;
		//
		pre.next = next;
		next.pre = pre;
		return el.data;
	}

	@Override
	public E valueOf(int index) {
		return search(index).data;
	}

	private Element search(int index) {
		if (index <= count / 2) {
			Element tmp = head;
			for (int i = 0; i < index; i++)
				tmp = tmp.next;
			return tmp;
		} else if (index < count) {
			Element tmp = tail;
			for (int i = count - 1; i >= index; i--)
				tmp = tmp.pre;
			return tmp;
		} else
			return null;
	}

	@Override
	public int indexOf(Object o) {
		Element tmp = head;
		for (int index = 0; index < count; index++)
			if (tmp.data == o)
				return index;
			else
				tmp = tmp.next;
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		Element tmp = tail;
		for (int index = count - 1; index >= 0; index--)
			if (tmp.data == o)
				return index;
			else
				tmp = tmp.pre;
		return -1;
	}

}
