package com.example.myapplication;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Login extends VerticalLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextField tfLogin;
	PasswordField tfPassword;
	Button btnLogin;
	XMLReader reader = new XMLReader();
	public Login(){
		tfLogin = new TextField();
		tfLogin.setPlaceholder("login");
		
		tfPassword = new PasswordField();
		tfPassword.setPlaceholder("password");
		
		btnLogin = new Button("Login");
		btnLogin.addStyleName("friendly");
		
		btnLogin.addClickListener(b->{
			for(int i = 0; i<reader.users.size(); i++){
				if(reader.users.get(i)[0].equals(tfLogin.getValue()) && reader.users.get(i)[1].equals(tfPassword.getValue())){
					Notification.show("You log in succesfully!");
					getUI().getNavigator().addView("event", new EventFeedback(Integer.parseInt(reader.users.get(i)[2]),tfLogin.getValue()));
					getUI().getNavigator().navigateTo("event");
				} 
			}
		});
		
		HorizontalLayout HLayout = new HorizontalLayout();
		HLayout.addComponent(btnLogin);
		
		VerticalLayout formLayout = new VerticalLayout(tfLogin,tfPassword, HLayout);
		formLayout.setMargin(true);
		formLayout.setComponentAlignment(tfLogin, Alignment.MIDDLE_CENTER);
		formLayout.setComponentAlignment(tfPassword, Alignment.MIDDLE_CENTER);
		formLayout.setComponentAlignment(HLayout, Alignment.MIDDLE_CENTER);
		
		Panel loginPanel = new Panel("Log in", formLayout);
		loginPanel.setWidth("450");
		loginPanel.setHeight("250");
		
		this.addComponent(loginPanel);
		this.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		this.setHeight("100%");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
