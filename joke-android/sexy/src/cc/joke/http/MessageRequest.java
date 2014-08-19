package cc.joke.http;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.cache.ImageDownLoadCallback;
import cc.joke.common.Constants;
import cc.joke.debug.Logger;
import cc.joke.entity.BusinessMessage;

public class MessageRequest extends BaseRequest implements Runnable
{
    private Context context;

    private BusinessMessage[] messages;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message message)
        {
            for (BusinessMessage msg : messages)
            {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                final Notification notif = new Notification();
                notif.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.icon = R.drawable.icon;
                notif.defaults = Notification.DEFAULT_SOUND;
                notif.tickerText = msg.getMsg_content();
                notif.contentView = new RemoteViews(context.getPackageName(), R.layout.business_msg_notif);
                notif.contentView.setTextViewText(R.id.title, msg.getMsg_title());
                notif.contentView.setTextViewText(R.id.content, "\u3000" + msg.getMsg_content());

                GlobalApplication.bitmapCache.getBitmap(msg.getMsg_pic(), null, new ImageDownLoadCallback()
                {
                    public void imageDownLoaded(Bitmap bm)
                    {
                        if (bm != null)
                        {
                            notif.contentView.setImageViewBitmap(R.id.icon, bm);
                            notif.contentView.setViewVisibility(R.id.icon, View.VISIBLE);
                        }
                    }
                });

                try
                {
                    if (msg.getMsg_type() == 0)
                    {// 广告消息
                        Intent intent = new Intent();
                        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
                        notif.contentIntent = pIntent;
                        manager.notify(msg.getId(), notif);
                    }
                    else
                    {// 指令消息
                        int type = -1;
                        int index = msg.getCmd_target().indexOf(":");
                        if (index == -1)
                        {
                            type = Integer.parseInt(msg.getCmd_target());
                        }
                        else
                        {
                            String typeStr = msg.getCmd_target().substring(0, index);
                            type = Integer.parseInt(typeStr);
                        }
                        switch (type)
                        {
                            case 1:// 打开市场
                                String packageName = context.getPackageName();
                                PackageManager pm = context.getPackageManager();
                                Intent intent = pm.getLaunchIntentForPackage(packageName);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
                                notif.contentIntent = pIntent;
                                manager.notify(msg.getId(), notif);
                                break;
                            case 2:// 推送栏目
                                String[] arr = msg.getCmd_target().split(":");
                                if (arr != null && arr.length >= 3)
                                {
                                    // String shelf_id = arr[1];
                                    // String dynaName = arr[2];
                                    // String url =
                                    // Constants.HOST+"/dynaProductList?"+shelf_id;
                                    // intent = new Intent();
                                    // intent.setClass(context,DynaProductInfoActivity.class);
                                    // intent.putExtra("dynaName", dynaName);
                                    // intent.putExtra("url",url);
                                    // pIntent =
                                    // PendingIntent.getActivity(context, 0,
                                    // intent, 0);
                                    // notif.contentIntent = pIntent;
                                    // manager.notify(msg.getId(), notif);
                                }
                                break;
                            case 3:// 推送游戏
                                arr = msg.getCmd_target().split(":");
                                if (arr != null && arr.length > 0)
                                {
                                    // String id = arr[arr.length-1];
                                    // intent = new Intent();
                                    // intent.setClass(context,ProductInfo.class);
                                    // intent.putExtra("productId", id);
                                    // pIntent =
                                    // PendingIntent.getActivity(context, 0,
                                    // intent, 0);
                                    // notif.contentIntent = pIntent;
                                    // manager.notify(msg.getId(), notif);
                                }
                                break;
                            case 4:// 推送链接
                                String url = msg.getCmd_target().substring(2);
                                intent = new Intent(Intent.ACTION_VIEW);
                                Uri uri = Uri.parse(url);
                                intent.setData(uri);
                                pIntent = PendingIntent.getActivity(context, 0, intent, 0);
                                notif.contentIntent = pIntent;
                                manager.notify(msg.getId(), notif);
                                break;
                            case 5:// 打开游戏
                                arr = msg.getCmd_target().split(":");
                                if (arr != null && arr.length > 0)
                                {
                                    packageName = arr[4];
                                    pm = context.getPackageManager();
                                    intent = pm.getLaunchIntentForPackage(packageName);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    pIntent = PendingIntent.getActivity(context, 0, intent, 0);
                                    notif.contentIntent = pIntent;
                                    manager.notify(msg.getId(), notif);
                                }
                                break;
                            case 6:// 安装游戏
                                break;
                        }
                    }

                }
                catch (Exception e)
                {
                    Logger.error(e);
                }
            }

        }
    };

    public MessageRequest(Context context)
    {
        this.context = context;
    }

    public BusinessMessage getMessage(JSONObject o) throws Exception
    {
        if (o == null)
        {
            return null;
        }
        BusinessMessage message = new BusinessMessage();
        message.setId(o.getInt("id"));
        message.setCmd_target(o.getString("cmd_target"));
        message.setCreatetime(o.getString("createtime"));
        message.setModifytime(o.getString("modifytime"));
        message.setMsg_content(o.getString("msg_content"));
        message.setMsg_name(o.getString("msg_name"));
        message.setMsg_network(o.getInt("msg_network"));
        message.setMsg_pic(o.getString("msg_pic"));
        message.setMsg_title(o.getString("msg_title"));
        message.setMsg_type(o.getInt("msg_type"));
        message.setSort(o.getInt("sort"));
        message.setUnused_time(o.getString("unused_time"));
        message.setUsed_time(o.getString("used_time"));

        return message;
    }

    public JSONArray getJSONArrayByName(JSONObject obj, String name)
    {
        try
        {
            return obj.getJSONArray(name);
        }
        catch (Exception e)
        {
            Logger.error(e);
            return null;
        }
    }

    public void run()
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        if (wifiState != null && wifiState == State.CONNECTED)
        {// WIFI
         // messages = getMessages(ConnectivityManager.TYPE_WIFI);
        }
        else if (mobileState != null && mobileState == State.CONNECTED)
        {// GPRS
         // messages = getMessages(ConnectivityManager.TYPE_MOBILE);
        }

        if (messages != null && messages.length > 0)
        {
            mHandler.sendMessage(mHandler.obtainMessage(0));
        }

    }

    // 请求首页信息
    public BusinessMessage[] getMessages(int netWorkType)
    {
        try
        {
            putParameter("netWorkType", netWorkType);
            JSONObject resultObject = getResponse(Constants.OP_BUSINESS_MESSAGE_URL);
            if (resultObject == null)
            {
                Logger.e("", "请求运营消息列表返回空值");
                return null;
            }
            if (resultObject.getInt("Result") != 0)
            {
                Logger.e("", "请求运营消息列表发生错误");
                return null;
            }

            Logger.i("获取运营消息请求json", resultObject.toString());
            JSONArray messages = getJSONArrayByName(resultObject, "Data");
            if (messages != null && messages.length() > 0)
            {
                BusinessMessage[] tmm = new BusinessMessage[messages.length()];
                for (int i = 0; i < messages.length(); i++)
                {
                    JSONObject o = (JSONObject) messages.get(i);
                    tmm[i] = getMessage(o);
                }
                return tmm;
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        return null;

    }
}
