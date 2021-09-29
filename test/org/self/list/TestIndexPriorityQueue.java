package org.self.list;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.self.queue.IndexPriorityQueue;
import org.self.tree.StdRandom;

class TestIndexPriorityQueue {

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
		String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };

		IndexPriorityQueue<String> queue = new IndexPriorityQueue<String>(strings.length);
		for (int i = 0; i < strings.length; i++)
			queue.insert(i, strings[i]);

		// print each key using the iterator
		for (int i : queue)
			System.out.print(i + " " + strings[i] + "\t");
		System.out.println();

		// increase or decrease the key
		for (int i = 0; i < strings.length; i++)
			if (StdRandom.uniform() < 0.5)
				queue.increaseKey(i, strings[i] + strings[i]);
			else
				queue.decreaseKey(i, strings[i].substring(0, 1));

		// delete and print each key
		while (!queue.isEmpty()) {
			String key = queue.maxKey();
			int i = queue.pop();
			System.out.print(i + " " + key + "\t");
		}
		System.out.println();

		// reinsert the same strings
		for (int i = 0; i < strings.length; i++)
			queue.insert(i, strings[i]);

		// delete them in random order
		int[] perm = new int[strings.length];
		for (int i = 0; i < strings.length; i++)
			perm[i] = i;
		StdRandom.shuffle(perm);
		for (int i = 0; i < perm.length; i++) {
			String key = queue.keyOf(perm[i]);
			queue.delete(perm[i]);
			System.out.print(perm[i] + " " + key + "\t");
		}
	}

}
