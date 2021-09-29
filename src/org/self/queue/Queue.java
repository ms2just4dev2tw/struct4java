package org.self.queue;

/**
 * 先进先出表
 * <p>
 * 在队尾进行插入操作，在首段进行删除操作
 * <p>
 * 
 * @author wh136
 *
 */
public interface Queue<E> {

	/**
	 * 进队
	 * 
	 * @param e
	 * @return
	 */
	boolean enter(E e);

	/**
	 * 取得队首元素，并且移除队首元素
	 * 
	 * @return
	 */
	E poll();

	/**
	 * 取得队首元素，但不移除队首元素
	 * @return
	 */
	E peek();

	/**
	 * 判断是否为空队
	 * 
	 * @return
	 */
	boolean isEmpty();

}
