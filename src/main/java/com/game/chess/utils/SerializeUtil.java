package com.game.chess.utils;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
 * @Description 序列化工具类
 *
 * @author YingJH
 * @Date 2018年3月14日
 * @version v1.1
 */
public class SerializeUtil {

	private static RedisSerializer<String> keySerializer = new StringRedisSerializer();
	private static RedisSerializer<Object> valSerializer = new JdkSerializationRedisSerializer();
	
	public static byte[] keySerializer(String key){
		 return keySerializer.serialize(key);
	}
	
	public static String keyDeserialize(byte[] key){
		 return keySerializer.deserialize(key);
	}
	
	public static byte[] valSerializer(Object val){
		return valSerializer.serialize(val);
	}
	
	public static Object valDeserialize(byte[] val){
		return valSerializer.deserialize(val);
	}
	
}
