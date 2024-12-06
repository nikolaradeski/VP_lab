package mk.ukim.finki.wp.lab.repository;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.EventBooking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EventBookingRepository {
    private List<EventBooking> eventBookings = new ArrayList<>();

    public List<EventBooking> findAll() {
        return eventBookings;
    }

    public void addEventBooking(EventBooking eb)
    {
        eventBookings.add(eb);
    }
}

