package com.joke.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.BucketSummary;
import com.baidu.inf.iis.bcs.model.Empty;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.model.Resource;
import com.baidu.inf.iis.bcs.model.SuperfileSubObject;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.baidu.inf.iis.bcs.policy.PolicyAction;
import com.baidu.inf.iis.bcs.policy.PolicyEffect;
import com.baidu.inf.iis.bcs.policy.Statement;
import com.baidu.inf.iis.bcs.request.CreateBucketRequest;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListBucketRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.request.PutSuperfileRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

public class BdBucUtil {

	private static final Log log = LogFactory.getLog(BdBucUtil.class);
	// ----------------------------------------
	private static String bucket = "jinbuc";

	private static BaiduBCS baiduBCS;

	static {
		String host = "bcs.duapp.com";
		String accessKey = "2LHZTGnHYjCI7S1rIlm875sj";
		String secretKey = "5BWxtiL7cxbSCLMmPDfx5Fkp8G92BSUC";

		if (baiduBCS == null) {
			BCSCredentials credentials = new BCSCredentials(accessKey,
					secretKey);
			baiduBCS = new BaiduBCS(credentials, host);
			// baiduBCS.setDefaultEncoding("GBK");
			baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		}
	}

	public static int putFile(File file, String object) {
		if (file == null) {
			return 0;
		}
		putObjectByFile(baiduBCS, file, object);
		return 1;
	}

	public static int putFilePub(File file, String object) {
		if (file == null) {
			return 0;
		}

		PutObjectRequest request = new PutObjectRequest(bucket, object, file);
		ObjectMetadata metadata = new ObjectMetadata();
		// metadata.setContentType("text/html");
		metadata.setHeader("x-bs-acl", "public-read");
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
		log.info("x-bs-request-id: " + response.getRequestId());
		log.info(objectMetadata);

		return 1;
	}

	public static File readFile(String path) {
		return null;
	}

	public static void generateUrl(BaiduBCS baiduBCS, String object) {
		GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(
				HttpMethodName.GET, bucket, object);
		generateUrlRequest.setBcsSignCondition(new BCSSignCondition());
		generateUrlRequest.getBcsSignCondition().setIp("1.1.1.1");
		generateUrlRequest.getBcsSignCondition().setTime(123455L);
		generateUrlRequest.getBcsSignCondition().setSize(123455L);
		System.out.println(baiduBCS.generateUrl(generateUrlRequest));
	}

	public static void copyObject(BaiduBCS baiduBCS, String destBucket,
			String destObject, String object) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("image/jpeg");
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(
				destBucket, destObject), objectMetadata);
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(
				destBucket, destObject), null);
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(
				destBucket, destObject));
	}

	@SuppressWarnings("unused")
	private static void createBucket(BaiduBCS baiduBCS) {
		// baiduBCS.createBucket(bucket);
		baiduBCS.createBucket(new CreateBucketRequest(bucket,
				X_BS_ACL.PublicRead));
	}

	@SuppressWarnings("unused")
	private static void deleteBucket(BaiduBCS baiduBCS) {
		baiduBCS.deleteBucket(bucket);
	}

	public static void deleteObject(BaiduBCS baiduBCS, String object) {
		Empty result = baiduBCS.deleteObject(bucket, object).getResult();
		log.info(result);
	}

	@SuppressWarnings("unused")
	private static void getBucketPolicy(BaiduBCS baiduBCS) {
		BaiduBCSResponse<Policy> response = baiduBCS.getBucketPolicy(bucket);

		log.info("After analyze: " + response.getResult().toJson());
		log.info("Origianal str: " + response.getResult().getOriginalJsonStr());
	}

	public static void getObjectMetadata(BaiduBCS baiduBCS, String object) {
		ObjectMetadata objectMetadata = baiduBCS.getObjectMetadata(bucket,
				object).getResult();
		log.info(objectMetadata);
	}

	@SuppressWarnings("unused")
	private static void getObjectPolicy(BaiduBCS baiduBCS, String object) {
		BaiduBCSResponse<Policy> response = baiduBCS.getObjectPolicy(bucket,
				object);
		log.info("After analyze: " + response.getResult().toJson());
		log.info("Origianal str: " + response.getResult().getOriginalJsonStr());
	}

	@SuppressWarnings("unused")
	private static void getObjectWithDestFile(BaiduBCS baiduBCS, File file,
			String object) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, object);
		baiduBCS.getObject(getObjectRequest, file);
	}

	@SuppressWarnings("unused")
	private static void listBucket(BaiduBCS baiduBCS) {
		ListBucketRequest listBucketRequest = new ListBucketRequest();
		BaiduBCSResponse<List<BucketSummary>> response = baiduBCS
				.listBucket(listBucketRequest);
		for (BucketSummary bucket : response.getResult()) {
			log.info(bucket);
		}
	}

	@SuppressWarnings("unused")
	private static void listObject(BaiduBCS baiduBCS) {
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setStart(0);
		listObjectRequest.setLimit(20);
		// ------------------by dir
		{
			// prefix must start with '/' and end with '/'
			// listObjectRequest.setPrefix("/1/");
			// listObjectRequest.setListModel(2);
		}
		// ------------------only object
		{
			// prefix must start with '/'
			// listObjectRequest.setPrefix("/1/");
		}
		BaiduBCSResponse<ObjectListing> response = baiduBCS
				.listObject(listObjectRequest);
		log.info("we get [" + response.getResult().getObjectSummaries().size()
				+ "] object record.");
		for (ObjectSummary os : response.getResult().getObjectSummaries()) {
			log.info(os.toString());
		}
	}

	@SuppressWarnings("unused")
	private static void putBucketPolicyByPolicy(BaiduBCS baiduBCS) {
		Policy policy = new Policy();
		Statement st1 = new Statement();
		st1.addAction(PolicyAction.all).addAction(PolicyAction.get_object);
		st1.addUser("zhengkan").addUser("zhangyong01");
		st1.addResource(bucket + "/111").addResource(bucket + "/111");
		st1.setEffect(PolicyEffect.allow);
		policy.addStatements(st1);
		baiduBCS.putBucketPolicy(bucket, policy);
	}

	@SuppressWarnings("unused")
	private static void putBucketPolicyByX_BS_ACL(BaiduBCS baiduBCS,
			X_BS_ACL acl) {
		baiduBCS.putBucketPolicy(bucket, acl);
	}

	public static void putObjectByFile(BaiduBCS baiduBCS, File file,
			String object) {
		PutObjectRequest request = new PutObjectRequest(bucket, object, file);
		ObjectMetadata metadata = new ObjectMetadata();
		// metadata.setContentType("text/html");
		// metadata.setHeader("x-bs-acl", "public-read");
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
		log.info("x-bs-request-id: " + response.getRequestId());
		log.info(objectMetadata);
	}

	public static void putObjectByInputStream(BaiduBCS baiduBCS, File file,
			String object) throws FileNotFoundException {
		// File file = createSampleFile();
		InputStream fileContent = new FileInputStream(file);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("text/html");
		objectMetadata.setContentLength(file.length());
		PutObjectRequest request = new PutObjectRequest(bucket, object,
				fileContent, objectMetadata);
		ObjectMetadata result = baiduBCS.putObject(request).getResult();
		log.info(result);
	}

	@SuppressWarnings("unused")
	private static void putObjectPolicyByPolicy(BaiduBCS baiduBCS, String object) {
		Policy policy = new Policy();
		Statement st1 = new Statement();
		st1.addAction(PolicyAction.all).addAction(PolicyAction.get_object);
		st1.addUser("zhengkan").addUser("zhangyong01");
		st1.addResource(bucket + object).addResource(bucket + object);
		st1.setEffect(PolicyEffect.allow);
		policy.addStatements(st1);
		baiduBCS.putObjectPolicy(bucket, object, policy);
	}

	@SuppressWarnings("unused")
	private static void putObjectPolicyByX_BS_ACL(BaiduBCS baiduBCS,
			X_BS_ACL acl, String object) {
		baiduBCS.putObjectPolicy(bucket, object, acl);
	}

	public static void putSuperfile(BaiduBCS baiduBCS, String object) {
		List<SuperfileSubObject> subObjectList = new ArrayList<SuperfileSubObject>();
		// 0
		BaiduBCSResponse<ObjectMetadata> response1 = baiduBCS.putObject(bucket,
				object + "_part0", createSampleFile());
		subObjectList.add(new SuperfileSubObject(bucket, object + "_part0",
				response1.getResult().getETag()));
		// 1
		BaiduBCSResponse<ObjectMetadata> response2 = baiduBCS.putObject(bucket,
				object + "_part1", createSampleFile());
		subObjectList.add(new SuperfileSubObject(bucket, object + "_part1",
				response2.getResult().getETag()));
		// put superfile
		PutSuperfileRequest request = new PutSuperfileRequest(bucket, object
				+ "_superfile", subObjectList);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS
				.putSuperfile(request);
		ObjectMetadata objectMetadata = response.getResult();
		log.info("x-bs-request-id: " + response.getRequestId());
		log.info(objectMetadata);
	}

	public static void setObjectMetadata(BaiduBCS baiduBCS, String object) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("text/html12");
		baiduBCS.setObjectMetadata(bucket, object, objectMetadata);
	}

	private static File createSampleFile() {
		try {
			File file = File.createTempFile("D:/data", ".txt");
			file.deleteOnExit();

			Writer writer = new OutputStreamWriter(new FileOutputStream(file));
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.close();

			return file;
		} catch (IOException e) {
			log.error("tmp file create failed.");
			return null;
		}
	}

	public static void main(String[] args) {
		putFile(new File("F:/data/Hydrangeas.jpg"), "/aa/bb/123.jpg");

	}

}
