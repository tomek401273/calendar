package tgrajkowski.calendar.controller;

import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tgrajkowski.calendar.model.EventDto;
import tgrajkowski.calendar.model.EventExeption;
import tgrajkowski.calendar.service.CalendarService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/event/")
@CrossOrigin(value = "*")
public class MainController {
    @Autowired
    private CalendarService calendarService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<EventDto> getMessage() throws EventExeption, IOException {
        return calendarService.getAllEvents();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public EventDto addEvent(@RequestBody EventDto eventDto) throws EventExeption {
        return calendarService.addEvent(eventDto);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public boolean deleteEvent(@RequestParam String id) throws EventExeption{
        return calendarService.deleteEvent(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public EventDto updateEvent(@RequestBody EventDto eventDto) throws EventExeption {
        return calendarService.updateEvent(eventDto);
    }
}
