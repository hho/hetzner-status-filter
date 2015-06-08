package de.xneo.hetznerstatusfilter;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface FeedSource {
    Document getFeed(Language language) throws IOException, SAXException;
}
