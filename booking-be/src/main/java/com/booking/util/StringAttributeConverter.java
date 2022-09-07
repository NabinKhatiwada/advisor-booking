package com.booking.util;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.booking.exception.BadRequestException;

@Component
public class StringAttributeConverter implements AttributeConverter<String, String> {

	private static final Logger logger = LogManager.getLogger(StringAttributeConverter.class);
	private static final String AES = "AES";
	private static final byte[] encryptionKey = "9844601922-Nabin".getBytes();

	private static Cipher encryptCipher;
	private final Cipher decryptCipher;

	public StringAttributeConverter() throws Exception {
		Key key = new SecretKeySpec(encryptionKey, AES);
		encryptCipher = Cipher.getInstance(AES);
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		decryptCipher = Cipher.getInstance(AES);
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	public static String encodeString(String stringToEncode) {
		try {
			return Base64.getEncoder().encodeToString(encryptCipher.doFinal(stringToEncode.getBytes()));
		} catch (Exception e) {
			logger.log(Level.ERROR, "Error! while encoding string %s", e.getMessage());
			throw new BadRequestException("Encryption Error");
		}
	}

	@Override
	public String convertToDatabaseColumn(String attribute) {
		if (!StringUtils.hasText(attribute)) {
			return null;
		}
		try {
			return Base64.getEncoder().encodeToString(encryptCipher.doFinal(attribute.getBytes()));
		} catch (Exception e) {
			logger.log(Level.ERROR, "Error! while decoding string" + e.getMessage());
			throw new BadRequestException("Decryption Error");
		}
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		if (!StringUtils.hasText(dbData)) {
			return null;
		}
		try {
			return new String(decryptCipher.doFinal(Base64.getDecoder().decode(dbData)));
		} catch (Exception e) {
			logger.log(Level.ERROR, "Error! while decoding string" + e.getMessage());
			throw new BadRequestException("Decryption Error");
		}
	}
}
