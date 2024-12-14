package dev.hicksm.climbing.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionalLinkedListTest {
	
	/*
		This test checks that one item can be inserted into a PLL.
	*/
	@Test
	void testInsert1() {
		
		PositionalLinkedList<String> pll = new PositionalLinkedList<String>();
		String s = "mulch";
		
		pll.insert(s);
		
		Assertions.assertEquals(pll.size(), 1);
		Assertions.assertNotNull(pll.peekLastPosition());
	}
	
	/*
		This test checks that multiple items can be inserted into a PLL.
	*/
	@Test
	void testInsert2() {
		
		PositionalLinkedList<String> pll = new PositionalLinkedList<String>();
		String s1 = "mulch1";
		String s2 = "mulch2";
		String s3 = "mulch3";
		String s4 = "mulch4";
		
		pll.insert(s1);
		pll.insert(s2);
		pll.insert(s3);
		pll.insert(s4);
		
		Assertions.assertEquals(pll.size(), 4);
		Assertions.assertNotNull(pll.peekLastPosition());
	}

}