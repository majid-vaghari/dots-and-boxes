package net.data.protocol;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> The Protocol class looks for specified keywords in our protocol in a given xml file and saves all of them in a
 * HashMap object </p> <p> The keywords are given in the protocol.xml file in the same folder as source of this class.
 * </p> <p> Created by Majid Vaghari on 12/4/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.3.0
 * @see HashMap
 * @since version 1.3.0
 */
public class Protocol {
    private static final Map<String, String> KEYWORDS = new HashMap<>();

    private static final String XML_PATH  = "Core/src/net/data/protocol/protocol.xml";
    private static final String ROOT_NAME = "protocol";
    private static final String DELIMITER = ".";

    static {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            final Document        dom     = builder.parse(XML_PATH);

            final Element root = dom.getDocumentElement();

            if (root.getNodeName().equalsIgnoreCase(ROOT_NAME))
                getChildren(root);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void getChildren(Node node) {
        final NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            final Node item = children.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                if (item.getChildNodes().getLength() == 1) {
                    String name   = item.getNodeName();
                    Node   parent = item;
                    while (!(parent = parent.getParentNode()).getNodeName().equalsIgnoreCase(ROOT_NAME))
                        name = parent.getNodeName() + DELIMITER + name;

                    KEYWORDS.put(name, item.getTextContent().trim());
                } else
                    getChildren(item);
            }
        }

    }

    public static String get(String key) {
        try {
            String value = KEYWORDS.get(key);

            if (value == null)
                throw new NullPointerException();

            return value;
        } catch (ClassCastException | NullPointerException ignored) {
            return "";
        }
    }
}
