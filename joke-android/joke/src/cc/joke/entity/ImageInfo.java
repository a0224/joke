package cc.joke.entity;

public class ImageInfo
{

    private String id;// 远程服务器数据库中的产品主键ID

    private String title;// 标题

    private String explain;// 说明

    private String url;// 图片的url

    private int star;// 热门星度值

    private boolean isRecommend;// 是否推荐

    private boolean isHot;// 是否是热门

    private boolean isNew;// 是否是最新

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getExplain()
    {
        return explain;
    }

    public void setExplain(String explain)
    {
        this.explain = explain;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getStar()
    {
        return star;
    }

    public void setStar(int star)
    {
        this.star = star;
    }

    public boolean isRecommend()
    {
        return isRecommend;
    }

    public void setRecommend(boolean isRecommend)
    {
        this.isRecommend = isRecommend;
    }

    public boolean isHot()
    {
        return isHot;
    }

    public void setHot(boolean isHot)
    {
        this.isHot = isHot;
    }

    public boolean isNew()
    {
        return isNew;
    }

    public void setNew(boolean isNew)
    {
        this.isNew = isNew;
    }

}
