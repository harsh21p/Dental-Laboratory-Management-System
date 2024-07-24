package com.dental.enrollment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    @GetMapping
    @RequestMapping("/roles")
    public String getRoles() {
        return "Hello Get Roles!";
    }

}
