package dev.hicksm.climbing.util;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.hicksm.climbing.util.PasswordHasher.HashedPassword;

public class PasswordHasherTest {
	
	/*
		This test checks that, without a salt, a password will be hashed.
	*/
	@Test
	void testHashPassword1() {
		String s = "donnahaditcoming";
		
		HashedPassword res = PasswordHasher.hashPassword(s, null);
		
		Assertions.assertNotEquals(s, res.hashedPassword);
	}
	
	/*
		This test checks that two passwords are hashed to separate values.
	*/
	@Test
	void testHashPassword2() {
		String s1 = "donnahaditcoming", s2 = "iloveyou";
		
		HashedPassword r1 = PasswordHasher.hashPassword(s1, null), 
				r2 = PasswordHasher.hashPassword(s2, null);
		
		Assertions.assertNotEquals(r1.hashedPassword, r2.hashedPassword);
	}
	
	/*
		This test checks that, given a different salt, the same password 
		will be hashed to two different values.
	*/
	@Test
	void testHashPassword3() {
		String s = "donnahaditcoming";
		String salt1 = "o78chasd", salt2 = "89c7v ";
		
		HashedPassword r1 = PasswordHasher.hashPassword(s, salt1),
				r2 = PasswordHasher.hashPassword(s, salt2);
		
		Assertions.assertNotEquals(r1.hashedPassword, r2.hashedPassword);
	}
	
	/*
		This test checks that, given the same salt, the same password will 
		be hashed the same way.
	*/
	@Test
	void testHashPassword4() {
		String s = "donnahaditcoming", salt = "98vdfsa";
		
		HashedPassword r1 = PasswordHasher.hashPassword(s, salt),
				r2 = PasswordHasher.hashPassword(s, salt);
		
		Assertions.assertEquals(r1.hashedPassword, r2.hashedPassword);
	}
	
	/*
		This test checks that hashing with a null password is the same as 
		hashing with an empty string.
	*/
	@Test
	void testHashPassword5() {
		String s = "donnahaditcoming";
		
		HashedPassword r1 = PasswordHasher.hashPassword(s, null),
				r2 = PasswordHasher.hashPassword(s, "");
		
		Assertions.assertEquals(r1.hashedPassword, r2.hashedPassword);
	}
	
	/*
		This test verifies that a null password cannot be passed to the hasher.
	*/
	@Test
	void testHashPassword6() {
		try {
			PasswordHasher.hashPassword(null, null);
			Assertions.fail("Password hasher allowed null password");
		} catch (IllegalArgumentException e) {}
	}
	
	/*
		This test verifies that two different passwords with two different salts 
		will hash to different values.
	*/
	@Test
	void testHashPassword7() {
		String s1 = "donnahaditcoming", s2 = "iloveyou";
		String salt1 = "8790sad", salt2 = "v6as834d";
		
		HashedPassword r1 = PasswordHasher.hashPassword(s1, salt1),
				r2 = PasswordHasher.hashPassword(s2, salt2);
		
		Assertions.assertNotEquals(r1.hashedPassword, r2.hashedPassword);
	}
	
	/*
		This test verifies that 2^16 salts can be generated without any collisions.
	*/
	@Test
	void testGenerateSalt() {
		long saltCount = (long) Math.floor(Math.pow(2, 16));
		
		Set<String> salts = new HashSet<>();
		
		for (int i = 0; i < saltCount; i++) {
			salts.add(PasswordHasher.generateSalt());
		}
		
		Assertions.assertEquals(saltCount, salts.size());
	}
	
	/*
		Simple use-case oriented test for PasswordHasher: hashing two different 
		passwords with two different salts.
	*/
	@Test 
	void testPasswordHasher() {
		String s1 = "iloveyou", s2 = "donnahaditcoming";
		String salt1 = PasswordHasher.generateSalt(), salt2 = PasswordHasher.generateSalt();
		
		HashedPassword r1 = PasswordHasher.hashPassword(s1, salt1),
				r2 = PasswordHasher.hashPassword(s2, salt2);
		
		Assertions.assertNotEquals(r1.hashedPassword, r2.hashedPassword);
	}

}
