package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.Project;
import graphicuml.ClassDrawing;

public class ProjectJFrame extends JFrame {
	private UMLConverterGUI gui ;
	private ClassDrawing drawClass ;
	
	private JPanel button = new JPanel();
	private JButton create = new JButton("Create new project");
	private JButton cancel = new JButton("Cancel");
	
	private JPanel principal = new JPanel();
	private JPanel namePanel = new JPanel();
	private JLabel name = new JLabel("Project Name : ");
	private JTextField nameTF = new JTextField("Choose a name for your new project.") ;
	private JPanel placePanel = new JPanel();
	private JTextField place = new JTextField("Choose a place for your new project.");
	private JButton placeJFCActioner = new JButton("Choose a directory");
	private JFileChooser placeJFC = new JFileChooser();
	
	public ProjectJFrame (UMLConverterGUI gui, Project project, ClassDrawing drawClass) {
		super("New Project");
		this.gui = gui ;
		project = null ;
		this.drawClass = drawClass ;
		init() ;
	}
	
	private void init() {
		Container pan = this.getContentPane();
		this.setSize(400, 150);
		this.setLocationRelativeTo(null);
		
		place.setEnabled(false);
		placeJFC.setFileSelectionMode(placeJFC.DIRECTORIES_ONLY);
		
		button.setLayout(new GridLayout(1, 2));
		button.add(create);
		button.add(cancel);
		
		principal.setLayout(new GridLayout(2, 1));
		
		namePanel.add(name);
		namePanel.add(nameTF);
		
		placePanel.add(placeJFCActioner);
		placePanel.add(place);
		
		principal.add(namePanel);
		principal.add(placePanel);
		
		pan.add(principal);
		pan.add(button, BorderLayout.SOUTH);
		
		create.addActionListener(new CreateButton());
		cancel.addActionListener(new CancelButton());
		placeJFCActioner.addActionListener(new placeChoose());
		this.setVisible(true);
	}
	
	private class CancelButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			dispose() ;
		}
	}
	
	private class CreateButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Project project = new Project(nameTF.getText(), place.getText()+"/"+nameTF.getText());
			File directory = new File(place.getText() + "/" + nameTF.getText());
			directory.mkdir();
			String javaDirectory = directory.getPath() + "/Java" ;
			File javaDir = new File(javaDirectory);
			javaDir.mkdir();
			
			if (drawClass != null){
				gui.remove(drawClass);
			}
			
			drawClass = new ClassDrawing(project.getProjectProgram());
			gui.initProject(project, drawClass);
			dispose();
		}
	}
	
	private class placeChoose implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			placeJFC.setApproveButtonText("Choose");
			if (placeJFC.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				place.setText(placeJFC.getSelectedFile().getAbsolutePath());
			}
		}
	}
}
