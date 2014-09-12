package cc.joke.http;

import org.json.JSONObject;

import cc.joke.common.Constants;
import cc.joke.entity.Feedback;

public class FeedbackRequest extends BaseRequest
{

    public boolean doSubmit(Feedback feedback)
    {
        putParameter("P_DEVICE_ID", "");
        putParameter("CONTENT", feedback.getContent());
        putParameter("EMAIL", feedback.getEmail());
        putParameter("NAME", feedback.getName());
        putParameter("PRODUCTID", feedback.getProductID());
        putParameter("QQ", feedback.getQq());
        JSONObject json = getResponse(Constants.FEEDBACK_URL);
        if (json != null)
        {
            try
            {
                if (!json.isNull("Result"))
                {
                    int result = json.getInt("Result");
                    return result == 0;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

}
