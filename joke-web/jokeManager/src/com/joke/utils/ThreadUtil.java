package com.joke.utils;

public class ThreadUtil extends Thread {
	private String name;

	public ThreadUtil(String name) {
		super(name);
		this.name = name;
	}

	public void run() {
		try {
			if ("loadBlack".equals(this.name)) {
			} else if ("loadAisle".equals(this.name)) {
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Thread getThread(String name) {
		return new ThreadUtil(name);
	}

	public static void main(String[] args) {
		// Thread t1 = new ThreadUtil("阿三");
		// Thread t2 = new ThreadUtil("李四");
		// t1.start();
		// t2.start();
	}
}
