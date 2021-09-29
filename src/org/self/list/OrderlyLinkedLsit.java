package org.self.list;

/**
 * 有序链接列表
 * 
 * @author TungWang
 */
public class OrderlyLinkedLsit {

	private int num;
	private OrderEnum type;

	public OrderlyLinkedLsit() {
		type = OrderEnum.ASCEND;
	}

	public OrderlyLinkedLsit(OrderEnum type) {
		this.type = type;
	}

}
