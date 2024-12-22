package com.scm.scm.controller.authController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.scm.util.UserDataUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDataUtil userDataUtil;

    public AuthController(UserDataUtil userDataUtil) {
        this.userDataUtil = userDataUtil;
    }
}
