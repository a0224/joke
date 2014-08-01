package com.joke.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClientFactory {
	private static volatile ShardedJedisPool shardedRedisPool;

	//
	public static List<JedisShardInfo> shardInfoList;
	private static JedisPoolConfig poolConfig = new JedisPoolConfig();

	static {
		
		poolConfig.setTestOnReturn(true);
		shardInfoList = new ArrayList<JedisShardInfo>();

		try {
			Properties pro = new Properties();
			pro.load(RedisCache.class.getResourceAsStream("jedis.pro"));

			JedisShardInfo shardInfo = null;
			Set<Object> keySet = pro.keySet();
			for (Object key : keySet) {
				if (key.equals("maxActive")) {
					poolConfig.setMaxActive(Integer.parseInt(pro
							.getProperty(key.toString())));

				} else if (key.equals("maxIdle")) {
					poolConfig.setMaxIdle(Integer.parseInt(pro.getProperty(key
							.toString())));

				} else if (key.equals("minIdle")) {
					poolConfig.setMinIdle(Integer.parseInt(pro.getProperty(key
							.toString())));

				} else if (key.equals("maxWait")) {
					poolConfig.setMaxWait(Integer.parseInt(pro.getProperty(key
							.toString())));

					// ShardedJedisPool
					// redis.clients.util.RedisOutputStream.write(RedisOutputStream.java:35)
				} else if (key.equals("testOnReturn")) {
					poolConfig.setTestOnReturn(Boolean.valueOf(pro
							.getProperty(key.toString())));

				} else if (key.equals("shared")) {
					String[] shardedList = pro.getProperty("shared").split(";");
					String[] values = null;
					for (String server : shardedList) {
						values = server.split(":");
						shardInfo = new JedisShardInfo(values[0],
								Integer.parseInt(values[1]));
						if (values.length == 3) {
							shardInfo.setPassword(values[2]);
						}
						shardInfoList.add(shardInfo);
					}
				}
			}

			

			//System.out.println("RedisCache use config jedis.pro");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private RedisClientFactory() {

	}

	public static ShardedJedisPool getInstance() {
		if (shardedRedisPool == null) {
			synchronized (RedisClientFactory.class) {
				if (shardedRedisPool == null) {
					shardedRedisPool = new ShardedJedisPool(poolConfig, shardInfoList);
				}
			}
		}
		return shardedRedisPool;
	}
}
