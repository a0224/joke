package cc.joke.entity;

/**
 * 客户端版本更新检测
 */
public class CheckClient
{
    public int id;

    /**
     * 新代码版本号
     */
    public int versionCode;

    /**
     * 新版本号
     */
    private String versionName;

    /**
     * 更新说明
     */
    private String description;

    /**
     * 资源服务器
     */
    private String baseUrl;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getVersionCode()
    {
        return versionCode;
    }

    public void setVersionCode(int versionCode)
    {
        this.versionCode = versionCode;
    }

    public String getVersionName()
    {
        return versionName;
    }

    public void setVersionName(String versionName)
    {
        this.versionName = versionName;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
