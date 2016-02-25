package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import textualuml.Link;
import textualuml.LinkEnum;
import textualuml.ObjectAbstractClass;
import textualuml.ObjectAbstractMethod;
import textualuml.ObjectAttribute;
import textualuml.ObjectClass;
import textualuml.ObjectInterface;
import textualuml.ObjectMethod;
import textualuml.ObjectPackage;
import textualuml.ObjectProgram;
import textualuml.VisibilityEnum;
/**
 * 
 * @author Alexandre Fourgs
 *
 * This class is used for convert, save or open project.
 */

public class Engine {
	
	public Engine() {
	}
	
	/**
	 * This method create the packages directories, class files.java and interface files.java. 
	 * @param project is the project which contains the program which we want to convert.
	 */
	public void umlToJava (Project project){
		
		createPackages(project);
		createAndWriteClass(project);
		createAndWriteInterface(project);	
	}
	
	/**
	 * This method create all the package in the directory of the project.
	 * @param project is the project which contains the program which we want to convert.
	 */
	private void createPackages (Project project) {
		List<ObjectPackage> packages = project.getProjectProgram().getAllPackages() ;
		String packageName ;
		String place = project.getPlace();
		
		for (int i = 0 ; i < packages.size() ; i++){
			packageName = packages.get(i).getName();
			new File(place + "/Java/" + packageName).mkdir();
		}
	}
	
	/**
	 * This method create all class files.java and write the code inside them.
	 * @param project is the project which contains the program which we want to convert.
	 */
	private void createAndWriteClass (Project project) {
		for (int i = 0 ; i < project.getProjectProgram().getAllPackages().size() ; i++) {
			String packageName = project.getProjectProgram().getAllPackages().get(i).getName();
			for (int j = 0 ; j < project.getProjectProgram().getAllPackages().get(i).getAllClass().size() ; j++){
				ObjectClass actual = project.getProjectProgram().getAllPackages().get(i).getAllClass().get(j);
				File java = new File(project.getPlace() + "/Java/" + packageName + "/" + actual.getName() + ".java");
				
				try {
					if (!java.exists()){
						java.createNewFile();
					}
					
					FileWriter writer = new FileWriter(java);
					try {
						writer.write(actual.javaCode());
					}
					catch (Exception e){
						System.out.println("Erreur pour écrire : "+project.getProjectProgram().getAllPackages().get(i).getAllClass().get(j));
					}
					finally {
						writer.close();
					}
					
				}
				catch (IOException e){
					System.out.println("Impossible de créé le fichier");
				}
			}	
		}
	}
	
	/**
	 * This method create all interface files.java and write the code inside them.
	 * @param project is the project which contains the program which we want to convert.
	 */
	private void createAndWriteInterface (Project project) {
		for (int i = 0 ; i < project.getProjectProgram().getAllPackages().size() ; i++) {
			String packageName = project.getProjectProgram().getAllPackages().get(i).getName();
			for (int j = 0 ; j < project.getProjectProgram().getAllPackages().get(i).getAllInterface().size() ; j++){
				ObjectInterface actual = project.getProjectProgram().getAllPackages().get(i).getAllInterface().get(j);
				File java = new File(project.getPlace() + "/Java/" + packageName + "/" + actual.getName() + ".java");
				
				try {
					if (!java.exists()){
						java.createNewFile();
					}
					
					FileWriter writer = new FileWriter(java);
					try {
						writer.write(actual.javaCode());
					}
					catch (Exception e){
						
					}
					finally {
						writer.close();
					}
					
				}
				catch (IOException e){
					System.out.println("Impossible de créé le fichier");
				}
			}	
		}
	}
	
	/**
	 * This method save the uml as an image.
	 * @param panel is the panel to transform into image.
	 * @param name is the name for the image.
	 * @param place is where the image will be save.
	 */
	public void saveUmlAsImage (JPanel panel, String name, String place){
		BufferedImage bufferImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		File image = new File(place + "/" + name + ".jpg");
		Graphics g = bufferImage.createGraphics();
		panel.paint(g);
		
		try {
			ImageIO.write(bufferImage, "jpg", image);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveProject (Project project, String place){
		ObjectOutputStream oos;
		
		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(place + project.getName() + ".ser"))));
			oos.writeObject(project);
			oos.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is for open a project which was saved.
	 * @param place is the absoluth path of the file.
	 * @param project is the object which will be open with the file.
	 */
	public void openProject (String place, Project project){
		ObjectInputStream ois;
		
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(place))));
			project = (Project) ois.readObject();
			ois.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method parse a file.java for transform it into uml.
	 * @param javaFile the file.java to parse.
	 * @return an object like ObjectClass, ObjectAbstractClass or ObjectInterface, it depends the parsing.
	 */
	private Object parseJavaFile (File javaFile) {		
		
		// Patterns for general information on the object (Class, Abstract Class, Interface)
		Pattern patternClassName = Pattern.compile("public class (.+)\\{");
		Pattern patternAbstractClassName = Pattern.compile("public abstract class (.+)\\{");
		Pattern patternInterfaceName = Pattern.compile("public interface (.+)\\{");
		
		// Patterns for informations on links
		
		
		// First, we read the file .java
		String code = "" ;
		
		
		try {
			InputStream ips = new FileInputStream(javaFile);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne ;
			while ((ligne = br.readLine()) != null){
				code = code + ligne + "\n" ;
			}
			br.close();
		}
		catch (IOException e){
			System.out.println(e.toString());
		}
		
		// Secondly we parse it and create the object
		Matcher matchClass = patternClassName.matcher(code);
		Matcher matchAbstractClass = patternAbstractClassName.matcher(code);
		Matcher matchInterface = patternInterfaceName.matcher(code);
		
		ArrayList<ObjectAttribute> attributes = parseAttribute(code);
		ArrayList<ObjectMethod> methods = parseMethod(code);
		
		if (matchClass.find()){
			String className = matchClass.group(1);
			String[] partsClass = className.split(" ");
			
			ObjectClass objClass = new ObjectClass();
			objClass.setName(partsClass[0]);
			
			if (partsClass.length > 1) {
				for (int i = 1 ; i < partsClass.length ; i=i+2){
					Link link ;
					String linkString = partsClass[i];
					ObjectClass objClassTemporaire = null ;
					ObjectInterface objInterfaceTemporaire = null ;
					
					if(partsClass[i].equalsIgnoreCase("extends")){
						LinkEnum linkE = LinkEnum.EXTENDS;
						objClassTemporaire = new ObjectClass();
						objClassTemporaire.setName(partsClass[i+1]);
						link = new Link(linkE, objClassTemporaire, objClass);
						objClass.addLink(link);
					}
					else if (linkString.equalsIgnoreCase("implements")){
						LinkEnum linkE = LinkEnum.IMPLEMENTS;
						objInterfaceTemporaire = new ObjectInterface();
						objInterfaceTemporaire.setName(partsClass[i+1]);
						link = new Link(linkE, objInterfaceTemporaire, objClass);
						objClass.addLink(link);
					}
					
					
				}
			}
			
			for (int i = 0 ; i < attributes.size() ; i++){
				objClass.addAttribute(attributes.get(i));
			}
			
			for (int i = 0 ; i < methods.size() ; i++){
				objClass.addMethod(methods.get(i));
			}
			objClass.setObjectWidthAndHeight();
			return objClass ;
		}
		else if (matchAbstractClass.find()){
			String className = matchAbstractClass.group(1);
			String[] partsClass = className.split(" ");
			
			
			ObjectAbstractClass objAbstractClass = new ObjectAbstractClass();
			objAbstractClass.setName(partsClass[0]);
			
			if (partsClass.length > 1) {
				for (int i = 1 ; i < partsClass.length ; i=i+2){
					Link link ;
					String linkString = partsClass[i];
					
					ObjectClass objClassTemporaire = null ;
					ObjectInterface objInterfaceTemporaire = null ;
					
					if(linkString == LinkEnum.EXTENDS.toString()){
						LinkEnum linkE = LinkEnum.EXTENDS;
						objClassTemporaire = new ObjectClass();
						objClassTemporaire.setName(partsClass[i+1]);
						link = new Link(linkE, objClassTemporaire, objAbstractClass);
						objAbstractClass.addLink(link);
					}
					else if (linkString == LinkEnum.IMPLEMENTS.toString()){
						LinkEnum linkE = LinkEnum.IMPLEMENTS;
						objInterfaceTemporaire = new ObjectInterface();
						objInterfaceTemporaire.setName(partsClass[i+1]);
						link = new Link(linkE, objInterfaceTemporaire, objAbstractClass);
						objAbstractClass.addLink(link);
					}
					
					
					
				}
			}
			
			ArrayList<ObjectAbstractMethod> abstractMethods = parseAbstractMethod(code);
			
			
			for (int i = 0 ; i < attributes.size() ; i++){
				objAbstractClass.addAttribute(attributes.get(i));
			}
			
			for (int i = 0 ; i < methods.size() ; i++){
				objAbstractClass.addMethod(methods.get(i));
			}
			
			for (int i = 0 ; i < abstractMethods.size() ; i++){
				objAbstractClass.addMethod(abstractMethods.get(i));
			}
			objAbstractClass.setObjectWidthAndHeight();
			return objAbstractClass ;
		}
		else if (matchInterface.find()) {
			String className = matchInterface.group(1);
			String[] partsClass = className.split(" ");
			
			ObjectInterface objInterface = new ObjectInterface();
			objInterface.setName(partsClass[0]);
			
			if (partsClass.length > 1) {
				for (int i = 1 ; i < partsClass.length ; i=i+2){
					Link link ;
					String linkString = partsClass[i];
					
					ObjectInterface objInterfaceTemporaire = null ;
					
					if(linkString == LinkEnum.EXTENDS.toString()){
						LinkEnum linkE = LinkEnum.EXTENDS;
						objInterfaceTemporaire = new ObjectInterface();
						objInterfaceTemporaire.setName(partsClass[i+1]);
						link = new Link(linkE, objInterfaceTemporaire, objInterface);
						objInterface.addLink(link);
					}
					
				}
			}
			
			ArrayList<ObjectAbstractMethod> abstractMethods = parseAbstractMethod(code);
			
			for (int i = 0 ; i < abstractMethods.size() ; i++){
				objInterface.addAbstractMethod(abstractMethods.get(i));
			}
			objInterface.setObjectWidthAndHeight();
			return objInterface ;
		}
		else {
			return "Error parsing" ;
		}
		
	}
	
	/**
	 * This method parse a code and return the list of the attributes found in the code.
	 * @param code of the file .java that we want to parse
	 * @return a list of every attributes in the code
	 */
	private ArrayList<ObjectAttribute> parseAttribute (String code){
		ArrayList<ObjectAttribute> attributes = new ArrayList<ObjectAttribute>();
		
		// Patterns for informations on attributes
		Pattern patternPrivateAttribute = Pattern.compile("private (.*);");
		Pattern patternPublicAttribute = Pattern.compile("public (.*);");
		Pattern patternProtectedAttribute = Pattern.compile("protected (.*);");
			
		Matcher privateMatch = patternPrivateAttribute.matcher(code);
		Matcher publicMatch = patternPublicAttribute.matcher(code);
		Matcher protectedMatch = patternProtectedAttribute.matcher(code);
		
		while (privateMatch.find()){
			String attribute = privateMatch.group(1);
			String[] parts = attribute.split(" ");
			String type = parts [0] ;
			String name = parts[1] ;			
			ObjectAttribute objAttribute = new ObjectAttribute(VisibilityEnum.PRIVATE, type, name);
			attributes.add(objAttribute);
		}
		
		while (publicMatch.find()){
			String attribute = publicMatch.group(1);
			
			String[] parts = attribute.split(" ");
			String type = parts [0] ;
			String name = parts[1] ;
						
			ObjectAttribute objAttribute = new ObjectAttribute(VisibilityEnum.PUBLIC, type, name);
			attributes.add(objAttribute);
		}
		
		while (protectedMatch.find()){
			String attribute = protectedMatch.group(1);
		
			String[] parts = attribute.split(" ");
			String type = parts [0] ;
			String name = parts[1] ;
			
			ObjectAttribute objAttribute = new ObjectAttribute(VisibilityEnum.PROTECTED, type, name);
			attributes.add(objAttribute);
		}
		
		return attributes ;
	}
	
	/**
	 * This method parse a code and return the list of the methods found in the code.
	 * @param code of the file .java that we want to parse
	 * @return a list of objects that represent methods in the code
	 */
	private ArrayList<ObjectMethod> parseMethod (String code) {
		ArrayList<ObjectMethod> methods = new ArrayList<ObjectMethod>();
		
		// Patterns for informations on methods
		Pattern patternPrivateMethod = Pattern.compile("private (.*)\\{");
		Pattern patternPublicMethod = Pattern.compile("public (?!class)(.*)\\{");
		Pattern patternProtectedMethod = Pattern.compile("protected (.*)\\{");
		
		Matcher privateMatch = patternPrivateMethod.matcher(code);
		Matcher publicMatch = patternPublicMethod.matcher(code);
		Matcher protectedMatch = patternProtectedMethod.matcher(code);
		
		while (privateMatch.find()){
			ArrayList<ObjectAttribute> parameters = new ArrayList<ObjectAttribute>();
			
			String method = privateMatch.group(1);
			String[] parts = method.split(" ");
			String type = parts[0];
			String name = parts[1];
			int taille = type.length() + 1 + name.length() + 1 ;
			method.substring(taille, method.length());
			System.out.println(method);
			
			Pattern patternParameters = Pattern.compile(type + " " + name + " \\((.*)\\)?\\{");
			Matcher parametersMatch = patternParameters.matcher(method);
			
			while(parametersMatch.find()){
				System.out.println(parametersMatch.group());
				String[] parametersTab = parametersMatch.group(1).split(" ");
				
				for (int i = 0 ; i < parametersTab.length ; i=i+2){
					System.out.println("Type : " +parametersTab[i]+ "; Name :" +parametersTab[i+1]);
					String parameterType = parametersTab[i];
					String parameterName = "" ;
					if((i+2) == parametersTab.length-1){
						parameterName = parametersTab[i+1];
						
					}
					else {
						parameterName = parametersTab[i+1].substring(0, parametersTab[i+1].length()-1);
					}
					
					ObjectAttribute parameter = new ObjectAttribute(VisibilityEnum.NULL, parameterType, parameterName);
					parameters.add(parameter);
				}
				
			}
			
			ObjectMethod objMethod = new ObjectMethod(VisibilityEnum.PRIVATE, name, type, parameters);
			methods.add(objMethod);
		}
		
		while (publicMatch.find()){
			ArrayList<ObjectAttribute> parameters = new ArrayList<ObjectAttribute>();
			
			String method = publicMatch.group(1);
			String[] parts = method.split(" ");
			String type = parts[0];
			String name = parts[1];
			/*int taille = type.length() + 1 + name.length() + 1 ;
			method.substring(taille, method.length());
			System.out.println(method);*/
			
			Pattern patternParameters = Pattern.compile(type + " " + name + " \\((.*)\\)");
			Matcher parametersMatch = patternParameters.matcher(method);
			
			while(parametersMatch.find()){
				String[] parametersTab = parametersMatch.group(1).split(" ");
				
				for (int i = 0 ; i < parametersTab.length ; i=i+2){
					String parameterType = parametersTab[i];
					String parameterName = "" ;
					if((i+2) == parametersTab.length-1){
						parameterName = parametersTab[i+1];
						
					}
					else {
						parameterName = parametersTab[i+1].substring(0, parametersTab[i+1].length()-1);
					}
					
					ObjectAttribute parameter = new ObjectAttribute(VisibilityEnum.NULL, parameterType, parameterName);
					parameters.add(parameter);
				}
				
			}
			
			ObjectMethod objMethod = new ObjectMethod(VisibilityEnum.PUBLIC, name, type, parameters);
			methods.add(objMethod);
		}
		
		while (protectedMatch.find()){
			ArrayList<ObjectAttribute> parameters = new ArrayList<ObjectAttribute>();
			
			String method = protectedMatch.group(1);
			String[] parts = method.split(" ");
			String type = parts[0];
			String name = parts[1];
			
			Pattern patternParameters = Pattern.compile(type + " " + name + " \\((.*)\\)\\{");
			
			
			Matcher parametersMatch = patternParameters.matcher(method);

			while(parametersMatch.find()){
				String[] parametersTab = parametersMatch.group(1).split(" ");
				
				for (int i = 0 ; i < parametersTab.length ; i=i+2){
					System.out.println("Type : " +parametersTab[i]+ "; Name :" +parametersTab[i+1]);
					String parameterType = parametersTab[i];
					String parameterName = "" ;
					if((i+2) == parametersTab.length-1){
						parameterName = parametersTab[i+1];
						
					}
					else {
						parameterName = parametersTab[i+1].substring(0, parametersTab[i+1].length()-1);
					}
					
					ObjectAttribute parameter = new ObjectAttribute(VisibilityEnum.NULL, parameterType, parameterName);
					parameters.add(parameter);
				}
				
			}
			
			ObjectMethod objMethod = new ObjectMethod(VisibilityEnum.PROTECTED, name, type, parameters);
			methods.add(objMethod);
		}
		
		return methods ;
	}
	
	/**
	 * This method parse a code and return the list of the abstract methods found in the code.
	 * @param code of the file .java that we want to parse
	 * @return a list of objects that represent abstract methods in the code
	 */
	private ArrayList<ObjectAbstractMethod> parseAbstractMethod (String code){
		ArrayList<ObjectAbstractMethod> abstractMethods = new ArrayList<ObjectAbstractMethod>();
		
		// Patterns for informations on methods
		Pattern patternAbstractMethod = Pattern.compile("abstract (?!class)(.*);");
		
			
		Matcher abstractMatch = patternAbstractMethod.matcher(code);
				
		while (abstractMatch.find()){
			String method = abstractMatch.group(1);
			String[] parts = method.split(" ");
			String type = parts[0];
			String name = parts[1];
			
			ArrayList<ObjectAttribute> parameters = new ArrayList<ObjectAttribute>();
						
			Pattern patternParameters = Pattern.compile(type + " " + name + " \\((.*)\\)\\{");
			Matcher parametersMatch = patternParameters.matcher(method);

			while(parametersMatch.find()){
				String[] parametersTab = parametersMatch.group(1).split(" ");
				
				for (int i = 0 ; i < parametersTab.length ; i=i+2){
					System.out.println("Type : " +parametersTab[i]+ "; Name :" +parametersTab[i+1]);
					String parameterType = parametersTab[i];
					String parameterName = "" ;
					if((i+2) == parametersTab.length-1){
						parameterName = parametersTab[i+1];
					}
					else {
						parameterName = parametersTab[i+1].substring(0, parametersTab[i+1].length()-1);
					}
					
					ObjectAttribute parameter = new ObjectAttribute(VisibilityEnum.NULL, parameterType, parameterName);
					parameters.add(parameter);
				}
			}
			ObjectAbstractMethod objMethod = new ObjectAbstractMethod(name, type, parameters);
			abstractMethods.add(objMethod);
		}

		return abstractMethods ;
	}
	
	/**
	 * 
	 * @param dir
	 * @param pack
	 * @param project
	 */
	private void listRep (String dir, ObjectPackage pack, Project project) {
		File file = new File(dir);
		
		File[] files = file.listFiles();
		
		if (files != null) {
			for (int i = 0 ; i < files.length ; i++) {
				if (files[i].isDirectory()){
					ObjectPackage dirPack = new ObjectPackage(files[i].getName());
					project.getProjectProgram().addPackage(dirPack);
					listRep(files[i].getAbsolutePath(), dirPack, project);
				}
				else if (files[i].getName().endsWith(".java")){
					Object obj = parseJavaFile(files[i]);
					
					if (obj instanceof ObjectClass) {
						pack.addClass((ObjectClass) obj);
						((ObjectClass) obj).setObjPackage(pack);
					}
					else if (obj instanceof ObjectInterface) {
						pack.addInterface((ObjectInterface) obj);
						((ObjectInterface) obj).setObjPackage(pack);
					}
				}
			}
		}
	}
	
	public void convertJavaToUML (String dir, ObjectPackage pack, Project project){
		listRep(dir, pack, project);
		
		for (int i = 0 ; i < project.getProjectProgram().getAllPackages().size() ; i++){
			ObjectPackage actualPackage = project.getProjectProgram().getAllPackages().get(i);
			
			for (int j = 0 ; j < actualPackage.getAllClass().size() ; j++){
				ObjectClass actualClass = actualPackage.getAllClass().get(j);
				
				for (int k = 0 ; k < actualClass.getLinks().size() ; k++){
					Link actualLink = actualClass.getLinks().get(k);
					
					if (actualLink.getLink() == LinkEnum.EXTENDS){
						ObjectClass classLinkedTempo = actualLink.getClassLinked();
						
						for (int l = 0 ; l < project.getProjectProgram().getAllPackages().size() ; l++){
							ObjectPackage actualPackageBis = project.getProjectProgram().getAllPackages().get(l);
							
							for (int m = 0 ; m < actualPackageBis.getAllClass().size() ; m++){
								ObjectClass actualClassBis = actualPackageBis.getAllClass().get(m);
								if (actualClassBis.getName().equalsIgnoreCase(classLinkedTempo.getName())){
									actualLink.setClassLinked(actualClassBis);
								}
							}
						}
					}
					
					else if (actualLink.getLink() == LinkEnum.IMPLEMENTS){
						ObjectInterface interfaceLinkedTempo = actualLink.getInterfaceLinked();
						
						for (int l = 0 ; l < project.getProjectProgram().getAllPackages().size() ; l++){
							ObjectPackage actualPackageBis = project.getProjectProgram().getAllPackages().get(l);
							
							for (int m = 0 ; m < actualPackageBis.getAllInterface().size() ; m++){
								ObjectInterface actualInterfaceBis = actualPackageBis.getAllInterface().get(m);
								if (actualInterfaceBis.getName().equalsIgnoreCase(interfaceLinkedTempo.getName())){
									actualLink.setInterfaceLinked(actualInterfaceBis);
								}
							}
						}
					}
				}
			}
			for (int j = 0 ; j < actualPackage.getAllInterface().size() ; j++){
				ObjectInterface actualInterface = actualPackage.getAllInterface().get(j);
				
				for (int k = 0 ; k < actualInterface.getLinks().size() ; k++){
					Link actualLink = actualInterface.getLinks().get(k);
					
					if (actualLink.getLink() == LinkEnum.EXTENDS){
						ObjectInterface interfaceLinkedTempo = actualLink.getInterfaceLinked();
						
						for (int l = 0 ; l < project.getProjectProgram().getAllPackages().size() ; l++){
							ObjectPackage actualPackageBis = project.getProjectProgram().getAllPackages().get(l);
							
							for (int m = 0 ; m < actualPackageBis.getAllInterface().size() ; m++){
								ObjectInterface actualInterfaceBis = actualPackageBis.getAllInterface().get(m);
								if (actualInterfaceBis.getName().equalsIgnoreCase(interfaceLinkedTempo.getName())){
									actualLink.setInterfaceLinked(actualInterfaceBis);
								}
							}
						}
					}
				}
			}
		}
	}
}
