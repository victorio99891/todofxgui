package pl.wiktor.todosgui.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChuckNorrisJoke {
    private String id;
    private String url;
    private String value;
}
