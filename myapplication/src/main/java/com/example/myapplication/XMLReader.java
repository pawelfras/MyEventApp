package com.example.myapplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader {
	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder builder;
	private Document doc;
	//private EventForm eventForm;
	public List<String[]> users = new ArrayList<String[]>();
	public List<String[]> events = new ArrayList<String[]>();
	
	public void modifyExistedEvent(MyEvent entry){
		NodeList events = doc.getElementsByTagName("event");
		
		for(int i = 0; i<events.getLength(); i++){
			Element event = (Element) events.item(i);
			if(event.getAttribute("id").equals(Integer.toString(entry.getId()))){
				NodeList parameters = event.getChildNodes();
				//parameters.item(0).setTextContent(entry.getUsername());
				parameters.item(1).setTextContent(entry.getEvent());
				parameters.item(2).setTextContent(entry.getDate().toString());
				parameters.item(3).setTextContent(Integer.toString(entry.getRating()));
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File file = new File("DB.xml");
			if (file.canRead()){
				StreamResult result = new StreamResult(file);
				transformer.transform(source, result);
			}
			
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveToFile(MyEvent entry) {
		Element event = doc.createElement("event");
		Element userName = doc.createElement("username");
		Element eventName = doc.createElement("eventname");
		Element date = doc.createElement("date");
		Element rating = doc.createElement("rating");
		
		event.setAttribute("id", Integer.toString(entry.getId()));
		userName.setTextContent(entry.getUsername());
		eventName.setTextContent(entry.getEvent());
		date.setTextContent(entry.getDate().toString());
		rating.setTextContent(Integer.toString(entry.getRating()));
		
		event.appendChild(userName);
		event.appendChild(eventName);
		event.appendChild(date);
		event.appendChild(rating);
		Node db = doc.getElementsByTagName("db").item(0).appendChild(event);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File file = new File("DB.xml");
			if (file.canRead()){
				StreamResult result = new StreamResult(file);
				transformer.transform(source, result);
			}
			
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public XMLReader(){
		
		//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try{
			factory.setIgnoringElementContentWhitespace(true);
	        //factory.setSchema(schema);
	        factory.setNamespaceAware(true);
			builder = factory.newDocumentBuilder();
			doc = builder.parse("DB.xml");
			NodeList userList = doc.getElementsByTagName("user");
			for(int i = 0; i<userList.getLength(); i++){
				Node nUser = userList.item(i);
				Element user = (Element) nUser;
				
				users.add(i,new String[3]);
				users.get(i)[0] = user.getAttribute("login");
				users.get(i)[1] = user.getAttribute("password");
				users.get(i)[2] = user.getAttribute("isAdmin");
			}
			
			NodeList eList = doc.getElementsByTagName("event");
			for(int i = 0; i<eList.getLength(); i++){
				Node nEvent = eList.item(i);
				Element event = (Element) nEvent;
				events.add(i,new String[5]);
				events.get(i)[0] = event.getAttribute("id");
				NodeList eventList = event.getChildNodes();
				int idx=0;
				for (int j=0; j<eventList.getLength();j++){
					Node nEventElement = eventList.item(j);
					if(nEventElement.getNodeType()==Node.ELEMENT_NODE){
						Element eventElement = (Element) nEventElement;
						events.get(i)[idx+1] = eventElement.getTextContent();
						++idx;
					}
				}
			}
			
		} catch (ParserConfigurationException e){
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
