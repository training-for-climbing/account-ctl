package dev.hicksm.climbing.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PositionalLinkedList<T> {
	
	private Position head, tail;
	
	private Map<Position, T> data;
	
	private int size;
	
	public PositionalLinkedList() {
		this.data = new HashMap<Position, T>();
		this.size = 0;
		
		// Set up dummy nodes
		this.head = new Position();
		this.tail = new Position();
		this.tail.next = this.head;
		this.head.previous = this.tail;
	}
	
	public Position insert(T t) {
		// Create a new position
		Position newNode = new Position();
		this.size++;
		
		// Assign data to position
		this.data.put(newNode, t);
		
		// Move the new position to the front
		this.moveToFront(newNode);
		
		return newNode;
	}
	
	public void moveToFront(Position p) {
		// Set lastAccess to current date-time
		p.lastAccess = LocalDateTime.now();
		
		// Save p's neighboring positions
		Position oldPrevious = p.previous;
		Position oldNext = p.next;
		
		// Move p to the front
		p.previous = this.head.previous;
		p.next = this.head;
		
		// Re-link p's old neighboring positions (if not null)
		if (oldPrevious != null) {
			oldPrevious.next = oldNext;
			oldNext.previous = oldPrevious;
		}
	}
	
	public Position peekLastPosition() {
		return this.head.previous;
	}
	
	public T popFromBack() {
		// Edge case: If PLL is already empty, return null
		if (this.size == 0) { return null; }
		
		// Save the position after the last non-dummy position
		Position oldBack = this.tail.next;
		Position newBack = oldBack.next;
		
		// Get data from last non-dummy position
		T res = data.get(oldBack);
		
		// Link new back with tail
		newBack.previous = this.tail;
		this.tail.next = newBack;
		
		// Reduce size of PLL
		this.size--;
		
		return res;
	}
	
	public int size() { return size; }
	
	public static class Position {
		
		public Position previous, next;
		
		public LocalDateTime lastAccess;
		
	}

}
