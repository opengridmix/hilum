package com.hilum.image.controller.dto;

import javax.validation.constraints.NotNull;

public class ImageSaveRequest {

    @NotNull(message = "名称不可空")
    private String name;
    @NotNull(message = "base64数据不可空")
    private String base64;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64 == null ? null : base64.trim();
    }
}
