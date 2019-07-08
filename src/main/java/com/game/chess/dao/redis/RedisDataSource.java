package com.game.chess.dao.redis;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {
	
    public abstract ShardedJedis getRedisClient();
    
}

