package no.stelar7.api.l4j8.tests.currentgame;

import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javafx.util.Pair;
import no.stelar7.api.l4j8.basic.DataCall;
import no.stelar7.api.l4j8.basic.DataCall.DataCallBuilder;
import no.stelar7.api.l4j8.basic.DataCall.ResponseType;
import no.stelar7.api.l4j8.basic.Platform;
import no.stelar7.api.l4j8.basic.Server;
import no.stelar7.api.l4j8.basic.URLEndpoint;
import no.stelar7.api.l4j8.tests.SecretFile;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CurrentGameTest
{

    DataCallBuilder builder = DataCall.builder();

    @Before
    public void init()
    {
        System.err.println("TESTING CURRENT GAME");
        this.builder.withAPIKey(SecretFile.API_KEY);
        this.builder.withServer(Server.EUW);
        this.builder.withEndpoint(URLEndpoint.CURRENTGAME);
    }

    public void parseResult(final Pair<ResponseType, Object> result)
    {
        switch (result.getKey())
        {
            case OK:
            {
                if (result.getValue() instanceof Map)
                {
                    ((Map<?, ?>) result.getValue()).keySet().forEach(k -> {
                        System.out.println(k);
                        System.out.println("\t" + ((Map<?, ?>) result.getValue()).get(k));
                    });
                } else
                {
                    System.out.println(result.getValue());
                }
                break;
            }
            default:
            {
                System.out.println(result.getKey());
                System.out.println(result.getValue());
                break;
            }
        }
    }

    @Test
    public void testSingle()
    {
        System.err.println("TESTING SINGLE");
        this.builder.withVerbose(true);
        this.builder.withURLData("{summonerId}", "19613950");
        this.builder.withURLData("{platformId}", Platform.EUW1.name());
        final Pair<ResponseType, Object> summonerCall = this.builder.build();
        this.parseResult(summonerCall);
    }
}