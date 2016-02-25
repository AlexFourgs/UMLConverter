package textualuml;

import java.io.Serializable;

public enum LinkEnum implements Serializable{
	EXTENDS ("extends"),
	IMPLEMENTS ("implements"),
	AGGREGATION ("aggregation"),
	COMPOSITION ("composition");
	
	private String name = "" ;
	
	LinkEnum(String name){
		this.name = name ;
	}
	
	public String toString(){
		return name ;
	}
	
	
}
