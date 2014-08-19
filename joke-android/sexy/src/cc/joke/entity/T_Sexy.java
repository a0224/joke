package cc.joke.entity;

public class T_Sexy
{
    private Integer _id; // 主键ID

    private String title;// 标题

    private String imgUrl;//

    private String description;// 描述

    private String htmlUrl;// 详情

    private int ischarge;// 是否为收费

    private int price;// 价格

    private int source;//来源
    
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getHtmlUrl()
    {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl)
    {
        this.htmlUrl = htmlUrl;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getIscharge()
    {
        return ischarge;
    }

    public void setIscharge(int ischarge)
    {
        this.ischarge = ischarge;
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
