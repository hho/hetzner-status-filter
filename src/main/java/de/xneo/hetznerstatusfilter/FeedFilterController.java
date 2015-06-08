package de.xneo.hetznerstatusfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

@Controller
public class FeedFilterController {
    private final Logger logger;
    private final FeedSource feedSource;
    private final Transformer transformer;
    private final XPathFactory xpf;

    @Autowired
    public FeedFilterController(FeedSource feedSource) throws TransformerConfigurationException {
        this.feedSource = feedSource;

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();

        xpf = XPathFactory.newInstance();

        logger = LoggerFactory.getLogger(getClass());
    }

    @RequestMapping(value = "/{language}.atom", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public void filterFeed(
            @PathVariable("language") Language language,
            @RequestParam(value = "filter", required = false) Set<String> filter,
            OutputStream output
    ) throws IOException, SAXException, TransformerException, XPathExpressionException {
        logger.info("Incoming Request; filtering {}", filter);

        Document doc = feedSource.getFeed(language);
        Element feed = (Element) doc.getFirstChild();

        if (filter != null && !filter.isEmpty()) {
            XPathExpression xpath = xpf.newXPath().compile("title/text()");
            NodeList entries = feed.getElementsByTagName("entry");
            for (int i = entries.getLength() - 1; i >= 0; i--) {
                Element entry = (Element) entries.item(i);
                String title = xpath.evaluate(entry);
                for (String term : filter) {
                    if (title.toLowerCase().contains(term)) {
                        if (entry.getPreviousSibling().getNodeType() == Node.TEXT_NODE) {
                            feed.removeChild(entry.getPreviousSibling());
                        }
                        feed.removeChild(entry);
                        break;
                    }
                }
            }
        }
        DOMSource source = new DOMSource(feed);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }
}
