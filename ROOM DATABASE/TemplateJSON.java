package com.cashmoney.calculate.report.manager.dto;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TemplateJSON {
    @SerializedName("backgroundId")

    /* renamed from: a  reason: collision with root package name */
    int f1248a;
    @SerializedName("createdOn")
    String b;
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    String c;
    @SerializedName("viewsInCard")
    List<ViewInCard> d;

    public TemplateJSON(String str, String str2, int i, List<ViewInCard> list) {
        this.c = str;
        this.b = str2;
        this.f1248a = i;
        this.d = list;
    }

    public String getName() {
        return this.c;
    }

    public void setName(String str) {
        this.c = str;
    }

    public String getCreatedOn() {
        return this.b;
    }

    public void setCreatedOn(String str) {
        this.b = str;
    }

    public int getBackgroundId() {
        return this.f1248a;
    }

    public void setBackgroundId(int i) {
        this.f1248a = i;
    }

    public List<ViewInCard> getViewsInCard() {
        return this.d;
    }

    public void setViewsInCard(List<ViewInCard> list) {
        this.d = list;
    }
}
