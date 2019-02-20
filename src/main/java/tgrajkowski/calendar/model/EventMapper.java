package tgrajkowski.calendar.model;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMapper {
   private SimpleDateFormat simpleDateFormat;

    public EventMapper() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private EventDateTime createEventDateTime(String dataTxt) throws EventExeption{
        try {
            EventDateTime eventDateTime = new EventDateTime();
            eventDateTime.set("dateTime", new DateTime(simpleDateFormat.parse(dataTxt)));
            return eventDateTime;
        } catch (ParseException e) {
            throw new EventExeption("Something go wrong during formating data");
        }
    }

    public Event mapToEvent(EventDto eventDto) throws EventExeption{
        Event event = new Event();
        event.setSummary(eventDto.getSummary());
        event.setDescription(eventDto.getDescription());
        event.setStart(createEventDateTime(eventDto.getStart()));
        event.setEnd(createEventDateTime(eventDto.getEnd()));
        return event;
    }

    public EventDto mapToEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setSummary(event.getSummary());
        eventDto.setDescription(event.getDescription());
        eventDto.setStart(simpleDateFormat.format(new Date(event.getStart().getDateTime().getValue())));
        eventDto.setEnd(simpleDateFormat.format(new Date(event.getEnd().getDateTime().getValue())));
        return eventDto;
    }

    public List<EventDto> mapToEventDtoList(List<Event> eventList) {
        return eventList.stream().map(this::mapToEventDto).collect(Collectors.toList());
    }
}
