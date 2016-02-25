package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;

import textualuml.Link;
import textualuml.LinkEnum;
import textualuml.ObjectClass;
import textualuml.ObjectInterface;
import textualuml.ObjectPackage;
import engine.Project;
import graphicuml.ClassDrawing;

/**
 * Cette classe est la fenêtre qui sert à créer ou supprimer les implémentations entre les classes et les interfaces.
 * @author Alexandre Fourgs
 *
 */
public class ImplementsJFrame extends LinksJFrame {
	private Project project ;
	private ClassDrawing drawClass;
	JComboBox<Object> classFrom = super.getClassFrom();
	JComboBox<ObjectInterface> interfaceImplements = super.getInterfaceImplements();
	DefaultListModel<Link> dataLinks = super.getDataLinks();
	JList<Link> linksJL = super.getLinks();
	JPanel bottom = super.getBottom();
	JButton add = super.getAdd();
	JButton del = super.getDel();
	
	public ImplementsJFrame (Project project, ClassDrawing drawClass) {
		super("Implements", drawClass, "./Images/implement.jpg", true);
		this.project = project ;
		setJComboBox();
		setJlists();
		listeners();
	}
	
	/**
	 * Cette méthode permet d'ajouter dans les JComboBox les classes et interfaces.
	 */
	private void setJComboBox(){
		List<ObjectPackage> allPackages = project.getProjectProgram().getAllPackages();
		List<ObjectClass> allClass = new ArrayList<ObjectClass>();
		List<ObjectInterface> allInterface = new ArrayList<ObjectInterface>();
		
		for(int i = 0 ; i <allPackages.size() ; i++){
			ObjectPackage actualPackage = allPackages.get(i);
			for (int j = 0 ; j < actualPackage.getAllClass().size() ; j++){
				ObjectClass actualClass = actualPackage.getAllClass().get(j);
				allClass.add(actualClass);				
			}
			for (int j = 0 ; j < actualPackage.getAllInterface().size(); j++){
				ObjectInterface actualInterface = actualPackage.getAllInterface().get(j);
				allInterface.add(actualInterface);
			}
		}
		
		for (int i = 0 ; i < allClass.size() ; i++){
			classFrom.addItem(allClass.get(i));
		}
		for (int i = 0 ; i < allInterface.size() ; i++){
			interfaceImplements.addItem(allInterface.get(i));
		}
	}
	
	/**
	 * Cette méthode permet d'ajouter dans la JList toute les implémentations du projet ouvert qui existent
	 * déjà.
	 */
	private void setJlists(){
		
		for(int i=0 ; i < project.getProjectProgram().getAllPackages().size() ; i++){
			ObjectPackage actualPackage = project.getProjectProgram().getAllPackages().get(i);
			for (int j = 0 ; j < actualPackage.getAllClass().size() ; j++){
				ObjectClass actualClass = actualPackage.getAllClass().get(j);
				// Rajouter ici une condition pour ajouter les liens dans la Jlist.
				ArrayList<Link> links = (ArrayList<Link>) actualClass.getLinks();
				for (int k = 0 ; k < links.size() ; k++){
					if (links.get(k).getLink() == LinkEnum.IMPLEMENTS){
						dataLinks.addElement(links.get(k));
					}
				}
			}
			
			for (int j = 0 ; j < actualPackage.getAllInterface().size() ; j++){
				ObjectInterface actualInterface = actualPackage.getAllInterface().get(j);
				ArrayList<Link> links = (ArrayList<Link>) actualInterface.getLinks();
				for (int k = 0 ; k < links.size() ; k++){
					if (links.get(k).getLink() == LinkEnum.IMPLEMENTS){
						dataLinks.addElement(links.get(k));
					}
				}
			}
		}
		linksJL.setModel(dataLinks);
		bottom.revalidate();
	}
	
	/**
	 * Cette méthode est composer de tout les ajoûts des listeners au différents composant de la fenêtre.
	 */
	private void listeners () {
		add.addActionListener(new AddListener());
		del.addActionListener(new DeleteListener());
	}
	
	/**
	 * Cette classe est le listener du bouton "Add" qui permet de créer une implémentation.
	 * @author Alexandre Fourgs
	 *
	 */
	private class AddListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object selectedClass = (Object) classFrom.getSelectedItem();
			ObjectInterface interfaceToAdd = (ObjectInterface) interfaceImplements.getSelectedItem();
			Link implementation = new Link(LinkEnum.IMPLEMENTS, interfaceToAdd, selectedClass);
			
			if (selectedClass instanceof ObjectClass){
				((ObjectClass) selectedClass).addLink(implementation);
			}
			else if (selectedClass instanceof ObjectInterface){
				((ObjectInterface) selectedClass).addLink(implementation);
			}
			
			dataLinks.addElement(implementation);
			linksJL.setModel(dataLinks);
			bottom.revalidate();
		}
	}
	
	/**
	 * Cette classe est le listener du bouton "Delete" qui permet de supprimer le lien sélectionner dans la JList.
	 * @author Alexandre Fourgs
	 *
	 */
	private class DeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Link selectedLink = linksJL.getSelectedValue() ;
			Object objLinker = selectedLink.getClassLinker();
			
			if (objLinker instanceof ObjectClass){
				((ObjectClass) objLinker).delLink(selectedLink);
			}
			else if (objLinker instanceof ObjectInterface){
				((ObjectInterface) objLinker).delLink(selectedLink);
			}
			
			dataLinks.removeElement(selectedLink);
			linksJL.setModel(dataLinks);
			bottom.revalidate();
		}
	}
}
