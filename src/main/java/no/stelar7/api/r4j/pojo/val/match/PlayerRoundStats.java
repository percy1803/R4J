package no.stelar7.api.r4j.pojo.val.match;

import java.io.Serializable;
import java.util.*;

public class PlayerRoundStats implements Serializable
{
    private static final long serialVersionUID = -8892321230434651879L;
    
    private String       puuid;
    private List<Kill>   kills;
    private List<Damage> damage;
    private int          score;
    private Economy      economy;
    private Ability      ability;
    
    public String getPuuid()
    {
        return puuid;
    }
    
    public List<Kill> getKills()
    {
        return kills;
    }
    
    public List<Damage> getDamage()
    {
        return damage;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public Economy getEconomy()
    {
        return economy;
    }
    
    public Ability getAbility()
    {
        return ability;
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
        PlayerRoundStats that = (PlayerRoundStats) o;
        return score == that.score &&
               Objects.equals(puuid, that.puuid) &&
               Objects.equals(kills, that.kills) &&
               Objects.equals(damage, that.damage) &&
               Objects.equals(economy, that.economy) &&
               Objects.equals(ability, that.ability);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(puuid, kills, damage, score, economy, ability);
    }
    
    @Override
    public String toString()
    {
        return "PlayerRoundStats{" +
               "puuid='" + puuid + '\'' +
               ", kills=" + kills +
               ", damage=" + damage +
               ", score=" + score +
               ", economy=" + economy +
               ", ability=" + ability +
               '}';
    }
}
