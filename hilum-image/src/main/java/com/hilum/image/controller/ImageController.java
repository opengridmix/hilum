package com.hilum.image.controller;

import com.hilum.image.common.exception.ApplicationException;
import com.hilum.image.common.utils.ObjectUtils;
import com.hilum.image.controller.dto.ImageSaveRequest;
import com.hilum.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    ImageService imageService;

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> put(MultipartFile file) throws IOException {
        String clientId = getClientId();
        long id = imageService.save(clientId, file);
        return ResponseEntity.ok(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "save")
    @ResponseBody
    public ResponseEntity<?> save(MultipartFile[] files) throws IOException {
        String clientId = getClientId();
        List<Long> ids = imageService.save(clientId, files);
        return ResponseEntity.ok(ids);
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveBase64")
    @ResponseBody
    public ResponseEntity<?> saveBase64(@RequestBody ImageSaveRequest imageSaveRequest) throws IOException {
        String clientId = getClientId();
        long id = imageService.save(clientId, imageSaveRequest);
        return ResponseEntity.ok(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    @ResponseBody
    public void get(@PathVariable String id, HttpServletResponse response) throws IOException {
        String clientId = getClientId();
        String url = imageService.getURL(clientId, id);
        if(!ObjectUtils.isBlank(url)) {
            response.sendRedirect(url);
        } else {
            throw new ApplicationException("没有对应资源");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    @ResponseBody
    public void delete(@PathVariable String id, HttpServletResponse response) throws IOException {
        String clientId = getClientId();
        imageService.delete(clientId, id);
    }

    protected String getClientId() {
        String clientId = "";
        return clientId;
    }

}