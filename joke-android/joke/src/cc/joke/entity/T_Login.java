package cc.joke.entity;

/**
 * 产品好激活信息
 * 
 * @author
 */
public class T_Login
{
    /** 维护主键KEY **/
    private Integer _id;

    /** 是否登陆 1激活 其它未激活 **/
    private int status;

    private int userid;

    private String username;

    private String usericon;

    private String token;

    private String openid;

    public Integer get_id()
    {
        return _id;
    }

    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsericon()
    {
        return usericon;
    }

    public void setUsericon(String usericon)
    {
        this.usericon = usericon;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
}
