package org.self.list;

// 压缩存储矩阵上三角元素
public class TriangleArray {

	// rank，矩阵的边长
	private int rank, array[];

	public TriangleArray(int rank) {
		this.rank = rank;
		int length = (rank + 1) * rank / 2 + rank % 2 * (rank + 1) / 2;
		array = new int[length];

		// head是表示像[x][x]这样的节点，gap是距离x的下一个间距
		for (int i = 0, head = 0, gap = length; i < length; i++)
			if (i == head) {
				array[head] = 0;
				head += gap--;
			} else
				array[i] = Integer.MAX_VALUE;
	}

	public void setValue(int row, int column, int value) {
		if (row > column) // 由于是上三角压缩，下三角元素直接映射成等值元素
			setValue(column, row, value);
		else
			array[getIndex(row, column)] = value;
	}

	public int getValue(int row, int column) {
		if (row > column) // 由于是上三角压缩，下三角元素直接映射成等值元素
			return getValue(column, row);
		else
			return array[getIndex(row, column)];
	}

	/**
	 * 获得当前元素的索引
	 * 
	 * @param row 行数
	 * @param column 列数
	 * @return 元素索引
	 */
	private int getIndex(int row, int column) {
		int triangleLen = rank - row;
		// triangle是row下面的小三角拥有元素的个数或者说是面积
		// 另外注意triangleLen/2 * (triangleLen+1)不可写成(triangleLen+1) * triangleLen/2的形式
		int triangle = triangleLen / 2 * (triangleLen + 1) + triangleLen % 2 * (triangleLen + 1) / 2;
		int index = array.length - triangle + column - row;
		return index;
	}

}
