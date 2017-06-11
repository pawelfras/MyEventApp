package com.example.myapplication;

import java.time.LocalDate;

public class MyEvent {
	private int id;
	private String username = "";
	private String event = "";
	private LocalDate date;
	private int rating;
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setEvent(String event){
		this.event = event;
	}
	
	public String getEvent(){
		return event;
	}
	
	public void setDate(LocalDate date){
		this.date = date;
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	public void setRating(int rating){
		this.rating = rating;
	}
	
	public int getRating(){
		return rating;
	}
}


