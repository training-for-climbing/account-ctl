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
		// Create a new position in front of head, then reassign head
		Position newHead = new Position();
		if (this.head != null) {
			newHead.previous = this.head;
			this.head.next = newHead;
		}
		this.head = newHead;
		
		// Insert data at new head
		this.data.put(this.head, t);
		this.size++;
		return this.head;
	}
	
	public void moveToFront(Position p) {
		// TODO: Implement stub
	}
	
	public Position peekLastPosition() {
		// TODO: Implement stub
		return null;
	}
	
	public T popFromBack() {
		// TODO: Implement stub
		return null;
	}
	
	public int size() { return size; }
	
	public static class Position {
		
		public Position previous, next;
		
		public LocalDateTime lastAccess;
		
	}

}
