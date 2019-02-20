package tgrajkowski.calendar.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tgrajkowski.calendar.model.EventDto;
import tgrajkowski.calendar.model.EventExeption;
import tgrajkowski.calendar.model.EventMapper;

import java.io.IOException;
import java.util.List;

@Service
public class CalendarService extends CalendarServiceAbstract {
    @Autowired
    private EventMapper eventMapper;

    @Override
    public List<EventDto> getAllEvents() throws IOException, EventExeption {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getCalendar().events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        return eventMapper.mapToEventDtoList(events.getItems());
    }

    @Override
    public EventDto addEvent(EventDto eventDto) throws EventExeption {
        try {
            Event event = eventMapper.mapToEvent(eventDto);
            Event result = getCalendar().events().insert("primary", event).execute();
            return eventMapper.mapToEventDto(result);
        } catch (IOException e) {
            throw new EventExeption(e.getMessage());
        }
    }

    @Override
    public boolean deleteEvent(String id) throws EventExeption {
        try {
            getCalendar().events().delete("primary", id).execute();
            return true;
        } catch (IOException e) {
            throw new EventExeption(e.getMessage());
        }
    }

    @Override
    public EventDto updateEvent(EventDto eventDto) throws EventExeption {
        try {
            Event event = eventMapper.mapToEvent(eventDto);
            Event result = getCalendar().events().update("primary", eventDto.getId(), event).execute();
            return eventMapper.mapToEventDto(result);
        } catch (IOException e) {
            throw new EventExeption(e.getMessage());
        }
    }
}
