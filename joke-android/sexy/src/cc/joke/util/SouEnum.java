package cc.joke.util;

public enum SouEnum {
	// 关于推广位
	SOU_1(1, "内涵段子"), SOU_2(2, "糗事百科"), SOU_3(3, "百度新闻"), SOU_4(4, "腾讯新闻"), SOU_5(
			5, "网易新闻"), SOU_6(6, "360新闻"), SOU_7(7, "头条两性");

	private final Integer id;

	private final String name;

	SouEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static String getSourceName(int source) {
		String name = null;
		try {
			name = SouEnum.valueOf("SOU_" + source).getName();
		} catch (Exception e) {
			name = null;
		}
		return name;
	}
}
