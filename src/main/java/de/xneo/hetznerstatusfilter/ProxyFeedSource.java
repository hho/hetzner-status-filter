package de.xneo.hetznerstatusfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ProxyFeedSource extends AbstractFeedSource {

    private final String baseUri;

    @Autowired
    public ProxyFeedSource(@Value("${baseUri}") String baseUri) {
        super();
        this.baseUri = baseUri;
    }

    protected InputStream getFeedInputStream(Language language) throws IOException {
        String uri = baseUri + language + ".atom";

        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/atom+xml");

        return connection.getInputStream();
    }
}
