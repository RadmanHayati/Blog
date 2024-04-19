package com.scenius.mosaheb.repositories;

import com.scenius.mosaheb.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
   // List<Blog> findByAuthor(String author);
    // findByTag
     // etc
}
