package no.stelar7.api.l4j8.tests.summoner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javafx.util.Pair;
import no.stelar7.api.l4j8.basic.DataCall;
import no.stelar7.api.l4j8.basic.DataCall.DataCallBuilder;
import no.stelar7.api.l4j8.basic.DataCall.ResponseType;
import no.stelar7.api.l4j8.basic.Server;
import no.stelar7.api.l4j8.basic.URLEndpoint;
import no.stelar7.api.l4j8.pojo.summoner.masteries.Mastery;
import no.stelar7.api.l4j8.pojo.summoner.masteries.MasteryPage;
import no.stelar7.api.l4j8.tests.SecretFile;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SummonerMasteriesTest
{

    DataCallBuilder builder = DataCall.builder();

    @Before
    public void init()
    {
        System.err.println("TESTING SUMMONER MASTERIES");
        this.builder.withAPIKey(SecretFile.API_KEY);
        this.builder.withServer(Server.EUW);
        this.builder.withEndpoint(URLEndpoint.SUMMONER_MASTERIES_BY_ID);
    }

    @Test
    public void doTest()
    {
        // Generate list of summoner IDs
        List<String> keys = Arrays.asList("19613950", "22291359", "33540589");

        // Add them as a parameter to the URL
        keys.forEach((String k) -> this.builder.withURLData("{summonerId}", k));

        // Get the response
        final Pair<ResponseType, Object> dataCall = this.builder.build();

        // Map it to the correct return value
        Map<String, List<MasteryPage>> data = (Map<String, List<MasteryPage>>) dataCall.getValue();

        // Make sure all the data is returned as expected
        data.forEach(doAssertions);
    }

    private BiConsumer<String, List<MasteryPage>> doAssertions = (String key, List<MasteryPage> value) -> {

        value.forEach((MasteryPage page) -> {
            Assert.assertNotNull("Mastery Page does not have an id", page.getId());
            Assert.assertNotNull("Mastery Page does not have a name", page.getName());
            Assert.assertNotNull("Mastery Page does not contain any masteries", page.getMasteries());
            Assert.assertNotNull("Unable to determine current Mastery page", page.isCurrent());

            page.getMasteries().forEach((Mastery mastery) -> {
                Assert.assertNotNull("Mastery does not have an id", mastery.getId());
                Assert.assertNotNull("Mastery does not have a rank", mastery.getRank());

                Assert.assertNotEquals("Mastery does not have a valid id", mastery.getId(), (Integer) 0);
                Assert.assertNotEquals("Mastery does not have a valid rank", mastery.getRank(), (Integer) 0);
            });
        });

        Assert.assertTrue("There is not exactly ONE \"current\" page", value.stream().filter((MasteryPage page) -> page.isCurrent()).count() == 1);
    };
}