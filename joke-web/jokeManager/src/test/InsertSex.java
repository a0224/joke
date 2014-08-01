package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.joke.bean.JokedBean;
import com.joke.bean.MaBean;
import com.joke.pojo.JarPojo;
import com.joke.pojo.JokePojo;
import com.joke.pojo.NewsPojo;
import com.joke.service.JokeService;
import com.joke.service.NewsService;
import com.joke.service.SexyService;
import com.joke.utils.GetDate;

public class InsertSex {

	public static String lineTxt = null;
	public static String line = null;

	public static void readTxtFile(String filePath) {
		try {
			String encoding = "utf8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((lineTxt = bufferedReader.readLine()) != null) {
					line = lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

	private static Map<String, String> parseData(String data) {
		GsonBuilder gb = new GsonBuilder();
		Gson g = gb.create();
		Map<String, String> map = g.fromJson(data,
				new TypeToken<Map<String, String>>() {
				}.getType());
		return map;
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		SexyService service = (SexyService) context.getBean("sexyService");

        String dateUrl = "http://ic.snssdk.com/2/article/v15/stream/?detail=1&image=1&category=bisexual&count=4000&min_behot_time=1401957421&loc_mode=0&lac=6338&cid=64593&iid=293538879&device_id=2438698769&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=344&device_platform=android&device_type=HUAWEI%20G610-U00&os_api=17&os_version=4.2.1&uuid=860623023321506&openudid=b75475664a9929e9";

		JarPojo data = new JarPojo();
		data.setUrl("F:/JokeHandler_fat.jar");
		data.setClassName("com.joke.Handler");
		service.addBatchTest(data,dateUrl);
		
		
		// System.out.println(maBean.getData().get(0).getContent());
		// service.add(jokeP);

	}
}
