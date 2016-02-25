package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import textualuml.Link;
import textualuml.LinkEnum;
import textualuml.ObjectClass;
import textualuml.ObjectInterface;
import textualuml.ObjectPackage;
import engine.Project;
import graphicuml.ClassDrawing;

public class HeritageJFrame extends LinksJFrame {
	private Project project ;
	private ClassDrawing drawClass;
	JComboBox<Object> classFrom = super.getClassFrom();
	JComboBox<Object> classTo = super.getClassTo();
	DefaultListModel<Link> dataLinks = super.getDataLinks();
	JList<Link> linksJL = super.getLinks();
	JPanel bottom = super.getBottom();
	JButton add = super.getAdd();
	JButton del = super.getDel();
	
	public HeritageJFrame (Project project, ClassDrawing drawClass) {
		super ("Extends editor", drawClass, "./Images/extends.jpg", false);
		this.project = project ;
		setJComboBox();
		setJlists();
		listeners() ;
	}
	
	private void setJComboBox() {
		List<ObjectPackage> allPackages = project.getProjectProgram().getAllPackages();
		List<ObjectClass> allClass = new ArrayList<ObjectClass>();
		List<ObjectInterface> allInterface = new ArrayList<ObjectInterface>();
		
 		for(int i = 0 ; i <allPackages.size() ; i++){
			ObjectPackage actualPackage = allPackages.get(i);
			for (int j = 0 ; j < actualPackage.getAllClass().size() ; j++){
				ObjectClass actualClass = actualPackage.getAllClass().get(j);
				allClass.add(actualClass);				
			}
			for (int j = 0 ; j < actualPackage.getAllInterface().size() ; j++){
				ObjectInterface actualInterface = actualPackage.getAllInterface().get(j);
				allInterface.add(actualInterface);
			}
		}
		
		for (int i = 0 ; i < allClass.size() ; i++){
			classFrom.addItem(allClass.get(i));
			classTo.addItem(allClass.get(i));
		}
		for (int i = 0 ; i < allInterface.size() ; i++){
			classFrom.addItem(allInterface.get(i));
			classTo.addItem(allInterface.get(i));
		}
	}
	
	private void setJlists(){
		
		for(int i=0 ; i < project.getProjectProgram().getAllPackages().size() ; i++){
			ObjectPackage actualPackage = project.getProjectProgram().getAllPackages().get(i);
			for (int j = 0 ; j < actualPackage.getAllClass().size() ; j++){
				ObjectClass actualClass = actualPackage.getAllClass().get(j);
				// Rajouter ici une condition pour ajouter les liens dans la Jlist.
				ArrayList<Link> links = (ArrayList<Link>) actualClass.getLinks();
				for (int k = 0 ; k < links.size() ; k++){
					if (links.get(k).getLink() == LinkEnum.EXTENDS){
						dataLinks.addElement(links.get(k));
					}
				}
			}
			
			for (int j = 0 ; j < actualPackage.getAllInterface().size() ; j++){
				ObjectInterface actualInterface = actualPackage.getAllInterface().get(j);
				ArrayList<Link> links = (ArrayList<Link>) actualInterface.getLinks();
				for (int k = 0 ; k < links.size() ; k++){
					if (links.get(k).getLink() == LinkEnum.EXTENDS){
						dataLinks.addElement(links.get(k));
					}
				}
			}
		}
		linksJL.setModel(dataLinks);
		bottom.revalidate();
	}
	
	private void listeners(){
		add.addActionListener(new AddListener());
		del.addActionListener(new DeleteListener());
	}
	
	private class AddListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object classFromToAdd = classFrom.getSelectedItem();
			Object classToToAdd = classTo.getSelectedItem();
			
			if (classFromToAdd instanceof ObjectClass){
				if (classToToAdd instanceof ObjectClass){
					Link heritage = new Link(LinkEnum.EXTENDS, (ObjectClass) classToToAdd, classFromToAdd);
					((ObjectClass) classFromToAdd).addLink(heritage);
					dataLinks.addElement(heritage);
					linksJL.setModel(dataLinks);
					bottom.revalidate();
				}
				else {
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null, "You can't extends a class or abstract class with an interface !", "Warning !", JOptionPane.WARNING_MESSAGE);
				}
			}
			else if (classFromToAdd instanceof ObjectInterface){
				if (classToToAdd instanceof ObjectInterface){
					Link heritage = new Link(LinkEnum.EXTENDS, (ObjectInterface) classToToAdd, classFromToAdd);
					((ObjectInterface) classFromToAdd).addLink(heritage);
					dataLinks.addElement(heritage);
					linksJL.setModel(dataLinks);
					bottom.revalidate();
				}
				else {
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null, "You can't extends an interface with a class or an abstract class !", "Warning !", JOptionPane.WARNING_MESSAGE);
				}
			}
			else {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "Error ! Contact our support.", "Error !", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class DeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Link selectedLink = linksJL.getSelectedValue();
			Object classLinker = selectedLink.getClassLinker();
			
			if (classLinker instanceof ObjectClass){
				((ObjectClass) classLinker).delLink(selectedLink);
			}
			else if (classLinker instanceof ObjectInterface){
				((ObjectInterface) classLinker).delLink(selectedLink);
			}
			
			dataLinks.removeElement(selectedLink);
			linksJL.setModel(dataLinks);
			bottom.revalidate();
		}
	}
}
