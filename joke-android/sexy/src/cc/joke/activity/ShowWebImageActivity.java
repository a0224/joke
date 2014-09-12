package cc.joke.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.debug.Logger;
import cc.joke.view.ZoomableImageView;

public class ShowWebImageActivity extends Activity
{
    private TextView imageTextView = null;

    private String imagePath = null;

    private ZoomableImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_webimage);
        this.imagePath = getIntent().getStringExtra("image");

        this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
        imageTextView.setText(this.imagePath);
        imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);
        Logger.i("ShowWebImageActivity", "JavascriptInterface ShowWebImageActivity:" + imagePath);

        new AsyncTask<Void, Void, Void>()
        {
            Bitmap bitmap = null;

            protected Void doInBackground(Void... params)
            {
                try
                {
                    bitmap = ((BitmapDrawable) ShowWebImageActivity.loadImageFromUrl(imagePath)).getBitmap();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result)
            {
                if (bitmap != null)
                {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }.execute(null, null, null);
    }

    public static Drawable loadImageFromUrl(String url) throws IOException
    {
        URL m = new URL(url);
        InputStream i = (InputStream) m.getContent();
        Drawable d = Drawable.createFromStream(i, "src");

        return d;
    }
}
