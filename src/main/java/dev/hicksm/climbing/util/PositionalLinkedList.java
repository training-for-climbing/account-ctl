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
		this.tail = new Position();
		this.head = new Position();
		this.head.next = this.tail;
		this.tail.previous = this.head;
	}
	
	public Position insert(T t) {
		// Create a new position
		Position newNode = new Position();
		
		// Move the new position to the front
		this.moveToFront(newNode);
		
		// Assign data to position
		this.data.put(newNode, t);
		this.size++;
		
		return newNode;
	}
	
	public void moveToFront(Position p) {
		// Set lastAccess to current date-time
		p.lastAccess = LocalDateTime.now();
		
		// Save p's neighboring positions
		Position oldPrevious = p.previous;
		Position oldNext = p.next;
		
		// Re-link p's old neighboring positions (if not null)
		if (oldPrevious != null) { oldPrevious.next = oldNext; }
		if (oldNext != null) { oldNext.previous = oldPrevious; }
		
		// Move p to the front
		p.previous = this.tail.previous;
		p.next = this.tail;
		
		this.tail.previous.next = p;
		this.tail.previous = p;
	}
	
	public Position peekLastPosition() {
		return size == 0 ? null : this.tail.previous;
	}
	
	public T popFromBack() throws IllegalStateException {
		// Edge case: If PLL is empty, throw IllegalStateException
		if (this.size == 0) { 
			throw new IllegalStateException("Cannot pop from an empty PositionalLinkedList.");
		}
		
		// Save the position after the last non-dummy position
		Position oldBack = this.head.next;
		Position newBack = oldBack.next;
		
		// Get data from last non-dummy position
		T res = data.remove(oldBack);
		
		// Link new back with head
		newBack.previous = this.head;
		this.head.next = newBack;
		
		// Reduce size of PLL
		this.size--;
		
		return res;
	}
	
	public int size() { return size; }
	
	protected Map<Position, T> getData() { return this.data; }
	
	protected Position getHead() { return this.head; }
	
	public static class Position {
		
		public Position previous, next;
		
		public LocalDateTime lastAccess;
		
	}

}
