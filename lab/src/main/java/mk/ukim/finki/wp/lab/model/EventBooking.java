package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

@Data
@Table(name = "events_booking_table")
public class EventBooking {
    private Long id;
    private String eventName;
    private String attendeeName;
    private String attendeeAddress;
    private Long numberOfTickets;

    public EventBooking(String eventName, String attendeeName, String attendeeAddress, Long numberOfTickets) {
        this.id = (long) (Math.random()*1000);
        this.eventName = eventName;
        this.attendeeName = attendeeName;
        this.attendeeAddress = attendeeAddress;
        this.numberOfTickets = numberOfTickets;
    }
}
