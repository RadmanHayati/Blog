package com.scenius.mosaheb.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class AddBlogRequest {

    @NotBlank
    @Size(min = 10)
    private String title;

    @NotBlank
    @Size(min = 50)
    private String body;

    private List<String> tags;

    public List<String> getTags() {

        return tags == null ? Collections.emptyList() : new ArrayList<>(tags);
    }

    public void setTags(List<String> tags) {

        if (tags == null) {
            this.tags = null;
        } else {
            this.tags = Collections.unmodifiableList(tags);
        }
    }
}

