package cc.joke.entity;

public class T_Gallery
{
    private Integer _id;// 主键ID(序号)

    private String sid;// 对应服务器表中的主键ID

    private String imageurl;// 图片URL

    public Integer get_id()
    {
        return _id;
    }

    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    public String getSid()
    {
        return sid;
    }

    public void setSid(String sid)
    {
        this.sid = sid;
    }

    public String getImageurl()
    {
        return imageurl;
    }

    public void setImageurl(String imageurl)
    {
        this.imageurl = imageurl;
    }

}
