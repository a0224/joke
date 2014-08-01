/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.joke.util;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * An interface which defines the contract between a ViewFlow and a
 * FlowIndicator.<br/>
 * A FlowIndicator is responsible to show an visual indicator on the total
 * views number and the current visible view.<br/>
 */
public interface FlowIndicator
{

    /**
     * Set the current ViewFlow. This method is called by the ViewFlow when
     * the FlowIndicator is attached to it.
     * 
     * @param view
     */
    public void setViewPager(ViewPager view);

    /**
     * The scroll position has been changed. A FlowIndicator may implement
     * this method to reflect the current position
     * 
     * @param h
     * @param v
     * @param oldh
     * @param oldv
     */
    public void onScrolled(int h, int v, int oldh, int oldv);

    /**
     * This method is called when a new View has been scrolled to.
     * 当一个新视图移出来时调用
     * 
     * @param view the {@link View} currently in focus.
     * @param position The position in the adapter of the {@link View}
     *            currently in focus.
     */
    void onSwitched(int position);
}
