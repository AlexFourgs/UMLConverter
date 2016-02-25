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
 * Cette classe est la fenêtre qui sert à créer ou supprimer les aggrégations entre les classes.
 * @author Alexandre Fourgs
 *
 */
public class AggregationJFrame extends LinksJFrame{
	private ClassDrawing drawClass;
	private Project project ;
	JComboBox<Object> classFrom = super.getClassFrom();
	JComboBox<Object> classTo = super.getClassTo();
	DefaultListModel<Link> dataLinks = super.getDataLinks();
	JList<Link> linksJL = super.getLinks();
	JPanel bottom = super.getBottom();
	JButton add = super.getAdd();
	JButton del = super.getDel();
	
	public AggregationJFrame (Project project, ClassDrawing drawClass) {
		super ("Aggregation", drawClass, "./Images/agreg.jpg", false);
		this.drawClass = drawClass;
		this.project = project ;
		setJComboBox();
		setJlists();
		listeners();
	}
	
	private void setJComboBox() {
		List<ObjectPackage> allPackages = project.getProjectProgram().getAllPackages();
		List<ObjectClass> allClass = new ArrayList<ObjectClass>();
		
		for(int i = 0 ; i <allPackages.size() ; i++){
			ObjectPackage actualPackage = allPackages.get(i);
			for (int j = 0 ; j < actualPackage.getAllClass().size() ; j++){
				ObjectClass actualClass = actualPackage.getAllClass().get(j);
				allClass.add(actualClass);				
			}
		}
		
		for (int i = 0 ; i < allClass.size() ; i++){
			classFrom.addItem(allClass.get(i));
			classTo.addItem(allClass.get(i));
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
					if (links.get(k).getLink() == LinkEnum.AGGREGATION){
						dataLinks.addElement(links.get(k));
					}
				}
			}
			
			for (int j = 0 ; j < actualPackage.getAllInterface().size() ; j++){
				ObjectInterface actualInterface = actualPackage.getAllInterface().get(j);
				ArrayList<Link> links = (ArrayList<Link>) actualInterface.getLinks();
				for (int k = 0 ; k < links.size() ; k++){
					if (links.get(k).getLink() == LinkEnum.AGGREGATION){
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
			ObjectClass classFromToAdd = (ObjectClass) classFrom.getSelectedItem();
			ObjectClass classToToAdd = (ObjectClass) classTo.getSelectedItem();
			Link aggregation = new Link(LinkEnum.AGGREGATION, classToToAdd, classFromToAdd);
			
			classFromToAdd.addLink(aggregation);
			
			dataLinks.addElement(aggregation);
			linksJL.setModel(dataLinks);
			bottom.revalidate();
		}
	}
	
	private class DeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Link selectedLink = linksJL.getSelectedValue();
			ObjectClass classLinker = (ObjectClass) selectedLink.getClassLinker();
			
			classLinker.delLink(selectedLink);
			
			dataLinks.removeElement(selectedLink);
			linksJL.setModel(dataLinks);
			bottom.revalidate();
		}
	}
}
