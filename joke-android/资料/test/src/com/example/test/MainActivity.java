package com.example.test;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is
        // present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            initViews(rootView);
            return rootView;
        }
    }
    
    
    public static void initViews(View rootView)
    {

        final TextView mac = (TextView) rootView.findViewById(R.id.mac);
        final TextView device = (TextView) rootView.findViewById(R.id.device);
        final TextView subscriber = (TextView) rootView.findViewById(R.id.subscriber);
        final TextView display = (TextView) rootView.findViewById(R.id.display);

        
        Button button1 = (Button) rootView.findViewById(R.id.button1);
        Button button2 = (Button) rootView.findViewById(R.id.button2);
             
        button1.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Build.mIsOpen = !Build.mIsOpen; 
            }
        });
        
        button2.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mac.setText("mac:" + Utils.getMacAddress());

                device.setText("devicesid:" + Utils.getDeviceId());

                subscriber.setText("imei:" + Utils.getSubscriberId());

                display.setText("display:" + Utils.getMetricsWidth() + "X" + Utils.getMetricsHeight());
            }
        });
    }

}
