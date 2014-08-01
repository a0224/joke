package com.joke.utils;
//package cn.joy.sdk.util;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.List;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import cn.joy.pojo.AirPushReportPojo;
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
//public class ExcelUtil {
//
//	/** log */
//	private static Log log = LogFactory.getLog(ExcelUtil.class);
//
//	/**
//	 * 数据库导出至Excel表格
//	 * 
//	 * @throws Exception
//	 */
//	public static String createExcel(List<AirPushReportPojo> reportList,
//			String fileName) throws Exception {
//		// 准备设置excel工作表的标题
//		String[] title = { "标题", "类型", "接受量", "下载量", "软件来源" };
//		try {
//			// 获得开始时间
//			// long start = System.currentTimeMillis();
//			// 输出的excel的路径
//			String filePath = Constant.getParam().get("excelPath") + "/"
//					+ fileName;
//			File fileDir = new File((String) Constant.getParam().get(
//					"excelPath"));
//			if (!fileDir.exists()) {
//				fileDir.mkdir();
//			}
//			// 创建Excel工作薄
//			WritableWorkbook wwb;
//			// 新建立一个jxl文件,即在e盘下生成testJXL.xls
//			OutputStream os = new FileOutputStream(filePath);
//			wwb = Workbook.createWorkbook(os);
//			// 添加第一个工作表并设置第一个Sheet的名字
//			WritableSheet sheet = wwb.createSheet("airpush统计分析", 0);
//			Label label;
//			for (int i = 0; i < title.length; i++) {
//				// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
//				// 在Label对象的子对象中指明单元格的位置和内容
//				label = new Label(i, 0, title[i]);
//				// 将定义好的单元格添加到工作表中
//				sheet.addCell(label);
//			}
//			// 下面是填充数据
//			/*
//			 * 保存数字到单元格，需要使用jxl.write.Number 必须使用其完整路径，否则会出现错误
//			 */
//			Label titleLabel = null;
//			Label typeLabel = null;
//			Label receiveLabel = null;
//			Label downLabel = null;
//			Label resTypeLabel = null;
//			String cloudType;
//			int resType = 0;
//			for (int y = 0; y < reportList.size(); y++) {
//				// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
//				// 在Label对象的子对象中指明单元格的位置和内容
//				titleLabel = new Label(0, y + 1, reportList.get(y).getTitle());
//				String airpushType = "--";
//				if (reportList.get(y).getAirpushType().equals("1")) {
//					airpushType = "单广告";
//				} else if (reportList.get(y).getAirpushType().equals("2")) {
//					airpushType = "外链";
//				} else if (reportList.get(y).getAirpushType().equals("3")) {
//					airpushType = "文本";
//				}
//				typeLabel = new Label(1, y + 1, airpushType);
//				receiveLabel = new Label(2, y + 1, reportList.get(y)
//						.getReceiveCount());
//				downLabel = new Label(3, y + 1, reportList.get(y)
//						.getDownloadSuccCount());
//				resType = reportList.get(y).getResType();
//				switch (resType) {
//				case 1:
//					cloudType = "云端广告";
//					break;
//				case 2:
//					cloudType = "云端资源";
//					break;
//				case 3:
//					cloudType = "自有广告";
//					break;
//				case 4:
//					cloudType = "自有资源";
//					break;
//				default:
//					cloudType = "--";
//				}
//				resTypeLabel = new Label(4, y + 1, cloudType);
//
//				// 将定义好的单元格添加到工作表中
//				sheet.addCell(titleLabel);
//				sheet.addCell(typeLabel);
//				sheet.addCell(receiveLabel);
//				sheet.addCell(downLabel);
//				sheet.addCell(resTypeLabel);
//			}
//			// // 填充产品编号
//			// jxl.write.Number number = new jxl.write.Number(0, 1, 20071001);
//			// sheet.addCell(number);
//			// // 填充产品名称
//			// label = new Label(1, 1, "金鸽瓜子");
//			// sheet.addCell(label);
//			// /*
//			// * 定义对于显示金额的公共格式 jxl会自动实现四舍五入 例如 2.456会被格式化为2.46,2.454会被格式化为2.45
//			// */
//			// jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");
//			// jxl.write.WritableCellFormat wcf = new
//			// jxl.write.WritableCellFormat(
//			// nf);
//			// // 填充产品价格
//			// jxl.write.Number nb = new jxl.write.Number(2, 1, 2.45, wcf);
//			// sheet.addCell(nb);
//			//
//			// // 填充产品数量
//			// jxl.write.Number numb = new jxl.write.Number(3, 1, 200);
//			// sheet.addCell(numb);
//			// /*
//			// * 定义显示日期的公共格式 如:yyyy-MM-dd hh:mm
//			// */
//			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			// String newdate = sdf.format(new Date());
//			// // 填充出产日期
//			// label = new Label(4, 1, newdate);
//			// sheet.addCell(label);
//			// // 填充产地
//			// label = new Label(5, 1, "陕西西安");
//			// sheet.addCell(label);
//			// /*
//			// * 显示布尔值
//			// */
//			// jxl.write.Boolean bool = new jxl.write.Boolean(6, 1, true);
//			// sheet.addCell(bool);
//			// /*
//			// * 合并单元格 通过writablesheet.mergeCells(int x,int y,int m,int n);来实现的
//			// * 表示将从第x+1列，y+1行到m+1列，n+1行合并
//			// */
//			// sheet.mergeCells(0, 3, 2, 3);
//			// label = new Label(0, 3, "合并了三个单元格");
//			// // view plaincopyprint =
//			// sheet.addCell(label);
//			// /*
//			// *
//			// * 定义公共字体格式 通过获取一个字体的样式来作为模板 首先通过web.getSheet(0)获得第一个sheet
//			// * 然后取得第一个sheet的第二列，第一行也就是"产品名称"的字体
//			// */
//			// CellFormat cf = wwb.getSheet(0).getCell(1, 0).getCellFormat();
//			// WritableCellFormat wc = new WritableCellFormat();
//			// // 设置居中
//			// wc.setAlignment(Alignment.CENTRE);
//			// // 设置边框线
//			// wc.setBorder(Border.ALL, BorderLineStyle.THIN);
//			// // 设置单元格的背景颜色
//			// wc.setBackground(jxl.format.Colour.RED);
//			// label = new Label(1, 5, "字体", wc);
//			// sheet.addCell(label);
//
//			// 写入数据
//			wwb.write();
//			// 关闭文件
//			wwb.close();
//			return filePath;
//			// long end = System.currentTimeMillis();
//			// System.out.println("----完成该操作共用的时间是:" + (end - start) / 1000);
//		} catch (Exception e) {
//			log.error("---报表生成 出现异常---");
//			e.printStackTrace();
//			log.error(e);
//			throw e;
//		}
//	}
//
//	public static void main(String[] args) {
//	}
//
//}