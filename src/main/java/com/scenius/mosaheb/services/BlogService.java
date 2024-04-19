package com.scenius.mosaheb.services;

import com.scenius.mosaheb.dto.AddBlogRequest;
import com.scenius.mosaheb.dto.PagedResponse;
import com.scenius.mosaheb.entities.Blog;
import com.scenius.mosaheb.entities.User;
import org.apache.coyote.BadRequestException;

import java.nio.file.attribute.UserPrincipal;

public interface BlogService {

    PagedResponse<Blog> getAllBlogs(int page, int size) throws BadRequestException;

    PagedResponse<Blog> getBlogsByCreatedBy(String username, int page, int size);

    // PagedResponse<Blog> getBlogsByCategory(Long id, int page, int size);

    PagedResponse<Blog> getBlogsByTag(Long id, int page, int size);

    // Blog updatePost(Long id, PostRequest newPostRequest, UserPrincipal currentUser);

    String deleteBlog(Long id, User currentUser);

    Blog addBlog(AddBlogRequest addBlogRequest, User currentUser) throws BadRequestException;

    Blog getBlog(Long id);
}
