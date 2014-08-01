package com.joke.handler;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.joke.bean.JokeBean;
import com.joke.util.SouEnum;
import com.joke.util.Utils;

public class JokeHandler {

	public String getJokeList(String jsonTextString, String jsonImageString, Integer userid) {

		List<JokeBean> textList = getJokeList(jsonTextString, userid);

		List<JokeBean> imageList = getJokeList(jsonImageString, userid);

		textList.addAll(imageList);

		Gson gson = new Gson();

		return gson.toJson(textList);
	}

	public List<JokeBean> getJokeList(String jsonString, Integer userid) {
		List<JokeBean> list = new ArrayList<JokeBean>();

		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(jsonString); // 将json字符串转换成JsonElement
		JsonObject jo = jsonElement.getAsJsonObject();
		jsonElement = jo.get("data");
		JsonArray jsonArray = jsonElement.getAsJsonArray(); // 将JsonElement转换成JsonArray
		JokeBean bean = null;

		for (JsonElement job : jsonArray) {
			jo = job.getAsJsonObject();
			bean = new JokeBean();

			String large_url = null;
			if (jo.has("large_url")) {
				JsonElement obj = jo.get("large_url");
				if (obj != null) {
					try {
						large_url = obj.getAsString();
					} catch (Exception e) {
						large_url = null;
					}
				}
			}
			String screen_name = jo.has("screen_name") ? jo.get("screen_name").getAsString() : "";
			String profile_image_url = jo.has("profile_image_url") ? jo.get("profile_image_url").getAsString() : null;
			String content = jo.has("content") ? jo.get("content").getAsString() : "";
			String tag_id = jo.has("tag_id") ? jo.get("tag_id").getAsString() : "0";
			bean.setSid(tag_id);
			bean.setUserIcon(profile_image_url);
			bean.setUserName(screen_name);
			bean.setUrl(large_url);
			bean.setMsg(content);

			bean.setUserId(userid);
			bean.setCreateUser("admin");
			bean.setCreateTime(Utils.GetNowDateTime());
			bean.setModifyUser("admin");
			bean.setModifyTime(Utils.GetNowDateTime());
			bean.setStatus(1);
			bean.setSource(SouEnum.SOU_1.getId());
			list.add(bean);
		}
		return list;
	}

}
