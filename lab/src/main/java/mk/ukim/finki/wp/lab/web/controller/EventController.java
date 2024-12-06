package mk.ukim.finki.wp.lab.web.controller;


import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.Location;
import mk.ukim.finki.wp.lab.service.impl.EventServiceImplementation;
import mk.ukim.finki.wp.lab.service.impl.LocationServiceImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventServiceImplementation eventService;
    private final LocationServiceImplementation locationService;

    public EventController(EventServiceImplementation eventService, LocationServiceImplementation locationService) {
        this.eventService = eventService;
        this.locationService = locationService;
    }

    @GetMapping("")
    public String getEventsPage(@RequestParam(required = false) String error, Model model)
    {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("events", eventService.listAll());
        return "listEvents";
    }

    @GetMapping("/add-form")
    public String getAddEventPage(Model model) {
        // Pass the list of locations to the view for the dropdown
        model.addAttribute("locations", locationService.findAll());
        return "add-Event";
    }

    @PostMapping("/add")
    public String saveEvent(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double popularityScore,
            @RequestParam Long locationId) {

        eventService.saveEvent(name, description, popularityScore, locationId);

        return "redirect:/events";
    }


    @GetMapping("/getEditForm/{eventId}")
    public String getEditEventForm(@PathVariable Long eventId, Model model) {
        Event event = eventService.findById(eventId).get();
        List<Location> locations = locationService.findAll();

        // Add existing event details and list of locations to the model
        model.addAttribute("event", event);
        model.addAttribute("locations", locations);

        return "edit-event"; // Returns the edit form view
    }

    @PostMapping("/edit/{eventId}")
    public String updateEvent(
            @PathVariable Long eventId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double popularityScore,
            @RequestParam Long locationId) {

        // Update the event details
        eventService.updateEvent(eventId, name, description, popularityScore, locationId);

        return "redirect:/events"; // Redirect back to the list of events
    }

    @GetMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable Long eventId)
    {
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

}
