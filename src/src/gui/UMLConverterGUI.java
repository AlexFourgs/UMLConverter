package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ListResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import textualuml.ObjectAbstractClass;
import textualuml.ObjectClass;
import textualuml.ObjectInterface;
import textualuml.ObjectPackage;
import engine.Engine;
import engine.Project;
import graphicuml.ClassDrawing;


/**
 * This class is the GUI.
 * 
 * @author Alexandre Fourgs
 *
 * 
 */
public class UMLConverterGUI extends JFrame {
	private UMLConverterGUI gui = this ;
	private Project project = new Project() ;
	private Engine engine = new Engine() ;
	private JPanel cache = new JPanel();
	
	/**
	 * JPanel for tab
	 */
	
	private JPanel uml = new JPanel();
	private JPanel code = new JPanel();
	/**
	 * JMenu for the JMenuBar
	 */
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFile = new JMenu("File");
	private JMenu menuEdit = new JMenu("Edit");
	private JMenu menuHelp = new JMenu("Help");
	
	/**
	 * Items for the JMenu menuFile
	 */
	private JMenuItem itemNew = new JMenuItem("New Project");
	private JMenuItem itemOpen = new JMenuItem("Open ...");
	//private JMenuItem itemClose = new JMenuItem("Close");
	private JMenuItem itemSave = new JMenuItem("Save Project");
	//private JMenuItem itemImport = new JMenuItem("Import .java file(s)");
	private JMenuItem itemSaveUMLasImage = new JMenuItem("Export UML to .jpg");
	//private JMenuItem itemConvert = new JMenuItem("Convert");
	private JMenuItem itemConvertUMLtoJava = new JMenuItem("UML to .java");
	private JMenuItem itemConvertJavaToUML = new JMenuItem(".java to UML");
	private JMenuItem itemExit = new JMenuItem("Exit");

	/**
	 * Items for the JMenu menuEdit
	 */
	private JMenuItem itemUndo = new JMenuItem("Undo");
	private JMenuItem itemRedo = new JMenuItem("Redo");
	
	/**
	 * Items for the JMenu menuHelp 
	 */
	private JMenuItem itemTutorial = new JMenuItem("Tutorial");
	private JMenuItem itemAbout = new JMenuItem("About UMLConverter");
	
	/**
	 * Elements from UML Tab
	 */
	private JTabbedPane onglet;
	private JPanel panUMLButton = new JPanel() ;
	private GridLayout gl1 = new GridLayout(2, 1);
	private JPanel panUMLButtonClass = new JPanel();
	private GridLayout gl1_2 = new GridLayout(5, 1);
	private JPanel panUMLButtonLinks = new JPanel();
	private GridLayout gl1_3 = new GridLayout(5, 1);
	private JLabel jlClass = new JLabel("Package, Class & inc :");
	private ImageIcon packageIcon = new ImageIcon("./Images/package.jpg");
	private JButton bPackage = new JButton(packageIcon);
	private ImageIcon classIcon = new ImageIcon("./Images/class.jpg");
	private JButton bClass = new JButton(classIcon);
	private ImageIcon abstractClassIcon = new ImageIcon("./Images/abstract.jpg");
	private JButton bAbstractClass = new JButton(abstractClassIcon);
	private ImageIcon interfaceIcon = new ImageIcon("./Images/interface.jpg");
	private JButton bInterface = new JButton(interfaceIcon);
	private JLabel jlLinks = new JLabel("Links :");
	private JButton bImplements = new JButton("Implementation");
	private JButton bAggregation = new JButton("Aggregation");
	private JButton bComposition = new JButton("Composition");
	private JButton bHeritage = new JButton("Heritage");
	private JPanel drawZone = new JPanel();
	private ClassDrawing drawClass ;
	//private JPanel panUMLGraphic ;
	private JScrollPane umlScrollPane ;
	
	/**
	 * Onglet "Code"
	 */
	private String pathFileSelect ;
	private JTree projectTree ;
	private DefaultMutableTreeNode projectNode ;
	private JPanel codeWest = new JPanel();
	private JPanel explorer = new JPanel();
	private JScrollPane explorerPane = new JScrollPane(explorer);
	private JPanel textAeraJP = new JPanel();
	private JTextPane textAera = new JTextPane();
	private JScrollPane codeScrollPane = new JScrollPane(textAera, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	private JPanel buttonPanCode = new JPanel();
	private JButton saveFile = new JButton("Save");
	
	/**
	 * Elements for modify class and inc...
	 */
	
	private JPanel modif = new JPanel();
	private JPanel classPanel = new JPanel();
	private JLabel classLabel = new JLabel("Class :");
	private JList<ObjectClass> classModifList = new JList<ObjectClass>();
	private DefaultListModel<ObjectClass> dataClassList = new DefaultListModel<ObjectClass>();
	private JScrollPane classScrollPane = new JScrollPane(classModifList);
	private JPanel abstractPanel = new JPanel();
	private JLabel abstractLabel = new JLabel("Abstract Class :");
	private JList<ObjectAbstractClass> abstractModifList = new JList<ObjectAbstractClass>();
	private DefaultListModel<ObjectAbstractClass> dataAbstractList = new DefaultListModel<ObjectAbstractClass>();
	private JScrollPane abstractScrollPane = new JScrollPane(abstractModifList);
	private JPanel interfacePanel = new JPanel();
	private JLabel interfaceLabel = new JLabel("Interface :");
	private JList<ObjectInterface> interfaceModifList = new JList<ObjectInterface>();
	private DefaultListModel<ObjectInterface> dataInterfaceList = new DefaultListModel<ObjectInterface>();
	private JScrollPane interfaceScrollPane = new JScrollPane(interfaceModifList);
	private JPanel modifButtons = new JPanel();
	private JButton edit = new JButton("Edit");
	private JButton delete = new JButton("Delete");
	private JSplitPane split ;
	
	/**
	 * Constructor.
	 */
	
	public UMLConverterGUI () {
		super("UML Converter") ;
		init();
	}

	public void initProject (Project project, ClassDrawing drawClass) {
		this.project = project ;
		this.drawClass = drawClass ;
		
		if(split != null){
			drawZone.remove(split);
		}
		
		umlScrollPane = new JScrollPane(drawClass, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, umlScrollPane, modif);
		split.setDividerLocation(0.75);
		split.setResizeWeight(0.75);
		drawZone.add(split);
	    drawZone.revalidate();	    
	    
	    this.remove(cache);
	    this.getContentPane().add(onglet);
		this.validate();
		
		
	}
	
	/**
	 * This method initialize all container and component for the GUI.
	 * On devrait peut-être faire plusieurs init pour différencier chaque partie de l'interface 
	 * (exemple : Une pour le menuBar, une pour la partie UML, une pour la partie pseudo IDE...)
	 */
	private void init () {
		Container pan = this.getContentPane();
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/**
		 * Ajout des éléments dans la barre de menus.
		 */
		barre() ;
	    
		/**
		 * Ajout des onglets.
		 */
	    onglet = new JTabbedPane();
	    onglet.addTab("UML", uml);
	    onglet.addTab("Code", code);
	    pan.add(cache);
	    
	    /**
	     * Configuration de l'onglet UML.
	     */
	    uml.setLayout(new BorderLayout());
	    
	    panUMLButton.setLayout(gl1);
	    panUMLButtonClass.setLayout(gl1_2);
	    panUMLButtonLinks.setLayout(gl1_3);
	    panUMLButton.add(panUMLButtonClass);
	    panUMLButton.add(panUMLButtonLinks);
	    panUMLButtonClass.add(jlClass);
	    panUMLButtonClass.add(bPackage);
	    panUMLButtonClass.add(bClass);
	    panUMLButtonClass.add(bAbstractClass);
	    panUMLButtonClass.add(bInterface);
	    panUMLButtonLinks.add(jlLinks);
	    panUMLButtonLinks.add(bHeritage);
	    panUMLButtonLinks.add(bComposition);
	    panUMLButtonLinks.add(bAggregation);
	    panUMLButtonLinks.add(bImplements);
	    uml.add(panUMLButton, BorderLayout.WEST);
	    
	    drawZone.setLayout(new GridLayout(1, 1));
	    modificationBarre();
	    
	    uml.add(drawZone);
	    
	    
	    /**
	     * Configuration de l'onglet "Code"
	     */
	    buttonPanCode.add(saveFile);
	    code.setLayout(new BorderLayout());
	    code.add(codeWest, BorderLayout.WEST);
	    textAeraJP.setLayout(new GridLayout(1, 1));
	    textAeraJP.add(codeScrollPane, BorderLayout.CENTER);
	    code.add(textAeraJP);
	    
	   
	    
		setVisible(true);
		setExtendedState(super.MAXIMIZED_BOTH);
		
		itemExit.addActionListener(new CloseProgram());
		bClass.addActionListener(new ClassEditor());
		bPackage.addActionListener(new PackageEditor());
		bInterface.addActionListener(new InterfaceEditor());
		bAbstractClass.addActionListener(new AbstractClassEditor());
		itemNew.addActionListener(new NewProject());
		bHeritage.addActionListener(new HeritageEditor());
		bAggregation.addActionListener(new AggregationEditor());
		bComposition.addActionListener(new CompositionEditor());
		bImplements.addActionListener(new ImplementEditor());
		itemSave.addActionListener(new SaveProject());
		itemSaveUMLasImage.addActionListener(new ExportUMLtoImage());
		itemOpen.addActionListener(new OpenProject());
		itemConvertUMLtoJava.addActionListener(new ExportUMLtoJava());
		classModifList.addListSelectionListener(new ModifierListListener());
		abstractModifList.addListSelectionListener(new ModifierListListener());
		interfaceModifList.addListSelectionListener(new ModifierListListener());
		edit.addActionListener(new EditElement());
		delete.addActionListener(new DeleteElement());
		itemConvertJavaToUML.addActionListener(new JavaToUML());
		saveFile.addActionListener(new saveFile());
		
	}
	
	/**
	 * This method is for add component item in the Menu.
	 */
	private void barre () {
		this.menuBar.add(menuFile);
		this.menuBar.add(menuEdit);
		this.menuBar.add(menuHelp);
		
		this.menuFile.add(itemNew);
		this.menuFile.add(itemOpen);
		//this.menuFile.add(itemClose);
		this.menuFile.add(itemSave);
		menuFile.addSeparator();
		this.menuFile.add(itemSaveUMLasImage);
		this.menuFile.add(itemConvertUMLtoJava);
		this.menuFile.add(itemConvertJavaToUML);
		menuFile.addSeparator();
		this.menuFile.add(itemExit);

		this.menuEdit.add(itemUndo);
		this.menuEdit.add(itemRedo);
		
		this.menuHelp.add(itemTutorial);
		this.menuHelp.add(itemAbout);		
		
		this.setJMenuBar(menuBar);
	}
	
	// Initialise la barre qui permettra de sélectionner les objets que l'utilisateur voudra modifier.
	private void modificationBarre () {
		modif.setLayout(new GridLayout(1, 4));
		
		classPanel.setLayout(new GridLayout(2, 1));
		classPanel.add(classLabel);
		classPanel.add(classScrollPane);
		
		abstractPanel.setLayout(new GridLayout(2, 1));
		abstractPanel.add(abstractLabel);
		abstractPanel.add(abstractScrollPane);
		
		interfacePanel.setLayout(new GridLayout(2, 1));
		interfacePanel.add(interfaceLabel);
		interfacePanel.add(interfaceScrollPane);
		
		modifButtons.setLayout(new GridLayout(2, 1));
		modifButtons.add(edit);
		modifButtons.add(delete);
		
		modif.add(classPanel);
		modif.add(abstractPanel);
		modif.add(interfacePanel);
		modif.add(modifButtons);
		
	}
	
	private void buildTree (){
		this.projectNode = new DefaultMutableTreeNode("Java");
		
		for (int i = 0 ; i < project.getProjectProgram().getAllPackages().size() ; i++) {
			ObjectPackage actualPackage = project.getProjectProgram().getAllPackages().get(i);
			//File packageFile = new File(project.getPlace() + "/Java/" + actualPackage.getName());
			DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode(actualPackage.getName());
			
			for (int j = 0 ; j < actualPackage.getAllClass().size() ; j++){
				ObjectClass actualClass = actualPackage.getAllClass().get(j);
				//File classFile = new File(project.getPlace() + "/Java/" + actualPackage.getName() + "/" + actualClass.getName() + ".java");
				
				DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(actualClass.getName());
				packageNode.add(classNode);
			}
			
			for (int j = 0 ; j < actualPackage.getAllInterface().size() ; j++){
				ObjectInterface actualInterface = actualPackage.getAllInterface().get(j);
				//File interfaceFile = new File(project.getPlace() + "/Java/" + actualPackage.getName() + "/" + actualInterface.getName() + ".java");
				
				DefaultMutableTreeNode interfaceNode = new DefaultMutableTreeNode(actualInterface.getName());
				packageNode.add(interfaceNode);
			}
			
			projectNode.add(packageNode);
		}
			
		projectTree = new JTree(projectNode);
	}
	
	
	private class NewProject implements ActionListener {
		public void actionPerformed(ActionEvent e){
			new ProjectJFrame(gui, project, drawClass);
		}
	}
	private class CloseProgram implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        System.exit(0);
	    }
	}
	
	private class ClassEditor implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ClassJFrame(project, drawClass, classModifList, dataClassList, classPanel);
		}
	}
	
	private class PackageEditor implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new PackageJFrame(project, drawClass);
		}
	}
	
	private class InterfaceEditor implements ActionListener {
		public void actionPerformed(ActionEvent e){
			new InterfaceJFrame(project, drawClass, interfaceModifList, dataInterfaceList, interfacePanel);
		}
	}
	
	private class AbstractClassEditor implements ActionListener {
		public void actionPerformed(ActionEvent e){
			new AbstractClassJFrame(project, drawClass, abstractModifList, dataAbstractList, abstractPanel);
		}
	}
	
	private class HeritageEditor implements ActionListener {
		public void actionPerformed(ActionEvent e){
			new HeritageJFrame(project, drawClass);
		}
	}
	
	private class CompositionEditor implements ActionListener {
		public void actionPerformed(ActionEvent e){
			new CompositionJFrame(project, drawClass);
		}
	}
	
	private class AggregationEditor implements ActionListener {
		public void actionPerformed(ActionEvent e){
			new AggregationJFrame(project, drawClass);
		}
	}
	
	private class ImplementEditor implements ActionListener {
		public void actionPerformed(ActionEvent e){
			new ImplementsJFrame(project, drawClass);
		}
	}
	
	private class ExportUMLtoJava implements ActionListener {
		public void actionPerformed(ActionEvent e){
			engine.umlToJava(project);
			explorer.removeAll();
			
			buildTree();
		    explorer.add(projectTree);
		    codeWest.add(explorerPane, BorderLayout.CENTER);
		    codeWest.add(buttonPanCode, BorderLayout.SOUTH);
		    projectTree.addTreeSelectionListener(new TreeListener());
		    explorerPane.revalidate();
		}
	}
	private class ExportUMLtoImage implements ActionListener {
		public void actionPerformed(ActionEvent e){
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(jfc.DIRECTORIES_ONLY);
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				engine.saveUmlAsImage(drawClass, jfc.getDialogTitle(), jfc.getSelectedFile().getAbsolutePath());
			}
		}		
	}
	
	private class SaveProject implements ActionListener {
		public void actionPerformed(ActionEvent e){
			JFileChooser jfc = new JFileChooser();
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				engine.saveProject(project, jfc.getSelectedFile().getAbsolutePath());
			}
		}
	}
	
	private class OpenProject implements ActionListener {
		public void actionPerformed(ActionEvent e){
			JFileChooser jfc = new JFileChooser();
			if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				engine.openProject(jfc.getSelectedFile().getAbsolutePath(), project);
			}
			
			if (drawClass != null){
				gui.remove(drawClass);
			}
			
			drawClass = new ClassDrawing(project.getProjectProgram());
			drawClass.revalidate();
			gui.initProject(project, drawClass);
		}
	}
	
	private class EditElement implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if (classModifList.getSelectedIndex() >= 0) {
				ObjectClass selected = classModifList.getSelectedValue();
				new ClassJFrame(project, drawClass, classModifList, dataClassList, classPanel, selected);
				
			}
			else if (abstractModifList.getSelectedIndex() >= 0){
				ObjectAbstractClass selected = abstractModifList.getSelectedValue();
				new AbstractClassJFrame(project, drawClass, abstractModifList, dataAbstractList, abstractPanel, selected);
			}
			else if (interfaceModifList.getSelectedIndex() >= 0){
				ObjectInterface selected = interfaceModifList.getSelectedValue();
				new InterfaceJFrame(project, drawClass, interfaceModifList, dataInterfaceList, interfacePanel, selected);
			}
			else {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "Please, select a class, an abstract class or an interface", "Nothing selected", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private class DeleteElement implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if (classModifList.getSelectedIndex() >= 0) {
				ObjectClass selected = classModifList.getSelectedValue();
				selected.getObjPackage().getAllClass().remove(selected);
				dataClassList.removeElement(selected);
				classModifList.setModel(dataClassList);
				classPanel.revalidate();
				selected = null ;
				drawClass.repaint();
			}
			else if (abstractModifList.getSelectedIndex() >= 0){
				ObjectAbstractClass selected = abstractModifList.getSelectedValue();
				selected.getObjPackage().getAllClass().remove(selected);
				dataAbstractList.removeElement(selected);
				abstractModifList.setModel(dataAbstractList);
				abstractPanel.revalidate();
				selected = null ;
				drawClass.repaint();
			}
			else if (interfaceModifList.getSelectedIndex() >= 0){
				ObjectInterface selected = interfaceModifList.getSelectedValue();
				selected.getObjPackage().getAllInterface().remove(selected);
				dataInterfaceList.removeElement(selected);
				interfaceModifList.setModel(dataInterfaceList);
				interfacePanel.revalidate();
				selected = null ;
				drawClass.repaint();
			}
			else {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "Please, select a class, an abstract class or an interface", "Nothing selected", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private class ModifierListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			JList selected = (JList) e.getSource();
				
			if (selected == classModifList){
				abstractModifList.clearSelection();
				interfaceModifList.clearSelection();
			}
			else if (selected == abstractModifList){
				classModifList.clearSelection();
				interfaceModifList.clearSelection();
			}
			else {
				classModifList.clearSelection();
				abstractModifList.clearSelection();
			}
		}
	}
	
	private class JavaToUML implements ActionListener {
		public void actionPerformed (ActionEvent e){
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(jfc.DIRECTORIES_ONLY);
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				engine.convertJavaToUML(jfc.getSelectedFile().getAbsolutePath(), project.getProjectProgram().getAllPackages().get(0), project);
				for (int i = 0 ; i < project.getProjectProgram().getAllPackages().size() ; i++){
					ObjectPackage actualPackage = project.getProjectProgram().getAllPackages().get(i);
					for (int j = 0 ; j < actualPackage.getAllClass().size() ; j++){
						ObjectClass actualClass = actualPackage.getAllClass().get(j);
						if (actualClass instanceof ObjectAbstractClass){
							dataAbstractList.addElement((ObjectAbstractClass) actualClass);
							abstractModifList.setModel(dataAbstractList);
							abstractPanel.revalidate();
						}
						else {
							dataClassList.addElement(actualClass);
							classModifList.setModel(dataClassList);
							classPanel.revalidate();
						}
					}
					for (int j = 0 ; j < actualPackage.getAllInterface().size() ; j++){
						ObjectInterface actualInterface = actualPackage.getAllInterface().get(j);
						dataInterfaceList.addElement(actualInterface);
						interfaceModifList.setModel(dataInterfaceList);
						interfacePanel.revalidate();
					}
				}
			}
			drawClass.repaint();
			uml.revalidate();
		}
	}
	
	private class TreeListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e){
			String path = project.getPlace() ;
			TreePath treePath = e.getPath();
			
			for (Object name : treePath.getPath()){
				path = path + "/" + name ; 
			}
			path = path + ".java" ;
			pathFileSelect = path ;
			File javaFile = new File(path);
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
			catch (IOException ioe){
				System.out.println(ioe.toString());
			}
			textAera.setText(code);
		}
	}
	
	private class saveFile implements ActionListener {
		public void actionPerformed(ActionEvent e){
			File java = new File(pathFileSelect);
			
			try {
				if (!java.exists()){
					java.createNewFile();
				}
				
				FileWriter writer = new FileWriter(java);
				try {
					writer.write(textAera.getText());
				}
				catch (Exception excep){
					System.out.println("Erreur d'écriture");
				}
				finally {
					writer.close();
				}
				
			}
			catch (IOException ioe){
				System.out.println("Impossible de créé le fichier");
			}
		}
	}
	
	public static void main (String[] args) {
		new UMLConverterGUI() ;
	}
		
}
