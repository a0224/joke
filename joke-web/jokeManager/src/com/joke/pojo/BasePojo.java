package com.joke.pojo;

import java.io.Serializable;

public class BasePojo implements Serializable {
	/** serialVersionUID */
	/***
	 * 管理员 开发者 渠道商 通道商 子渠道商 运维 商务 财务
	 */
	public static final Integer[] roleNum = new Integer[] { 1, 3, 5, 8, 4, 2,
			6, 7 };
}
