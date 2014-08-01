package com.joke.cache;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheScheduler {
	private static ReadWriteLock rwlock = new ReentrantReadWriteLock(false);

	//mode key 写入header
	private static HashMap<String, ReadWriteLock> rwModeLockMap = new HashMap<String, ReadWriteLock>();

	/**
	 * 
	 * @param modeKey
	 * @return
	 */
	public static ReadWriteLock getModeRWLock(String modeKey) {
		rwlock.readLock().lock();
		ReadWriteLock modeLock = rwModeLockMap.get(modeKey);
		if (modeLock == null) {
			rwlock.readLock().unlock();
			rwlock.writeLock().lock();

			modeLock = rwModeLockMap.get(modeKey);
			if (modeLock == null) {
				modeLock = new ReentrantReadWriteLock();
				rwModeLockMap.put(modeKey, modeLock);
			}
			rwlock.readLock().lock();
			rwlock.writeLock().unlock();
		}
		rwlock.readLock().unlock();

		return modeLock;
	}
}
