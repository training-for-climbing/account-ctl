package dev.hicksm.climbing.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PositionalLinkedList<T> {
	
	private Position head, tail;
	
	private Map<Position, T> data;
	
	private int size;
	
	public PositionalLinkedList() {
		this.head = null;
		this.tail = null;
		this.data = new HashMap<Position, T>();
		this.size = 0;
	}
	
	public Position insert(T t) {
		// Create a new position and assign data to it
		Position newHead = new Position();
		this.data.put(newHead, t);
		this.size++;
		
		// Edge case: If newHead is the first position, make it the head and tail
		if (this.head == null) {
			this.head = newHead;
			this.tail = newHead;
		}
		
		// Move the new position to the front
		this.moveToFront(newHead);
		
		return this.head;
	}
	
	public void moveToFront(Position p) {
		// Set lastAccess to current date-time
		p.lastAccess = LocalDateTime.now();
		
		// Edge case: p is already in the front
		if (p == this.head) {
			return;
		}
		
		// Re-link the positions before and after p
	    if (p.previous != null) {
	        p.previous.next = p.next;
	    }
	    if (p.next != null) {
	        p.next.previous = p.previous;
	    }

	    // Edge case: if p is the only node in the list
	    if (this.head == null) {
	        this.head = p;
	        this.tail = p;
	        p.previous = null;
	        p.next = null;
	    } else {
	        // Move p to the front
	        p.next = this.head;
	        this.head.previous = p;
	        p.previous = null;
	        this.head = p;
	    }
	}
	
	public Position peekLastPosition() {
		return this.head;
	}
	
	public T popFromBack() {
		// Edge case: tail does not exist
		if (this.tail == null) {
			return null;
		}
		
		// Get data from tail
		T res = this.data.get(this.tail);
		
		// Delete tail
		Position newTail = this.tail.next;
		data.remove(this.tail);
		this.tail = newTail;
		this.size--;
		
		return res;
	}
	
	public int size() { return size; }
	
	public static class Position {
		
		public Position previous, next;
		
		public LocalDateTime lastAccess;
		
	}

}
