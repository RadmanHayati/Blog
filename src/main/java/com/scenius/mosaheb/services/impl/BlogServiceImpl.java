package com.scenius.mosaheb.services.impl;

import com.scenius.mosaheb.dto.AddBlogRequest;
import com.scenius.mosaheb.dto.BasicResponse;
import com.scenius.mosaheb.dto.PagedResponse;
import com.scenius.mosaheb.entities.Blog;
import com.scenius.mosaheb.entities.Tag;
import com.scenius.mosaheb.entities.User;
import com.scenius.mosaheb.repositories.BlogRepository;
import com.scenius.mosaheb.repositories.TagRepository;
import com.scenius.mosaheb.repositories.UserRepository;
import com.scenius.mosaheb.services.BlogService;
import com.scenius.mosaheb.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final StorageService storageService;

    @Override
    public PagedResponse<Blog> getAllBlogs(int page, int size) throws BadRequestException {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogs = blogRepository.findAll(pageable);
        List<Blog> content = blogs.getNumberOfElements() == 0 ? Collections.emptyList() : blogs.getContent();

        for (Blog blog : content) {
            blog.setImage(storageService.generateImageUrl(blog.getImage()));  // replace 'fieldName' with your field's setter method
        }

        return new PagedResponse<>(content, blogs.getNumber(), blogs.getSize(), blogs.getTotalElements(),
                blogs.getTotalPages(), blogs.isLast());
    }

    @Override
    public PagedResponse<Blog> getBlogsByCreatedBy(String username, int page, int size) {
        return null;
    }

    @Override
    public PagedResponse<Blog> getBlogsByTag(Long id, int page, int size) {
        return null;
    }

    @Override
    public String deleteBlog(Long id, User currentUser) {
        return null;
    }

    @Override
    public BasicResponse addBlog(AddBlogRequest addBlogRequest, MultipartFile blogImage, User currentUser) throws BadRequestException {
        BasicResponse basicResponse = new BasicResponse();
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new BadRequestException("user not found"));
        try {

            if (addBlogRequest != null && !blogImage.isEmpty()) {
                List<Tag> tags = new ArrayList<>(addBlogRequest.getTags().size());

                for (String name : addBlogRequest.getTags()) {
                    Tag tag = tagRepository.findByName(name);
                    tag = tag == null ? tagRepository.save(new Tag(name)) : tag;
                    tags.add(tag);
                }

                storageService.uploadImageToFileSystem(blogImage);

                Blog blog = new Blog();
                blog.setUser(user);
                blog.setTitle(addBlogRequest.getTitle());
                blog.setContent(addBlogRequest.getBody());
                blog.setTags(tags);
                blog.setImage(blogImage.getOriginalFilename());
                Blog b = blogRepository.save(blog);
                b.setImage(storageService.generateImageUrl(blogImage.getOriginalFilename()));

                basicResponse.setData(b);
                basicResponse.setMessage("Blog added successfully");
                basicResponse.setStatus(true);
            } else {
                basicResponse.setMessage("Blog addtion failed!");
                basicResponse.setStatus(false);
            }
        } catch (Exception e) {
            basicResponse.setMessage(e.getMessage());
            basicResponse.setStatus(false);
        }
        return basicResponse;
    }

    @Override
    public BasicResponse getBlogById(Long id) {
        BasicResponse basicResponse = new BasicResponse();
        try {

            Optional<Blog> myBlog = blogRepository.findById(id);
            if (myBlog.isPresent()) {
                Blog blogModel = myBlog.get();
                blogModel.setImage(storageService.generateImageUrl(blogModel.getImage()));
                basicResponse.setData(blogModel);
                basicResponse.setMessage("Blog details");
                basicResponse.setStatus(true);
            } else {
                basicResponse.setMessage("No blog details");
                basicResponse.setStatus(false);
            }
        } catch (Exception e) {
            basicResponse.setMessage(e.getMessage());
            basicResponse.setStatus(false);
        }
        return basicResponse;
    }

    @Override
    public BasicResponse updateBlog(Blog blogModel, MultipartFile blogImage) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            Optional<Blog> checkBlog = blogRepository.findById(blogModel.getId());
            if (checkBlog.isPresent()) {
                Blog b = checkBlog.get();

                // file upload start
                File savFile = new ClassPathResource("static/blogImages").getFile();
                Path path = Paths.get(savFile.getAbsolutePath() + File.separator + blogImage.getOriginalFilename());
                Files.copy(blogImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                // file upload end

                b.setTitle(blogModel.getTitle());
                b.setContent(blogModel.getContent());
                b.setImage(blogImage.getOriginalFilename());

                Blog blogModel2 = blogRepository.save(b);

                basicResponse.setData(blogModel2);
                basicResponse.setMessage("Blog updated successfully");
                basicResponse.setStatus(true);

            } else {
                basicResponse.setMessage("Blog updation failed!");
                basicResponse.setStatus(false);
            }
        } catch (Exception e) {
            basicResponse.setMessage(e.getMessage());
            basicResponse.setStatus(false);
        }
        return basicResponse;
    }

    @Override
    public BasicResponse deleteBlogById(Long id) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            Optional<Blog> checkBlog = blogRepository.findById(id);
            if (checkBlog.isPresent()) {
                Blog blogModel = checkBlog.get();
                blogRepository.delete(blogModel);
                basicResponse.setData(blogModel);
                basicResponse.setMessage("Blog deleted successfully");
                basicResponse.setStatus(true);
            } else {
                basicResponse.setMessage("Blog deletion failed");
                basicResponse.setStatus(false);
            }
        } catch (Exception e) {
            basicResponse.setMessage(e.getMessage());
            basicResponse.setStatus(false);
        }
        return basicResponse;
    }


    private void validatePageNumberAndSize(int page, int size) throws BadRequestException {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new BadRequestException("Size number cannot be less than zero.");
        }

        if (size > 50) {
            throw new BadRequestException("Page size must not be greater than " + 50);
        }
    }
}