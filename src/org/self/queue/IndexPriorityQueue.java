package org.self.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexPriorityQueue<Key extends Comparable<Key>> implements Iterable<Integer> {

	// 二进堆结构，按照完全二叉结构保存data的索引
	private int heap[];
	// 堆的索引，保存的是data元素在堆中的索引位置
	private int index[];
	// 保存的数据
	private Key data[];
	// 二进堆的类型
	private BinaryHeapEnum type;
	// num4Heap，堆的长度；num4Data，数据的长度
	private int num4Heap, num4Data;

	public IndexPriorityQueue(int initCapacity) {
		this(initCapacity, BinaryHeapEnum.MAX_HEAP);
	}

	public IndexPriorityQueue(int initCapacity, BinaryHeapEnum type) {
		this.type = type;
		num4Heap = 0;
		num4Data = initCapacity;
		heap = new int[initCapacity + 1];
		index = new int[initCapacity];
		data = (Key[]) new Comparable[initCapacity];
		// 堆的索引初始为-1
		for (int i = 0; i < initCapacity; i++)
			index[i] = -1;
	}

	public void insert(int i, Key key) {
		validateIndex(i);
		if (contains(i))
			throw new IllegalArgumentException("index is already in the priority queue");

		num4Heap += 1;
		data[i] = key; // 将key值保存到指定位置 i
		heap[num4Heap] = i; // 堆指向数据
		index[i] = num4Heap; // 堆索引指向堆
		// 堆上浮元素
		swim(num4Heap);
	}

	public void delete(int i) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		// 与尾部交换
		int indexi = index[i];
		// 与尾部交换 i 的key在堆中位置
		exch(indexi, num4Heap--);
		// 上浮
		swim(indexi);
		// 下沉
		sink(indexi);
		data[i] = null;
		index[i] = -1;
	}

	public int pop() {
		if (num4Heap == 0)
			throw new NoSuchElementException("Priority queue underflow");
		int max = heap[1];
		// 交换首部与尾部
		exch(1, num4Heap--);
		// 下沉首部
		sink(1);
		// 断言堆的num4Heap + 1的还是最大key值索引
		assert heap[num4Heap + 1] == max;
		index[max] = -1; // 从堆索引中删除
		data[max] = null; // 将数据集合的key值置为null
		heap[num4Heap + 1] = -1; // not needed
		return max;
	}

	public Key keyOf(int i) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		else
			return data[i];
	}

	// 改变指定位置的key值
	public void changeKey(int i, Key key) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		data[i] = key;
		swim(index[i]);
		sink(index[i]);
	}

	// 增大key的值
	public void increaseKey(int i, Key key) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		if (data[i].compareTo(key) == 0)
			throw new IllegalArgumentException(
					"Calling increaseKey() with a key equal to the key in the priority queue");
		if (data[i].compareTo(key) > 0)
			throw new IllegalArgumentException(
					"Calling increaseKey() with a key that is strictly less than the key in the priority queue");

		data[i] = key;
		swim(index[i]);
	}

	// 减小key的值
	public void decreaseKey(int i, Key key) {
		validateIndex(i);
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		if (data[i].compareTo(key) == 0)
			throw new IllegalArgumentException(
					"Calling decreaseKey() with a key equal to the key in the priority queue");
		if (data[i].compareTo(key) < 0)
			throw new IllegalArgumentException(
					"Calling decreaseKey() with a key that is strictly greater than the key in the priority queue");
		data[i] = key;
		sink(index[i]);
	}

	// 验证索引的正确性
	private void validateIndex(int i) {
		if (i < 0)
			throw new IllegalArgumentException("index is negative: " + i);
		if (i >= num4Data)
			throw new IllegalArgumentException("index >= capacity: " + i);
	}

	// 上浮操作
	private void swim(int k) {
		// 如果满足堆的规则就与父节点交换，直到到达堆的顶部
		for (; k > 1 && waysOfHeap(k / 2, k); k = k / 2)
			exch(k, k / 2);
	}

	// 下沉操作
	private void sink(int k) {
		for (int tmp = k * 2; tmp <= num4Heap; k = tmp) {
			// 从 k 的左右节点中找出（最大或最小）值
			if (tmp < num4Heap && waysOfHeap(tmp, tmp + 1))
				tmp += 1;
			// 如果最值节点与 k 节点相比符合下沉规则，就下沉，否则停止
			if (!waysOfHeap(k, tmp))
				break;
			// 交换
			exch(k, tmp);
		}
	}

	// 根据堆的类型来选择上浮和下沉时使用的方式
	private boolean waysOfHeap(int i, int j) {
		if (type == BinaryHeapEnum.MAX_HEAP)
			return less(i, j);
		else if (type == BinaryHeapEnum.MIN_HEAP)
			return greater(i, j);
		else
			throw new UnsupportedOperationException("暂未支持的二进堆类型");
	}

	// 最小堆的方式
	private boolean greater(int i, int j) {
		return data[heap[i]].compareTo(data[heap[j]]) > 0;
	}

	// 最大堆的方式
	private boolean less(int i, int j) {
		return data[heap[i]].compareTo(data[heap[j]]) < 0;
	}

	// 交换
	private void exch(int i, int j) {
		// 交换堆的数据
		int swap = heap[i];
		heap[i] = heap[j];
		heap[j] = swap;
		// 交换堆索引数组的1数据
		index[heap[i]] = i;
		index[heap[j]] = j;
	}

	// 堆中的最大key值的索引
	public int maxIndex() {
		if (num4Heap == 0)
			throw new NoSuchElementException("Priority queue underflow");
		return heap[1];
	}

	// 堆中最大key值
	public Key maxKey() {
		if (num4Heap == 0)
			throw new NoSuchElementException("Priority queue underflow");
		return data[heap[1]];
	}

	public boolean isEmpty() {
		return num4Heap == 0;
	}

	public boolean contains(int i) {
		validateIndex(i);
		return index[i] != -1;
	}

	public int size() {
		return num4Heap;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<Integer> {
		// create a new pq
		private IndexPriorityQueue<Key> copy;

		// add all elements to copy of heap
		// takes linear time since already in heap order so no keys move
		public HeapIterator() {
			copy = new IndexPriorityQueue<Key>(heap.length - 1);
			for (int i = 1; i <= num4Heap; i++)
				copy.insert(heap[i], data[heap[i]]);
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
		public Integer next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return copy.pop();
		}
	}

}
