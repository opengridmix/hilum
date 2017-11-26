package com.hilum.image.service;

import com.hilum.image.common.utils.*;
import com.hilum.image.controller.dto.ImageSaveRequest;
import com.hilum.image.dataaccess.mapper.ImageInfoMapper;
import com.hilum.image.dataaccess.model.ImageInfo;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Aspect
public class ImageService {
    private static final Logger log = LoggerFactory.getLogger(ImageService.class);
    @Autowired
    ImageInfoMapper imageInfoMapper;

    @Autowired
    SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Autowired
    OSSService ossService;

    @Transactional
    public long save(String clientId, MultipartFile file) throws IOException {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setCreateAt(new Date());
        imageInfo.setSize(file.getSize());
        imageInfo.setContentType(file.getContentType());
        imageInfo.setClientId(clientId);
        imageInfo.setName(file.getOriginalFilename());
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        imageInfo.setExt(ext);
        return save(imageInfo, file.getInputStream());
    }

    public List<Long> save(String clientId, MultipartFile[] files) throws IOException {
        List<Long> ids = new ArrayList<>();
        for(MultipartFile file : files) {
            long id = save(clientId, file);
            ids.add(id);
        }
        return ids;
    }

    public long save(String clientId, ImageSaveRequest imageSaveRequest) throws IOException {
        String base64String = imageSaveRequest.getBase64();
        DecodeInfo decodeInfo = Base64Utils.decode(base64String);
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setCreateAt(new Date());
        imageInfo.setSize(decodeInfo.getSize());
        String filename = imageSaveRequest.getName();
        String contentType = decodeInfo.isImage() ? decodeInfo.getImageType() : filename;
        imageInfo.setContentType(contentType);
        imageInfo.setClientId(clientId);
        imageInfo.setName(filename);
        String ext = FilenameUtils.getExtension(filename);
        imageInfo.setExt(ext);
        return save(imageInfo, decodeInfo.getInputStream());
    }


    protected long save(ImageInfo imageInfo, InputStream inputStream) {
        imageInfo.setId(snowFlakeIdGenerator.nextId());
        String signedurl = uploadImage(imageInfo, inputStream);
        log.info("upload success:" + signedurl);
        imageInfo.setUrl(signedurl);
        imageInfoMapper.insert(imageInfo);
        return imageInfo.getId();
    }


    protected Date getOSSExpiration() {
        LocalDateTime now = LocalDateTime.now();
        now = now.plusSeconds(30);
        Date expirationDate = DateTimeUtils.toDate(now);
        return expirationDate;
    }

    protected String uploadImage(ImageInfo imageInfo, InputStream inputStream) {
        String clientId = imageInfo.getClientId();
        String key = ossService.configKey(clientId, imageInfo.getName());
        Date expirationDate = getOSSExpiration();
        imageInfo.setUrlExpiration(expirationDate);
        String etag = ossService.upload(clientId, key, inputStream);
        imageInfo.setMd5(etag);
        URL signurl = ossService.getSignedURL(clientId, key, expirationDate);
        return signurl.toString();
    }

    public String getURL(String clientId, String id) {
        ImageInfo imageInfo = imageInfoMapper.selectById(id);
        if(ObjectUtils.isBlank(imageInfo)) {
            return null;
        }
        Duration expiration = DateTimeUtils.timeDiff(DateTimeUtils.dateNow(), imageInfo.getUrlExpiration());
        if(expiration.getSeconds() > 0) {
            String key = ossService.configKey(clientId, imageInfo.getName());
            URL signurl = ossService.getSignedURL(clientId, key, getOSSExpiration());
            imageInfo.setUrl(signurl.toString());
            imageInfoMapper.updateByPrimaryKeySelective(imageInfo);
        }
        return imageInfo.getUrl();
    }

    public void delete(String clientId, String id) {
        ImageInfo imageInfo = imageInfoMapper.selectById(id);
        if(!ObjectUtils.isBlank(imageInfo)) {
            String key = ossService.configKey(clientId, imageInfo.getName());
            ossService.delete(clientId, key);
            imageInfoMapper.deleteByPrimaryKey(id);
        }
    }



}
