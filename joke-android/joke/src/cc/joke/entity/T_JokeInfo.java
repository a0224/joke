package cc.joke.entity;

import java.io.Serializable;

public class T_JokeInfo implements Serializable
{

    private Integer _id; // 主键ID

    private Integer id; // 主键ID

    private String title;// 标题

    private String type;// 笑话类型

    private String description;// 描述

    private String iconUrl;// 头像url

    private String imgUrl;// img图片的url

    private String dspImages;// 详情图片(逗号隔开的多个URL地址)

    private int talknum;// 评论数

    private int highPraise;// 好评

    private int badPraise;// 差评
    
    private int source;//来源

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Integer get_id()
    {
        return _id;
    }

    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String iconUrl)
    {
        this.imgUrl = iconUrl;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    public String getDspImages()
    {
        return dspImages;
    }

    public void setDspImages(String dspImages)
    {
        this.dspImages = dspImages;
    }

    public int getTalknum()
    {
        return talknum;
    }

    public void setTalknum(int talknum)
    {
        this.talknum = talknum;
    }

    public int getHighPraise()
    {
        return highPraise;
    }

    public void setHighPraise(int highPraise)
    {
        this.highPraise = highPraise;
    }

    public int getBadPraise()
    {
        return badPraise;
    }

    public void setBadPraise(int badPraise)
    {
        this.badPraise = badPraise;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getSource()
    {
        return source;
    }

    public void setSource(int source)
    {
        this.source = source;
    }
}
