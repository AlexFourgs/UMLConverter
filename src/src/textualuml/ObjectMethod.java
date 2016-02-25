package textualuml;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjectMethod implements Serializable{
	private VisibilityEnum visibility ;
	private String name ;
	private String type ;
	private ArrayList<ObjectAttribute> parameters = new ArrayList <ObjectAttribute>() ;
	
	public ObjectMethod(VisibilityEnum visibility, String name, String type, ArrayList<ObjectAttribute> parameters){
		this.visibility = visibility ;
		this.name = name ;
		this.type = type ;
		
		for (int i = 0 ; i < parameters.size() ; i++){
			this.parameters.add(parameters.get(i));
		}
	}
	public VisibilityEnum getVisibility() {
		return visibility ;
	}
	
	public void setVisibility (VisibilityEnum visibility){
		this.visibility = visibility ;
	}
	
	public String getName() {
		return name ;
	}
	
	public void setName (String name){
		this.name = name ;
	}
	
	public String getType () {
		return type ;
	}
	
	public void setType (String type) {
		this.type = type ;
	}
	
	public ArrayList<ObjectAttribute> getParameter () {
		return parameters;
	}
	
	public void addParameter (ObjectAttribute parameter){
		parameters.add(parameter);
	}
	
	public void delParameter (ObjectAttribute parameter){
		parameters.remove(parameter);
	}
	/**
	 * 
	 * @return all parameters of the method as a list of String with "," between them for the method toString.
	 */
	protected String parametersIn () {
		String parametersString = "" ;
		for(int i = 0 ; i < parameters.size() ; i++){
			if (i == parameters.size()-1){
				parametersString = parametersString+ parameters.get(i).javaCodeParameter() ;
			}
			else {
				parametersString = parametersString + parameters.get(i).javaCodeParameter()+ ", ";
			}
		}
		return parametersString ;
	}

	public String stringForDraw() {
		return (visibility.getSign() + type + " (" + parametersIn() + "): " + name);
	}
	
	@Override
	public String toString () {
		return (visibility.getSign() + " " + type + " " + name + " (" + parametersIn() + ") ;"); 
	}
	
	/**
	 * The method toString is used for "write" a method in java language.
	 */
	public String javaCode () {
		String methodCode = "/**\n\t *" ;
		if (parameters.size() > 0){
			for (int i = 0 ; i < parameters.size() ; i++){
				methodCode = methodCode + "\n\t * @param " +parameters.get(i).getName() ;
			}
		}
		
		if (type != "void"){
			methodCode = methodCode + "\n\t * @return\n\t */";
		}
		else {
			methodCode = methodCode + "\n\t */" ;
		}
		
		methodCode = methodCode + "\n\t" + (visibility.toString() + " " + type + " " + name + " (" + parametersIn() + ") {\n\n\t}"); 
		
		return methodCode ; 
	}
}
