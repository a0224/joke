package cc.joke.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.util.Util;

public class NetworkDialog extends Dialog implements OnClickListener
{
    private Button conti;// 继续

    private Button setting;// 设置

    private Button exit;// 退出

    private Context context;

    public NetworkDialog(Context context)
    {
        super(context, R.style.network_dialog);
        this.context = context;
        setContentView(R.layout.network_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (GlobalApplication.widthPixels * 0.9);
        params.height = (int) (GlobalApplication.heightPixels * 0.35);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setCanceledOnTouchOutside(false);// 点击对话框外部对话框还是显示

        conti = (Button) findViewById(R.id.network_continue);
        setting = (Button) findViewById(R.id.network_setting);
        exit = (Button) findViewById(R.id.network_exit);

        conti.setOnClickListener(this);
        setting.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    public void show(String msg)
    {
        TextView content = (TextView) findViewById(R.id.no_network_content);
        content.setText(msg);
        super.show();
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.network_continue:
                cancel();
                break;
            case R.id.network_setting:
                Intent intent = null;
                // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10)
                {
                    intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                }
                else
                {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }
                context.startActivity(intent);
                cancel();
                break;
            case R.id.network_exit:
                cancel();
                Util.exit();
                break;
        }
    }
}
