package textualuml;

import graphicuml.ObjectPositions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectPackage implements Serializable {
	private ObjectPositions positions = new ObjectPositions() ;
	private String name ;
	private List<ObjectClass> allClass = new ArrayList<ObjectClass>();
	private List<ObjectInterface> allInterface = new ArrayList<ObjectInterface>();
	
	public ObjectPackage () {
	}
	
	public ObjectPackage (String name) {
		this.name = name ;
	}
	
	public String getName () {
		return name ;
	}
	
	public void setName (String name) {
		this.name = name ;
	}
	
	public List<ObjectInterface> getAllInterface() {
		return allInterface;
	}
	
	public void addInterface (ObjectInterface interfaceToAdd){
		allInterface.add(interfaceToAdd);
	}

	public List<ObjectClass> getAllClass () {
		return allClass ;
	}
	
	public void addClass (ObjectClass objClass) {
		allClass.add(objClass);
	}
	
	public ObjectPositions getObjectPositions (){
		return positions ;
	}
	
	public void setObjectPositions (int x, int y) {
		positions.setX(x);
		positions.setY(y);
	}
	
	/**
	 * Algorithme permettant de calculer la largeur et la hauteur du carré correspondant au package.
	 */
	public void setObjectWidthAndHeight (){
		int width = 80 ;
		int height = 20 ;
		// Nombre de classes et d'interface au total
		int nbObject = allClass.size() + allInterface.size() ;
		
		// Racine carré du nombre de de classe et des interface arrondis au nombre suppérieur.
		int nbRanger = (int) Math.ceil(Math.sqrt(nbObject));
		
		for (int i = 0 ; i < allClass.size() ; i++){
			width = width + allClass.get(i).getObjectPositions().getWidth() + 40 ;
		}
		
		for (int i = 0 ; i < allInterface.size() ; i++){
			//width
		}
		
		
	}
	/**
	 * The method toString is used for "write" an importation of package in java language.
	 */
	@Override
	public String toString() {
		return "package " +name+ " ;\n";
	}
	
	
}
