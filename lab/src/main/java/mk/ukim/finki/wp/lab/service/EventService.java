package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();
    List<Event> searchEvents(String text);

    Optional<Event> findById(long parseLong);

    Optional<Event> findByName(String Name);

    List<Event> searchByName(String searchName, List<Event> events);

    List<Event> searchByRating(double searchRating, List<Event> events);

    List<Event> searchByLocation(String locationName, List<Event> events);

    void saveEvent(String name, String description, Double popularityScore, Long locationId);

    Event updateEvent(Long eventId, String name, String description, Double popularityScore, Long locationId);

    void deleteEvent(Long eventId);
}
