package textualuml;

import graphicuml.ObjectPositions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectInterface implements Serializable {
	private ObjectPositions positions = new ObjectPositions() ;
	private String name ;
	private List <ObjectMethod> abstractMethods = new ArrayList <ObjectMethod>() ;
	private List<Link> links = new ArrayList<Link>();
	private ObjectPackage objPackage ;
	
	public ObjectInterface () {
	}
	
	public ObjectPositions getObjectPositions (){
		return positions ;
	}
	
	public void setObjectPositions (int x, int y) {
		positions.setX(x);
		positions.setY(y);
	}
	
	public String getName (){
		return name ;
	}
	
	public void setName (String name){
		this.name = name ;
	}
	
	public ObjectPackage getObjPackage() {
		return objPackage;
	}
	
	public void setObjPackage(ObjectPackage objPackage) {
		this.objPackage = objPackage;
	}
	
	public void addAbstractMethod (ObjectMethod abstractMethod){
		abstractMethods.add(abstractMethod);
	}
	
	public void delAbstractMethod (ObjectMethod abstractMethod){
		abstractMethods.remove(abstractMethod);
	}
	
	public List<ObjectMethod> getAbstractMethods() {
		return abstractMethods;
	}
	
	public List<Link> getLinks () {
		return links ;
	}
	
	public void addLink (Link link){
		links.add(link);
	}
	
	public void delLink (Link link){
		links.remove(link);
	}
	
	public void setObjectWidthAndHeight () {
		int widthClass = name.length() ;
		
		for(int i = 0 ; i < abstractMethods.size() ; i++){
			if (widthClass < abstractMethods.get(i).stringForDraw().length()){
				widthClass = abstractMethods.get(i).stringForDraw().length() ;
			}
		}
		
		int heightClass = 25 ;
		
		widthClass = widthClass*7 ;
		
		heightClass = heightClass + (abstractMethods.size()*11) ;
		
		positions.setWidth(widthClass);
		positions.setHeight(heightClass);
	}
	
	/**
	 * This method is used for "write" an interface in java langage.
	 */
	public String javaCode () {
		String text = "" ;
		text = text + "\n/**\n *\n * @author User\n *\n */";
		
		text = text + "\npublic interface " +name ;
		
		if (links.size() > 0) {
			for (int i = 0 ; i < links.size() ; i++){
				text = text + " " + links.get(i).getLink() + " " + links.get(i).getClassLinked().getName() ;
			}
		}
		
		text = text + " {\n" ;
		
		for (int i = 0 ; i<abstractMethods.size() ; i++){
			text = text + "\t" + abstractMethods.get(i).javaCode() ;
		}
		text = text + "\n}";
		return text ;
	}
	
	@Override
	public String toString(){
		return name ;
	}
}
