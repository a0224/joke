/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * FileName: BlockEnum.java
 * Author:   zhuheng
 * Date:     2014年5月4日 上午10:26:05
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.joke.bean;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author zhuheng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum SourceEnum {
	// 关于推广位
	nhdz(1, "内涵段子"), xsbk(2, "糗事百科"), bdxw(3, "百度新闻"), txxx(4, "腾讯新闻"), wyxx(5,
			"网易新闻"), qh360xw(6, "360新闻");
	

	private final Integer id;
	private final String name;

	SourceEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public static void main(String[] args) {
		for (SourceEnum s : SourceEnum.values())
			System.out.println(s.getId() + ", SourceEnum " + s.getName());
		System.out.println(SourceEnum.valueOf("nhdz").getName());
		
	}

}
