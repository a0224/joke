package cc.joke.activity;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.entity.CheckClient;
import cc.joke.util.JokeDownloadManager;

public class VerCheDilog {
	
    private Dialog mMessageDialog;
	
    public void showCheckMessage(final CheckClient cc,Activity activity, LayoutInflater layoutInflater)
    {
        mMessageDialog = new Dialog(activity);
        mMessageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = layoutInflater.inflate(R.layout.check_client_dialog, null);
        Button updateButton = (Button) contentView.findViewById(R.id.check_client_update);
        updateButton.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                JokeDownloadManager.getInstance().startApk(cc);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
