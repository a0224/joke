package com.joke.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public final static String STR_PATTERN = "\\$\\{([^}]+)}";

	/**
	 * 换行或者回车
	 */
	public final static Pattern newlinePattern = Pattern.compile("\r|\n");

	/**
	 * �������ַ���ƥ����ַ��滻��ָ�����ַ�
	 * 
	 * @param inStr
	 * @param patternStr
	 * @param targetValue
	 * @return
	 */
	public static String replaceByPattern(String inStr, String patternStr,
			String targetValue) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(inStr);
		if (matcher.find()) {
			inStr = matcher.replaceAll(targetValue);
		}
		return inStr;
	}

	/**
	 * �ж��ַ��Ƿ�ƥ
	 */
	public static boolean isEcho(String inStr, String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(inStr);
		return matcher.matches();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List list) {
		return list == null || list.isEmpty();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Map map) {
		return map == null || map.isEmpty();
	}

	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}

	public static String lpad(String str, int len, String pad) {
		String ret = "";
		int i = str.getBytes().length;
		if (i > len)
			return str;
		// return str.substring(0,len);
		while (i++ < len) {
			ret += pad;
		}
		ret += str;
		return ret;
	}

	public static String rpad(String str, int len, String pad) {
		int i = str.getBytes().length;
		if (i > len)
			return str;
		while (i++ < len) {
			str += pad;
		}
		return str;
	}

	/**
	 * ҳ�������ӵĿո���
	 */
	public static void appendSpace(StringBuffer sb, int mount) {
		if (mount < 1)
			return;
		while (mount-- > 0) {
			sb.append("&nbsp;");
		}
	}

	public static String transformNvl(Object obj, String defaultValue) {
		if (obj == null)
			return defaultValue;
		return String.valueOf(obj);
	}

	public static String emptyStr(String str) {
		return str == null ? "" : str;
	}

	/**
	 * �ж��ַ��Ƿ�Ϊnull��մ�
	 */
	public static boolean isNullOrBlank(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNullOrBlank(Object str) {
		if (str == null || str.toString().trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ���ַ�ת����UTF-8���ַ�
	 */
	public static String encodeStr(String str) {
		return encodeByCharset(str, "UTF-8");
	}

	/**
	 * ���ַ�ת����ָ���ı��뷽ʽ���ַ�
	 * 
	 * @param str
	 *            -��Ҫת������ַ�
	 * @param chartset
	 *            -���뷽ʽ��Ĭ��Ϊ��UTF-8��
	 */
	public static String encodeByCharset(String str, String chartset) {
		if (isNullOrBlank(str)) {
			return "";
		}
		if (isNullOrBlank(chartset)) {
			chartset = "UTF-8";
		}
		String rString = "";
		try {
			rString = new String(str.getBytes(), chartset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// System.out.println("====����ǰ�ַ�=" + str + " =������ַ�==" + rString);
		return rString;
	}

	/***************************************************************************
	 * ʹ��cycChar�����strSet���������ÿ���ַ�ʹ��joinChar������strSet����������ַ�
	 * 
	 * @param strSet
	 *            -���Ҫ������ַ���
	 * @param cycChar
	 *            -����ַ���Χ���ַ�
	 * @param joinChar
	 *            -�����ַ���ַ�
	 * @return
	 */
	public static String setToStr(Set<String> strSet, String cycChar,
			String joinChar) {
		if (strSet.size() == 0) {
			return "";
		}
		String cc = "";
		if (!isNullOrBlank(cycChar)) {
			cc = cycChar;
		}
		StringBuilder sBuilder = new StringBuilder();
		for (String str : strSet) {
			sBuilder.append(cc).append(str).append(cc).append(joinChar);
		}
		sBuilder.delete(sBuilder.lastIndexOf(joinChar), sBuilder.length());
		return sBuilder.toString();
	}

	/***************************************************************************
	 * ʹ��joinChar������strSet����������ַ�
	 * 
	 * @param strSet
	 *            -���Ҫ������ַ���
	 * @param joinChar
	 *            -�����ַ���ַ�
	 * @param cycChar
	 *            -����ַ���Χ���ַ�
	 * @return
	 */
	public static String setToStr(Set<String> strSet, String joinChar) {
		return setToStr(strSet, null, joinChar);
	}

	/**
	 * ������ת�����ַ�
	 * 
	 * @param strs
	 *            -���Ҫ������ַ�����
	 * @param joinChar
	 *            -�����ַ���ַ�
	 * @return
	 */
	public static String arrayToStr(String[] strs, String joinChar) {
		return arrayToStr(strs, null, joinChar);
	}

	/**
	 * ������ת�����ַ�
	 * 
	 * @param strs
	 *            -���Ҫ������ַ�����
	 * @param joinChar
	 *            -�����ַ���ַ�
	 * @param cycChar
	 *            -����ַ���Χ���ַ�
	 * @return
	 */
	public static String arrayToStr(String[] strs, String cycChar,
			String joinChar) {
		String cc = "";
		if (!isNullOrBlank(cycChar)) {
			cc = cycChar;
		}
		StringBuilder sBuilder = new StringBuilder();
		for (String str : strs) {
			sBuilder.append(cc).append(str).append(cc).append(joinChar);
		}
		sBuilder.delete(sBuilder.lastIndexOf(joinChar), sBuilder.length());
		return sBuilder.toString();
	}

	public static String listToStr(List<Integer> strs, String cycChar,
			String joinChar) {
		String cc = "";
		if (null == strs || strs.size() < 1) {
			return cc;
		}
		if (!isNullOrBlank(cycChar)) {
			cc = cycChar;
		}
		StringBuilder sBuilder = new StringBuilder();
		for (Integer str : strs) {
			sBuilder.append(cc).append(str).append(cc).append(joinChar);
		}
		sBuilder.delete(sBuilder.lastIndexOf(joinChar), sBuilder.length());
		return sBuilder.toString();
	}

	/**
	 * ����null�ַ�
	 */
	public static String getDealStr(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * ��ָ������ת��ΪMap(���䣩
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map objReflectToMap(Object obj) {
		Map hashMap = new HashMap();
		try {
			Class c = obj.getClass();
			Method m[] = c.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().indexOf("get") == 0) {
					if (m[i].getReturnType().toString().indexOf("java") != -1)
						hashMap.put(m[i].getName().substring(3).toLowerCase(),
								m[i].invoke(obj, new Object[0]));
				}
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
		return hashMap;
	}

	/**
	 * ������10001110011100111100000001���ַ�ת����Ϊ��Ӧ��FN�б�
	 * 
	 * @param str
	 *            -����10001110011100111100000001���ַ�
	 * @return List<String>
	 */
	public static List<String> convertToFnList(String str) {
		List<String> fnList = new ArrayList<String>();
		char[] tempChars = str.toCharArray();
		int index = 1;
		for (char c : tempChars) {
			if (new Character(c).toString().equals("1")) {
				fnList.add(String.valueOf(index));
			}
			index++;
		}
		return fnList;
	}

	/**
	 * ������89,90,91,92,93��FN�ַ�ת��Ϊ00011011101�ĸ�ʽ(89,90,91,92,93��û�д�С˳��Ҳû
	 * ��ϵ���н�������)
	 * 
	 * @param fnStr
	 *            -����89,90,91,92,93��FN�ַ�ע�������û�С�,����
	 * @return String-Ϊ00011011101�ĸ�ʽ���ַ�
	 */
	public static String convertFnStr(String fnStr) {
		String[] fns = fnStr.split(",");
		int max = 0;
		int temp = 0;
		Set<Integer> tempSet = new HashSet<Integer>();
		for (int i = 0; i < fns.length; i++) {
			temp = Integer.valueOf(fns[i]);
			if (temp > max) {
				max = temp;
			}
			tempSet.add(temp);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < max; i++) {
			if (tempSet.contains(i + 1)) {
				sb.append("1");
			} else {
				sb.append("0");
			}
		}
		return sb.toString();
	}

	/**
	 * ��ָ������ת��ΪMap(���䣩����д)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map objReflectToMapToUpper(Object obj) {
		Map hashMap = new HashMap();
		try {
			Class c = obj.getClass();
			Method m[] = c.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().indexOf("get") == 0) {
					if (m[i].getReturnType().toString().indexOf("java") != -1)
						hashMap.put(m[i].getName().substring(3).toUpperCase(),
								m[i].invoke(obj, new Object[0]));
				}
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
		return hashMap;
	}

	/**
	 * 编码为了url
	 */
	public static String decodeContentForUrl(String content) {

		try {
			return (content == null ? "" : URLDecoder.decode(content, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 清楚新的换行符号
	 */
	public static String clearNewLine(String oldStr) {
		if (oldStr != null) {
			oldStr = newlinePattern.matcher(oldStr).replaceAll("");
		}

		return oldStr;
	}

	/**
	 * 清楚新的换行符号
	 */
	public static String subString(String oldStr, int length) {
		if (oldStr != null && oldStr.length() > length) {
			oldStr = oldStr.substring(0, length);
		}

		return oldStr;
	}

	/**
	 * 
	 * @param file
	 * @return .txt
	 */
	public static String getExtension(String file) {
		String extension = null;
		int i = file.lastIndexOf(".");
		if (i > 0 && i < file.length() - 1) {
			extension = file.substring(i).toLowerCase();
		}
		return extension;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List resultSetToList(ResultSet rs)
			throws java.sql.SQLException {
		if (rs == null)
			return Collections.EMPTY_LIST;
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		List list = new ArrayList();
		Map rowData = new HashMap();
		while (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(rowData);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map resultSetToMap(ResultSet rs) throws java.sql.SQLException {
		Map rowData = null;
		if (rs == null)
			return rowData;
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		if (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
		}
		return rowData;
	}

	public static String getProvince(String address) {
		String[] provinces = { "北京", "天津", "河北", "山西", "内蒙古", "辽宁", "吉林",
				"黑龙江", "上海", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北",
				"湖南", "广东", "广西", "海南", "重庆", "四川", "贵州", "云南", "西藏", "陕西",
				"甘肃", "青海", "宁夏", "新疆", "台湾", "香港", "澳门" };
		for (String s : provinces) {
			if (address.contains(s)) {
				return s;
			}
		}

		return address;
	}

	public static String getCity(String address) {
		String[] provinces = { "北京", "天津", "邢台", "衡水", "保定", "沧州", "唐山", "承德",
				"石家庄", "秦皇岛", "张家口", "邯郸", "廊坊", "临汾", "长治", "忻州", "太原", "晋城",
				"朔州", "运城", "吕梁", "晋中", "大同", "阳泉", "呼伦贝尔", "鄂尔多斯", "呼和浩特",
				"巴彦淖尔", "赤峰", "乌兰察布", "包头", "通辽", "兴安", "阿拉善", "锡林郭勒", "乌海",
				"抚顺", "丹东", "本溪", "营口", "朝阳", "铁岭", "辽阳", "盘锦", "大连", "锦州",
				"葫芦岛", "沈阳", "鞍山", "阜新", "珲春", "通化", "延边", "辽源", "长春", "松原",
				"白城", "吉林", "白山", "四平", "牡丹江", "大庆", "佳木斯", "大兴安岭", "双鸭山",
				"七台河", "黑河", "哈尔滨", "齐齐哈尔", "鸡西", "伊春", "鹤岗", "绥化", "上海", "南通",
				"镇江", "扬州", "常州", "淮安", "盐城", "无锡", "苏州", "连云港", "宿迁", "南京",
				"徐州", "泰州", "绍兴", "丽水", "嘉兴", "舟山", "宁波", "杭州", "衢州", "金华",
				"湖州", "台州", "温州", "巢湖", "合肥", "芜湖", "滁州", "阜阳", "宣城", "蚌埠",
				"六安", "池州", "宿州", "黄山", "淮北", "安庆", "铜陵", "亳州", "淮南", "马鞍山",
				"泉州", "莆田", "三明", "南平", "龙岩", "福州", "宁德", "厦门", "漳州", "南昌",
				"宜春", "上饶", "九江", "新余", "赣州", "鹰潭", "萍乡", "抚州", "吉安", "景德镇",
				"聊城", "日照", "莱芜", "济南", "潍坊", "淄博", "滨州", "济宁", "烟台", "泰安",
				"东营", "威海", "青岛", "德州", "菏泽", "临沂", "枣庄", "驻马店", "南阳", "安阳",
				"洛阳", "濮阳", "鹤壁", "平顶山", "商丘", "三门峡", "许昌", "开封", "焦作", "郑州",
				"信阳", "新乡", "漯河", "周口", "孝感", "荆州", "恩施", "江汉", "十堰", "随州",
				"黄石", "宜昌", "鄂州", "黄冈", "襄阳", "武汉", "荆门", "咸宁", "襄樊", "长沙",
				"郴州", "湘西", "益阳", "张家界", "常德", "株洲", "湘潭", "怀化", "衡阳", "永州",
				"岳阳", "娄底", "邵阳", "肇庆", "汕头", "汕尾", "深圳", "清远", "中山", "阳江",
				"珠海", "河源", "江门", "佛山", "惠州", "湛江", "广州", "茂名", "梅州", "潮州",
				"云浮", "揭阳", "韶关", "东莞", "钦州", "梧州", "柳州", "贺州", "河池", "百色",
				"北海", "来宾", "崇左", "防城港", "桂林", "贵港", "玉林", "南宁", "万宁", "海口",
				"三亚", "东方", "儋州", "琼海", "重庆", "广安", "巴中", "乐山", "达州", "绵阳",
				"成都", "资阳", "攀枝花", "甘孜", "遂宁", "泸州", "阿坝", "南充", "眉山", "广元",
				"内江", "自贡", "雅安", "宜宾", "德阳", "凉山", "贵阳", "六盘水", "黔东南", "黔南",
				"黔西南", "安顺", "毕节", "铜仁", "遵义", "玉溪", "昆明", "保山", "临沧", "昭通",
				"思茅", "迪庆", "德宏", "大理", "文山", "丽江", "怒江", "红河", "普洱", "楚雄",
				"曲靖", "西双版纳", "拉萨", "日喀则", "阿里", "昌都", "林芝", "那曲", "山南", "延安",
				"西安", "宝鸡", "商洛", "榆林", "铜川", "汉中", "渭南", "咸阳", "安康", "张掖",
				"定西", "武威", "金昌", "兰州", "庆阳", "陇南", "宁夏", "酒泉", "天水", "甘南",
				"白银", "嘉峪关", "平凉", "格尔木", "海南", "海西", "果洛", "玉树", "海东", "黄南",
				"西宁", "德令哈", "海北", "中卫", "固原", "银川", "吴忠", "石嘴山", "克拉玛依",
				"阿克苏", "博尔塔拉", "昌吉", "五家渠", "塔城", "奎屯", "巴音郭楞", "伊宁", "阿勒",
				"和田", "克孜勒苏柯尔克孜", "石河子", "吐鲁番", "伊犁", "哈密", "喀什", "乌鲁木齐", "嘉义",
				"新竹", "高雄", "台北", "台中", "台南", "香港", "澳门" };
		for (String s : provinces) {
			if (address.contains(s)) {
				return s;
			}
		}
		return address;
	}
}
