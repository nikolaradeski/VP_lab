package mk.ukim.finki.wp.lab.web.controller;


import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.Location;
import mk.ukim.finki.wp.lab.service.impl.EventServiceImplementation;
import mk.ukim.finki.wp.lab.service.impl.LocationServiceImplementation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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
    public String getEventsPage(@RequestParam(required = false) String error,
                                @RequestParam(required = false) String searchName,
                                @RequestParam(required = false) Integer searchRating,
                                @RequestParam(required = false) String location,
                                Model model)
    {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Event> events = eventService.listAll();
        if(!Objects.equals(searchName, ""))
        {
            events = eventService.searchByName(searchName, events);
        }
        if(searchRating != null)
        {
            events = eventService.searchByRating(searchRating, events);
        }
        if(!Objects.equals(location, ""))
        {
            events = eventService.searchByLocation(location, events);
        }

        model.addAttribute("events", events);
        return "listEvents";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddEventPage(Model model) {
        // Pass the list of locations to the view for the dropdown
        model.addAttribute("locations", locationService.findAll());
        return "add-Event";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveEvent(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Integer popularityScore,
            @RequestParam Long locationId) {

        eventService.saveEvent(name, description, popularityScore, locationId);

        return "redirect:/events";
    }


    @GetMapping("/getEditForm/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditEventForm(@PathVariable Long eventId, Model model) {
        Event event = eventService.findById(eventId).get();
        List<Location> locations = locationService.findAll();

        // Add existing event details and list of locations to the model
        model.addAttribute("event", event);
        model.addAttribute("locations", locations);

        return "edit-event"; // Returns the edit form view
    }

    @PostMapping("/edit/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateEvent(
            @PathVariable Long eventId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Integer popularityScore,
            @RequestParam Long locationId) {

        // Update the event details
        eventService.updateEvent(eventId, name, description, popularityScore, locationId);

        return "redirect:/events"; // Redirect back to the list of events
    }

    @GetMapping("/delete/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEvent(@PathVariable Long eventId)
    {
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

}
