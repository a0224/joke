package cc.joke.dialog;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cc.joke.R;
import cc.joke.activity.PublishActivity;
import cc.joke.application.GlobalApplication;
import cc.joke.debug.Logger;
import cc.joke.util.ThreadPool;
import cc.joke.util.Util;

public class PublishDialog extends Dialog implements OnClickListener
{
    private PublishActivity mPublishActivity;

    private EditText comment_publish;// 评论内容

    private InputMethodManager inputManager;

    private LinearLayout mImageLayout;

    private ImageView mImageView;

    private final int IMAGE_COUNT = 6;

    List<String> mImgPath = new ArrayList<String>();

    @SuppressWarnings("deprecation")
    public PublishDialog(PublishActivity mPublishActivity)
    {
        super(mPublishActivity, R.style.publish_dialog);
        this.mPublishActivity = mPublishActivity;
        setContentView(R.layout.publish_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = LayoutParams.FILL_PARENT;
        params.height = LayoutParams.FILL_PARENT;
        window.setAttributes(params);
        setCanceledOnTouchOutside(false);

        inputManager = (InputMethodManager) mPublishActivity.getSystemService(Context.INPUT_METHOD_SERVICE);

        initViews();
    }

    private void initViews()
    {

        // 点击事件
        Button btn_send = (Button) findViewById(R.id.send_btn);
        btn_send.setOnClickListener(this);

        comment_publish = (EditText) findViewById(R.id.publish_edit);

        mImageLayout = (LinearLayout) findViewById(R.id.send_images);

        createImageItem(null);
    }

    private ImageView hasView(int index)
    {
        int count = mImageLayout.getChildCount();
        if (index < 0 | index >= count)
        {
            return null;
        }
        ImageView view = (ImageView) mImageLayout.getChildAt(index);

        return view;
    }

    private void updateImageItem(Bitmap bitmap, int tag)
    {
        if (tag == 10000)
        {
            createImageItem(bitmap);
        }
        else
        {
            ImageView imgView = hasView(tag);
            if (imgView != null)
            {
                imgView.setImageBitmap(bitmap);
            }
        }

        int index = mImageLayout.getChildCount() - 1;
        if (index >= IMAGE_COUNT)
        {
            mImageLayout.getChildAt(index).setVisibility(View.GONE);
        }

    }

    private void createImageItem(Bitmap bitmap)
    {

        int w = (int) getContext().getResources().getDimension(R.dimen.publish_send_images_height);
        int h = w;

        ImageView img = new ImageView(getContext());

        LayoutParams params = new LinearLayout.LayoutParams(w, h);
        img.setLayoutParams(params);
        img.setBackgroundResource(R.drawable.pubish_add_image_bg);

        if (bitmap == null)
        {
            img.setImageResource(R.drawable.pubish_add_image);
            mImageLayout.addView(img);
            img.setTag(10000);
        }
        else
        {
            img.setImageBitmap(bitmap);
            mImageLayout.addView(img, mImageLayout.getChildCount() - 1);
            img.setTag(mImageLayout.indexOfChild(img));
        }

        img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Object obj = v.getTag();
                if (obj != null)
                {
                    // requestCode必须大于等于0
                    mPublishActivity.startActivityForResult(i, (Integer) obj);
                }
            }
        });

    }

    public void show()
    {
        super.show();

        ThreadPool.add(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(200);
                }
                catch (InterruptedException e)
                {
                    Logger.error(e);
                }
                // 弹出软键盘
                inputManager.showSoftInput(comment_publish, 0);
            }
        });

    }

    @Override
    public void cancel()
    {
        // 清空
        comment_publish.setText("");
        inputManager.hideSoftInputFromWindow(comment_publish.getWindowToken(), 0);
        super.cancel();
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.send_btn)
        {
            String content = comment_publish.getText().toString();
            if (TextUtils.isEmpty(content))
            {
                return;
            }
            if (content.length() > 500)
            {
                Util.toast(mPublishActivity, Util.getResourcesStr(R.string.commit_comment_large));
                return;
            }
            if (GlobalApplication.getLoginInfo() != null)
            {
                int userId = GlobalApplication.getLoginInfo().getUserid();
                String userName = GlobalApplication.getLoginInfo().getUsername();
                String userIcon = GlobalApplication.getLoginInfo().getUsericon();
                mPublishActivity.publish(userId, userName, userIcon, content, mImgPath);
            }
            else
            {
                Util.toast(getContext(), "请先去登陆！");
            }

            cancel();
        }
    }

    public void showImage(String path, int tag)
    {
        if (TextUtils.isEmpty(path))
        {
            return;
        }
        mImgPath.add(path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        updateImageItem(bitmap, tag);
    }
}
