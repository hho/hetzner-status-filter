package de.xneo.hetznerstatusfilter;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HetznerStatusFilterApplication.class)
@WebAppConfiguration
public class HetznerStatusFilterApplicationTests {

    @Autowired
    private FeedFilterController filterController;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testEmptyFilter() throws Exception {
        filterController.filterFeed(Language.de, Collections.emptySet(), System.out);
    }

    @Test
    public void testFilterVhost() throws Exception {
        filterController.filterFeed(Language.de, Collections.singleton("vhost"), System.out);
    }

    @Test
    public void testFilterEverything() throws Exception {
        ImmutableSet<String> terms = ImmutableSet.of("vhost", "sql");
        filterController.filterFeed(Language.de, terms, System.out);
    }
}
