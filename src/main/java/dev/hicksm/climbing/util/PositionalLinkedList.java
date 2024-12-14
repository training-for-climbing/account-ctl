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
		
		// If newHead is the first position, make it the head and tail
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
		if (p.next == null) {
			return;
		}
		
		// Re-link the positions before and after p
		if (p.previous != null) {
			p.previous.next = p.next;
		}
		p.next.previous = p.previous;
		
		// Move p to the front
		this.head.next = p;
		p.previous = this.head;
		p.next = null;
		
		// Make p the new head
		this.head = p;
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
