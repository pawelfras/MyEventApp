package com.example.myapplication;

import java.io.File;

import javax.xml.transform.TransformerException;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EventForm extends VerticalLayout implements View {
	private Label label = new Label("<b>Event Feedback</b>",ContentMode.HTML);
	private TextField eventname = new TextField();
	
	private NativeSelect<Integer> rating = new NativeSelect<>();
	private DateField date = new DateField();
	private Button submit = new Button("Submit");

	private EventService service = EventService.getInstance();
	private EventFeedback eventFeedback;
	private MyEvent event;
	private MyEvent eventToModify;
	private XMLReader reader = new XMLReader();
	private Label hint = new Label();
	private Binder<MyEvent> binder = new Binder<>(MyEvent.class);
	String username;
	
	public EventForm(EventFeedback eventFeedback, String username, MyEvent eventToModify){
		this.eventFeedback = eventFeedback;
		this.username = username;
		this.eventToModify = eventToModify;
		//label.setSizeFull();
		//submit.setSizeFull();
		eventname.setSizeFull();
		date.setSizeFull();
		date.setDateFormat("yyyy-MM-dd");
		rating.setSizeFull();
		rating.setItems(1,2,3,4,5);
		
		eventname.setValue(eventToModify.getEvent());
		rating.setValue(eventToModify.getRating());
		date.setValue(eventToModify.getDate());
		hint.setValue("Here you can edit the \n"+"rating reveived from user "+eventToModify.getUsername());
		hint.setSizeFull();
		hint.setContentMode(ContentMode.HTML);
		VerticalLayout vertLayout = new VerticalLayout(label,hint,eventname,rating,date,submit);
		
		vertLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(hint, Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(eventname, Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(rating, Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(date, Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(submit, Alignment.MIDDLE_CENTER);
		
		//addComponents(label,eventName,status,date,buttons);
		Panel panel = new Panel(vertLayout);
		panel.setWidth("250px");
		this.addComponent(panel);
		this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		this.setHeight("100%");
		
		//binder.bindInstanceFields(this);
		
		submit.addClickListener(b->modifyEvent());
	}
	public EventForm(EventFeedback eventFeedback, String username){
		this.eventFeedback = eventFeedback;
		this.username = username;
		//label.setSizeFull();
		//submit.setSizeFull();
		eventname.setSizeFull();
		eventname.setPlaceholder("Event name");
		date.setSizeFull();
		date.setDateFormat("yyyy-MM-dd");
		date.setPlaceholder("Set a date");
		rating.setSizeFull();
		rating.setItems(1,2,3,4,5);
		rating.setValue(1);
		
		VerticalLayout vertLayout = new VerticalLayout(label,eventname,rating,date,submit);
		vertLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(eventname, Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(rating, Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(date, Alignment.MIDDLE_CENTER);
		vertLayout.setComponentAlignment(submit, Alignment.MIDDLE_CENTER);
		
		//addComponents(label,eventName,status,date,buttons);
		Panel panel = new Panel(vertLayout);
		panel.setWidth("250px");
		this.addComponent(panel);
		this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		this.setHeight("100%");
		
		//binder.bindInstanceFields(this);
		
		submit.addClickListener(b->save());
	}
	
	public void setEvent(MyEvent event){
		this.event = event;
		this.event.setUsername(username);
		this.event.setEvent(eventname.getValue());
		this.event.setDate(date.getValue());
		this.event.setRating(rating.getValue());
	}
	public void modifyEvent(){
		//this.setEvent(eventToModify);
		this.event = eventToModify;
		this.event.setEvent(eventname.getValue());
		this.event.setDate(date.getValue());
		this.event.setRating(rating.getValue());
		service.save(event);
		reader.modifyExistedEvent(event);
		eventFeedback.updateList();
		getUI().getNavigator().navigateTo("event");
	}
	public void save(){
		this.setEvent(new MyEvent());
		if(service.save(event)){
			reader.saveToFile(event);
		}
		eventFeedback.updateList();
		getUI().getNavigator().navigateTo("event");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
