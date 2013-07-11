/**
 * Copyright (C) 2013 Tieto Poland Sp. z o.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * System private API for providing StackInfo
 *
 * {@hide}
 */
public class ActivityStackInfo implements Parcelable {
    public static ActivityStackInfo UNKNOWN = new ActivityStackInfo("Unknown");
    public static ActivityStackInfo MAIN = new ActivityStackInfo("Main");
    public static ActivityStackInfo CORNERSTONE = new ActivityStackInfo("CS");
    public static ActivityStackInfo CORNERSTONE_PANEL = new ActivityStackInfo("CSPanel");

    public final String name;

    private ActivityStackInfo(String stackName) {
        this.name = stackName;
    }

    public boolean isMain() {
        return (MAIN == this);
    }
    public boolean isCornerstone() {
        return (CORNERSTONE == this);
    }
    public boolean isCornerstonePanel() {
        return (CORNERSTONE_PANEL == this);
    }
    public boolean isUnknown() {
        return (UNKNOWN == this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public static final Parcelable.Creator<ActivityStackInfo> CREATOR = new Parcelable.Creator<ActivityStackInfo>() {
        public ActivityStackInfo createFromParcel(Parcel source) {
            String name = source.readString();

            if (name.equals(MAIN.name)) {
                return MAIN;
            } else if (name.equals(CORNERSTONE.name)) {
                return CORNERSTONE;
            } else if (name.equals(CORNERSTONE_PANEL.name)) {
                return CORNERSTONE_PANEL;
            } else {
                return UNKNOWN;
            }
        }
        public ActivityStackInfo[] newArray(int size) {
            return new ActivityStackInfo[size];
        }
    };
}
