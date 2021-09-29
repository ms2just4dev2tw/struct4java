package org.self.stack;

public interface Stack<E> {

	/**
	 * 出栈
	 *  
	 * @return
	 */
	E pop();

	/** 
	 * 压栈
	 * 
	 * @param e
	 * @return
	 */
	E push(E e);

	/**
	 * 取得栈顶元素，但不出栈
	 * 
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
