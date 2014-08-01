package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Aaa {
	public static void main(String str[]) throws ParseException {
		SimpleDateFormat simpleDateFormatForDay = new SimpleDateFormat("yyyy年");
		String stree = "2014年"; 
		Date date = new Date(0);
		date =(Date) simpleDateFormatForDay.parse(stree);
		System.out.print(date);
	}
}
