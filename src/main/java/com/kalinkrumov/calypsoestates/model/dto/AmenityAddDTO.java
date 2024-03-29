package com.kalinkrumov.calypsoestates.model.dto;

import javax.validation.constraints.NotBlank;

public class AmenityAddDTO {

    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
