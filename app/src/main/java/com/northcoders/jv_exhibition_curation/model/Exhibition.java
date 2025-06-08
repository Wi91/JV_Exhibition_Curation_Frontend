package com.northcoders.jv_exhibition_curation.model;

public class Exhibition {

    private Long id;
    private String title;

    public Exhibition(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
