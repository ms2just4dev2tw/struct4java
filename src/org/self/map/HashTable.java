package org.self.map;

import java.util.ArrayList;

public class HashTable {

	private class Node<T> {
		private T data; // 数据
		private int key; // 哈希表的索引
		private int ALSsucc; // 存放查找成功数，找到指定关键字
		private int ALSunsucc; // 存放查找不成功数，找到空白区

		public Node(T data, int key, int count) {
			ALSsucc = count;
			ALSunsucc = 1;// 默认值为1，为填充
			this.key = key;
			this.data = data;
		}

		public void updateALSunsucc() {
			int adr = key;
			int count = 1;
			// 直到找到空白位
			while (list.get(adr).data != null) {
				adr = ++adr % length;
				count++;
			}
			ALSunsucc = count;
		}
	}

	private int mod;
	private int length;
	private ArrayList<Node<Integer>> list;

	/** 拉链法（常用：数组+链表） */
	public HashTable() {
	}

	/** 开放定址法（常用：数组） @param n */
	public HashTable(int n) {
		mod = n / 2;
		length = n;
		list = new ArrayList<Node<Integer>>();

		// 为了模拟哈希表,必须填充list
		for (int i = 0; i < length; i++) {
			list.add(new Node<Integer>(null, i, -1));
			System.out.print(list.get(i).key + "\t");
			System.out.print(list.get(i).data + "\t");
			System.out.print(list.get(i).ALSsucc + "\t");
			System.out.print(list.get(i).ALSunsucc + "\t\n");
		}
	}

	public void insert(int y) {
		int count = 1;
		int adr = y % mod;

		if (list.get(adr).data == null) {
			Node<Integer> node = list.get(adr);
			node.data = y;
			node.ALSsucc = count;
		} else {
			do {
				adr = ++y % length;
				count++;
			} while (list.get(adr).data != null);

			Node<Integer> node = list.get(adr);
			node.data = y;
			node.ALSsucc = count;
		}
		display();
	}

	public void delete(int y) {
		int adr = search(y);

		if (adr != -1) {
			Node<Integer> node = list.get(adr);
			node.data = null;
			node.ALSsucc = -1;
		} else
			System.out.println("指定的数据不存在");

		// 展示
		display();
	}

	public int search(int y) {
		int adr = y % mod;

		while (list.get(adr).data != null && list.get(adr).data != y) {
			/**
			 * 搜索策略: 开放定址法 本程序使用 线性搜查法, 会有堆积问题 另外 平方搜查法也可以, 它不能搜查到所有单元
			 * 
			 * 还有, 如果要使用拉链法, 整个哈希表要使用 数组+链表的实现方式
			 */
			adr = ++adr % length;
		}

		if (list.get(adr).data == y)// 查找成功
			return adr;
		else
			return -1;// 找到空白处停止
	}

	public void display() {
		// 开始更新所有的ALSunsucc
		for (int i = 0; i < length; i++) {
			list.get(i).updateALSunsucc();
		}

		// 打印输出
		System.out.print("下标");
		for (int i = 0; i < length; i++) {
			System.out.print("\t" + i);
		}
		System.out.println();

		System.out.print("K值");
		for (int i = 0; i < length; i++) {
			if (list.get(i).data == null)
				System.out.print("\t" + " ");
			else
				System.out.print("\t" + list.get(i).data);
		}
		System.out.println();

		double sum_SU = 0, sum_UN = 0;
		System.out.print("成功");
		for (int i = 0; i < length; i++) {
			int count = list.get(i).ALSsucc;
			if (count == -1)
				System.out.print("\t" + " ");
			else {
				sum_SU += count;
				System.out.print("\t" + count);
			}
		}
		System.out.println();

		System.out.print("失败");
		for (int i = 0; i < length; i++) {
			int ALSunsucc = list.get(i).ALSunsucc;
			sum_UN += ALSunsucc;
			System.out.print("\t" + ALSunsucc);
		}

		System.out.print("\nALSsucc: " + sum_SU / length);
		System.out.println("\nALSunsucc: " + sum_UN / length);
	}

}
