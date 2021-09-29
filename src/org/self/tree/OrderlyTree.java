package org.self.tree;

public class OrderlyTree<V extends Comparable<V>> {

	public static class OrderlyNode<V extends Comparable<V>> {
		// 存放的数据
		private V data;
		// 当前节点的左边节点和右边节点的计数
		private int leftCount, RightCount;

		public OrderlyNode(V data) {
			this.data = data;
			// 初始节点的默认左右节点计数均为0
			this.leftCount = 0;
			this.RightCount = 0;
		}

		public V getData() {
			return data;
		}

		public void setData(V data) {
			this.data = data;
		}

		public int getLeftCount() {
			return leftCount;
		}

		public void setLeftCount(int leftCount) {
			this.leftCount = leftCount;
		}

		public int getRightCount() {
			return RightCount;
		}

		public void setRightCount(int rightCount) {
			RightCount = rightCount;
		}
	}

	/**
	 * 查找操作
	 * @param top 顶部节点，不一定是根节点
	 * @param index 查找的索引，一般是位于 [ 0,size() ) 
	 * @return 返回查找的节点，如果不存在该关键字的节点，返回最后查找的节点
	 */
	public OrderlyNode<V> search(OrderlyNode<V> top, int index) {
		return null;
	}

	/**
	 * 插入操作
	 * @param data 数据
	 * @return 存在一样关键字的节点返回false，否则插入并返回true
	 */
	public boolean put(V data) {
		return false;
	}

	/**
	 * 删除操作
	 * @param index 查找的索引，一般是位于 [ 0,size() ) 
	 * @return 存在该关键字的节点，删除并返回true，否则返回false
	 */
	public boolean delete(int index) {
		return false;
	}

	/**
	 * top节点下面的最小节点
	 * @param top 顶部节点，不一定是根节点
	 * @return 找到的最小节点
	 */
	public OrderlyNode<V> min(OrderlyNode<V> top) {
		return null;
	}

	/**
	 *  top节点下面的最大节点
	 * @param top 当前节点，并不一定是根节点
	 * @return 找到的最大节点
	 */
	public OrderlyNode<V> max(OrderlyNode<V> top) {
		return null;
	}

	public void display() {

	}

}
