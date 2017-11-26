package com.hilum.image.common.utils;

import java.io.InputStream;

public class DecodeInfo {
    private boolean image;
    private long size;
    private InputStream inputStream;
    private String imageType;

    public boolean isImage() {
        return image;
    }

    public long getSize() {
        return size;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType == null ? null : imageType.trim();
    }
}
