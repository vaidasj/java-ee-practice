package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Event;
import lt.vu.entities.Player;
import lt.vu.persistence.EventDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Events {

    @Inject
    private EventDAO eventDAO;

    @Getter @Setter
    private Event eventToCreate = new Event();

    @Getter
    private List<Event> allEvents;

    @PostConstruct
    public void init(){
        loadAllEvents();
    }

    @Transactional
    public void createEvent(){
        this.eventDAO.persist(eventToCreate);
    }

    private void loadAllEvents() {
        this.allEvents = eventDAO.loadAll();
    }

    @Transactional
    public void addPlayerToEvent(Integer playerId, Integer eventId){
        this.eventDAO.addPlayerToEvent(playerId, eventId);
    }

    public List<Player> getLoadPlayers(Integer eventId){
        return eventDAO.getPlayersByEventId(eventId);
    }
}
