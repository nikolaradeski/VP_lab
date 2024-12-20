package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "events_table")
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer popularityScore;
    @ManyToOne
//    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    public Event(String name, String description, Integer popularityScore, Location location) {
//        this.id = (long) (Math.random()*1000);
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPopularityScore() {
        return popularityScore;
    }

    public Location getLocation() {
        return location;
    }
}
