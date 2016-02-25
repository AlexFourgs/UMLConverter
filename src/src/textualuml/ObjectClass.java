package textualuml;

import graphicuml.ObjectPositions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the representation of a Class as an object.
 * 
 * @author Alexandre Fourgs
 * @author Florian Astori
 *
 */
public class ObjectClass implements Serializable {
	private ObjectPositions positions = new ObjectPositions();
	private ObjectPackage objPackage;
	private String name;
	private List<Link> links = new ArrayList<Link>();
	private List<ObjectAttribute> attributes = new ArrayList<ObjectAttribute>();
	private List<ObjectMethod> methods = new ArrayList<ObjectMethod>();

	// Soit au moment de l'ajout d'une classe on demande le nom de la classe à
	// l'utilisateur,
	// soit on l'appel class, faut voir.

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            is the name of the class.
	 */
	public ObjectClass() {
		objPackage = null;
		name = "Class";
	}

	/**
	 * Constructor.
	 * 
	 * @param objPackage
	 *            is the package where the class is if she has a package.
	 * @param name
	 *            is the name of the class.
	 */
	public ObjectClass(ObjectPackage objPackage, String name) {
		this.objPackage = objPackage;
		this.name = name;
	}

	public ObjectPackage getObjPackage() {
		return objPackage;
	}

	public void setObjPackage(ObjectPackage objPackage) {
		this.objPackage = objPackage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ObjectAttribute> getAttributes() {
		return attributes;
	}

	public List<ObjectMethod> getMethods() {
		return methods;
	}

	public String constructorWithoutParameters() {
		return "\tpublic " + name + " (){\n}";
	}

	public String constructorWithParameters() {
		return "";
	}

	public void addAttribute(ObjectAttribute objAttribute) {
		attributes.add(objAttribute);
	}

	public void delAttribute(ObjectAttribute attribute) {
		attributes.remove(attribute);
	}

	public void addMethod(ObjectMethod objMethod) {
		methods.add(objMethod);
	}

	public void delMethod(ObjectMethod method) {
		methods.remove(method);
	}
	
	public void addLink (Link link){
		links.add(link);
	}
	
	public void delLink (Link link){
		links.remove(link);
	}
	
	public ObjectPositions getObjectPositions() {
		return positions;
	}
	
	public List<Link> getLinks () {
		return links ;
	}
	
	/**
	 * Methods which calculate the size of the drawing of the rect.
	 */
	public void setObjectWidthAndHeight() {
		int widthClass = name.length();

		for (int i = 0; i < attributes.size(); i++) {
			if (widthClass < attributes.get(i).toString().length()) {
				widthClass = attributes.get(i).toString().length();
			}
		}
		for (int i = 0; i < methods.size(); i++) {
			if (widthClass < methods.get(i).stringForDraw().length()) {
				widthClass = methods.get(i).stringForDraw().length();
			}
		}

		int heightClass = 25;
		if (widthClass == name.length()) {
			widthClass *= 9;
		} else {
			widthClass = widthClass * 6;
		}

		heightClass = heightClass + (attributes.size() * 12)
				+ (methods.size() * 12);

		positions.setWidth(widthClass);
		positions.setHeight(heightClass);
	}

	public void setPositions(int x, int y) {
		positions.setX(x);
		positions.setY(y);
	}
	
	/**
	 * This method is used for "write" the class in java langage.
	 */
	public String javaCode(){
		String text = "";

		if (objPackage != null) {
			text = text + objPackage.toString() + "\n";
		}
		
		text = text + "\n/**\n *\n * @author User\n *\n */";
		
		if (links.size() > 0) {
			text = text + "\npublic class " + name + " ";
			for (int i = 0; i < links.size(); i++) {
				text = text + links.get(i).getLink() + " " + links.get(i).getClassLinked().getName() + " ";
			}	
		}
		else {
			text = text + "\npublic class " + name + " ";
		}
		text = text + "{\n";

		for (int i = 0; i < attributes.size(); i++) {
			text = text + "\t" + attributes.get(i).javaCode() + "\n";
		}
		
		text = text + "\n" ;
		
		for (int i = 0; i < methods.size(); i++) {
			text = text + "\t" + methods.get(i).javaCode() + "\n\n";
		}
		
		text = text + "}";

		return text;
	}
	
	@Override
	public String toString() {
		return name ;
	}
}