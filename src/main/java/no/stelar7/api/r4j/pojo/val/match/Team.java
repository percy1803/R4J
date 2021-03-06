package no.stelar7.api.r4j.pojo.val.match;

import java.io.Serializable;
import java.util.Objects;

public class Team implements Serializable
{
    private static final long serialVersionUID = -1562243042489225119L;
    
    private String  teamId;
    private Boolean won;
    private Integer roundsPlayed;
    private Integer roundsWon;
    private Integer numPoints;
    
    public Integer getNumPoints()
    {
        return numPoints;
    }
    
    public String getTeamId()
    {
        return teamId;
    }
    
    public Boolean getWon()
    {
        return won;
    }
    
    public Integer getRoundsPlayed()
    {
        return roundsPlayed;
    }
    
    public Integer getRoundsWon()
    {
        return roundsWon;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Team team = (Team) o;
        return Objects.equals(teamId, team.teamId) &&
               Objects.equals(won, team.won) &&
               Objects.equals(roundsPlayed, team.roundsPlayed) &&
               Objects.equals(roundsWon, team.roundsWon) &&
               Objects.equals(numPoints, team.numPoints);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(teamId, won, roundsPlayed, roundsWon, numPoints);
    }
    
    @Override
    public String toString()
    {
        return "Team{" +
               "teamId='" + teamId + '\'' +
               ", won=" + won +
               ", roundsPlayed=" + roundsPlayed +
               ", roundsWon=" + roundsWon +
               ", numPoints=" + numPoints +
               '}';
    }
}
