package com.scenius.mosaheb.controller;


import com.scenius.mosaheb.dto.SignUpRequest;
import com.scenius.mosaheb.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @PostMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hi boss");
    }

}
