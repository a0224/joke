package cc.joke.entity;

public class T_Publish
{
    private Integer _id;// 主键ID

    // private Integer pid;// 评论哪款产品

    private String username;// 谁评论的

    private Integer userid;// 用户id

    private String content;// 内容

    private Integer status;// 评审状态

    private String usericon;// 图标url

    private String imgUrl;

    public String getUsericon()
    {
        return usericon;
    }

    public void setUsericon(String usericon)
    {
        this.usericon = usericon;
    }

    public Integer get_id()
    {
        return _id;
    }

    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    // public Integer getPid() {
    // return pid;
    // }
    //
    // public void setPid(Integer pid) {
    // this.pid = pid;
    // }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    // public String getCreateDate() {
    // return createDate;
    // }
    //
    // public void setCreateDate(String createDate) {
    // this.createDate = createDate;
    // }
    //
    // public String getModel() {
    // return model;
    // }
    //
    // public void setModel(String model) {
    // this.model = model;
    // }
    //
    public Integer getUserid()
    {
        return userid;
    }

    public void setUserid(Integer userid)
    {
        this.userid = userid;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

}
