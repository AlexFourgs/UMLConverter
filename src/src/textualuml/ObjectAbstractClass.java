package textualuml;

import java.util.ArrayList;
import java.util.List;

public class ObjectAbstractClass extends ObjectClass {
	private List <ObjectMethod> abstractMethods = new ArrayList <ObjectMethod>() ;
	
	public ObjectAbstractClass(){
		super();
	}
	public ObjectAbstractClass (ObjectPackage objPackage, String name) {
		super(objPackage, name) ;
	}
	
	/**
	 * The method toString is used for "write" the abstract class in java langage.
	 */
	@Override
	public String javaCode () {
		List<ObjectAttribute> attributes = super.getAttributes();
		List<ObjectMethod> methods = super.getMethods();
		String text = "" ;
		
		text = text + "\n/**\n *\n * @author User\n *\n */";
		text = "\npublic abstract class " +super.getName();
		
		if(super.getLinks().size() > 0) {
			for (int i = 0 ; i < super.getLinks().size() ; i++){
				text = text + " " + super.getLinks().get(i).getLink() + " " + super.getLinks().get(i).getClassLinked().getName() ;
			}
		}
		
		text = text + " {\n";
		
		for (int i = 0 ; i<attributes.size() ; i++){
			text = text + "\t"+attributes.get(i).javaCode()+"\n";
		}
		for (int i = 0 ; i<methods.size() ; i++){
			text = text + "\t" +methods.get(i).javaCode()+"\n\n";
		}
		for (int i = 0 ; i<abstractMethods.size() ; i++){
			text = text + "\t" +abstractMethods.get(i).javaCode()+"\n\n";
		}
		
		text = text + "}";
		return text ;
	}
	
	@Override
	public String toString (){
		return super.getName();
	}
}
