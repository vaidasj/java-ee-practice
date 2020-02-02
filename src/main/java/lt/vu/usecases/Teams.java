package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.persistence.TeamsDAO;
import lt.vu.entities.Team;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Teams {

    @Inject
    private TeamsDAO teamsDAO;

    @Getter @Setter
    private Team teamToCreate = new Team();

    @Getter
    private List<Team> allTeams;

    @PostConstruct
    public void init(){
        loadAllTeams();
    }

    @Transactional
    public String createTeam(){
        this.teamsDAO.persist(teamToCreate);
        return "index?faces-redirect=true";
    }

    private void loadAllTeams(){
        this.allTeams = teamsDAO.loadAll();
    }
}
