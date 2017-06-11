package com.example.myapplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLREad {
	static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	Document doc;
	static List<String[]> users = new ArrayList<String[]>();
	static List<String[]> events = new ArrayList<String[]>();
	public static void main(String[] args){
		//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("DB2.xml");
			NodeList userList = doc.getElementsByTagName("user");
			//System.out.println(userList.getLength());
			for(int i = 0; i<userList.getLength(); i++){
				Node nUser = userList.item(i);
				Element user = (Element) nUser;
				
				users.add(i,new String[3]);
				users.get(i)[0] = user.getAttribute("login");
				users.get(i)[1] = user.getAttribute("password");
				users.get(i)[2] = user.getAttribute("isAdmin");
			}
			
			NodeList eList = doc.getElementsByTagName("event");
			NodeList personList = doc.getElementsByTagName("event");
			for(int i = 0; i < personList.getLength(); i++){
				Node p = personList.item(i);
				if(p.getNodeType() == Node.ELEMENT_NODE){
					Element person = (Element) p;
					events.add(i,new String[5]);
					events.get(i)[0] = person.getAttribute("id");
					String id = person.getAttribute("id");
					NodeList nameList = person.getChildNodes();
					System.out.println(nameList.getLength());
					int a=0;
					for(int j=0; j<nameList.getLength(); j++){
						
						Node n = nameList.item(j);
						if(n.getNodeType()==Node.ELEMENT_NODE){
							Element name = (Element) n;
							events.get(i)[a+1] = name.getTextContent();
							System.out.println("Person " + id + ":" + name.getTagName() + "=" + name.getTextContent());
							System.out.println(events.get(i)[a+1]);
							++a;
						}
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
		for (int i=0; i< events.size(); i++){
			for(int j=0; j<events.get(i).length;j++){
				System.out.println(events.get(i)[j]);
			}
			
		}
	}
}