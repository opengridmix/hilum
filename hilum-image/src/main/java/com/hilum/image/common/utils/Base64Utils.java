package com.hilum.image.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public abstract class Base64Utils {
    public static DecodeInfo decode(String base64String) throws IOException {
        DecodeInfo decodeInfo = new DecodeInfo();
        InputStream is;
        if(base64String.startsWith("data:image")) {
            is = decodeImage(decodeInfo, base64String);
        } else {
            is = decodeString(decodeInfo, base64String);
        }
        decodeInfo.setInputStream(is);
        return decodeInfo;
    }

    public static InputStream decodeImage(DecodeInfo decodeInfo, String base64String) throws IOException {
        int typeIndex = base64String.indexOf(";");
        if(typeIndex > 32 || typeIndex < 4) {
            throw new RuntimeException("图像格式不正确");
        }
        String imgType = base64String.substring("data:".length(), typeIndex);
        int imgIndex = base64String.indexOf(",");
        base64String = base64String.substring(imgIndex + 1);
        decodeInfo.setImageType(imgType);
        decodeInfo.setImage(true);
        return decodeString(decodeInfo, base64String);
    }

    public static InputStream decodeString(DecodeInfo decodeInfo, String base64String) throws IOException {
        byte[] decoderBytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decoderBytes);
        decodeInfo.setSize(decoderBytes.length);
        decodeInfo.setImage(false);
        return byteArrayInputStream;
    }
}
