package com.hilum.demo.controller;

import com.hilum.demo.controller.dto.UserRequest;
import com.hilum.demo.dataaccess.entity.User;
import com.hilum.demo.service.UserService;
import com.hilum.demo.common.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping
    public ResponseEntity<?> create(@RequestBody @Validated UserRequest userRequest) {
        User user = BeanCopyUtils.populateTbyDByCglib(userRequest, User.class);
        user = userService.insert(user);
        return ResponseEntity.ok(user);
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Validated UserRequest userRequest) {
        User user = BeanCopyUtils.populateTbyDByCglib(userRequest, User.class);
        user.setId(id);
        user = userService.update(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        User user = userService.select(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(null);
    }
}
