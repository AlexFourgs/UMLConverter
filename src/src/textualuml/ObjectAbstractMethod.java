package textualuml;

import java.util.ArrayList;


public class ObjectAbstractMethod extends ObjectMethod{
	/**
	 * The method toString is used for "write" the abstract method in java langage.
	 */
	
	public ObjectAbstractMethod(String name, String type, ArrayList<ObjectAttribute> parameters) {
		super(VisibilityEnum.NULL, name, type, parameters) ;
	}
	
	public String javaCode () {
		String methodCode = "\n\t/**\n\t *" ;
		if (super.getParameter().size() > 0){
			for (int i = 0 ; i < super.getParameter().size() ; i++){
				methodCode = methodCode + "\n\t * @param " +super.getParameter().get(i).getName() ;
			}
		}
		
		if (super.getType() != "void"){
			methodCode = methodCode + "\n\t * @return\n\t */";
		}
		else {
			methodCode = methodCode + "\n\t */" ;
		}
		
		methodCode = methodCode + "\n\t" + ("abstract " + super.getType() + " " + super.getName() + " (" + super.parametersIn() + ") ;\n");
		
		return methodCode ;
	}
	
	public String toString () {
		return "abstract " + super.getType() + " " + super.getName() + " (" + super.parametersIn() + ");" ;
	}
}
