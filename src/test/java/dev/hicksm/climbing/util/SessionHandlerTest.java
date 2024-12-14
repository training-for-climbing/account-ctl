package dev.hicksm.climbing.util;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionHandlerTest {
	
	/* AES key generator. */
	private KeyGenerator gen;
	
	public SessionHandlerTest() {
		try {
			gen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gen.init(256);
	}
	
	/*
		This protected method sets the SessionHandler singleton to null.
	*/
	@BeforeEach
	void resetSessionHandler() { SessionHandler.reset(); }
	
	/*
		This test verifies that there is only one instance of SessionHandler.
		
		Since account-ctl is designed to be single-threaded as part of a larger 
		container structure, we can utilize a singleton more effectively than 
		in a large, multi-processed monolith.
	*/
	@Test
	void testGetInstance() {
		SessionHandler i1 = SessionHandler.getInstance(),
				i2 = SessionHandler.getInstance();
		Assertions.assertTrue(i1 == i2);
	}
	
	/*
		If a key for a given session does not exist, this method returns null.
		
		This behavior should be paired with a method used to check if the 
		session actually exists.
	*/
	@Test
	void testGetKey() {
		SessionHandler sh = SessionHandler.getInstance();
		Assertions.assertNull(sh.getKey(132l));
	}
	
	/*
		Tests that a key can be set, and then retrieved.
	*/
	@Test
	void testSessionHandler1() {
		Long id = 132l;
		SecretKey k = gen.generateKey();
		
		SessionHandler sh = SessionHandler.getInstance();
		
		// Assert that there is no key assigned to ID
		Assertions.assertNull(sh.getKey(id));
		
		// Assign key to ID
		sh.setKey(id, k);
		
		// Validate key assignment
		SecretKey res = sh.getKey(id);
		Assertions.assertNotNull(res);
		Assertions.assertEquals(k, res);
	}
	
	/*
		Tests that a key can be overwritten.
		
		This is largely a fringe case that isn't planned to be used in 
		the real system.
	*/
	@Test
	void testSessionHandler2() {
		Long id = 132l;
		SecretKey k1 = gen.generateKey(), k2;
		do {
			k2 = gen.generateKey();
		} while (k1 == k2);
		
		SessionHandler sh = SessionHandler.getInstance();
		
		// Assign key to ID
		sh.setKey(id, k1);
		
		// Validate key assignment
		SecretKey res1 = sh.getKey(id);
		Assertions.assertNotNull(res1);
		Assertions.assertEquals(k1, res1);
		Assertions.assertNotEquals(k2, res1);
		
		// Assign new key to ID
		sh.setKey(id, k2);
		
		// Validate key assignment
		SecretKey res2 = sh.getKey(id);
		Assertions.assertNotNull(res2);
		Assertions.assertNotEquals(k1, res2);
		Assertions.assertEquals(k2, res2);
	}
	
	/*
		Tests that, after expiration, a key will no longer be accessible.
		
		This ensure that the system does not become too clogged, and is built around 
		the use of a PositionalLinkedList for tracking of least recently used sessions.
		
		This also ensures that sessions do not stay alive for too long, which is good 
		in order to avoid security risks that occur with keys floating around.
		
		Session lifespan is parameterized in seconds.
	*/
	@Test
	void testSessionHandler3() {
		Long id = 132l;
		SecretKey k = gen.generateKey();
		
		SessionHandler sh = SessionHandler.getInstance(1);
		sh.setKey(id, k);
		
		try {
			// Wait for 1.05s
			Thread.sleep(1050);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assertions.fail("Could not sleep thread. See stack trace.");
		}
		
		Assertions.assertNull(sh.getKey(id));
		Assertions.assertNotEquals(k, sh.getKey(id));
	}
	
	/*
		Asserts that, if set to null, a session's key will not be removed.
		
		The SessionHandler behaves this way so that a session can be unencrypted, 
		a feature which will primarily be used in development.
		
		This may later be refactored into a test for a method such as
		`SessionHandler::containsSession(Long)`.
	*/
	@Test
	void testSetKey() {
		Long id = 132l;
		SecretKey k = gen.generateKey();
		
		SessionHandler sh = SessionHandler.getInstance();
		sh.setKey(id, k);
		
		sh.setKey(id, null);
		
		Assertions.assertTrue(sh.keyMap.containsKey(id));
		Assertions.assertNull(sh.getKey(id));
	}

}
