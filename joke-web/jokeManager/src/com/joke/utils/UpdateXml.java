package com.joke.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateXml {
	/** log */
	private static Log log = LogFactory.getLog(UpdateXml.class);

	public static boolean doc2XmlFile(Document document, String filename)
			throws Exception {
		boolean flag = true;
		try {

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			// transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
			log.info(ex);
			throw ex;
		}
		return flag;
	}

	public static Document load(String filename) throws Exception {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filename));
			document.normalize();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info(ex);
			throw ex;
		}
		return document;
	}

	public static void xmlUpdate(String name, String channel, String nodeValue)
			throws Exception {
		Document document = load(name);
		Node root = document.getDocumentElement();

		if (root.hasChildNodes()) {

			NodeList ftpnodes = root.getChildNodes();

			// System.out.println(root.getNodeName());

			for (int i = 0; i < ftpnodes.getLength(); i++) {
				// NodeList ftplist = ftpnodes.item(i).getChildNodes();
				Node result = ftpnodes.item(i);

				if (result.getNodeType() == Node.ELEMENT_NODE) {
					NamedNodeMap node = result.getAttributes();
					System.out.println();
					if ("string".equals(result.getNodeName())) {
						if (node.getNamedItem("name").getNodeValue()
								.equals(nodeValue)) {
							result.setTextContent(channel);
						}
					} else {
						if (node.getNamedItem("name").getNodeValue()
								.equals(nodeValue)) {
							node.getNamedItem("value").setNodeValue(channel);
						}
					}
				}

			}
		}
		doc2XmlFile(document, name);
	}

	public static void main(String args[]) throws Exception {
		// UpdateXml.xmlUpdate("D:/xml/joy_laucher_backup_preferences.xml",
		// "sassss","channel");

		UpdateXml.xmlUpdate("D:/data/joy_laucher_backup_preferences.xml",
				"true", "ordinary_user");
	}
}
