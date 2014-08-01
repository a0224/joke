package com.joke.action.joke;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.joke.action.BaseAction;
import com.joke.pojo.JarPojo;
import com.joke.service.JarService;
import com.joke.utils.Constant;
import com.joke.utils.DateUtil;

public class JarAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5386469775398514698L;
	/** log */
	private static Log log = LogFactory.getLog(JarAction.class);

	@Autowired
	private JarService jarService;

	private JarPojo data;
	private List<JarPojo> root;
	private Integer Count = 1;

	@Override
	protected String executeProcess() throws Exception {
		return "sucess";
	}

	public String list() throws Exception {
		log.info("list start");
		try {

			root = jarService.list(data);
			setTotalProperty(Constant.totalProperty);
			if (null == root) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("list failed");
				return "failed";
			} else {
				setResult("success");
				setMessage("获取数据成功");
				log.info("list success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("list failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String infoById() throws Exception {
		log.info("infoById start");
		try {

			data = jarService.infoById(data);

			if (null == data) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("infoById failed");
				return "failed";
			} else {
				setResult("success");
				setMessage("获取数据成功");
				log.info("infoById success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("infoById failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String add() throws Exception {
		log.info("add start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				return "failed";
			}

			data.setCreateUser(getUser().getLoginName());
			data.setCreateTime(DateUtil.GetNowDateTime());
			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime());
			data.setStatus(1);
			jarService.add(data);

			setResult("success");
			setMessage("更新数据成功");
			log.info("add success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("add failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String delById() throws Exception {
		log.info("delById start");
		try {

			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime());
			jarService.delById(data);
			setResult("success");
			setMessage("删除数据成功");
			log.info("delById success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("删除数据失败");
			log.error("delById failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String updateStatus() throws Exception {
		log.info("updateStatus start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				log.info("updateStatus failed");
				return "failed";
			}

			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime());

			jarService.updateStatus(data);

			setResult("success");
			setMessage("更新数据成功");
			setResult("success");
			log.info("updateStatus success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("updateStatus failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String update() throws Exception {
		log.info("update start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				log.info("update failed");
				return "failed";
			}

			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime());

			jarService.update(data);

			setResult("success");
			setMessage("更新数据成功");
			setResult("success");
			log.info("update success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("update failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	/**
	 * @return the data
	 */
	public JarPojo getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(JarPojo data) {
		this.data = data;
	}

	/**
	 * @return the root
	 */
	public List<JarPojo> getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(List<JarPojo> root) {
		this.root = root;
	}

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

}
