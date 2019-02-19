package tgrajkowski.calendar.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(value = "*")
public class MainController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMessage() {
        return "Logic Logic Logic";
    }
}
