/*
 * Copyright (C) 2017 The Android Open Source Project
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

package topphotoeditor.bokehphotoeditor.stickersView2;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import topphotoeditor.bokehphotoeditor.editor.util.Constant;

/**
 * Builder class for constructing a query for downloading a font.
 */
public class QueryBuilder {

    @NonNull
    private String mFamilyName;

    @Nullable
    private Float mWidth = null;

    @Nullable
    private Integer mWeight = null;

    @Nullable
    private Float mItalic = null;

    @Nullable
    private Boolean mBesteffort = null;

    public QueryBuilder(@NonNull String familyName) {
        mFamilyName = familyName;
    }

    public QueryBuilder withFamilyName(@NonNull String familyName) {
        mFamilyName = familyName;
        return this;
    }

    public QueryBuilder withWidth(float width) {
        if (width <= Constant.WIDTH_MIN) {
            throw new IllegalArgumentException("Width must be more than 0");
        }
        mWidth = width;
        return this;
    }

    public QueryBuilder withWeight(int weight) {
        if (weight <= Constant.WEIGHT_MIN || weight >= Constant.WEIGHT_MAX) {
            throw new IllegalArgumentException(
                    "Weight must be between 0 and 1000 (exclusive)");
        }
        mWeight = weight;
        return this;
    }

    public QueryBuilder withItalic(float italic) {
        if (italic < Constant.ITALIC_MIN || italic > Constant.ITALIC_MAX) {
            throw new IllegalArgumentException("Italic must be between 0 and 1 (inclusive)");
        }
        mItalic = italic;
        return this;
    }

    public QueryBuilder withBestEffort(boolean bestEffort) {
        mBesteffort = bestEffort;
        return this;
    }

    public String build() {
        if (mWeight == null && mWidth == null && mItalic == null && mBesteffort == null) {
            return mFamilyName;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("name=").append(mFamilyName);
        if (mWeight != null) {
            builder.append("&weight=").append(mWeight);
        }
        if (mWidth != null) {
            builder.append("&width=").append(mWidth);
        }
        if (mItalic != null) {
            builder.append("&italic=").append(mItalic);
        }
        if (mBesteffort != null) {
            builder.append("&besteffort=").append(mBesteffort);
        }
        return builder.toString();
    }
}
