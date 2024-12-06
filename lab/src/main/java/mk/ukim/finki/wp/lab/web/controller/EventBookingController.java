package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.EventBooking;
import mk.ukim.finki.wp.lab.service.impl.EventBookingServiceImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/eventsBooking")
public class EventBookingController {
    private final EventBookingServiceImplementation eventBookingService;

    public EventBookingController(EventBookingServiceImplementation eventBookingService) {
        this.eventBookingService = eventBookingService;
    }

    @PostMapping("")
    public String showReservation(@RequestParam String selectedEventName,
                                  @RequestParam String userName,
                                  @RequestParam String userAddress,
                                  @RequestParam String numTickets,
                                  Model model)
    {
        EventBooking newEventBooking = eventBookingService.placeBooking(selectedEventName, userName, userAddress, Integer.parseInt(numTickets));
        model.addAttribute("attendeeName", newEventBooking.getAttendeeName());
        model.addAttribute("attendeeAddress", newEventBooking.getAttendeeAddress());
        model.addAttribute("eventName", newEventBooking.getEventName());
        model.addAttribute("numberOfTickets", newEventBooking.getNumberOfTickets());

        return "bookingConfirmation";
    }
}
