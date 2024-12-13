package dev.hicksm.climbing.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.hicksm.climbing.util.PositionalLinkedList;

public class PositionalLinkedListTest {
	
	/*
		This test checks that one item can be inserted into a PLL.
	*/
	@Test
	void testInsert() {
		
		PositionalLinkedList<String> pll = new PositionalLinkedList<String>();
		String s = "mulch";
		
		pll.insert(s);
		
		//TODO: Check that s is the head and tail of the PLL
	}

}