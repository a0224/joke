package com.joke.cache;

import java.util.concurrent.atomic.AtomicInteger;

public class CacheInfo {
	public static final class ExpireTime {
		public static final Integer UNEXPIRE_TIME = Integer.MAX_VALUE;
		public static final Integer SESSION_EXPIRE = 60 * 60 * 24;	
		public static final Integer AISLEEXPIRE = 60 * 3;
		
	}

	public static abstract class MExpire {
		private static int MAX_TIME_VALUE = Integer.MAX_VALUE - 1000;
		private int minTime = 60; //
		private int totalTime = 10 * 60; //
		private int inter = totalTime / 10; //
		private AtomicInteger increaceAtomic = new AtomicInteger(); // 原子计数器

		MExpire(int totalTime, int step) {
			updateExpireInfo(totalTime, step);
		}

		/**
		 * @param totolTime
		 * @param step
		 */
		public void updateExpireInfo(int totalTime, int step) {
			this.totalTime = totalTime;
			inter = this.totalTime / step;
			increaceAtomic.set(0);
		}

		public int nextExpireTime() {
			increaceAtomic.compareAndSet(MAX_TIME_VALUE, 0);
			int nextIncr = increaceAtomic.addAndGet(inter);
			int nextTime = nextIncr % totalTime + minTime;
			return nextTime;
		}
	}
}
