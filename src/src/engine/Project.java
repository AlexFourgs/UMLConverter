package engine;

import java.io.Serializable;

import textualuml.ObjectProgram;

public class Project implements Serializable {
	private String name ;
	private String place ;
	private ObjectProgram program = new ObjectProgram();
	
	public Project(){
	}
	
	public Project(String name, String place) {
		this.name = name ;
		this.place = place ;
	}
	
	public void setName (String name){
		this.name = name ;
	}
	
	public String getName() {
		return name ;
	}
	
	public String getPlace () {
		return place ;
	}
	
	public void setPlace (String place){
		this.place = place ;
	}
	
	public ObjectProgram getProjectProgram() {
		return program ;
	}
}
