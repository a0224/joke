package cc.joke.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.joke.common.Constants;
import cc.joke.debug.Logger;
import cc.joke.entity.T_Comment;
import cc.joke.entity.T_JokeInfo;
import cc.joke.entity.T_Result;

public class JokeCommentRequest extends BaseRequest
{

    public T_Comment getComment(JSONObject o) throws Exception
    {
        if (o == null)
        {
            return null;
        }
        T_Comment comment = new T_Comment();
        comment.setContent(o.getString("msg"));
        comment.setCreateDate(o.getString("createdate"));
        comment.setModel(o.getString("model"));
        comment.setPid(o.getInt("jokeid"));
        comment.setUsername(o.getString("username"));
        if (!o.isNull("usericon"))
            comment.setUsericon(o.getString("usericon"));
        return comment;
    }

    // 获取评论信息
    public T_Result getComments(Integer jokeId, int pageNo)
    {

        T_Result obj = new T_Result();

        putParameter("op", Constants.OP_JOKE_TALK);
        putParameter("page", pageNo);
        putParameter("pagesize", 8);
        putParameter("id", jokeId);
        JSONObject resultObject = getResponse(Constants.OP_JOKE_TALK_LIST_URL);

        try
        {
            if (resultObject == null)
            {
                return null;
            }
            if (resultObject.getInt("status") != 1)
            {
                return null;
            }

            JSONArray arr = resultObject.getJSONArray("data");
            if (arr != null && arr.length() > 0)
            {
                List<T_Comment> list = new ArrayList<T_Comment>(arr.length());
                for (int i = 0; i < arr.length(); i++)
                {
                    list.add(getComment(arr.getJSONObject(i)));
                    obj.mState = 0;
                    obj.mData = list;
                }
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        return obj;
    }

    // 提交评论信息
    public void commitComment(T_Comment comment)
    {
        try
        {

            putParameter("op", Constants.OP_JOKE_ADD_TALK);
            putParameter("msg", comment.getContent());
            putParameter("model", comment.getModel());
            putParameter("jokeid", comment.getPid());
            putParameter("createdate", comment.getCreateDate());
            putParameter("userid", comment.getUserid());
            putParameter("username", comment.getUsername());
            putParameter("usericon", comment.getUsericon());

            getResponse(Constants.OP_JOKE_ADD_TALK_URL);
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
    }
}
