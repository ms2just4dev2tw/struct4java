package org.self.tree;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBinaryBalancedTree {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		Tree<Integer, Integer> tree = new BinaryBalancedTree<>();

		System.out.println("开始添加元素");
		int arr[] = { 16, 3, 7, 11, 9, 26, 18, 14, 15 };
		for (int key : arr) {
			tree.put(key, key);
			tree.display();
		}

		System.out.println("开始删除元素");
		tree.delete(11);
		tree.display();
	}

}
