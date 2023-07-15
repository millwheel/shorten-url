package project.shortlink.DTO;

import lombok.Builder;

@Builder
public class Data {
    private String shortId;
    private String url;
    private String createdAt;

}
