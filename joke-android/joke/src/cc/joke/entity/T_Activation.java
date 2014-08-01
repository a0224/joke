package cc.joke.entity;

/**
 * 产品好激活信息
 * 
 * @author
 */
public class T_Activation
{
    /** 维护主键KEY **/
    private Integer _id;

    /** 是否激活 1激活 其它未激活 **/
    private int status;

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
}
