package com.joke.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisCache {
	private final static Logger logger = LoggerFactory
			.getLogger(RedisCache.class);

	private ShardedJedisPool shardedRedisPool;

	//
	private List<JedisShardInfo> shardInfoList;

	public void initRedisCache() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
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
			shardedRedisPool = new ShardedJedisPool(poolConfig, shardInfoList);
			logger.info("RedisCache use config jedis.pro");

		} catch (Exception e) {
			logger.error("initRedisCache", e);
		}
	}

	/**
	 * jedis
	 * 
	 * @return
	 */
	public ShardedJedis getShardedJedis() {
		ShardedJedis jedis = null;
		try {
			jedis = shardedRedisPool.getResource();
		} catch (Exception e) {
			logger.error("getShardedJedis", e);
		}
		return jedis;
	}

	/**
	 * 
	 * @param jedis
	 */
	public void backShardedJedis(ShardedJedis jedis) {
		try {
			if (jedis != null) {
				if (shardedRedisPool != null) {
					shardedRedisPool.returnResource(jedis);
				} else {
					jedis.disconnect();
				}
			}
		} catch (Exception e) {
			logger.error("backShardedJedis", e);
		}
	}

	/**
	 * 
	 * @param keyPattern
	 * 
	 */
	public String deleteKeys(String keyPattern) {
		StringBuilder sb = new StringBuilder(
				"\n***********************************\nadmin op: deleteKeys - keyPattern: "
						+ keyPattern + "\n");
		if (shardInfoList != null) {
			sb.append("redis server count: " + shardInfoList.size() + "\n");
			Jedis jedis = null;
			for (JedisShardInfo shardInfo : shardInfoList) {
				try {
					sb.append("handle redis server: " + shardInfo.getHost()
							+ " prot: " + shardInfo.getPort() + "\n");
					jedis = new Jedis(shardInfo.getHost(), shardInfo.getPort());
					jedis.auth(shardInfo.getPassword());
					jedis.connect();
					Set<String> keySet = jedis.keys(keyPattern);
					sb.append("find keys: " + keySet.size() + "\n");

					if (keySet.size() > 0) {
						jedis.del(keySet.toArray(new String[0]));
					}

				} catch (Exception e) {
					logger.error("deleteKeys", e);

				} finally {
					try {
						jedis.disconnect();
					} catch (Exception e2) {
						logger.error("deleteKeys", e2);
					}
				}
				sb.append("delete keys over.\n----------------------------\n");
			}

		} else {
			sb.append("none redis server info.\n");
		}

		sb.append("***********************************\n");
		return sb.toString();
	}

	public boolean set(String key, Object value, Integer expiry) {
		ShardedJedis jedis = null;
		try {
			jedis = getShardedJedis();
			jedis.setex(key, expiry, value.toString());
			return true;
		} catch (Exception e) {
			logger.error("set", e);
		} finally {
			backShardedJedis(jedis);
		}
		return false;
	}

	public Object get(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getShardedJedis();
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("get", e);
		} finally {
			backShardedJedis(jedis);
		}
		return null;
	}

	public boolean remove(String key) {
		try {
			deleteKeys(key);
			return true;
		} catch (Exception e) {
			logger.error("remove", e);
		}
		return false;
	}

	public byte[] get(byte[] key) {
		ShardedJedis jedis = null;
		try {
			jedis = getShardedJedis();
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("get", e);
		} finally {
			backShardedJedis(jedis);
		}
		return null;
	}

	public boolean set(byte[] key, byte[] value, Integer seconds) {
		ShardedJedis jedis = null;
		try {
			jedis = getShardedJedis();
			jedis.setex(key, seconds, value);
			return true;
		} catch (Exception e) {
			logger.error("set", e);
		} finally {
			backShardedJedis(jedis);
		}
		return false;
	}
}
