package cc.joke.util;

public class StringUtils {
	
    /** 字符16进制后数字的长度. */
    public static final int LENGTH_CHAR_HEX = 4;
    
    /** 字符汉字字计数伪长度. */
    public static final int LENGTH_CHINESE_BYTE = 2;
    
    /** 字符"…"的长度. */
    public static final int LENGTH_OMIT = 2;

	public static String subString(String str, int num) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}

		if (num == -1) {
			num = 40;
		}

		if (str.length() <= num) {
			return str;
		} else {
			return str.substring(0, num) + "...";
		}
	}
	
	public static String omitString(String string, int length) {
		if (StringUtils.isEmpty(string)) {
			return string;
		}
		
        StringBuilder sb = new StringBuilder();
        
        if (length == -1) {
        	length = 75;
		}
        
        // 判断是否需要截取
        if (byteLength(string) > length) {
            int count = 0;
            // 遍历每个字符
            for (int i = 0; i < string.length(); i++) {
                char temp = string.charAt(i);
                // 判断是字符的字节长度，字母数字计1，汉字及标点计2
                if (Integer.toHexString(temp).length() == LENGTH_CHAR_HEX) {
                    count += LENGTH_CHINESE_BYTE;
                } else {
                    count++;
                }
                
                // 判断当前截取字符长度
                if (count < length - LENGTH_OMIT) {
                    sb.append(temp);
                } else if (count == length - LENGTH_OMIT) {
                    sb.append(temp);
                    break;
                } else if (count > length - LENGTH_OMIT) {
                    sb.append("");
                    break;
                }
            }
            sb.append("...");
        } else {
            sb.append(string);
        }
        return sb.toString();
    }
	
    /**
     * 计算字符串的伪字节长度(字母数字计1，汉字及标点计2)</br>.
     * 
     * @param string 字符串
     * @return 伪字节长度
     */
    public static int byteLength(String string) {
        int count = 0;
        // 遍历每个字符
        for (int i = 0; i < string.length(); i++) {
            // 判断是字符的字节长度，字母数字计1，汉字及标点计2
            if (Integer.toHexString(string.charAt(i)).length() == LENGTH_CHAR_HEX) {
                count += LENGTH_CHINESE_BYTE;
            } else {
                count++;
            }
        }
        return count;
    }

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
