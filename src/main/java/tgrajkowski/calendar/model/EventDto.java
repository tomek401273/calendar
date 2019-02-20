package tgrajkowski.calendar.model;

import com.google.api.client.util.DateTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventDto {
    private String id;
    private String summary;
    private String description;
    private String start;
    private String end;
}
