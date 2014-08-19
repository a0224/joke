package cc.joke.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SuperFragmentPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> fragments;

    private List<String> titles;

    public SuperFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles)
    {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    /**
     * 得到每个页面
     */
    @Override
    public Fragment getItem(int arg0)
    {
        return (fragments == null || fragments.size() == 0) ? null : fragments.get(arg0);
    }

    /**
     * 每个页面的title
     */
    public CharSequence getPageTitle(int position)
    {
        return (titles.size() > position) ? titles.get(position) : "";
    }

    /**
     * 页面的总个数
     */
    public int getCount()
    {
        return fragments == null ? 0 : fragments.size();
    }
}
