package by.tms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EVENTS")
public class Event implements Comparable<Event> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private LocalDateTime localDateTime;


    public Event(String description, LocalDateTime localDateTime) {
        this.description = description;
        this.localDateTime = localDateTime;
    }

    @Override
    public int compareTo(@NotNull Event o) {
        return o.getLocalDateTime().compareTo(getLocalDateTime());
    }
}
