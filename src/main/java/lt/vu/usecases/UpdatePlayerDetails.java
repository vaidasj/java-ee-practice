package lt.vu.usecases;


import lombok.Getter;
import lombok.Setter;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.PlayersDAO;
import lt.vu.entities.Player;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@ViewScoped
@Named
@Getter @Setter
public class UpdatePlayerDetails implements Serializable {

    private Player player;

    @Inject
    private PlayersDAO playersDAO;

    @PostConstruct
    private void init() {
        System.out.println("UpdatePlayerDetails INIT CALLED");
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer playerId = Integer.parseInt(requestParameters.get("playerId"));
        this.player = playersDAO.findOne(playerId);
    }

    @Transactional
    @LoggedInvocation
    public String updatePlayerJerseyNumber() {
        try{
            playersDAO.update(this.player);
        } catch (OptimisticLockException e) {
            return "/playerDetails.xhtml?faces-redirect=true&playerId=" + this.player.getId() + "&error=optimistic-lock-exception";
        }
        return "players.xhtml?teamId=" + this.player.getTeam().getId() + "&faces-redirect=true";
    }
}
