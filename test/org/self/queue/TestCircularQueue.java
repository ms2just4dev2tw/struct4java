package org.self.queue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCircularQueue {

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
		int insertArr[] = { 75, 23, 98, 44, 57, 12, 29, 64, 38, 82 };
		CircularQueue<Integer> queue = new CircularQueue<>();
		for (int el : insertArr)
			queue.enter(el);
		//
		while (!queue.isEmpty()) {
			int el = queue.poll();
			System.out.print(el + "\t");
		}
	}

}
