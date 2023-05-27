package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Event.findAll", query = "select ev from Event as ev"),
        @NamedQuery(name = "Event.findAllPlayers", query = "SELECT ev.players FROM Event ev WHERE ev.id = :eventId")
})
@Table(name = "EVENT")
@Getter @Setter
public class Event implements Serializable {

    public Event() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 60)
    @Column(name = "NAME")
    private String name;

    @ManyToMany
    private List<Player> players = new ArrayList<>();

}