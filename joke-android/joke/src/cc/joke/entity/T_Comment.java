package cc.joke.entity;

public class T_Comment
{
    private Integer _id;// 主键ID

    private Integer pid;// 评论哪款产品

    private Integer userid;// 谁评论的

    private String username;// 谁评论的

    private String content;// 内容

    private String createDate;// 评论时间

    private String model;// 用哪款手机评论的

    private String usericon;// 图标url

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

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
    }

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

    public String getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public Integer getUserid()
    {
        return userid;
    }

    public void setUserid(Integer userid)
    {
        this.userid = userid;
    }

}
