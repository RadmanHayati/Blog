package com.scenius.mosaheb.services.impl;

import com.scenius.mosaheb.dto.AddBlogRequest;
import com.scenius.mosaheb.dto.PagedResponse;
import com.scenius.mosaheb.entities.Blog;
import com.scenius.mosaheb.entities.Tag;
import com.scenius.mosaheb.entities.User;
import com.scenius.mosaheb.repositories.BlogRepository;
import com.scenius.mosaheb.repositories.TagRepository;
import com.scenius.mosaheb.repositories.UserRepository;
import com.scenius.mosaheb.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.scenius.mosaheb.utils.AppConstants.CREATED_AT;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Override
    public PagedResponse<Blog> getAllBlogs(int page, int size) throws BadRequestException {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogs = blogRepository.findAll(pageable);
        List<Blog> content = blogs.getNumberOfElements() == 0 ? Collections.emptyList() : blogs.getContent();

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
    public Blog addBlog(AddBlogRequest addBlogRequest, User currentUser) throws BadRequestException {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new BadRequestException("user not found"));
        List<Tag> tags = new ArrayList<>(addBlogRequest.getTags().size());

        for (String name : addBlogRequest.getTags()) {
            Tag tag = tagRepository.findByName(name);
            tag = tag == null ? tagRepository.save(new Tag(name)) : tag;
            tags.add(tag);
        }
        Blog blog = new Blog();
        blog.setUser(user);
        blog.setTitle(addBlogRequest.getTitle());
        blog.setContent(addBlogRequest.getBody());
        blog.setTags(tags);

        return blogRepository.save(blog);

    }


    @Override
    public Blog getBlog(Long id) {
        return null;
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