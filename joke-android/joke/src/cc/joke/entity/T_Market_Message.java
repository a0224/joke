package cc.joke.entity;

public class T_Market_Message
{
    private int _id;

    private String msg_name;

    private int msg_type;

    private int msg_network;

    private String msg_title;

    private String msg_content;

    private String cmd_target;

    private String msg_pic;

    private long used_time;

    private long unused_time;

    private int sort;

    private long createtime;

    private long modifytime;

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public String getMsg_name()
    {
        return msg_name;
    }

    public void setMsg_name(String msg_name)
    {
        this.msg_name = msg_name;
    }

    public int getMsg_type()
    {
        return msg_type;
    }

    public void setMsg_type(int msg_type)
    {
        this.msg_type = msg_type;
    }

    public int getMsg_network()
    {
        return msg_network;
    }

    public void setMsg_network(int msg_network)
    {
        this.msg_network = msg_network;
    }

    public String getMsg_title()
    {
        return msg_title;
    }

    public void setMsg_title(String msg_title)
    {
        this.msg_title = msg_title;
    }

    public String getMsg_content()
    {
        return msg_content;
    }

    public void setMsg_content(String msg_content)
    {
        this.msg_content = msg_content;
    }

    public String getCmd_target()
    {
        return cmd_target;
    }

    public void setCmd_target(String cmd_target)
    {
        this.cmd_target = cmd_target;
    }

    public String getMsg_pic()
    {
        return msg_pic;
    }

    public void setMsg_pic(String msg_pic)
    {
        this.msg_pic = msg_pic;
    }

    public long getUsed_time()
    {
        return used_time;
    }

    public void setUsed_time(long used_time)
    {
        this.used_time = used_time;
    }

    public long getUnused_time()
    {
        return unused_time;
    }

    public void setUnused_time(long unused_time)
    {
        this.unused_time = unused_time;
    }

    public int getSort()
    {
        return sort;
    }

    public void setSort(int sort)
    {
        this.sort = sort;
    }

    public long getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(long createtime)
    {
        this.createtime = createtime;
    }

    public long getModifytime()
    {
        return modifytime;
    }

    public void setModifytime(long modifytime)
    {
        this.modifytime = modifytime;
    }

}
