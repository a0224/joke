package cc.joke.entity;

/**
 * 新闻消息实体
 */
public class T_News
{
    /**
     * 本地新闻ID
     */
    private Integer _id;

    /**
     * 新闻消息ID
     */
    private Integer newsID;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 消息发布时间
     */
    private String createTime;

    /**
     * ICON地址
     */
    private String iconUrl;

    /**
     * 是否存在详情
     */
    private int existsDetail;

    /**
     * 详情地址
     */
    private String detailContent;

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

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    public int getExistsDetail()
    {
        return existsDetail;
    }

    public void setExistsDetail(int existsDetail)
    {
        this.existsDetail = existsDetail;
    }

    public String getDetailContent()
    {
        return detailContent;
    }

    public void setDetailContent(String detailContent)
    {
        this.detailContent = detailContent;
    }

    public Integer getNewsID()
    {
        return newsID;
    }

    public void setNewsID(Integer newsID)
    {
        this.newsID = newsID;
    }
}
