package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import engine.Project;
import graphicuml.ClassDrawing;
import textualuml.ObjectClass;
import textualuml.ObjectPackage;

public class PackageJFrame extends JFrame {
	private Project project ;
	private ClassDrawing classDrawer ;
	private ObjectPackage objPackage = new ObjectPackage() ;
	
	private JPanel all = new JPanel();
	
	/**
	 * Components for edit package
	 */
	private JPanel leftPan = new JPanel();
	private JPanel leftPanNorth = new JPanel();
	private JPanel leftPanSouth = new JPanel();
	private JLabel packageName = new JLabel("Name :");
	private JTextField nameTF = new JTextField() ;
	private JButton add = new JButton("Add");
	private JButton apply = new JButton("Apply");
	private JButton del = new JButton("Delete");
	
	/**
	 * Components for packages list
	 */
	private JPanel rightPan = new JPanel();
	private JList<ObjectPackage> packages = new JList<ObjectPackage>() ;
	private DefaultListModel<ObjectPackage> dataPackages = new DefaultListModel<ObjectPackage>();
	private JScrollPane jsp = new JScrollPane(packages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	/**
	 * Composants généraux
	 */
	private JPanel panSouth = new JPanel();
	private GridLayout glSouth = new GridLayout(1, 2);
	private JButton ok = new JButton("Ok");
	private JButton close = new JButton("Close");
	
	/**
	 * Component
	 */
	
	
	public PackageJFrame (Project project, ClassDrawing classDrawer) {
		super("Package Editor");
		this.project = project ;
		this.classDrawer = classDrawer ;
		init();
	}
	
	private void init() {
		Container pan = this.getContentPane() ;
		this.setSize(500, 250);
		this.setLocationRelativeTo(null);
		
		//pan.setLayout(new BorderLayout());
		all.setLayout(new GridLayout(1, 2));
		
		panSouth.setLayout(glSouth);
		panSouth.add(ok);
		panSouth.add(close);
		
		leftPan.setLayout(new GridLayout(2, 1));
		
		nameTF.setPreferredSize(new Dimension(150, 20));
		add.setEnabled(false);
		del.setEnabled(false);
		apply.setEnabled(false);
		leftPanNorth.add(packageName);
		leftPanNorth.add(nameTF);
		leftPanSouth.add(add);
		leftPanSouth.add(apply);
		leftPanSouth.add(del);
		leftPan.add(leftPanNorth);
		leftPan.add(leftPanSouth);
		
		for (int i = 0 ; i < project.getProjectProgram().getAllPackages().size() ; i++){
			dataPackages.addElement(project.getProjectProgram().getAllPackages().get(i));
		}
		packages.setModel(dataPackages);
		rightPan.add(jsp);
		
		all.add(leftPan);
		all.add(rightPan);
		
		pan.add(all);
		pan.add(panSouth, BorderLayout.SOUTH);
		
		listener();
		
		this.setVisible(true);
	}
	
	private void listener () {
		add.addActionListener(new Add());
		apply.addActionListener(new Apply());
		del.addActionListener(new Delete());
		ok.addActionListener(new Ok());
		close.addActionListener(new Close());
		packages.addListSelectionListener(new PackagesListListener());
		nameTF.getDocument().addDocumentListener(new NameTextFieldListener());
	}
	
	private class Ok implements ActionListener {
		public void actionPerformed(ActionEvent e){
			classDrawer.repaint();
			dispose() ;
		}
	}
	
	private class Close implements ActionListener {
		public void actionPerformed(ActionEvent e){
			dispose() ;
		}
	}
	
	private class Add implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String packageName = nameTF.getText();
			
			if (packageExist(packageName) == false){
				objPackage = new ObjectPackage(packageName);
				project.getProjectProgram().getAllPackages().add(objPackage);
				nameTF.setText("");
				
				dataPackages.addElement(objPackage);
				packages.setModel(dataPackages);
				rightPan.revalidate();
			}
			else {
				JOptionPane warn = new JOptionPane();
				warn.showMessageDialog(null, "This package already exist ! Please choose another name for it.", "Warning !", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		// Ne fonctionne pas
		private boolean packageExist (String name){
			
			for (int i = 0 ; i < project.getProjectProgram().getAllPackages().size() ; i++){
				String actualPackageName = project.getProjectProgram().getAllPackages().get(i).getName();
				if (actualPackageName == name){
					return true ;
				}
			}
			return false ;
		}
	}
	
	private class Apply implements ActionListener {
		public void actionPerformed(ActionEvent e){
			packages.getSelectedValue().setName(nameTF.getText());
		}
	}
	
	private class Delete implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectPackage defaultPackage = project.getProjectProgram().getAllPackages().get(0);
			ObjectPackage selectedPackage = packages.getSelectedValue();
			
			// On ajoute les classes qu'ils y avaient dans le package à suppr dans le default package
			for (int i = 0 ; i < selectedPackage.getAllClass().size() ; i++){
				defaultPackage.addClass(selectedPackage.getAllClass().get(i));
				selectedPackage.getAllClass().get(i).setObjectWidthAndHeight();
			}
			
			// On ajoute les interfaces qu'ils y avaient dans le package à suppr dans le default package 
			for (int i = 0 ; i < selectedPackage.getAllInterface().size() ; i++){
				defaultPackage.addInterface(selectedPackage.getAllInterface().get(i));
				selectedPackage.getAllInterface().get(i).setObjectWidthAndHeight();
			}
			
			project.getProjectProgram().getAllPackages().remove(packages.getSelectedValue());
			dataPackages.removeElement(packages.getSelectedValue());
			packages.setModel(dataPackages);
			rightPan.revalidate();
			packages.clearSelection();
		}
	}
	
	private class PackagesListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e){
			
			if (packages.getSelectedIndex() > 0){
				ObjectPackage selectedPackage = packages.getSelectedValue();
				del.setEnabled(true);
				apply.setEnabled(true);
				nameTF.setText(selectedPackage.getName());
			}
			else {
				del.setEnabled(false);
				apply.setEnabled(false);
			}
		}
	}
	
	private class NameTextFieldListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			changeButtonAdd(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			changeButtonAdd(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			changeButtonAdd(e);
		}
		
		private void changeButtonAdd(DocumentEvent e) {
			boolean enable = e.getDocument().getLength() > 0 ;
			add.setEnabled(enable);
		}		
	}
}
