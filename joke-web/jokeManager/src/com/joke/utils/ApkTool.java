package com.joke.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.free.apkparser.StreamReader;

public class ApkTool {
	/** log */
	private static Log log = LogFactory.getLog(ApkTool.class);

//	public static String apkBuild(ApkInfo apkInfo) throws Exception {
//		try {
//			String userDir = System.getProperty("user.dir");
//			log.debug(userDir);
//			// String apkFile = Constant.getParam().getProperty("apkFile");
//			// String aaptFile = Constant.getParam().getProperty("aaptFile");
//			// String apktoolFile =
//			// Constant.getParam().getProperty("apktoolFile");
//			String apkBackupsDir = Constant.getParam().getProperty("outFile")
//					+ Constant.getParam().getProperty("apkBackupsDir");
//			String apkOutPutDir = Constant.getParam().getProperty("outFile")
//					+ Constant.getParam().getProperty("apkOutPutDir");
//			String apktoolDir = Constant.PROJECT_PATH + "/apkTool";
//			log.info("apkOutPutDir---" + apkOutPutDir);
//			log.info("apktoolDir---" + apktoolDir);
//
//			String osName = System.getProperty("os.name");
//			System.out.println(osName);
//			Runtime rt = Runtime.getRuntime();
//			Process proc = null;
//			StringBuffer exeCommand = new StringBuffer();
//
//			if (osName.toLowerCase().contains("linux")) {
//				// exeCommand = toolPath + File.separator + "aapt d badging "
//				// + apkFile.getAbsolutePath();
//				exeCommand.append("java -jar ");
//				exeCommand.append(apktoolDir).append("/apktool.jar b ");
//				exeCommand.append(" -a ").append(apktoolDir);
//				exeCommand.append("/aapt ");
//				// exeCommand.append(apktoolDir).append("/")
//				// .append(apkInfo.getChannel()).append(" ");
//				exeCommand.append(apkBackupsDir).append("/")
//						.append(apkInfo.getChannel()).append(" ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append("_UnSign.apk");
//				log.info("exeCommand---" + exeCommand.toString());
//				proc = rt.exec(exeCommand.toString());
//			} else {
//				exeCommand.append("java -jar ");
//				exeCommand.append(apktoolDir).append("/apktool.jar b ");
//				exeCommand.append(" -a ").append(apktoolDir);
//				exeCommand.append("/aapt.exe ");
//				exeCommand.append(apkBackupsDir).append("/")
//						.append(apkInfo.getChannel()).append(" ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append("_UnSign.apk");
//				log.info("exeCommand---" + exeCommand.toString());
//				proc = rt.exec(exeCommand.toString());
//			}
//
//			StreamReader errorGobbler = new StreamReader(proc.getErrorStream(),
//					"ERROR");
//			StreamReader outputGobbler = new StreamReader(
//					proc.getInputStream(), "OUTPUT");
//
//			errorGobbler.start();
//			outputGobbler.start();
//			log.info(errorGobbler.getResult());
//			log.info(outputGobbler.getResult());
//
//			int exitVal = proc.waitFor();
//			if (exitVal == 0) {
//				// return freeAmlParser.initParserResult(apkFile,
//				// outputGobbler.getResult(), noJava, iconToDisk);
//				return "1";
//			} else {
//				throw new Exception();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//
//	}

//	public static String apkSignature(ApkInfo apkInfo) throws Exception {
//		try {
//			String userDir = System.getProperty("user.dir");
//			String apkPath = null;
//			log.debug(userDir);
//			// String apkFile = Constant.getParam().getProperty("apkFile");
//			// String aaptFile = Constant.getParam().getProperty("aaptFile");
//			// String apktoolFile =
//			// Constant.getParam().getProperty("apktoolFile");
//			String apkBackupsDir = Constant.getParam().getProperty("outFile")
//					+ Constant.getParam().getProperty("apkBackupsDir");
//			String apkOutPutDir = Constant.getParam().getProperty("outFile")
//					+ Constant.getParam().getProperty("apkOutPutDir");
//			String apktoolDir = Constant.PROJECT_PATH + "/apkTool";
//			log.info("apkOutPutDir---" + apkOutPutDir);
//			log.info("apktoolDir---" + apktoolDir);
//
//			String osName = System.getProperty("os.name");
//			System.out.println(osName);
//			Runtime rt = Runtime.getRuntime();
//			Process proc = null;
//			StringBuffer exeCommand = new StringBuffer();
//
//			if (osName.toLowerCase().contains("linux")) {
//				// exeCommand = toolPath + File.separator + "aapt d badging "
//				// + apkFile.getAbsolutePath();
//				exeCommand.append("jarsigner -verbose -keystore ");
//				exeCommand.append(apktoolDir).append("/joy_launcher.keystore ");
//				exeCommand.append(" -storepass ")
//						.append(Constant.darkkinger)
//						.append(" -signedjar ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append(".apk")
//						.append("  ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append("_UnSign.apk ")
//						.append(" joy_launcher");
//				log.info("exeCommand---" + exeCommand.toString());
//				proc = rt.exec(exeCommand.toString());
//			} else {
//				exeCommand.append("jarsigner -verbose -keystore ");
//				exeCommand.append(apktoolDir).append("/joy_launcher.keystore ");
//				exeCommand.append(" -storepass ")
//						.append(Constant.darkkinger)
//						.append(" -signedjar ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append(".apk")
//						.append("  ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append("_UnSign.apk ")
//						.append(" joy_launcher");
//				log.info("exeCommand---" + exeCommand.toString());
//				proc = rt.exec(exeCommand.toString());
//
//			}
//
//			StreamReader errorGobbler = new StreamReader(proc.getErrorStream(),
//					"ERROR");
//			StreamReader outputGobbler = new StreamReader(
//					proc.getInputStream(), "OUTPUT");
//
//			errorGobbler.start();
//			outputGobbler.start();
//			log.info(errorGobbler.getResult());
//			log.info(outputGobbler.getResult());
//			apkPath = Constant.getParam().getProperty("apkOutPutDir") + "/"
//					+ apkInfo.getCreateUser() + "/" + apkInfo.getChannel()
//					+ ".apk";
//			int exitVal = proc.waitFor();
//			if (exitVal == 0) {
//				// return freeAmlParser.initParserResult(apkFile,
//				// outputGobbler.getResult(), noJava, iconToDisk);
//				return apkPath;
//			} else {
//				throw new Exception();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//
//	}

//	public static String apkJarsigner(ApkInfo apkInfo) throws Exception {
//		try {
//			String userDir = System.getProperty("user.dir");
//			String apkPath = null;
//			log.debug(userDir);
//			// String apkFile = Constant.getParam().getProperty("apkFile");
//			// String aaptFile = Constant.getParam().getProperty("aaptFile");
//			// String apktoolFile =
//			// Constant.getParam().getProperty("apktoolFile");
//			String apkOutPutDir = Constant.getParam().getProperty("outFile")
//					+ Constant.getParam().getProperty("apkOutPutDir");
//			String apktoolDir = Constant.PROJECT_PATH + "/apkTool";
//			log.info("apkOutPutDir---" + apkOutPutDir);
//			log.info("apktoolDir---" + apktoolDir);
//
//			String osName = System.getProperty("os.name");
//			System.out.println(osName);
//			Runtime rt = Runtime.getRuntime();
//			Process proc = null;
//			StringBuffer exeCommand = new StringBuffer();
//
//			if (osName.toLowerCase().contains("linux")) {
//				// exeCommand = toolPath + File.separator + "aapt d badging "
//				// + apkFile.getAbsolutePath();
//				exeCommand.append("java -jar ");
//				exeCommand.append(apktoolDir).append("/sign/signapk.jar ");
//				exeCommand.append(apktoolDir).append("/sign/testkey.x509.pem ");
//				exeCommand.append(apktoolDir).append("/sign/testkey.pk8 ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append("_UnSign.apk ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append(".apk");
//				log.info("exeCommand---" + exeCommand.toString());
//				proc = rt.exec(exeCommand.toString());
//			} else {
//				exeCommand.append("java -jar ");
//				exeCommand.append(apktoolDir).append("/sign/signapk.jar ");
//				exeCommand.append(apktoolDir).append("/sign/testkey.x509.pem ");
//				exeCommand.append(apktoolDir).append("/sign/testkey.pk8 ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append("_UnSign.apk ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getCreateUser()).append("/")
//						.append(apkInfo.getChannel()).append(".apk");
//				log.info("exeCommand---" + exeCommand.toString());
//				proc = rt.exec(exeCommand.toString());
//
//			}
//
//			StreamReader errorGobbler = new StreamReader(proc.getErrorStream(),
//					"ERROR");
//			StreamReader outputGobbler = new StreamReader(
//					proc.getInputStream(), "OUTPUT");
//
//			errorGobbler.start();
//			outputGobbler.start();
//			log.info(errorGobbler.getResult());
//			log.info(outputGobbler.getResult());
//			apkPath = Constant.getParam().getProperty("apkOutPutDir") + "/"
//					+ apkInfo.getCreateUser() + "/" + apkInfo.getChannel()
//					+ ".apk";
//			int exitVal = proc.waitFor();
//			if (exitVal == 0) {
//				// return freeAmlParser.initParserResult(apkFile,
//				// outputGobbler.getResult(), noJava, iconToDisk);
//				return apkPath;
//			} else {
//				throw new Exception();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//
//	}

//	public static String apkDecode(ApkInfo apkInfo) throws Exception {
//		try {
//			String userDir = System.getProperty("user.dir");
//			log.debug(userDir);
//			// String apkFile = Constant.getParam().getProperty("apkFile");
//			// String aaptFile = Constant.getParam().getProperty("aaptFile");
//			// String apktoolFile =
//			// Constant.getParam().getProperty("apktoolFile");
//			String apkOutPutDir = Constant.getParam().getProperty("outFile")
//					+ Constant.getParam().getProperty("apkOutPutDir");
//			String apktoolDir = Constant.PROJECT_PATH + "/apkTool";
//			log.info("apkOutPutDir---" + apkOutPutDir);
//			log.info("apktoolDir---" + apktoolDir);
//
//			String osName = System.getProperty("os.name");
//			System.out.println(osName);
//			Runtime rt = Runtime.getRuntime();
//			Process proc = null;
//			StringBuffer exeCommand = new StringBuffer();
//
//			if (osName.toLowerCase().contains("linux")) {
//				// exeCommand = toolPath + File.separator + "aapt d badging "
//				// + apkFile.getAbsolutePath();
//				exeCommand.append("java -jar ");
//				exeCommand.append(apktoolDir).append("/apktool.jar d -f ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(Constant.getParam().getProperty("apkFileName"))
//						.append("  ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getChannel());
//				log.info("exeCommand---" + exeCommand.toString());
//				proc = rt.exec(exeCommand.toString());
//			} else {
//				// exeCommand = toolPath + File.separator +
//				// "aapt.exe d badging " + apkFile.getAbsolutePath();
//				// String[] cmd = new String[6];
//				if (osName.equals("Windows 95")) {
//					// cmd[0] = "command.com";
//					// cmd[1] = "/C";
//				} else {
//					// cmd[0] = "cmd.exe";
//					// cmd[1] = "/C";
//				}
//
//				// cmd[2] = ("\"" + toolPath + File.separator + "aapt.exe\"");
//				// cmd[3] = "d";
//				// cmd[4] = "badging";
//				// cmd[5] = ("\"" + apkFile.getAbsolutePath() + "\"");
//				// System.out.println("执行：" + cmd[2] + " " + cmd[3] + " " +
//				// cmd[4] + " " + cmd[5]);
//				// proc = rt.exec(cmd[2] + " " + cmd[3] + " " + cmd[4] + " " +
//				// cmd[5]);
//
//				exeCommand.append("java -jar ");
//				exeCommand.append(apktoolDir).append("/apktool.jar d -f ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(Constant.getParam().getProperty("apkFileName"))
//						.append(" ");
//				exeCommand.append(apkOutPutDir).append("/")
//						.append(apkInfo.getChannel());
//				log.info("exeCommand---" + exeCommand.toString());
//				proc = rt.exec(exeCommand.toString());
//			}
//
//			StreamReader errorGobbler = new StreamReader(proc.getErrorStream(),
//					"ERROR");
//			StreamReader outputGobbler = new StreamReader(
//					proc.getInputStream(), "OUTPUT");
//
//			errorGobbler.start();
//			outputGobbler.start();
//			log.info(errorGobbler.getResult());
//			log.info(outputGobbler.getResult());
//			int exitVal = proc.waitFor();
//			if (exitVal == 0) {
//				// return freeAmlParser.initParserResult(apkFile,
//				// outputGobbler.getResult(), noJava, iconToDisk);
//				return apkInfo.getChannel();
//			} else {
//				throw new Exception();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//
//	}
	
	public synchronized static String apkDecode(String reFile,String deskType) throws Exception {
		try {
			String userDir = System.getProperty("user.dir");
			log.debug(userDir);
			// String apkFile = Constant.getParam().getProperty("apkFile");
			// String aaptFile = Constant.getParam().getProperty("aaptFile");
			// String apktoolFile =
			// Constant.getParam().getProperty("apktoolFile");
			String apkOutPutDir = Constant.getParam().getProperty("outFile")
					+ Constant.getParam().getProperty("apktoolDir");
			String apktoolDir = Constant.PROJECT_PATH + "/apkTool";
			log.info("apkOutPutDir---" + apkOutPutDir);
			log.info("apktoolDir---" + apktoolDir);

			String osName = System.getProperty("os.name");
			System.out.println(osName);
			Runtime rt = Runtime.getRuntime();
			Process proc = null;
			StringBuffer exeCommand = new StringBuffer();

			if (osName.toLowerCase().contains("linux")) {
				// exeCommand = toolPath + File.separator + "aapt d badging "
				// + apkFile.getAbsolutePath();
				exeCommand.append("java -jar ");
				exeCommand.append(apktoolDir).append("/apktool.jar d -f ");
				exeCommand.append(reFile).append(" ");
				exeCommand.append(apkOutPutDir).append("/")
						.append(deskType);
				log.info("exeCommand---" + exeCommand.toString());
				proc = rt.exec(exeCommand.toString());
			} else {
				// exeCommand = toolPath + File.separator +
				// "aapt.exe d badging " + apkFile.getAbsolutePath();
				// String[] cmd = new String[6];
				if (osName.equals("Windows 95")) {
					// cmd[0] = "command.com";
					// cmd[1] = "/C";
				} else {
					// cmd[0] = "cmd.exe";
					// cmd[1] = "/C";
				}

				// cmd[2] = ("\"" + toolPath + File.separator + "aapt.exe\"");
				// cmd[3] = "d";
				// cmd[4] = "badging";
				// cmd[5] = ("\"" + apkFile.getAbsolutePath() + "\"");
				// System.out.println("执行：" + cmd[2] + " " + cmd[3] + " " +
				// cmd[4] + " " + cmd[5]);
				// proc = rt.exec(cmd[2] + " " + cmd[3] + " " + cmd[4] + " " +
				// cmd[5]);

				exeCommand.append("java -jar ");
				exeCommand.append(apktoolDir).append("/apktool.jar d -f ");
				exeCommand.append(reFile).append(" ");
				exeCommand.append(apkOutPutDir).append("/")
						.append(deskType);
				log.info("exeCommand---" + exeCommand.toString());
				proc = rt.exec(exeCommand.toString());
			}

			StreamReader errorGobbler = new StreamReader(proc.getErrorStream(),
					"ERROR");
			StreamReader outputGobbler = new StreamReader(
					proc.getInputStream(), "OUTPUT");

			errorGobbler.start();
			outputGobbler.start();
			log.info(errorGobbler.getResult());
			log.info(outputGobbler.getResult());
			int exitVal = proc.waitFor();
			if (exitVal == 0) {
				// return freeAmlParser.initParserResult(apkFile,
				// outputGobbler.getResult(), noJava, iconToDisk);
				return "true";
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public static void main(String arg[]) {

	}
}
