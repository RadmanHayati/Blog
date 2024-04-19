package com.scenius.mosaheb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "BLOG_TAG_TABLE",
            joinColumns = {
                    @JoinColumn(name = "blog_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "id")
            }
    )
    private List<Tag> tags;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public List<Tag> getTags() {
        return tags == null ? null : new ArrayList<>(tags);
    }

    public void setTags(List<Tag> tags) {
        if (tags == null) {
            this.tags = null;
        } else {
            this.tags = Collections.unmodifiableList(tags);
        }
    }

}