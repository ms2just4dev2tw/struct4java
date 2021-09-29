package org.self.tree;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestRedBlackTree {

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
		Tree<Integer, Integer> tree = new RedBlackTree<>();
		int insertArr1[] = { 8, 18, 5, 15 };
		for (int key : insertArr1) {
			tree.put(key, key);
			tree.display();
		}

		int delArr1[] = { 15, 8 };
		for (int key : delArr1) {
			tree.delete(key);
			tree.display();
		}

		int insertArr2[] = { 17, 25, 7, 6 };
		for (int key : insertArr2) {
			tree.put(key, key);
			tree.display();
		}

		tree.delete(8);
		tree.display();
	}

}
