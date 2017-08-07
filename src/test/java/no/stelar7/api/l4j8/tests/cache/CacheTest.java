package no.stelar7.api.l4j8.tests.cache;

import no.stelar7.api.l4j8.basic.cache.*;
import no.stelar7.api.l4j8.basic.calling.DataCall;
import no.stelar7.api.l4j8.basic.constants.api.*;
import no.stelar7.api.l4j8.impl.L4J8;
import no.stelar7.api.l4j8.pojo.match.MatchReference;
import no.stelar7.api.l4j8.tests.SecretFile;
import org.junit.*;
import org.junit.rules.Stopwatch;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheTest
{
    
    final L4J8 l4j8 = new L4J8(SecretFile.CREDS);
    
    @Rule
    public Stopwatch stopwatch = new Stopwatch() {};
    
    @Test
    public void testMemoryCache() throws InterruptedException
    {
        DataCall.setCacheProvider(new MemoryCacheProvider(5));
        doCacheStuff();
    }
    
    @After
    public void clearCacheProvider()
    {
        DataCall.setCacheProvider(CacheProvider.EmptyProvider.INSTANCE);
    }
    
    
    private void doCacheStuff() throws InterruptedException
    {
        List<MatchReference> recents = l4j8.getMatchAPI().getRecentMatches(Platform.EUW1, Constants.TEST_ACCOUNT_IDS[0]);
        MatchReference       ref     = recents.get(0);
        
        System.out.println("Starting timer");
        
        long start = stopwatch.runtime(TimeUnit.MICROSECONDS);
        ref.getFullMatch();
        System.out.println("1x url fetch time: " + (stopwatch.runtime(TimeUnit.MICROSECONDS) - start) + "µs");
        
        start = stopwatch.runtime(TimeUnit.MICROSECONDS);
        ref.getFullMatch();
        System.out.println("1x memory fetch time: " + (stopwatch.runtime(TimeUnit.MICROSECONDS) - start) + "µs");
        
        start = stopwatch.runtime(TimeUnit.MICROSECONDS);
        for (int i = 0; i < 10; i++)
        {
            ref.getFullMatch();
        }
        System.out.println("10x memory fetch time: " + (stopwatch.runtime(TimeUnit.MICROSECONDS) - start) + "µs");
        System.out.println();
        
        System.out.println("clearing cache");
        System.out.println();
        DataCall.getCacheProvider().clear(URLEndpoint.V3_MATCH);
        
        start = stopwatch.runtime(TimeUnit.MICROSECONDS);
        ref.getFullMatch();
        System.out.println("1x url fetch time: " + (stopwatch.runtime(TimeUnit.MICROSECONDS) - start) + "µs");
        
        start = stopwatch.runtime(TimeUnit.MICROSECONDS);
        for (int i = 0; i < 10; i++)
        {
            ref.getFullMatch();
        }
        System.out.println("10x memory fetch time: " + (stopwatch.runtime(TimeUnit.MICROSECONDS) - start) + "µs");
        System.out.println();
        
        System.out.println("Fetching 3 aditional matches");
        recents.get(1).getFullMatch();
        recents.get(2).getFullMatch();
        recents.get(3).getFullMatch();
        
        System.out.println("Cache size: " + DataCall.getCacheProvider().getSize());
        
        System.out.println("Waiting for cache timeout");
        TimeUnit.SECONDS.sleep(DataCall.getCacheProvider().getTimeToLive() - stopwatch.runtime(TimeUnit.SECONDS));
        
        System.out.println("Cache size: " + DataCall.getCacheProvider().getSize());
        
        System.out.println("Re-fetching cached items");
        recents.get(0).getFullMatch();
        recents.get(1).getFullMatch();
        recents.get(2).getFullMatch();
        recents.get(3).getFullMatch();
        
        System.out.println("Cache size: " + DataCall.getCacheProvider().getSize());
        System.out.println();
    }
    
}
