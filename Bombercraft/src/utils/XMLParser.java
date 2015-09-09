package utils;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bombercraft.game.entity.Helper;

public class XMLParser{
	private Document doc = null;
	
	public XMLParser(String fileName){
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try{
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(ResourceLoader.load(fileName));
		}catch (SAXException | IOException | ParserConfigurationException e){
			e.printStackTrace();
		};
	}
	
	public HashMap<String, String> getData(String search){
		HashMap<String, String> result = new HashMap<String, String>();
		NodeList data = doc.getElementsByTagName(search).item(0).getChildNodes();
		for (int i = 0; i < data.getLength(); i++){
			Node e = data.item(i);
			if(e.getFirstChild() != null)
				result.put(e.getNodeName(), e.getFirstChild().getNodeValue());
		}
		return result;
	}
	
	
}
