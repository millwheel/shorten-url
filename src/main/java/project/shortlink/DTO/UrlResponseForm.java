package project.shortlink.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlResponseForm {
    private Data data;

    public UrlResponseForm(Data data) {
        this.data = data;
    }
}
