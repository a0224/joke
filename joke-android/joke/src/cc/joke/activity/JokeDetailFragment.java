package cc.joke.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.adapter.ShowImagesAdapter;
import cc.joke.application.GlobalApplication;
import cc.joke.dialog.LoadingDialog;
import cc.joke.entity.T_JokeInfo;
import cc.joke.http.JokePraiseRequest;
import cc.joke.util.ThreadPool;
import cc.joke.util.Util;
import cc.joke.view.ShowGallery;
import cc.joke.view.ZoomableImageView;

@SuppressLint("ValidFragment")
public class JokeDetailFragment extends Fragment
{
    // 当前视图
    private View view;

    // 产品对象
    private T_JokeInfo jokeInfo;

    // 正在加载对话框
    private LoadingDialog mLoadingDialog;

    // 名称
    private TextView jokeTopTitle;

    /** 图片显示容器 **/
    private ShowGallery mImageGallery;

    /** Gallery大图数据适配器 **/
    private ShowImagesAdapter mImageAdapter;

    /** 浏览大图 **/
    private Dialog mShowImageDialog;

    private ZoomableImageView zoomImageView;

    // Handler
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:

                    break;
                case 2:// 显示正在加载对话框
                    mLoadingDialog.show();
                    break;
                case 3:// 关闭正在加载对话框
                    mLoadingDialog.cancel();
                    break;
                case 5:
                    break;
            }
        }
    };

    public JokeDetailFragment(T_JokeInfo jokeInfo, TextView joke_top_title)
    {
        this.jokeInfo = jokeInfo;
        this.jokeTopTitle = joke_top_title;
    }

    // 分享
    public void share()
    {
        if (jokeInfo != null)
        {
            // String shareContent = getString(R.string.share_app_url,
            // jokeInfo.getCode());
            String shareContent = "http://bcs.duapp.com/joke-bucket/update/Joke1.0.apk";
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT",
                    getString(R.string.share_app_message, jokeInfo.getDescription(), shareContent));
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            Intent localIntent2 = Intent.createChooser(intent, "分享...");
            startActivity(localIntent2);
        }
    }

    public String getJokeName()
    {
        if (jokeInfo != null && jokeInfo.getTitle() != null)
        {
            return jokeInfo.getTitle();
        }
        return "";
    }

    public void doCommitPraise(final int type)
    {
        if (!Util.isNetworkConnected())
        {
            Util.toast(getActivity(), getString(R.string.noNetwork));
            return;
        }
        ThreadPool.add(new Runnable()
        {

            @Override
            public void run()
            {
                JokePraiseRequest request = new JokePraiseRequest();
                int result = request.doCommit(jokeInfo.getId(), type);
                Message msg = new Message();
                msg.what = 5;
                msg.arg1 = type;
                msg.arg2 = result;
                mHandler.sendMessage(msg);
            }
        });

    }

    private void showGalleryDialog(int position)
    {

        View view = null;
        if (mShowImageDialog == null)
        {
            mShowImageDialog = new Dialog(getActivity(), R.style.search_dialog);
            view = getActivity().getLayoutInflater().inflate(R.layout.joke_detail_image, null);
            zoomImageView = (ZoomableImageView) view.findViewById(R.id.show_joke_detail_imageview);

            mShowImageDialog.setContentView(view);
            ImageButton imageBtn = (ImageButton) view.findViewById(R.id.product_detail_dialog_back);
            imageBtn.setOnClickListener(new ImageButton.OnClickListener()
            {
                public void onClick(View v)
                {
                    mShowImageDialog.dismiss();
                }
            });
        }

        String[] images = jokeInfo.getDspImages().split(",");
        GlobalApplication.bitmapCache.getBitmap(R.drawable.loading_background + "", zoomImageView, null);
        GlobalApplication.bitmapCache.getBitmap(images[position], zoomImageView, null);

        mShowImageDialog.show();
        WindowManager.LayoutParams lp = mShowImageDialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        mShowImageDialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (view == null)
        {
            view = inflater.inflate(R.layout.joke_detail, container, false);
            mLoadingDialog = new LoadingDialog(getActivity());

            initViews();
        }
        else
        {
            View oldParent = (View) view.getParent();
            if (oldParent != container)
            {
                ((ViewGroup) oldParent).removeView(view);
            }
        }
        return view;
    }

    private void initViews()
    {
        if (jokeInfo == null)
        {
            return;
        }
        else
        {
            // title
            jokeTopTitle.setText(getJokeName());

            // description
            TextView description = (TextView) view.findViewById(R.id.joke_description);
            description.setText("\u3000\u3000" + jokeInfo.getDescription());

            // Gallery图片展示
            if (jokeInfo.getDspImages() != null && !"".equals(jokeInfo.getDspImages()))
            {
                String[] images = jokeInfo.getDspImages().split(",");
                LinearLayout showImage_ball = (LinearLayout) view.findViewById(R.id.joke_showImage_ball);
                showImage_ball.removeAllViews();
                List<ImageView> balls = new ArrayList<ImageView>();
                for (int i = 0; i < images.length; i++)
                {
                    ImageView ball = new ImageView(GlobalApplication.context);
                    ball.setPadding(3, 3, 5, 3);
                    if (i == 0)
                    {
                        GlobalApplication.bitmapCache.getBitmap(R.drawable.product_ball_2 + "", ball, null);
                    }
                    else
                    {
                        GlobalApplication.bitmapCache.getBitmap(R.drawable.product_ball_1 + "", ball, null);
                    }
                    balls.add(ball);
                    showImage_ball.addView(ball);
                }
                mImageGallery = (ShowGallery) view.findViewById(R.id.joke_showImage);
                mImageGallery.setImages(balls);
                mImageAdapter = new ShowImagesAdapter(mImageGallery, images);
                mImageGallery.setAdapter(mImageAdapter);
                int p = ((mImageAdapter.getCount() / 2) % balls.size() == 0) ? mImageAdapter.getCount() / 2
                        : (mImageAdapter.getCount() / 2 - (mImageAdapter.getCount() / 2) % balls.size());
                mImageGallery.setSelection(p);
                mImageGallery.setOnItemClickListener(new Gallery.OnItemClickListener()
                {
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
                    {
                        showGalleryDialog(position);
                    }
                });
            }
        }
    }
}
