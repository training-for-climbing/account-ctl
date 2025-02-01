package dev.hicksm.climbing.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import dev.hicksm.climbing.util.PositionalLinkedList.Position;

public class SessionHandler {
	
	private static String LIFESPAN_ENV_NAME = "ACCOUNTCTL_SESSION_LIFESPAN";
	
	private static SessionHandler instance;
	
	private int sessionLifespan;
	
	private PositionalLinkedList<Long> lru;
	
	private Map<Long, SecretKey> keyMap;
	
	private Map<Long, Position> positionMap;
	
	private SessionHandler(int sessionLifespan) {
		this.sessionLifespan = sessionLifespan;
		lru = new PositionalLinkedList<>();
		keyMap = new HashMap<>();
		positionMap = new HashMap<>();
	}
	
	private void clean() {
		LocalDateTime now = LocalDateTime.now();
		// long seconds = ChronoUnit.SECONDS.between(now, now);
		
		while (lru.size() > 0) {
			Position last = lru.peekLastPosition();
			long diff = ChronoUnit.SECONDS.between(last.lastAccess, now);
			if (diff >= sessionLifespan) {
				Long key = lru.popFromBack();
				keyMap.remove(key);
				positionMap.remove(key);
			}
			else break;
		}
	}
	
	public SecretKey getKey(Long sessionId) {
		clean();
		SecretKey res = keyMap.getOrDefault(sessionId, null);
		if (res != null)
			lru.moveToFront(positionMap.get(sessionId));
		return res;
	}
	
	public void setKey(Long sessionId, SecretKey key) {
		keyMap.put(sessionId, key);
		positionMap.put(sessionId, lru.insert(sessionId));
	}
	
	public Boolean containsSession(Long sessionId) {
		clean();
		return keyMap.containsKey(sessionId);
	}
	
	public static SessionHandler getInstance() {
		if (instance != null)
			return instance;
		
		Integer sessionLifespan = 10 * 60;  // default: 10 mins
		String sessionLifespanEnv = null;
		
		try {
			sessionLifespanEnv = System.getenv(LIFESPAN_ENV_NAME);
			sessionLifespan = Integer.parseInt(sessionLifespanEnv);
		} catch (NullPointerException e) {
			System.err.println("Unable to read " + LIFESPAN_ENV_NAME + " from environment variables");
		} catch (SecurityException e) {
			System.err.println("Unable to read environment variables due to permission issue");
		} catch (NumberFormatException e) {
			System.err.println("Unable to parse integer session lifespan from " + sessionLifespanEnv);
		} finally {
			System.out.println("Session lifespan: " + sessionLifespan + "s");
		}
		
		return getInstance(sessionLifespan);
	}
	
	protected static SessionHandler getInstance(int sessionLifespan) {
		instance = new SessionHandler(sessionLifespan);
		return instance;
	}
	
	protected static void reset() { instance = null; }

}
