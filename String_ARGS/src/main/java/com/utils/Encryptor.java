package com.utils;

import org.apache.log4j.Logger;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.stereotype.Service;

/**
 * 
 * @(#) Encryptor.java v 0.1 Nov 18, 2010
 * 
 *      Copyright (c) 2010-2012 PurpleBits Infosystems Pvt. Ltd. Vadodara,
 *      Gujarat, India All rights reserved.
 * 
 *      This software is the confidential and proprietary information of
 *      PurpleBits Infosystems Pvt. Ltd. ("Confidential Information"). You shall
 *      not disclose such Confidential Information and shall use it only in
 *      accordance with the terms of the license agreement you entered into with
 *      PurpleBits.
 * 
 *      This class does encryption and decryption of all the passwords, database
 *      and pdf files. For pdf it uses itext library. For database and
 *      passwords, it uses jasypt library.
 * 
 * @author Deepak Gupta
 */
@Service
public class Encryptor {

	private final static Logger logger = Logger.getLogger(Encryptor.class.getName());

	private static final String PASSWORD_ENCRYPTION_KEY = "purpl3Plut0"; // Password

	/**
	 * Used to encrypt the password using jasypt
	 * 
	 * @param txtPasswd
	 *            - password that needs to be encrypted
	 * @return encrypted password
	 */
	public String encryptPassword(String txtPasswd) {

		try {
			logger.debug("Encryptor.encryptPassword");

			StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
			textEncryptor.setPassword(PASSWORD_ENCRYPTION_KEY);
			String ePasswd = textEncryptor.encrypt(txtPasswd);

			return ePasswd;
		} catch (Exception e) {
			logger.error("Encryptor.encryptPassword ", e);
			return null;
		}

	}

	/**
	 * Used to decrypts the existing encrypted password using jasypt
	 * 
	 * @param encryptedPasswd
	 *            - password in encrypted form
	 * @return normal string password
	 */
	public String decryptPassword(String encryptedPasswd) {

		try {
			logger.trace("Encryptor.decryptPassword");

			StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
			textEncryptor.setPassword(PASSWORD_ENCRYPTION_KEY);
			String decryptedPasswd = textEncryptor.decrypt(encryptedPasswd);
			return decryptedPasswd;
		} catch (Exception e) {
			logger.error("Encryptor.decryptPassword ", e);
			return null;
		}
	}

	public String generatePassword() {
		String value = null;
		char[] chars = new char[5];
		int i = Math.abs((int) Double.doubleToLongBits(Math.random() * 10000000));
		chars[0] = (char) (((i) % 26) + 97);
		i = Math.abs((int) Double.doubleToLongBits(Math.random() * 10000000));
		chars[1] = (char) (((i) % 26) + 97);
		i = Math.abs((int) Double.doubleToLongBits(Math.random() * 10000000));
		chars[2] = (char) (((i) % 26) + 97);
		i = Math.abs((int) Double.doubleToLongBits(Math.random() * 10000000));
		chars[3] = (char) (((i) % 26) + 97);
		i = Math.abs((int) Double.doubleToLongBits(Math.random() * 10000000));
		chars[4] = (char) (((i) % 26) + 97);
		i = Math.abs((int) Double.doubleToLongBits(Math.random() * 10000000));
		value = new String(chars) + Integer.toString((((int) i) % 100000));
		return value;
	}

}
