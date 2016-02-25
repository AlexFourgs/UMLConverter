package textualuml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectProgram implements Serializable {
	// Supprimer les lists allClass et allInterface et réadapter le programme.
	private List<ObjectPackage> allPackages = new ArrayList<ObjectPackage>();
	
	public ObjectProgram () {
		ObjectPackage objPackage = new ObjectPackage("(default package)");
		allPackages.add(objPackage);
	}
	
	public void addPackage (ObjectPackage packageToAdd) {
		allPackages.add(packageToAdd);
	}
	public List<ObjectPackage> getAllPackages () {
		return allPackages ;
	}
	
}
