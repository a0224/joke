package cc.joke.entity;

public class T_App
{
    /**
     * 主键ID
     */
    private Integer _id;

    /**
     * id
     */
    private Integer id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用地址
     */
    private String baseUrl;

    /**
     * ICON地址
     */
    private String iconUrl;

    /**
     * 描述
     */
    private String descrip;

    /**
     * 价格
     */
    private int price;

    /**
     * 对应渠道
     */
    private String channel;

    public Integer get_id()
    {
        return _id;
    }

    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    public String getDescrip()
    {
        return descrip;
    }

    public void setDescrip(String descrip)
    {
        this.descrip = descrip;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

}
