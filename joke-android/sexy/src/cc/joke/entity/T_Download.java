package cc.joke.entity;

public class T_Download
{

    private long id;

    private String fileName;

    public T_Download(long id, String filename)
    {
        this.id = id;
        this.fileName = filename;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public long getId()
    {
        return id;
    }

    public String getFileName()
    {
        return fileName;
    }

}
