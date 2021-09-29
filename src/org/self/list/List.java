package org.self.list;

/**
 * 列表
 * 
 * @author TungWang
 * @param <E>
 */
public interface List<E> {

	/**
	 * @return 返回当前列表的长度
	 */
	int size();

	/**
	 * @return 列表是否为空
	 */
	boolean isEmpty();

	/**
	 * 添加元素
	 * 
	 * @param e 元素类型
	 * @return 返回添加后的元素
	 */
	boolean add(E e);

	/**
	 * 移除指定的对象
	 * 
	 * @param o 对象
	 * @return 返回是否移除对象
	 */
	boolean remove(Object o);

	/**
	 * 根据指定的索引移除对象
	 * 
	 * @param index 索引
	 * @return 返回移除后的对象
	 */
	E remove(int index);

	/**
	 * 
	 * @param index
	 * @return
	 */
	E valueOf(int index);

	/**
	 * 根据对象找到在列表中第一次出现的索引
	 * 
	 * @param o 对象
	 * @return 返回对象在列表中第一次出现的索引
	 */
	int indexOf(Object o);

	/**
	 * 根据对象找到在列表中最后一次出现的索引
	 * 
	 * @param o 对象
	 * @return 返回对象在列表中最后一次出现的索引
	 */
	int lastIndexOf(Object o);

}
