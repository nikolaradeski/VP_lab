package mk.ukim.finki.wp.lab.DataInitializer;

import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.Location;
import mk.ukim.finki.wp.lab.repository.EventRepository;
import mk.ukim.finki.wp.lab.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(LocationRepository locationRepository, EventRepository eventRepository) {
        return args -> {
            // Create 10 Locations
            List<Location> locations = new ArrayList<>();
            locations.add(new Location("Venue A", "123 Main St", "500", "A large venue for concerts"));
            locations.add(new Location("Venue B", "456 Elm St", "300", "A cozy venue for small events"));
            locations.add(new Location("Venue C", "789 Oak St", "1000", "An open-air venue"));
            locations.add(new Location("Venue D", "321 Maple Ave", "200", "Perfect for workshops"));
            locations.add(new Location("Venue E", "654 Pine Ave", "400", "Great for exhibitions"));
            locations.add(new Location("Venue F", "987 Birch Blvd", "800", "Ideal for conferences"));
            locations.add(new Location("Venue G", "213 Cedar St", "250", "Suitable for private parties"));
            locations.add(new Location("Venue H", "546 Spruce St", "1500", "Designed for sports events"));
            locations.add(new Location("Venue I", "879 Palm Ave", "350", "A popular choice for weddings"));
            locations.add(new Location("Venue J", "135 Willow Ln", "600", "A versatile space for all events"));

            locationRepository.saveAll(locations);

            // Create 10 Events, each assigned to a location
            List<Event> events = new ArrayList<>();
            events.add(new Event("Music Fest", "A thrilling music festival", 9, locations.get(0)));
            events.add(new Event("Art Expo", "Showcasing modern art", 8, locations.get(1)));
            events.add(new Event("Tech Conference", "The latest in technology", 8, locations.get(2)));
            events.add(new Event("Food Fair", "A paradise for food lovers", 9, locations.get(3)));
            events.add(new Event("Literature Meet", "For the love of books", 7, locations.get(4)));
            events.add(new Event("Sports Gala", "A celebration of sports", 8, locations.get(5)));
            events.add(new Event("Film Screening", "A night of classic movies", 7, locations.get(6)));
            events.add(new Event("Startup Pitch", "Innovative ideas showcased", 8, locations.get(7)));
            events.add(new Event("Comedy Night", "Laugh out loud", 9, locations.get(8)));
            events.add(new Event("Cultural Fest", "Experience diverse cultures", 8, locations.get(9)));

            eventRepository.saveAll(events);
        };
    }
}

