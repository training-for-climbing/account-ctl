package dev.hicksm.climbing.util;

import java.util.Map;

import javax.crypto.SecretKey;

public class SessionHandler {
	
	private static SessionHandler instance;
	
	private int sessionLifespan;
	
	private PositionalLinkedList<Long> lru;
	
	protected Map<Long, SecretKey> keyMap;
	
	private SessionHandler(int sessionLifespan) {
		// TODO: Implement stub
	}
	
	public SecretKey getKey(Long sessionId) {
		// TODO: Implement stub
		return null;
	}
	
	public void setKey(Long sessionId, SecretKey key) {
		// TODO: Implement stub
	}
	
	public static SessionHandler getInstance() {
		// TODO: Implement stub
		return null;
	}
	
	protected static SessionHandler getInstance(int sessionLifespan) {
		// TODO: Implement stub
		return null;
	}
	
	protected static void reset() { instance = null; }

}
