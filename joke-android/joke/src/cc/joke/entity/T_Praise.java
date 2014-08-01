package cc.joke.entity;

/**
 * 产品好差屏信息
 * 
 * @author
 */
public class T_Praise
{
    /** 维护主键KEY **/
    private Integer _id;

    /** 产品ID **/
    private int productID;

    /** 评论类型 **/
    private int praiseType;

    public Integer get_id()
    {
        return _id;
    }

    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    public int getProductID()
    {
        return productID;
    }

    public void setProductID(int productID)
    {
        this.productID = productID;
    }

    public int getPraiseType()
    {
        return praiseType;
    }

    public void setPraiseType(int praiseType)
    {
        this.praiseType = praiseType;
    }
}
