package no.stelar7.api.r4j.impl.lol.liveclient;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import no.stelar7.api.r4j.basic.calling.DataCallBuilder;
import no.stelar7.api.r4j.basic.constants.api.*;
import no.stelar7.api.r4j.basic.exceptions.APIResponseException;
import no.stelar7.api.r4j.basic.utils.*;
import no.stelar7.api.r4j.pojo.lol.liveclient.*;
import no.stelar7.api.r4j.pojo.lol.liveclient.events.*;
import no.stelar7.api.r4j.pojo.lol.replay.ReplayTeamType;

import java.util.*;

public class LiveClientDataAPI
{
    public static ActiveGameData getGameData()
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_GAME_DATA)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            JsonObject obj = ((JsonElement) data).getAsJsonObject();
            
            ActiveGameData returnMe = new ActiveGameData(
                    Utils.getGson().fromJson(obj.get("activePlayer"), ActiveGameClientPlayer.class),
                    Utils.getGson().fromJson(obj.get("allPlayers"), new TypeToken<List<ActiveGamePlayer>>() {}.getType()),
                    parseEventObjectToList(obj.get("events")),
                    Utils.getGson().fromJson(obj.get("gameData"), ActiveGameState.class));
            
            return returnMe;
            
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    private static List<GameEvent> parseEventObjectToList(JsonElement data)
    {
        List<GameEvent> events = new ArrayList<>();
        JsonArray       obj    = data.getAsJsonObject().getAsJsonArray("Events");
        for (JsonElement elem : obj)
        {
            String        eventName = elem.getAsJsonObject().get("EventName").getAsString();
            GameEventType type      = GameEventType.getFromCode(eventName);
            
            switch (type)
            {
                case GAME_START:
                case GAME_END:
                case MINIONS_SPAWNING:
                {
                    events.add(Utils.getGson().fromJson(elem, GameEvent.class));
                    break;
                }
                case CHAMPION_KILL:
                {
                    events.add(Utils.getGson().fromJson(elem, ChampionKillEvent.class));
                    break;
                }
                case FIRST_BLOOD:
                case FIRST_TOWER:
                {
                    events.add(Utils.getGson().fromJson(elem, RewardEvent.class));
                    break;
                }
                
                case TOWER_KILLED:
                {
                    events.add(Utils.getGson().fromJson(elem, TurretKillEvent.class));
                    break;
                }
                
                case INHIBITOR_KILLED:
                {
                    events.add(Utils.getGson().fromJson(elem, InhibKillEvent.class));
                    break;
                }
                
                case HERALD_KILLED:
                case BARON_KILLED:
                {
                    events.add(Utils.getGson().fromJson(elem, EpicUnitKillEvent.class));
                    break;
                }
                
                case DRAGON_KILLED:
                {
                    events.add(Utils.getGson().fromJson(elem, DragonKillEvent.class));
                    break;
                }
                
                case INHIBITOR_RESPAWNING_SOON:
                {
                    events.add(Utils.getGson().fromJson(elem, InhibRespawningSoonEvent.class));
                    break;
                }
                
                case INHIBITOR_RESPAWNED:
                {
                    events.add(Utils.getGson().fromJson(elem, InhibRespawnedEvent.class));
                    break;
                }
                
                case MULTIKILL:
                {
                    events.add(Utils.getGson().fromJson(elem, MultikillEvent.class));
                    break;
                }
                
                case TEAM_ACE:
                {
                    events.add(Utils.getGson().fromJson(elem, AceEvent.class));
                    break;
                }
                
                case UNKNOWN:
                default:
                    System.out.println("Unknown event: " + elem.toString());
            }
        }
        return events;
    }
    
    public static List<GameEvent> getEventData()
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_EVENT_DATA)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            
            return parseEventObjectToList((JsonElement) data);
        } catch (APIResponseException e)
        {
            return Collections.emptyList();
        }
    }
    
    public static ActiveGameState getGameStats()
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_GAME_STATS)
                    .withRequestMethod("GET")
                    .build();
            
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (ActiveGameState) data;
            
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    
    public static ActiveGameClientPlayer getActivePlayer()
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_ACTIVE_PLAYER)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (ActiveGameClientPlayer) data;
            
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    public static ActiveGameClientPlayerAbilities getActivePlayerAbilities()
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_ACTIVE_PLAYER_ABILITIES)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (ActiveGameClientPlayerAbilities) data;
            
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    public static ActiveGameClientPlayerPerks getActivePlayerRunes()
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_ACTIVE_PLAYER_RUNES)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (ActiveGameClientPlayerPerks) data;
            
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    public static String getActivePlayerName()
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_ACTIVE_PLAYER_NAME)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (String) data;
            
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    public static List<ActiveGamePlayer> getPlayerList(ReplayTeamType team)
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_PLAYER_LIST)
                    .withQueryParameter("teamID", team.getValue())
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (List<ActiveGamePlayer>) data;
            
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    public static ActiveGamePlayerPerks getPlayerMainRunes(String summoner)
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_PLAYER_RUNES)
                    .withQueryParameter("summonerName", summoner)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (ActiveGamePlayerPerks) data;
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    public static List<ActiveGameItem> getPlayerItems(String summoner)
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_PLAYER_ITEMS)
                    .withQueryParameter("summonerName", summoner)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (List<ActiveGameItem>) data;
            
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    public static ActiveGamePlayerScores getPlayerScores(String summoner)
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_PLAYER_SCORES)
                    .withQueryParameter("summonerName", summoner)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (ActiveGamePlayerScores) data;
        } catch (APIResponseException e)
        {
            return null;
        }
    }
    
    public static ActiveGamePlayerSummonerSpells getPlayerSummonerSpells(String summoner)
    {
        try
        {
            Object data = new DataCallBuilder()
                    .withLimiters(false)
                    .withProxy(Constants.CLIENT_PROXY)
                    .withEndpoint(URLEndpoint.LIVECLIENT_PLAYER_SUMMONERS)
                    .withQueryParameter("summonerName", summoner)
                    .withRequestMethod("GET")
                    .build();
            
            if (data instanceof Pair)
            {
                return null;
            }
            
            return (ActiveGamePlayerSummonerSpells) data;
        } catch (APIResponseException e)
        {
            return null;
        }
    }
}
