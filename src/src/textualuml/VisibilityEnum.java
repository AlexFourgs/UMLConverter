package textualuml;

import java.io.Serializable;

public enum VisibilityEnum implements Serializable{
	NULL ("", ""),
	PUBLIC ("public", "+"),
	PRIVATE ("private", "-"),
	PROTECTED ("protected", "#");
	
	private String name = "" ;
	private String sign = "" ;
	
	VisibilityEnum(String name, String sign){
		this.name = name ;
		this.sign = sign ;
	}
	
	public String getSign() {
		return sign ;
	}
	
	public String toString() {
		return name ;
	}
}