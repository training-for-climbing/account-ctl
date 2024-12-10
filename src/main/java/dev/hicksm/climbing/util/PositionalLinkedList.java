package dev.hicksm.climbing.util;

import java.time.LocalDateTime;
import java.util.Map;

public class PositionalLinkedList<T> {
	
	private Position head, tail;
	
	private Map<Position, T> data;
	
	private int size;
	
	public PositionalLinkedList() {
		// TODO: Implement stub
	}
	
	public Position insert(T t) {
		// TODO: Implement stub
		return null;
	}
	
	public void moveToFront(Position p) {
		// TODO: Implement stub
	}
	
	public Position peekLastPosition() {
		// TODO: Implement stub
		return null;
	}
	
	public T popFromBacck() {
		// TODO: Implement stub
		return null;
	}
	
	public int size() { return size; }
	
	public class Position {
		
		public Position previous, next;
		
		public LocalDateTime lastAccess;
		
	}

}
