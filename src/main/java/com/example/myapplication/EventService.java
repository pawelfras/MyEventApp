package com.example.myapplication;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Notification;

public class EventService {
	private static EventService instance;
	private List<MyEvent> events = new ArrayList<>();
	public XMLReader reader = new XMLReader();
	
	private EventService(){		
	}
	
	public static EventService getInstance(){
		if(instance == null){
			instance = new EventService();
			instance.getData();
		}
		return instance;
	}
	
	public List<MyEvent> findAll(){
		return events;
	}
	
	public List<MyEvent> findAll(String filter){
		ArrayList<MyEvent> arrayList = new ArrayList<>();
		for (MyEvent event : events){
			boolean passesFilter = (filter == null || filter.isEmpty()) || event.getEvent().toLowerCase().contains(filter.toLowerCase());
			if(passesFilter){
				arrayList.add(event);
			}
		}
		return arrayList;
	}
	
	public void getData(){
		for(int i=0; i<reader.events.size();i++){
			MyEvent e = new MyEvent();
			e.setId(Integer.parseInt(reader.events.get(i)[0]));
			e.setUsername(reader.events.get(i)[1]);
			e.setEvent(reader.events.get(i)[2]);
			e.setDate(LocalDate.parse(reader.events.get(i)[3]));
			e.setRating(Integer.parseInt(reader.events.get(i)[4]));
			save(e);
		}
	}
	
	public boolean save(MyEvent entry){
		boolean saved = true;
		int id=0;
		if (events.isEmpty()){
			saved = true;
		}
		for ( MyEvent event : events){
			if(event.getEvent().equals(entry.getEvent())){
				saved = false;
			} 
			id++;
		}
		if(saved){
			entry.setId(id);
			events.add(entry);
		}
		return saved;
	}
}
