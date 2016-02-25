package textualuml;

import java.io.Serializable;

/**
 * 
 * @author alex
 * The link between class is : classFrom --> classTo
 * Example : Class A extends Class B
 * classFrom = Class A
 * ClassTo = Class B
 * 
 */

public class Link implements Serializable {
	/**
	 * 
	 */
	private LinkEnum link ;
	private Object classLinker ;
	private ObjectClass classLinked ;
	private ObjectInterface interfaceLinked ;
	
	public Link (LinkEnum link, ObjectClass classLinked, Object classLinker){
		this.link = link ;
		this.classLinked = classLinked ;
		this.classLinker = classLinker ;
	}
	
	public Link (LinkEnum link, ObjectInterface interfaceLinked, Object classLinker) {
		this.link = link ;
		this.interfaceLinked = interfaceLinked ;
		this.classLinker = classLinker ;
	}
	
	public LinkEnum getLink () {
		return link ;
	}
	
	public void setLink (LinkEnum link) {
		this.link = link ;
	}
	
	public ObjectClass getClassLinked () {
		return classLinked ;
	}
	
	public void setClassLinked (ObjectClass classLinked){
		this.classLinked = classLinked ;
	}
	
	public void setInterfaceLinked (ObjectInterface interfaceLinked){
		this.interfaceLinked = interfaceLinked ;
	}
	
	public ObjectInterface getInterfaceLinked () {
		return interfaceLinked ;
	}
	
	public Object getClassLinker () {
		return classLinker ;
	}
	
	public String javaCode(){
		if ((link == LinkEnum.EXTENDS)||(link == LinkEnum.IMPLEMENTS)){
			return link + " ";
		}
		else {
			return "" ;
		}
	}
	@Override
	public String toString () {
		if (interfaceLinked != null) {
			return classLinker.toString() + " " +link+ " " + interfaceLinked.toString() ;
		}
		else if (classLinked != null) {
			return classLinker.toString() + " " +link+ " " + classLinked.toString() ;
		}
		else {
			return "error" ;
		}
		
		
	}
}
