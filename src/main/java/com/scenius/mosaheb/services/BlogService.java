package com.scenius.mosaheb.services;

import com.scenius.mosaheb.dto.AddBlogRequest;
import com.scenius.mosaheb.dto.BasicResponse;
import com.scenius.mosaheb.dto.PagedResponse;
import com.scenius.mosaheb.entities.Blog;
import com.scenius.mosaheb.entities.User;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.attribute.UserPrincipal;

public interface BlogService {

    PagedResponse<Blog> getAllBlogs(int page, int size) throws BadRequestException;

    PagedResponse<Blog> getBlogsByCreatedBy(String username, int page, int size);

    // PagedResponse<Blog> getBlogsByCategory(Long id, int page, int size);

    PagedResponse<Blog> getBlogsByTag(Long id, int page, int size);

    BasicResponse updateBlog(Blog blogModel, MultipartFile blogImage);

    String deleteBlog(Long id, User currentUser);

     BasicResponse addBlog(AddBlogRequest addBlogRequest, MultipartFile blogImage, User currentUser) throws BadRequestException ;

    BasicResponse getBlogById(Long id);

    BasicResponse deleteBlogById(Long id);
}
