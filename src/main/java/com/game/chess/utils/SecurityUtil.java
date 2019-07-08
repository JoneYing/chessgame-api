package com.game.chess.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description 加解密算法工具类
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
public class SecurityUtil {

	private static Base64 base64=new Base64();
	public static String encrypt(String text, String key) {
		String enc=null;
		try{
	        SecretKeySpec keySpec = new SecretKeySpec(getKey(key), "AES");
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
	        enc= base64.encodeAsString(cipher.doFinal(text.getBytes()));			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return enc;
	}
	
	public static String decrypt(String text, String key) {
		String enc=null;
		try{
	        SecretKeySpec aesKey = new SecretKeySpec(getKey(key), "AES");
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, aesKey);
	        enc= new String(cipher.doFinal(base64.decode(text)));			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return enc;
	}
	
	private static byte[] getKey(String key) {
		byte[] bytes=key.trim().getBytes();
		if(bytes.length<16){
			return StringUtils.rightPad(key, 16).getBytes();
		}else{
			byte[] _key=new byte[16];
			System.arraycopy(bytes, 0, _key, 0, 16);
			return _key;
		}		
	}
	
	public static String md5(String text, String key){
		return DigestUtils.md5Hex(text+key);
	}
}