package lt.vu.persistence;

import lt.vu.entities.Player;
import lt.vu.entities.Event;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EventDAO {

    @Inject
    private EntityManager em;

    public List<Event> loadAll(){
        return em.createNamedQuery("Event.findAll", Event.class).getResultList();
    }

    public void persist(Event ev){
        this.em.persist(ev);
    }

    //The transaction ensures that either all the operations within it succeed, or if any operation fails, all the
    // changes made within the transaction are rolled back to their previous state.
    @Transactional
    public void addPlayerToEvent(Integer playerId, Integer eventId){
        Player player = em.find(Player.class, playerId);
        Event event = em.find(Event.class, eventId);

        if(player != null && event != null){
            event.getPlayers().add(player);
            em.persist(event);
        }
    }

    public List<Player> getPlayersByEventId(int eventId){
        Event event = em.find(Event.class, eventId);
        if(event != null){
            return event.getPlayers();
        }
        return new ArrayList<>();
    }

    public Event find(Integer id){
        return em.find(Event.class, id);
    }

    public Event update(Event event){
        return em.merge(event);
    }

    //add delete perhaps

}
