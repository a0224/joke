package cc.joke.dialog;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.debug.Logger;
import cc.joke.entity.CheckClient;
import cc.joke.util.JokeDownloadManager;
import cc.joke.util.ThreadPool;
import cc.joke.util.Util;

public class VerCheDilog {
	
    private Dialog mMessageDialog;
	
    public void showCheckMessage(final CheckClient cc,final Activity activity, LayoutInflater layoutInflater)
    {
        mMessageDialog = new Dialog(activity);
        mMessageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = layoutInflater.inflate(R.layout.check_client_dialog, null);
        Button updateButton = (Button) contentView.findViewById(R.id.check_client_update);
        updateButton.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
				JokeDownloadManager.getInstance().startApk(cc.getBaseUrl(),cc.getVersionName());
                mMessageDialog.dismiss();
                mMessageDialog = null;
            }
        });
        Button cancelButton = (Button) contentView.findViewById(R.id.check_client_cancel);
        cancelButton.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                mMessageDialog.dismiss();
                mMessageDialog = null;
            }
        });
        TextView text = (TextView) contentView.findViewById(R.id.check_client_message);
        StringBuffer buffer = new StringBuffer();
        buffer.append("检测到新版本: ").append(cc.getVersionName()).append("\n").append(cc.getDescription());
        text.setText(buffer.toString());
        mMessageDialog.setContentView(contentView);
        mMessageDialog.setCanceledOnTouchOutside(false);
        mMessageDialog.show();
        Window window = mMessageDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (GlobalApplication.widthPixels * 0.9);
        window.setAttributes(lp);
    }
    
}
