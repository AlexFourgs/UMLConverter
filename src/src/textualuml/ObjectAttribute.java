package textualuml;

import java.io.Serializable;

public class ObjectAttribute implements Serializable{
	private VisibilityEnum visibility ;
	private String type ;
	private String name ;
	private boolean getter ;
	private boolean setter ;
	
	public ObjectAttribute (){
		type = null ;
		name = null ;
		getter = false ;
		setter = false ;
	}
	
	public ObjectAttribute (VisibilityEnum visibility, String type, String name){
		this.visibility = visibility ;
		this.type = type ;
		this.name = name ;
	}
	
	public VisibilityEnum getVisibility () {
		return visibility ;
	}
	
	public void setVisibility (VisibilityEnum visibility) {
		this.visibility = visibility ;
	}
	
	public String getType (){
		return type ;
	}
	
	public void setType (String type){
		this.type = type ;
	}
	
	public String getName (){
		return name ;
	}
	
	public void setName (String name){
		this.name = name ;
	}
	
	public boolean getGetter (){
		return getter ;
	}
	
	public void setGetter (boolean getter){
		this.getter = getter ;
	}
	
	public boolean getSetter () {
		return setter ;
	}
	
	public void setSetter (boolean setter){
		this.setter = setter ;
	}
	
	public String getterCode () {
		String getterCode = null ;
		// Changer name pour mettre la première lettre en majuscule.
		getterCode = "public " + type + " get" + name + " () {\n\t" ;
		getterCode = getterCode + "return " + name + " ;\n}" ;
		
		return getterCode ;
	}
	
	public String setterCode () {
		String setterCode = null ;
		// Changer name pour mettre la première lettre en maj.
		setterCode = "public void set" + name + " (" + type + name + ") {\n\t";
		setterCode = "this." + name + "= " + name + " ;";
		
		return setterCode ;
	}
	/**
	 * The method toString is used for "write" the declaration of an attribute in java language.
	 */
	
	public String javaCode () {
		return visibility.toString() + " " +type+ " " +name+ " ;";
	}
	
	public String javaCodeParameter () {
		return type + " " + name ;
	}
	
	public String toString () {
		return visibility.getSign() + " " +name+ " : " +type;
	}
}
