//package com.joke.utils;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import cn.joy.sdk.pojo.AislePojo;
//import cn.joy.sdk.pojo.MessagePojo;
//import cn.joy.sdk.service.AisleService;
//import jxl.*;
//import jxl.format.Alignment;
//import jxl.format.Border;
//import jxl.format.BorderLineStyle;
//import jxl.format.CellFormat;
//import jxl.write.Boolean;
//import jxl.write.Label;
//import jxl.write.Number;
//import jxl.write.WritableCellFormat;
//import jxl.write.WritableFont;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//
//public class AisleFromExcel {
//
//	/** log */
//	private static Log log = LogFactory.getLog(AisleFromExcel.class);
//
//	/**
//	 * 数据库导出至Excel表格
//	 * 
//	 * @throws Exception
//	 */
//	public static void createExcel(String fileName) throws Exception {
//		try {
//
//			HashMap<Integer, String> rankNameMap = new HashMap<Integer, String>();
//			HashMap<String, String> lineMap = null;
//			// row
//			int rank = 0;// 列
//			int line = 3; // 行
//			int rankCount = 1;
//
//			log.info("fileName---" + fileName);
//			Workbook book = Workbook.getWorkbook(new File(fileName));
//			// 获得第一个工作表对象
//			Sheet sheet = book.getSheet("aisle");
//			// 列名
//			for (int i = 0;; i++) {
//				Cell cell1 = sheet.getCell(i, 2);// 前一个参数代表列，后一个参数代表行
//				String result = cell1.getContents();
//				if (result == null || "".equals(result)) {
//					break;
//				}
//				rankNameMap.put(i, result);
//				log.info("rankName---" + result);
//				rankCount++;
//
//			}
//			AislePojo aisle = null;
//			MessagePojo message = null;
//
//			for (int j = line;; j++) {
//				lineMap = new HashMap<String, String>();
//				for (int i = 0; i < rankCount; i++) {
//					Cell cell1 = sheet.getCell(i, j);// 前一个参数代表列，后一个参数代表行
//					String value = cell1.getContents();
//					lineMap.put(rankNameMap.get(i), value);
//					log.info("rankName---" + value);
//				}
//				message = new MessagePojo();
//				message.setMessage(lineMap.get("message"));
//				message.setLongCode(lineMap.get("longCode"));
//				message.setDayLimit(Integer.valueOf(lineMap.get("dayLimit")));
//				message.setMonLimit(Integer.valueOf(lineMap.get("monLimit")));
//				message.setDflowLimit(Integer.valueOf(lineMap.get("dflowLimit")));
//				message.setMflowLimit(Integer.valueOf(lineMap.get("mflowLimit")));
//				message.setKeyword(lineMap.get("keyword"));
//				message.setSecType(Integer.valueOf(lineMap.get("secType")));
//				message.setReplyType(lineMap.get("replyType"));
//				log.info("message---" + message);
//
//				aisle = new AislePojo();
//				aisle.setFcharge(message.getId());
//				aisle.setCharge(Integer.valueOf(lineMap.get("charge")));
//				aisle.setCp(lineMap.get("cp"));
//				aisle.setPrice(Double.valueOf(lineMap.get("price")));
//				aisle.setType(Integer.valueOf(lineMap.get("type")));
//				aisle.setSaparate(Double.valueOf(lineMap.get("saparate")));
//				aisle.setProvince(lineMap.get("province"));
//				aisle.setSimCard(Integer.valueOf(lineMap.get("simCard")));
//				aisle.setMaskArea(lineMap.get("maskArea"));
//				aisle.setBlacklist(lineMap.get("blacklist"));
//				aisle.setMemo(lineMap.get("memo"));
//				aisle.setStatus(1);
//				aisle.setCreateTime(GetDate.GetNowDateTime());
//				aisle.setCreateUser("");
//				aisle.setModifyTime(GetDate.GetNowDateTime());
//				aisle.setModifyUser("");
//				log.info("aisle---" + aisle);
//
//				Cell cell1 = sheet.getCell(0, j + 1);// 前一个参数代表列，后一个参数代表行
//				String result = cell1.getContents();
//				if (result == null) {
//					break;
//				}
//
//			}
//
//			book.close();
//			log.info("end");
//		} catch (Exception e) {
//			log.info(e);
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				new String[] { "applicationContext.xml" });// 从classpath中加载
//
//		AisleService aisleService = (AisleService) context
//				.getBean("aisleService");
////		createExcel("D:/data/aisleInsert.xls");
//		aisleService.insertAisle("D:/data/aisleInsert.xls");
//	}
//
//}