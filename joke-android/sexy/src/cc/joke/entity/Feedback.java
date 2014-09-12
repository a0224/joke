package cc.joke.entity;

/**
 * 用户反馈
 */
public class Feedback
{
    /**
     * 产品ID
     */
    private int productID;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * QQ联系号码
     */
    private String qq;

    /**
     * 反馈内容
     */
    private String content;

    public int getProductID()
    {
        return productID;
    }

    public void setProductID(int productID)
    {
        this.productID = productID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getQq()
    {
        return qq;
    }

    public void setQq(String qq)
    {
        this.qq = qq;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
