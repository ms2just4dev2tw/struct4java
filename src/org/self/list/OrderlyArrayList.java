package org.self.list;

/**
 * 有序数组列表
 * 
 * @author TungWang
 * @param <E>
 */
public class OrderlyArrayList<E extends Comparable<E>> implements List<E> {

	private OrderEnum type;
	private ArrayList<E> list;

	public OrderlyArrayList() {
		this(16);
	}

	public OrderlyArrayList(int initCapacity) {
		this(initCapacity, OrderEnum.ASCEND);
	}

	public OrderlyArrayList(int initCapacity, OrderEnum type) {
		this.type = type;
		list = new ArrayList<>(initCapacity);
	}

	/**
	 * 使用二分查找来添加元素
	 * 
	 * @param el
	 */
	@Override
	public boolean add(E el) {
		validateElement(el);
		// 二分查找
		int low = 0, high = size() - 1, mid = (low + high) / 2;
		for (; low <= high; mid = (low + high) / 2)
			if (inLeft(mid, el)) {
				high = mid - 1;
			} else if (inRight(mid, el)) {
				low = mid + 1;
			} else
				throw new IllegalStateException("the value " + el + "is already exist");
		// 使用索引 low
		list.add(low, el);
		return true;
	}

	private void validateElement(Object obj) {
		if (obj == null)
			throw new NullPointerException("the element is null");
	}

	/**
	 * 重置元素
	 * 
	 * @param oldE 旧元素
	 * @param newE 新元素
	 */
	public void reset(E oldE, E newE) {
		reset(indexOf(oldE), newE);
	}

	/**
	 * 重置 index 处的元素
	 * 
	 * @param index 旧元素的索引
	 * @param newE 新元素
	 */
	public void reset(int index, E newE) {
		validateElement(newE);
		// leftE: index 左边一位元素 rightE: index 右边一位元素
		E leftE = index == 0 ? null : valueOf(index - 1);
		E rightE = index == size() - 1 ? null : valueOf(index + 1);
		// 根据 newE 与 leftE，rightE 的比较结果处理
		if (leftE != null && newE.compareTo(leftE) < 0)
			leftMove(newE, index);
		else if (rightE != null && newE.compareTo(rightE) > 0)
			rightMove(newE, index);
		else
			list.set(index, newE);
	}

	/**
	 * 将元素 el 插入到 [0, end) 区间内
	 * 
	 * @param el
	 * @param end 这个位置的元素是将被替换的元素
	 */
	private void leftMove(E el, int end) {
		int index = search(el, 0, end - 1);
		E tmp = el, cache = null;
		for (int i = index; i < end; i++) {
			cache = valueOf(i);
			list.set(index, tmp);
			tmp = cache;
		}
	}

	/**
	 * 将元素 el 插入到 (start, size()-1] 区间内
	 * 
	 * @param el
	 * @param start 这个位置的元素是将被替换的元素
	 */
	private void rightMove(E el, int start) {
		int index = search(el, start + 1, size() - 1);
		E tmp = el, cache = null;
		for (int i = index; i > start; i--) {
			cache = valueOf(i);
			list.set(index, tmp);
			tmp = cache;
		}
	}

	// 在 [start，end] 中找到最接近 el 的位置
	private int search(E el, int start, int end) {
		int low = start, high = end, mid = (low + high) / 2;
		for (; low <= high; mid = (low + high) / 2)
			if (inLeft(mid, el)) {
				high = mid - 1;
			} else if (inRight(mid, el)) {
				low = mid + 1;
			} else
				return mid;
		return low;
	}

	@Override
	public boolean remove(Object obj) {
		validateElement(obj);
		int index = indexOf(obj);
		return remove(index).equals(obj);
	}

	/**
	 * @param index
	 * @return 返回移除 index 后的元素
	 */
	@Override
	public E remove(int index) {
		return list.remove(index);
	}

	// 移除第一个元素
	public E removeHead() {
		return remove(0);
	}

	// 移除最后一个元素
	public E removeTail() {
		return remove(size() - 1);
	}

	@Override
	public int indexOf(Object obj) {
		validateElement(obj);
		@SuppressWarnings("unchecked")
		E data = (E) obj;
		int low = 0, high = size() - 1, mid = (low + high) / 2;
		for (; low <= high; mid = (low + high) / 2)
			if (inLeft(mid, data)) {
				high = mid - 1;
			} else if (inRight(mid, data)) {
				low = mid + 1;
			} else
				return mid;
		// 要查找的数据不在列表中，这是个不正常的参数
		throw new IllegalArgumentException("illegal argument with： /r/n" + data.toString());
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException("lastIndexOf");
	}

	/**
	 * @param mid
	 * @param data
	 * @return data 是否在索引 mid 的左边
	 */
	private boolean inLeft(int mid, E data) {
		if (type == OrderEnum.ASCEND) // 升序
			return data.compareTo(list.valueOf(mid)) < 0;
		else if (type == OrderEnum.DESCEND) // 降序
			return data.compareTo(list.valueOf(mid)) > 0;
		else
			throw new UnsupportedOperationException("unknown operation");
	}

	/**
	 * 
	 * @param mid
	 * @param data
	 * @return data 是否在索引 mid 的右边
	 */
	private boolean inRight(int mid, E data) {
		if (type == OrderEnum.ASCEND) // 升序
			return data.compareTo(list.valueOf(mid)) > 0;
		else if (type == OrderEnum.DESCEND) // 降序
			return data.compareTo(list.valueOf(mid)) < 0;
		else
			throw new UnsupportedOperationException("unknown operation");
	}

	@Override
	public E valueOf(int index) {
		return list.valueOf(index);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(list.size);
		for (int index = 0; index < list.size; index++)
			sb.append(list.valueOf(index) + "  ");
		return sb.toString();
	}

}
