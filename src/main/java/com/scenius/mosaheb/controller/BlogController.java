package com.scenius.mosaheb.controller;

import com.scenius.mosaheb.config.CurrentUser;
import com.scenius.mosaheb.dto.AddBlogRequest;
import com.scenius.mosaheb.dto.PagedResponse;
import com.scenius.mosaheb.entities.Blog;
import com.scenius.mosaheb.entities.User;
import com.scenius.mosaheb.services.BlogService;
import com.scenius.mosaheb.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/blogs")
@RequiredArgsConstructor
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/all")
    public ResponseEntity<PagedResponse<Blog>>  getAllBlogs(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) throws BadRequestException {
        PagedResponse<Blog> response = blogService.getAllBlogs(page, size);

        return new ResponseEntity< >(response, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<Blog> postBlog(
            @Valid @RequestBody AddBlogRequest blogRequest,
            @CurrentUser User currentUser
    ) {
        try {
            Blog blog = blogService.addBlog(blogRequest, currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(blog);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }


}
