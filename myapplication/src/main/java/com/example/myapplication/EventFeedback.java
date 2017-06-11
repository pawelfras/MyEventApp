package com.example.myapplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class EventFeedback extends VerticalLayout implements View {

	private EventService service = EventService.getInstance();
	private TextField tfFilter = new TextField(); 
	private Grid<MyEvent> grid = new Grid<>(MyEvent.class);
	private Button add = new Button("Add an event");
	private Label loggedUser;
	private Button logoutLink = new Button("Logout");
	private boolean isAdmin = false;
	String username;
	public EventFeedback(int isAdmin, String username){
		if (isAdmin!=0){
			this.isAdmin = true;
		}
		this.username = username;
		final VerticalLayout logout = new VerticalLayout();
		final HorizontalLayout buttons = new HorizontalLayout();
		final VerticalLayout layout = new VerticalLayout();
		
		loggedUser = new Label("Hello, "+username+"!");
		logoutLink.setCaption("Logout");
		logoutLink.addClickListener(e->{
			getUI().getNavigator().addView("login", new Login().getClass());
	        getUI().getNavigator().navigateTo("login");
		});
		logoutLink.setStyleName(ValoTheme.BUTTON_LINK);
		logout.addComponents(loggedUser,logoutLink);
		logout.setComponentAlignment(loggedUser, Alignment.MIDDLE_RIGHT);
		logout.setComponentAlignment(logoutLink, Alignment.TOP_RIGHT);
		this.addComponent(logout);
		this.setComponentAlignment(logout, Alignment.TOP_RIGHT);
		
		add.addClickListener(b->{
			grid.asSingleSelect().clear();
			
			getUI().getNavigator().addView("eventForm", new EventForm(this, username));
			getUI().getNavigator().navigateTo("eventForm");
		});
		
		buttons.addComponent(add);
		grid.setColumns("username","event","date","rating");
		grid.setWidth("100%");
		
		tfFilter.setPlaceholder("Search an event");
		tfFilter.addValueChangeListener(e->updateList());
		tfFilter.setValueChangeMode(ValueChangeMode.LAZY);
		Button clearFilter = new Button(VaadinIcons.CLOSE);
		clearFilter.setDescription("Clear for corrent filter");
		clearFilter.addClickListener(b-> tfFilter.clear());
		
		CssLayout filtering = new CssLayout();
		filtering.addComponents(tfFilter,clearFilter);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		
		layout.addComponents(filtering,grid,buttons);
		layout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		
		
		updateList();
		
		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		
		if(this.isAdmin){
		grid.asSingleSelect().addValueChangeListener(e->{
			if(e.getValue()!=null){
				getUI().getNavigator().addView("eventForm", new EventForm(this, username, e.getValue()));
				getUI().getNavigator().navigateTo("eventForm");
			}
		});
		}
		
		//Notification.show(service.findAll().get(0).getEvent());
	}

	public void updateList() {
		List<MyEvent> events = service.findAll(tfFilter.getValue());
		grid.setItems(events);
		grid.setHeightByRows(events.size());
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
	
}
