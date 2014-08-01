package cc.joke.entity;

public class BusinessMessage
{
    private int id;

    private String msg_name;

    private int msg_type;

    private int msg_network;

    private String msg_title;

    private String msg_content;

    private String cmd_target;

    private String msg_pic;

    private String used_time;

    private String unused_time;

    private int sort;

    private String createtime;

    private String modifytime;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public String getUsed_time()
    {
        return used_time;
    }

    public void setUsed_time(String used_time)
    {
        this.used_time = used_time;
    }

    public String getUnused_time()
    {
        return unused_time;
    }

    public void setUnused_time(String unused_time)
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

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public String getModifytime()
    {
        return modifytime;
    }

    public void setModifytime(String modifytime)
    {
        this.modifytime = modifytime;
    }

}
