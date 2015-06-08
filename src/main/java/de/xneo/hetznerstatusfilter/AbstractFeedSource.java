package de.xneo.hetznerstatusfilter;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractFeedSource implements FeedSource {
    protected final DocumentBuilder db;

    protected AbstractFeedSource() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Document getFeed(Language language) throws IOException, SAXException {
        checkNotNull(language);
        InputStream xmlInput = getFeedInputStream(language);
        return db.parse(xmlInput);
    }

    protected abstract InputStream getFeedInputStream(Language language) throws IOException;
}
