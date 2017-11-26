
package com.hilum.image.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class OSSService {
    private static final Logger log = LoggerFactory.getLogger(OSSService.class);
	@Autowired
	private Environment env;

	private Map<String, Properties> propertiesMap = new HashMap<>();

    protected String upload(String clientId, String key, InputStream inputStream) {
        Assert.notNull(key, "OSS 存储key不能为空");
        //Assert.notNull(url, "OSS signed url不能为空");

        OSSClient ossClient = getOSSClient(clientId);
        String bucketName = getBucketName(clientId);
        try {
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, key, inputStream);
            String ret = putResult.getETag();
            return ret;
        } catch (Exception e) {
            log.error("upload :", e);
            throw new OSSException(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }


	public void delete(String clientId, String key) {
		OSSClient ossClient = getOSSClient(clientId);
        String bucketName = getBucketName(clientId);
        try {
            ossClient.deleteObject(bucketName, key);
        } catch (Exception e) {
            log.error("delete:", e);
        } finally {
            ossClient.shutdown();
        }
	}

	private OSSClient getOSSClient(String clientId) {
        Properties properties = getProperties(clientId);
        OSSClient ossClient = new OSSClient(properties.getProperty("endpoint"), properties.getProperty("accessKeyId"),
                properties.getProperty("accessKeySecret"));
        return ossClient;
	}

	private Properties getProperties(String clientId) {
	    Properties properties = propertiesMap.get(clientId);
        if(properties == null) {
            String ossSerectHome = env.getProperty("oss.secret");
            if(ossSerectHome != null) {
                Resource resource = new FileSystemResource(ossSerectHome + "/ossseret." + clientId);
                try {
                    properties = PropertiesLoaderUtils.loadProperties(resource);
                    propertiesMap.put(clientId, properties);
                } catch (IOException e) {
                        throw new OSSException("没有权限上传文件");
                }
            } else {
                throw new OSSException("没有权限上传文件");
            }
        }
        return properties;
    }

    private String getBucketName(String clientId) {
        Properties properties = getProperties(clientId);
        String bucketName = properties.getProperty("bucketName");
        if(StringUtils.isBlank(bucketName)) {
            bucketName = clientId;
        }
        return bucketName;
    }

    public String configKey(String clientId, String name) {
        return clientId + "/" + name;
    }

    public URL getSignedURL(String clientId, String key, Date expiration) {
        String bucketName = getBucketName(clientId);
        OSSClient ossClient = getOSSClient(clientId);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        return url;
    }
}
