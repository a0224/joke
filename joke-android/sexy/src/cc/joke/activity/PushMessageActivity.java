package cc.joke.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cc.joke.R;
import cc.joke.cache.BitmapCache;
import cc.joke.cache.ImageDownLoadCallback;

public class PushMessageActivity extends BaseActivity
{

    private View mRoot;

    private TextView mTitle;

    private ImageView mDelete;

    private ImageView mImage;

    private TextView mText;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.push_message_layout);

        initViews(getIntent());
    }

    private void initViews(Intent intent)
    {

        mRoot = findViewById(R.id.push_message_item);
        mTitle = (TextView) findViewById(R.id.title);
        mDelete = (ImageView) findViewById(R.id.delete);
        mImage = (ImageView) findViewById(R.id.image);
        mText = (TextView) findViewById(R.id.text);

        mImage.setVisibility(View.GONE);

        String titleStr = intent.getStringExtra("title");
        String imageUrl = intent.getStringExtra("imageUrl");
        String description = intent.getStringExtra("description");

        if (!TextUtils.isEmpty(imageUrl))
        {
            BitmapCache.getInstance().getBitmap(imageUrl, mImage, new ImageDownLoadCallback()
            {
                @Override
                public void imageDownLoaded(Bitmap bm)
                {
                    mImage.setVisibility(View.VISIBLE);
                }
            }, null);
        }

        if (!TextUtils.isEmpty(description))
        {
            mText.setText(description);
        }

        mRoot.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(PushMessageActivity.this, "点击广告", Toast.LENGTH_SHORT).show();
            }
        });

        mDelete.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PushMessageActivity.this.finish();
            }
        });
    }
}
