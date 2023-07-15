package project.shortlink.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Data {
    private String shortId;
    private String url;
    private String createdAt;

}
