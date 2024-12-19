package dev.hicksm.climbing.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.hicksm.climbing.util.PositionalLinkedList.Position;

public class PositionalLinkedListTest {
	
	private PositionalLinkedList<String> pll;
	
	@BeforeEach
	void resetPLL() { pll = new PositionalLinkedList<String>(); }
	
	/*
	  	This test verifies the expected properties of an empty PLL:
	  	
	  	- pll.size() == 0
	  	- pll.peekLastPosition() == null
	  	- pll.popFromBack() throws IllegalStateException
	*/
	@Test
	void testEmpty() {
		// pll.size == 0
		Assertions.assertEquals(0, pll.size());
		
		// pll.peekLastPosition() == null
		Assertions.assertNull(pll.peekLastPosition());
		
		// pll.popFromBack() throws IllegalStateException
		Assertions.assertThrows(
			IllegalStateException.class, 
			() -> pll.popFromBack()
		);
	}
	
	/*
		This test checks that one item can be inserted into a PLL.
	*/
	@Test
	void testInsert1() {
		// Add an item to the PLL
		String s = "mulch";
		pll.insert(s);
		
		// Check that the PLL's size has actually increased
		Assertions.assertEquals(1, pll.size());
		
		// Check that the PLL's head is now non-empty
		Assertions.assertNotNull(pll.peekLastPosition());
	}
	
	/*
		This test checks that multiple items can be inserted into a PLL
		and their Positions are properly linked.
	*/
	@Test
	void testInsert2() {
		// Add multiple items to the PLL
		String[] strings = {"first", "second", "third", "last"};
		for (int i = 0; i < strings.length; i++) {
			pll.insert(strings[i]);
		}
		
		// Check that the PLL's size has increased as expected
		Assertions.assertEquals(strings.length, pll.size());
		
		// Check that the Positions are properly linked
		Assertions.assertNotNull(pll.peekLastPosition().previous);
		Assertions.assertNotNull(pll.peekLastPosition().previous.previous.previous.next);
	}
	
	/*
	  	This test verifies the expected behavior of pll.popFromBack():
	  	
	  	- It reduces the size of the PLL by one
	  	- It returns the FIRST inserted item
	*/
	@Test
	void testPopFromBack1() {
		// Add multiple items to the PLL
		String[] strings = {"first", "second", "third", "last"};
		for (int i = 0; i < strings.length; i++) {
			pll.insert(strings[i]);
		}
		
		// popFromBack time!
		String res = pll.popFromBack();
		
		// Check that the PLL's size has been reduced as expected
		Assertions.assertEquals(strings.length - 1, pll.size());
		
		// Check that the FIRST inserted item is returned
		Assertions.assertEquals("first", res);
	}
	
	/*
  		This test verifies that, when pll.popFromBack() is called repeatedly
  		to clear out a PLL:
	  	
	  	- Items are popped in FIFO order
	  	- PLL size decreases by 1 each time
	*/
	@Test
	void testPopFromBack2() {
		// Add multiple items to the PLL
		String[] strings = {"first", "second", "last"};
		for (int i = 0; i < strings.length; i++) {
			pll.insert(strings[i]);
		}
		
		// Check that the FIRST element has been popped
		Assertions.assertEquals("first", pll.popFromBack());
		Assertions.assertEquals(2, pll.size());
		
		// Check that the SECOND element has been popped
		Assertions.assertEquals("second", pll.popFromBack());
		Assertions.assertEquals(1, pll.size());
		
		// Check that the LAST element has been popped
		Assertions.assertEquals("last", pll.popFromBack());
		Assertions.assertEquals(0, pll.size());
	}
	
	/*
	 	This test checks that pll.moveToFront() can re-order
	 	the Positions of a PLL.
	*/
	@Test
	void testMoveToFront() {
		// Add multiple items to the PLL
		String[] strings = {"first", "second", "third", "last"};
		for (int i = 0; i < strings.length; i++) {
			pll.insert(strings[i]);
		}
		
		// Get SECOND position and move it to the front
		Position second = pll.peekLastPosition().previous.previous;
		pll.moveToFront(second);
		
		// Now, the PLL should have this order:
		// "first", "third", "last", "second"
		Assertions.assertEquals("first", pll.popFromBack());
		Assertions.assertEquals("third", pll.popFromBack());
		Assertions.assertEquals("last", pll.popFromBack());
		Assertions.assertEquals("second", pll.popFromBack());
	}
	

}