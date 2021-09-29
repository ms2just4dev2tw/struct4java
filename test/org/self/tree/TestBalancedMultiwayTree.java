package org.self.tree;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBalancedMultiwayTree {

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
	void insertTest() {
		System.out.println("B-树的元素插入");
		BalancedMultiwayTree<Integer, Integer> tree = new BalancedMultiwayTree<>(5);
		int insertArr[] = { 1, 2, 6, 7, 11, 4, 8, 13, 10, 5, 17, 9, 16, 20 };

		for (int key : insertArr) {
			tree.put(key, key);
			tree.display();
		}

		int insertArr2[] = { 3, 12, 14, 18, 19, 15 };
		for (int key : insertArr2) {
			tree.put(key, key);
			tree.display();
		}
		System.out.println();
		//
		deleteTest(tree);
	}

	void deleteTest(BalancedMultiwayTree<Integer, Integer> tree) {
		System.out.println("B-树的元素删除");
		int delArr[] = { 8, 16, 15, 4 };
		for (int key : delArr) {
			tree.delete(key);
			tree.display();
		}
		System.out.println();
	}

}
