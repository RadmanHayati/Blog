package com.scenius.mosaheb.repositories;

import com.scenius.mosaheb.entities.Blog;
import com.scenius.mosaheb.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
    // List<Blog> findByAuthor(String author);
    // findByTag
    // etc
}
