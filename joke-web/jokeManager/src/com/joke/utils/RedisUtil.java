package com.joke.utils;

import com.joke.cache.RedisCache;

public class RedisUtil {
	public static RedisCache redisCache;

	public static RedisCache getRedisCache() {
		if (redisCache != null) {
			return redisCache;
		} else {
			return new RedisCache();
		}
	}
}
