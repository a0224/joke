package cc.joke.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import cc.joke.R;
import cc.joke.debug.Logger;
import cc.joke.util.ThreadPool;

public class LoadingDialog extends Dialog
{
    private Context context;

    private boolean isok = false; // 是否转起来

    private int r = 0;// 旋转的角度

    @SuppressWarnings("deprecation")
    public LoadingDialog(Context context)
    {
        super(context, R.style.loading_dialog);
        this.context = context;
        setContentView(R.layout.loading_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = LayoutParams.FILL_PARENT;
        params.height = LayoutParams.FILL_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setCanceledOnTouchOutside(false);// 点击对话框外部对话框还是显示
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        // 监听返回键，让其无效
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void show()
    {
        super.show();
        // 图片转动起来
        isok = true;
        ThreadPool.add(new TrunImage());
    }

    @Override
    public void cancel()
    {
        super.cancel();
        // 图片停止转动
        isok = false;
    }

    class TrunImage implements Runnable
    {

        Handler mHandler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                Resources res = LoadingDialog.this.getContext().getResources();
                Bitmap img = BitmapFactory.decodeResource(res, R.drawable.loading);
                Matrix matrix = new Matrix();
                matrix.postRotate(r);
                int width = img.getWidth();
                int height = img.getHeight();
                Bitmap img_a = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
                ImageView iv = (ImageView) findViewById(R.id.loading);
                iv.setImageBitmap(img_a);
            }
        };

        public void run()
        {
            while (isok)
            {
                try
                {
                    mHandler.sendMessage(mHandler.obtainMessage(0));
                    Thread.sleep(100);
                    r += 30;
                }
                catch (Exception e)
                {
                    Logger.error(e);
                }
            }
        }
    }

}
