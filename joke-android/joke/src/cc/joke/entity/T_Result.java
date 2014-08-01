package cc.joke.entity;

public class T_Result
{
    /**
     * 操作结果
     */
    public int mState;

    /**
     * 结果数据
     */
    public Object mData;

    public int getState()
    {
        return mState;
    }

    public void setState(int mState)
    {
        this.mState = mState;
    }

    public Object getData()
    {
        return mData;
    }

    public void setData(Object mData)
    {
        this.mData = mData;
    }

}
