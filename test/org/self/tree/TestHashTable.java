package org.self.tree;

import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.self.map.HashTable;

class TestHashTable {

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
		HashTable table = new HashTable(13);
		Scanner sc = new Scanner(System.in);
		for (int key = 1; key != 0;) {
			System.out.print("提示【(0)-退出 (1)-插入 (2)-查找 (3)-删除 】\r\n 请输入指令：");
			key = Integer.parseInt(sc.next());
			switch (key) {
			case 1:
				System.out.print("请输入需要插入的关键字：");
				table.insert(Integer.parseInt(sc.next()));
				break;
			case 2:
				System.out.print("请输入你要查找的关键字：");
				table.search(Integer.parseInt(sc.next()));
				break;
			case 3:
				System.out.print("请输入需要删除的关键字：");
				table.delete(Integer.parseInt(sc.next()));
				break;
			case 0:
				System.out.println("System: Good Bye");
				break;
			default:
				System.out.println("ERROR: command not exist");
				System.exit(1);
			}
		}
		sc.close();
	}
}
