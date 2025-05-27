package com.asnakhader.ecs.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageRequest {
    @JsonProperty("base64VisaImage")
    private String base64VisaImage;

    @JsonProperty("base64VisaImage")
    public String getBase64VisaImage() {
        return base64VisaImage;
    }

    @JsonProperty("base64VisaImage")
    public void setBase64VisaImage(String base64VisaImage) {
        this.base64VisaImage = base64VisaImage;
    }
}

