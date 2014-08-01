package com.joke.bean;

import java.sql.Timestamp;

public class JokeBean extends BaseBean
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1416995517198285442L;

    private Integer id;

    private int userId;

    private String msg;

    private int type;

    private String url;

    private int good;

    private int bad;

    private int talk;

    private Timestamp createTime;

    private Timestamp modifyTime;

    private String createUser;

    private String modifyUser;

    private Integer status;

    private String validate;

    private int pageNo;

    private int pageSize;

    private int totalProperty;

    private String sortModel;

    private String sortName;

    private String ids;

    private String deduct;

    private String parentId;

    private int source;

    private String sid;

    private String userIcon;

    private String userName;

    public JokeBean()
    {
        super();
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public int getUserId()
    {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    /**
     * @return the msg
     */
    public String getMsg()
    {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the createTime
     */
    public Timestamp getCreateTime()
    {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    /**
     * @return the modifyTime
     */
    public Timestamp getModifyTime()
    {
        return modifyTime;
    }

    /**
     * @param modifyTime the modifyTime to set
     */
    public void setModifyTime(Timestamp modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    /**
     * @return the createUser
     */
    public String getCreateUser()
    {
        return createUser;
    }

    /**
     * @param createUser the createUser to set
     */
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    /**
     * @return the modifyUser
     */
    public String getModifyUser()
    {
        return modifyUser;
    }

    /**
     * @param modifyUser the modifyUser to set
     */
    public void setModifyUser(String modifyUser)
    {
        this.modifyUser = modifyUser;
    }

    /**
     * @return the status
     */
    public Integer getStatus()
    {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    /**
     * @return the validate
     */
    public String getValidate()
    {
        return validate;
    }

    /**
     * @param validate the validate to set
     */
    public void setValidate(String validate)
    {
        this.validate = validate;
    }

    /**
     * @return the pageNo
     */
    public int getPageNo()
    {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * @return the totalProperty
     */
    public int getTotalProperty()
    {
        return totalProperty;
    }

    /**
     * @param totalProperty the totalProperty to set
     */
    public void setTotalProperty(int totalProperty)
    {
        this.totalProperty = totalProperty;
    }

    /**
     * @return the sortModel
     */
    public String getSortModel()
    {
        return sortModel;
    }

    /**
     * @param sortModel the sortModel to set
     */
    public void setSortModel(String sortModel)
    {
        this.sortModel = sortModel;
    }

    /**
     * @return the sortName
     */
    public String getSortName()
    {
        return sortName;
    }

    /**
     * @param sortName the sortName to set
     */
    public void setSortName(String sortName)
    {
        this.sortName = sortName;
    }

    /**
     * @return the ids
     */
    public String getIds()
    {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(String ids)
    {
        this.ids = ids;
    }

    /**
     * @return the deduct
     */
    public String getDeduct()
    {
        return deduct;
    }

    /**
     * @param deduct the deduct to set
     */
    public void setDeduct(String deduct)
    {
        this.deduct = deduct;
    }

    /**
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    /**
     * @return the good
     */
    public int getGood()
    {
        return good;
    }

    /**
     * @param good the good to set
     */
    public void setGood(int good)
    {
        this.good = good;
    }

    /**
     * @return the bad
     */
    public int getBad()
    {
        return bad;
    }

    /**
     * @param bad the bad to set
     */
    public void setBad(int bad)
    {
        this.bad = bad;
    }

    /**
     * @return the talk
     */
    public int getTalk()
    {
        return talk;
    }

    /**
     * @param talk the talk to set
     */
    public void setTalk(int talk)
    {
        this.talk = talk;
    }

    public int getSource()
    {
        return source;
    }

    public void setSource(int source)
    {
        this.source = source;
    }

    public String getSid()
    {
        return sid;
    }

    public void setSid(String sid)
    {
        this.sid = sid;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserIcon()
    {
        return userIcon;
    }

    public void setUserIcon(String userIcon)
    {
        this.userIcon = userIcon;
    }

}
