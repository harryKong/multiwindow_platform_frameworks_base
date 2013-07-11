/*
 * Copyright (C) 2013 Tieto Poland Sp. z o.o.
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

package com.android.server.am;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.os.Looper;

public class StackAdapter {

    /**
     * Main stack for this {@link StackAdapter}
     */
    ActivityStack mMainStack;

    /**
     * Based on a (risky) assumption that we'll use integers as unique panel-ActivityStack
     * mapping, SparseArray seems a perfect solution to hold the list of active
     * ActivityStacks  
     */
    private final Map<Integer, ActivityStack> mPanels = new HashMap<Integer, ActivityStack>();

    public StackAdapter(ActivityStack mainStack) {
        mMainStack = mainStack;
    }

    public boolean containsStackPanel(int panelId) {
        return (mPanels.get(panelId) != null);
    }

    public boolean containsStackMain(int stackId) {
        return (mMainStack.getId() == stackId);
    }

    public boolean containsStackAny(int stackId) {
        return (containsStackMain(stackId) || containsStackPanel(stackId));
    }

    /**
     * Returns a panel stack with a given ID
     * 
     * @param panelId
     * @return null if {@link ActivityStack} with given ID is not found
     */
    public ActivityStack getStackPanel(int panelId) {
        return mPanels.get(panelId);
    }

    /**
     * Returns main stack for this {@link StackAdapter}
     * @return
     */
    public ActivityStack getStackMain() {
        return mMainStack;
    }

    /**
     * Returns mains stack for this {@link StackAdapter}
     * @return
     */
    public ActivityStack getStackAny(int stackId) {
        return (mMainStack.getId() == stackId) ? mMainStack : mPanels.get(stackId);
    }

    /**
     * Removes a CSPanel {@link ActivityStack} with a given ID
     * 
     * @param panelId
     * @return true if panelId was found, false otherwise
     */
    public boolean removeStackPanel(int panelId) {
        if (getStackPanel(panelId) != null) {
            mPanels.remove(panelId);
            return true;
        }
        return false;
    }

    /**
     * Installs given {@link ActivityStack} as a CSPanel
     * 
     * @return
     */
    public ActivityStack installStackPanel(ActivityStack panelStack) {
        mPanels.put(panelStack.mStackId, panelStack);
        return panelStack;
    }

    public ActivityStack installStackPanel(ActivityManagerService am, Context context, Looper looper) {
        ActivityStack panelStack = ActivityStack.buildPanelStack(am, context, looper);
        return installStackPanel(panelStack);
    }

    public Collection<ActivityStack> getStacksPanels() {
        return mPanels.values();
    }
}
