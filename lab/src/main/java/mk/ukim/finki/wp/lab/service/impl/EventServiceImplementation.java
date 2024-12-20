package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.repository.EventRepository;
import mk.ukim.finki.wp.lab.repository.LocationRepository;
import mk.ukim.finki.wp.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImplementation implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public EventServiceImplementation(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text) {
        List<Event> allEvents = eventRepository.findAll();
        List<Event> resultList = allEvents.stream().filter(e->e.getName().contains(text) || e.getDescription().contains(text)).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public Optional<Event> findById(long eventId) {
        return eventRepository.findAll().stream().filter(e->e.getId().equals(eventId)).findFirst();
    }

    @Override
    public Optional<Event> findByName(String Name) {
        return eventRepository.findAll().stream().filter(e->e.getName().equals(Name)).findFirst();
    }

    @Override
    public List<Event> searchByName(String searchName, List<Event> events) {
        return events.stream().filter(event -> event.getName().toLowerCase().contains(searchName.toLowerCase())).toList();
    }

    @Override
    public List<Event> searchByRating(Integer searchRating, List<Event> events) {
        return events.stream().filter(event -> event.getPopularityScore() >= searchRating).toList();
    }

    @Override
    public List<Event> searchByLocation(String locationName, List<Event> events) {
        return events.stream().filter(event -> event.getLocation().getName().toLowerCase().contains(locationName.toLowerCase())).toList();
    }

    @Override
    public void saveEvent(String name, String description, Integer popularityScore, Long locationId) {
        eventRepository.save(new Event(name,description,popularityScore,locationRepository.findById(locationId).get()));
    }

    @Override
    public Event updateEvent(Long eventId, String name, String description, Integer popularityScore, Long locationId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found!"));
        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setLocation(locationRepository.findById(locationId).get());
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
