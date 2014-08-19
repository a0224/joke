package cc.joke.entity;

public class T_Pay
{
    /** 维护主键KEY **/
    private Integer _id;

    /** 是否支付，1支付，0未支付 **/
    private int pay;

    /**
     * @return 返回 _id
     */
    public Integer get_id()
    {
        return _id;
    }

    /**
     * @param 对_id进行赋值
     */
    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    /**
     * @return 返回 pay
     */
    public int getPay()
    {
        return pay;
    }

    /**
     * @param 对pay进行赋值
     */
    public void setPay(int pay)
    {
        this.pay = pay;
    }
}
