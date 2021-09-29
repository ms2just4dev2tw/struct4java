package org.self.list;

/**
 * 数组列表
 * 
 * @author TungWang
 * @param <E>
 * @see java.util.ArrayList
 */
public class ArrayList<E> implements List<E> {

	// 当前已经使用的空间
	protected int size;
	// 数组的实际容量
	protected int capacity;
	protected Object array[];
	// 默认初始化的容量
	private final static int DEFAULT_CAPACITY = 16;

	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}

	public ArrayList(int initCapacity) {
		if (initCapacity <= 0)
			throw new IllegalArgumentException("the initCapacity less than or equal to 0");
		capacity = initCapacity;
		array = new Object[capacity];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean add(E e) {
		// 如果当前使用的容量已经到顶了
		if (size == capacity)
			resize();
		array[size++] = e;
		return true;
	}

	public void add(int index, E e) {
		// 如果当前使用的容量已经到顶了
		if (size == capacity)
			resize();
		// 检测插入索引的合理性
		rangeCheckForAdd(index);
		// 元素后移
		for (int last = size++; last > index; last--)
			array[last] = array[last - 1];
		array[index] = e;
	}

	/**
	 * 将 index 处的值重设为 e，这与 add 是不同的
	 * 
	 * @param index
	 * @param e
	 */
	public void set(int index, E e) {
		validateIndex(index);
		array[index] = e;
	}

	private void rangeCheckForAdd(int index) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(index + " is less than 0 or greater than size");
	}

	private void resize() {
		validateOutOfMemory();
		capacity = capacity << 1;
		Object newData[] = new Object[capacity];
		//
		for (int index = 0; index < size; index++)
			newData[index] = array[index];
		array = newData;
	}

	/**
	 * 由于扩容机制是左移运算，所以 {@code Integer.MAX_VALUE - (Integer.MAX_VALUE >> 1)}
	 * 的结果{@code 01000000000000000000000000000000 1073741824}
	 * <p>
	 * 如果容量大于等于这个限值，就不能再扩容了
	 */
	private void validateOutOfMemory() {
		if (capacity >= 0x40000000 - 1)
			throw new OutOfMemoryError("capacity greater than 1073741823");
	}

	@Override
	public boolean remove(Object o) {
		remove(indexOf(o));
		return true;
	}

	@Override
	public E remove(int index) {
		validateIndex(index);
		E tmp = valueOf(index);
		// index右边的数据左移
		for (int i = index; i < size - 1; i++)
			array[i] = array[i + 1];
		size--;
		return tmp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E valueOf(int index) {
		validateIndex(index);
		return (E) array[index];
	}

	private void validateIndex(int index) {
		int max = size - 1;
		// 小于最小值
		if (index < 0)
			throw new IndexOutOfBoundsException(index + " is less than 0");
		// 大于最大值
		else if (index > max)
			throw new IndexOutOfBoundsException(index + " is greater than " + max);
	}

	@Override
	public int indexOf(Object o) {
		if (o == null) {
			for (int index = 0; index < size; index++)
				if (array[index] == null)
					return index;
		} else {
			for (int index = 0; index < size; index++)
				if (o.equals(array[index]))
					return index;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int index = size - 1; index >= 0; index--)
				if (array[index] == null)
					return index;
		} else {
			for (int index = size - 1; index >= 0; index--)
				if (o.equals(array[index]))
					return index;
		}
		return -1;
	}

}
