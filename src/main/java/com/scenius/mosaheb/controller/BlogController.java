package com.scenius.mosaheb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scenius.mosaheb.config.CurrentUser;
import com.scenius.mosaheb.dto.AddBlogRequest;
import com.scenius.mosaheb.dto.BasicResponse;
import com.scenius.mosaheb.dto.PagedResponse;
import com.scenius.mosaheb.entities.Blog;
import com.scenius.mosaheb.entities.User;
import com.scenius.mosaheb.services.BlogService;
import com.scenius.mosaheb.services.StorageService;
import com.scenius.mosaheb.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/blogs")
@RequiredArgsConstructor
public class BlogController {

    @Autowired
    BlogService blogService;
    @Autowired
    StorageService storageService;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping(value = "/add")
    public ResponseEntity<BasicResponse> postBlog(
            @RequestParam("blog_image") MultipartFile blogImage,
            @Valid @RequestParam("blog_data") String blogRequest,
            @CurrentUser User currentUser
    ) {
        try {
            AddBlogRequest blogModel = objectMapper.readValue(blogRequest, AddBlogRequest.class);
            BasicResponse response = blogService.addBlog(blogModel, blogImage ,currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<PagedResponse<Blog>>  getAllBlogs(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) throws BadRequestException {
        PagedResponse<Blog> response = blogService.getAllBlogs(page, size);

        return new ResponseEntity< >(response, HttpStatus.OK);
    }





}
