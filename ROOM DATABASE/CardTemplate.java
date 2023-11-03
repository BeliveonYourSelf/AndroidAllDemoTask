package com.cashmoney.calculate.report.manager.dto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cardtemplate")
public class CardTemplate {
    @PrimaryKey(autoGenerate = true)

    public int backgroundId;

    public String createdOn;

    public long id;
    private byte[] image;

    public String name;
    public int previewHeight;
    public int previewWidth;

    public CardTemplate(String str, String str2, int i) {
        this.name = str;
        this.createdOn = str2;
        this.backgroundId = i;
    }

    public CardTemplate() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(String str) {
        this.createdOn = str;
    }

    public int getBackgroundId() {
        return this.backgroundId;
    }

    public void setBackgrountId(int i) {
        this.backgroundId = i;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] bArr) {
        this.image = bArr;
    }

    public int getPreviewWidth() {
        return this.previewWidth;
    }

    public void setPreviewWidth(int i) {
        this.previewWidth = i;
    }

    public int getPreviewHeight() {
        return this.previewHeight;
    }

    public void setPreviewHeight(int i) {
        this.previewHeight = i;
    }
}
