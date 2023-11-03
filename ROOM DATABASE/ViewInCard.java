package com.cashmoney.calculate.report.manager.dto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "viewincard")
public class ViewInCard {
    @SerializedName("deltaAngle")
    public float deltaAngle;
    @SerializedName("height")
    public int height;

    @SerializedName("iconId")
    public int iconId;

    @PrimaryKey(autoGenerate = true)
    public long id;

    @SerializedName("imagePath")
    public String imagePath;
    @SerializedName("isFront")
    public boolean isFront;
    @SerializedName("isSaved")
    public boolean isSaved;
    @SerializedName("pivotX")
    public float pivotX;
    @SerializedName("pivotY")
    public float pivotY;
    @SerializedName("positionX")
    public float positionX;
    @SerializedName("positionY")
    public float positionY;
    @SerializedName("scaleX")
    public float scaleX;
    @SerializedName("scaleY")
    public float scaleY;
    @SerializedName("shapeId")
    public int shapeId;
    @SerializedName("templateId")
    public long templateId;
    @SerializedName("text")
    public String text;
    @SerializedName("tintColor")
    public String tintColor;
    @SerializedName("viewType")
    public int viewType;

    @SerializedName("width")
    public int width;

    public ViewInCard(int i, String str, int i2, int i3, String str2, int i4, int i5, float f, float f2, float f3, float f4, long j, float f5, boolean z, boolean z2, String str3) {
        this.viewType = i;
        this.text = str;
        this.iconId = i2;
        this.shapeId = i3;
        this.imagePath = str2;
        this.width = i4;
        this.height = i5;
        this.positionX = f;
        this.positionY = f2;
        this.scaleX = f3;
        this.scaleY = f4;
        this.templateId = j;
        this.deltaAngle = f5;
        this.isSaved = z;
        this.isFront = z2;
        this.tintColor = str3;
    }

    public ViewInCard() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = (long) i;
    }

    public int getViewType() {
        return this.viewType;
    }

    public void setViewType(int i) {
        this.viewType = i;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int i) {
        this.iconId = i;
    }

    public int getShapeId() {
        return this.shapeId;
    }

    public void setShapeId(int i) {
        this.shapeId = i;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String str) {
        this.imagePath = str;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public float getPositionX() {
        return this.positionX;
    }

    public void setPositionX(int i) {
        this.positionX = (float) i;
    }

    public float getPositionY() {
        return this.positionY;
    }

    public void setPositionY(int i) {
        this.positionY = (float) i;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public void setScaleX(float f) {
        this.scaleX = f;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setScaleY(float f) {
        this.scaleY = f;
    }

    public long getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(long j) {
        this.templateId = j;
    }

    public float getDeltaAngle() {
        return this.deltaAngle;
    }

    public void setDeltaAngle(float f) {
        this.deltaAngle = f;
    }

    public boolean isSaved() {
        return this.isSaved;
    }

    public void setSaved(boolean z) {
        this.isSaved = z;
    }

    public String getTintColor() {
        return this.tintColor;
    }

    public void setTintColor(String str) {
        this.tintColor = str;
    }

    public float getPivotX() {
        return this.pivotX;
    }

    public void setPivotX(float f) {
        this.pivotX = f;
    }

    public float getPivotY() {
        return this.pivotY;
    }

    public void setPivotY(float f) {
        this.pivotY = f;
    }

    public boolean isFront() {
        return this.isFront;
    }

    public void setFront(boolean z) {
        this.isFront = z;
    }
}
