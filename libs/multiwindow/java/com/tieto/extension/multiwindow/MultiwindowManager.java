/*
 * Copyright (C) 2014 Tieto Poland Sp. z o.o.
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
package com.tieto.extension.multiwindow;

import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.ActivityManager.StackBoxInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;

import java.util.List;

public class MultiwindowManager {

    private static String TAG = "MultiwindowManager";

    private IActivityManager mService = null;
    private Context mContext = null;

    public MultiwindowManager(Context ctx) {
        mService = ActivityManagerNative.getDefault();
        mContext = ctx;
    }

    public void startActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_RUN_IN_WINDOW);
        mContext.startActivity(intent);
    }

    public boolean relayoutWindow(int stackId, Rect windowFrame) {
        try {
            return mService.relayoutWindow(stackId, windowFrame);
        } catch (RemoteException e) {
            Log.e(TAG, "relayoutWindow failed ", e);
        }
        return false;
    }

    public SparseArray<Rect> getAllWindows() {
        SparseArray<Rect> ret = new SparseArray<Rect>();
        List<StackBoxInfo> list;
        try {
            list = mService.getStackBoxes();
        } catch (RemoteException e) {
            Log.e(TAG, "getAllWindows failes", e);
            return ret;
        }
        for (StackBoxInfo sb : list) {
            if (sb.floating) {
                ret.append(sb.stackId, sb.bounds);
            }
        }
        return ret;
    }
}
