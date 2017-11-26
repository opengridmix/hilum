package com.hilum.image.dataaccess.model;

import javax.persistence.Id;
import java.util.Date;

public class ImageInfo {
    @Id
    private Long id;

    private String name;  //文件名
    private Long size;    //大小
    private String md5;   //md5
    private String contentType; //类型
    private String ext;   //后缀
    //private Integer dimension_x; //x像素
    //private Integer dimension_y; //y像素
    private String url; //external url
    private Date urlExpiration;//过期时间
    private String clientId; //应用
    private Date createAt;   //创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getUrlExpiration() {
        return urlExpiration;
    }

    public void setUrlExpiration(Date urlExpiration) {
        this.urlExpiration = urlExpiration;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
