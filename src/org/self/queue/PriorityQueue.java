package org.self.queue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PriorityQueue<Key extends Comparable<Key>> implements Iterable<Key> {

	private int num; // 堆结构中保存数据的个数
	private Key[] heap; // 将数据保存到堆结构中，这里指的是完全二叉树
	private BinaryHeapEnum type; // 二进堆的类型
	private Comparator<Key> comparator; // 可选的比较器

	public PriorityQueue(int initCapacity, BinaryHeapEnum type) {
		num = 0;
		this.type = type;
		heap = (Key[]) new Object[initCapacity + 1];
	}

	public PriorityQueue(int initCapacity, Comparator<Key> comparator, BinaryHeapEnum type) {
		num = 0;
		this.type = type;
		this.comparator = comparator;
		heap = (Key[]) new Object[initCapacity + 1];
	}

	public void insert(Key x) {
		// 如果当前的空间已满，开辟双倍的空间
		if (num == heap.length - 1)
			resize(2 * heap.length);

		// add x, and percolate it up to maintain heap invariant
		heap[++num] = x;
		swim(num); // 上浮x
		assert isHeap();
	}

	public Key pop() {
		if (isEmpty())
			throw new NoSuchElementException("Priority queue underflow");
		Key max = heap[1];
		exch(1, num--); // 将首部和尾部的元素交换
		sink(1); // 下沉首部的元素
		heap[num + 1] = null; // to avoid loitering and help with garbage collection

		// 堆中元素个数在等于开辟空间的 1/4 时，缩小空间
		if ((num > 0) && (num == (heap.length - 1) / 4))
			resize(heap.length / 2);
		assert isHeap();
		return max;
	}

	private void resize(int capacity) {
		assert capacity > num;
		// 重新开辟空间
		Key[] temp = (Key[]) new Object[capacity];
		for (int i = 1; i <= num; i++)
			temp[i] = heap[i];
		heap = temp;
	}

	/**
	 * 二进堆的上浮操作
	 * 
	 * @param k
	 */
	private void swim(int k) {
		// 如果满足堆的规则就与父节点交换，知道到达顶部
		for (; k > 1 && waysOfHeap(k / 2, k); k = k / 2)
			exch(k, k / 2);
	}

	/**
	 * 二进堆的下沉操作
	 * 
	 * @param k
	 */
	private void sink(int k) {
		for (int tmp = k * 2; tmp <= num; k = tmp) {
			// 从 k 的左右节点中找出（最大或最小）值
			if (tmp < num && waysOfHeap(tmp, tmp + 1))
				tmp += 1;
			// 如果最值节点与 k 节点相比符合下沉规则，就下沉，否则停止
			if (!waysOfHeap(k, tmp))
				break;
			// 交换
			exch(k, tmp);
		}
	}

	private boolean waysOfHeap(int i, int j) {
		if (type == BinaryHeapEnum.MAX_HEAP)
			return less(i, j);
		else if (type == BinaryHeapEnum.MIN_HEAP)
			return greater(i, j);
		else
			throw new UnsupportedOperationException("暂未支持的二进堆类型");
	}

	/**
	 * 最小堆的处理方式
	 * 
	 * @param i
	 * @param j
	 * @return 如果 i 代表的值大于 j 代表的值，返回true，否则返回false
	 */
	private boolean greater(int i, int j) {
		if (comparator == null) {
			return heap[i].compareTo(heap[j]) > 0;
		} else {
			return comparator.compare(heap[i], heap[j]) > 0;
		}
	}

	/**
	 * 最大堆的处理方式
	 * 
	 * @param i
	 * @param j
	 * @return 如果 i 代表的值小于 j 代表的值，返回true，否则返回false
	 */
	private boolean less(int i, int j) {
		if (comparator == null) {
			return heap[i].compareTo(heap[j]) < 0;
		} else {
			return comparator.compare(heap[i], heap[j]) < 0;
		}
	}

	// 交换元素
	private void exch(int i, int j) {
		Key swap = heap[i];
		heap[i] = heap[j];
		heap[j] = swap;
	}

	public boolean isEmpty() {
		return num == 0;
	}

	public int size() {
		return num;
	}

	// is pq[1..n] a max heap?
	private boolean isHeap() {
		// 堆内的数据不能为null
		for (int i = 1; i <= num; i++)
			if (heap[i] == null)
				return false;
		// 不在堆内的数据要为null
		for (int i = num + 1; i < heap.length; i++)
			if (heap[i] != null)
				return false;
		// 堆的第0位应该为null
		if (heap[0] != null)
			return false;
		return isHeapOrdered(1);
	}

	//
	private boolean isHeapOrdered(int k) {
		// 堆为空的情况，或终止递归方法
		if (k > num)
			return true;
		// 左右子节点
		int left = 2 * k, right = 2 * k + 1;
		// 父节点不能小于左子节点
		if (left <= num && waysOfHeap(k, left))
			return false;
		// 父节点不能小于右子节点
		if (right <= num && waysOfHeap(k, right))
			return false;
		// 递归检查子节点的结构
		return isHeapOrdered(left) && isHeapOrdered(right);
	}

	@Override
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<Key> {
		// create a new pq
		private PriorityQueue<Key> copy;

		// add all items to copy of heap
		// takes linear time since already in heap order so no keys move
		public HeapIterator() {
			if (comparator == null)
				copy = new PriorityQueue<Key>(size(), type);
			else
				copy = new PriorityQueue<Key>(size(), comparator, type);
			for (int i = 1; i <= num; i++)
				copy.insert(heap[i]);
		}

		@Override
		public boolean hasNext() {
			return !copy.isEmpty();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Key next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return copy.pop();
		}
	}

}
