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

import android.content.Context;
import android.os.Looper;

import java.util.LinkedList;
import java.util.List;

public class StackAdapterContainer {

    private final List<StackAdapter> mStackAdapters = new LinkedList<StackAdapter>();

    public StackAdapterContainer() {
    }

    /**
     * 
     * @param pReuse
     * @return
     * @note given list is not cleared! You want it emptied? Do i yourself!
     */
    public List<ActivityStack> fillStacksMain(List<ActivityStack> pReuse) {
        for (StackAdapter adapter : mStackAdapters) {
            // add main stacks of this adapter
            pReuse.add(adapter.getStackMain());
        }
        return pReuse;
    }

    /**
     * 
     * @param pReuse
     * @return
     * @note given list is not cleared! You want it emptied? Do i yourself!
     */
    public List<ActivityStack> fillStacksPanels(List<ActivityStack> pReuse) {
        for (StackAdapter adapter : mStackAdapters) {
            // add panels of this adapter
            pReuse.addAll(adapter.getStacksPanels());
        }
        return pReuse;
    }

    /**
     * 
     * @param pReuse
     * @return
     * @note given list is not cleared! You want it emptied? Do i yourself!
     */
    public List<ActivityStack> fillStacksAll(List<ActivityStack> pReuse) {
        for (StackAdapter adapter : mStackAdapters) {
            // add main stack of this adapter
            pReuse.add(adapter.getStackMain());
            // add panels of this adapter
            pReuse.addAll(adapter.getStacksPanels());
        }
        return pReuse;
    }

    /**
     * Returns a list of all installed {@link StackAdapter}
     * @return
     */
    public List<StackAdapter> getAdapters() {
        return mStackAdapters;
    }

    /**
     * Use to get the whole {@link StackAdapter} from a given ActivityStack
     * 
     * @param stackId
     * @return
     */
    public StackAdapter getAdapter(ActivityStack pStack) {
        return getAdapter(pStack.mStackId);
    }

    /**
     * Use to get the whole {@link StackAdapter} from a given stack ID
     * 
     * @param stackId
     * @return
     */
    public StackAdapter getAdapter(int stackId) {
        for (StackAdapter adapter : this.mStackAdapters) {
            if (adapter.containsStackAny(stackId)) {
                return adapter;
            }
        }
        return null;
    }

    /**
     * Use to get {@link ActivityStack} from a given stackId
     * 
     * @param stackId
     * @return
     */
    public ActivityStack getStack(int stackId) {
        for (StackAdapter adapter : this.mStackAdapters) {
            ActivityStack stack = adapter.getStackAny(stackId);
            if (stack != null) {
                return stack;
            }
        }
        return null;
    }

    public StackAdapter installCornerstone(ActivityManagerService am, Context context, Looper looper) {
        ActivityStack cornerstoneStack = ActivityStack.buildCornerstoneStack(am, context, looper);
        return buildAdapter(cornerstoneStack);
    }

    public StackAdapter installMain(ActivityManagerService am, Context context, Looper looper) {
        ActivityStack mainStack = ActivityStack.buildMainStack(am, context, looper);
        return buildAdapter(mainStack);
    }

    private StackAdapter buildAdapter(ActivityStack stack) {
        StackAdapter adapter = new StackAdapter(stack);
        this.mStackAdapters.add(adapter);
        return adapter;
    }
}
