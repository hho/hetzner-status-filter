package de.xneo.hetznerstatusfilter;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Primary
public class DummyFeedSource extends AbstractFeedSource {

    @Override
    protected InputStream getFeedInputStream(Language language) throws IOException {
        return getClass().getClassLoader().getResourceAsStream("atom.xml");
    }
}
