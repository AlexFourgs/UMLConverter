package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import textualuml.ObjectAttribute;
import textualuml.ObjectClass;
import textualuml.ObjectMethod;
import textualuml.ObjectPackage;
import textualuml.VisibilityEnum;
import engine.Project;
import graphicuml.ClassDrawing;


/**
 * 
 * @author Alexandre Fourgs & Florian Astori
 * 	Classe correspondant à la JFrame de
 *         modification et création d'une classe.
 */
public class ClassJFrame extends JFrame {
	private Project project;
	private ObjectClass objClass = new ObjectClass();
	
	private ClassDrawing drawClass;
	JList<ObjectClass> classModifList ;
	DefaultListModel<ObjectClass> dataClassList;
	JPanel classPanel ;

	/**
	 * Onglets
	 */
	private JPanel general = new JPanel();
	private JPanel attributes = new JPanel();
	protected JPanel methods = new JPanel();
	private JPanel classPackage = new JPanel();
	private JTabbedPane onglets = new JTabbedPane();

	/**
	 * Composants tout onglets
	 */
	private JPanel panSouth = new JPanel();
	private GridLayout glSouth = new GridLayout(1, 3);
	protected JButton ok = new JButton("Ok");
	protected JButton cancel = new JButton("Cancel");
	protected JButton apply = new JButton("Apply");

	/**
	 * Component for onglet "General"
	 */
	private JLabel className = new JLabel("Name :");
	protected JTextField nameTF = new JTextField();

	/**
	 * Component for onglet "Attributes"
	 */
	private JPanel panAttributesLeft = new JPanel();
	private JPanel panAttributesModif = new JPanel();
	private JLabel visibility = new JLabel("Visibility :");
	protected JComboBox<VisibilityEnum> visibilityList = new JComboBox<VisibilityEnum>();
	private JLabel attributeName = new JLabel("Name :");
	protected JTextField attributeNameTF = new JTextField();
	private JLabel attributeType = new JLabel("Type :");
	protected JTextField attributeTypeTF = new JTextField();
	protected JCheckBox attributeGetters = new JCheckBox("Getter");
	protected JCheckBox attributeSetters = new JCheckBox("Setter");
	private JPanel panAttributesButton = new JPanel();
	protected JButton add = new JButton("Add");
	protected JButton delete = new JButton("Delete");
	protected JButton applyModification = new JButton("Apply");
	protected JPanel panAttributesRight = new JPanel();

	/**
	 * Component for onglet "Methods"
	 */
	private JPanel panMethodsLeft = new JPanel();
	private JPanel panMethodsModif = new JPanel();
	private JLabel visibilityMethods = new JLabel("Visibility :");
	private JComboBox<VisibilityEnum> visibilityListMethods = new JComboBox<VisibilityEnum>();
	private JLabel methodName = new JLabel("Name :");
	private JTextField methodNameTF = new JTextField();
	private JLabel methodType = new JLabel("Type :");
	private JTextField methodTypeTF = new JTextField();
	private JPanel panMethodButton = new JPanel();
	private JPanel panMethodAttributes = new JPanel();
	private JPanel panMAGeneral = new JPanel();
	private JPanel panMAButton = new JPanel();
	private JLabel panMALabel = new JLabel("Parameters :");
	private JComboBox<ObjectAttribute> parametersList = new JComboBox<ObjectAttribute>();
	private JLabel panMAType = new JLabel("Type :");
	private JTextField panMATypeTF = new JTextField();
	private JLabel panMAName = new JLabel("Name :");
	private JTextField panMANameTF = new JTextField();
	private JButton parameterAdd = new JButton("Add");
	private JButton parameterDelete = new JButton("Delete");
	private JButton parameterApply = new JButton("Apply");
	private JButton methodAdd = new JButton("Add");
	private JButton methodDelete = new JButton("Delete");
	private JButton methodApply = new JButton("Apply");
	private JPanel panMethodsRight = new JPanel();
	private ArrayList<ObjectAttribute> parameters = new ArrayList<ObjectAttribute>();
	
	/**
	 * Component for onglet "package"
	 */
	protected JComboBox<ObjectPackage> packagesList = new JComboBox<ObjectPackage>();

	/**
	 * JList
	 */
	protected JList<ObjectAttribute> attributList = new JList<ObjectAttribute>();
	protected JScrollPane jsp = new JScrollPane(attributList);
	protected JList<ObjectMethod> methodList = new JList<ObjectMethod>();
	protected JScrollPane jspMethod = new JScrollPane(methodList);
	protected DefaultListModel<ObjectAttribute> dataAttribute = new DefaultListModel<ObjectAttribute>();
	protected DefaultListModel<ObjectMethod> dataMethod = new DefaultListModel<ObjectMethod>();


	public ClassJFrame(Project project, ClassDrawing drawClass, JList<ObjectClass> classModifList, DefaultListModel<ObjectClass> dataClassList, JPanel classPanel) {
		super("Class Editor");
		this.project = project;
		this.drawClass = drawClass;
		this.classModifList = classModifList ;
		this.dataClassList = dataClassList ;
		this.classPanel = classPanel ;
		init();
	}
	
	public ClassJFrame(Project project, ClassDrawing drawClass, JList<ObjectClass> classModifList, DefaultListModel<ObjectClass> dataClassList, JPanel classPanel, ObjectClass objClass) {
		super("Class Editor");
		this.project = project;
		this.drawClass = drawClass;
		this.classModifList = classModifList ;
		this.dataClassList = dataClassList ;
		this.classPanel = classPanel ;
		this.objClass = objClass ;
		initForExistingClass();
		init();
	}

	private void init() {
		Container pan = this.getContentPane();
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);

		pan.setLayout(new BorderLayout());
		
		southButton();
		
		onglets.add("General", general);
		onglets.add("Attributes", attributes);
		onglets.add("Methods", methods);
		onglets.add("Package", classPackage);

		nameTF.setPreferredSize(new Dimension(150, 20));
		general.add(className);
		general.add(nameTF);


		panAttribute();
		panMethod();
		
		for (int i = 0 ; i < project.getProjectProgram().getAllPackages().size() ; i++){
			packagesList.addItem(project.getProjectProgram().getAllPackages().get(i));
		}
		
		classPackage.add(packagesList);

		pan.add(onglets);
		pan.add(panSouth, BorderLayout.SOUTH);

		listener() ;
		
		this.setVisible(true);
	}
	
	private void initForExistingClass () {
		nameTF.setText(objClass.getName());
		
		for (int i = 0 ; i < objClass.getAttributes().size() ; i++){
			dataAttribute.addElement(objClass.getAttributes().get(i));
		}
		
		for (int i = 0 ; i < objClass.getMethods().size() ; i++){
			dataMethod.addElement(objClass.getMethods().get(i));
		}
		
		attributList.setModel(dataAttribute);
		methodList.setModel(dataMethod);
		panAttributesRight.revalidate();
		panMethodsRight.revalidate();
		
		packagesList.setSelectedItem(objClass.getObjPackage());
		
	}
	
	/**
	 * This method generates buttons on south's JFrame.
	 */
	protected void southButton() {
		panSouth.setLayout(glSouth);
		panSouth.add(ok);
		panSouth.add(apply);
		panSouth.add(cancel);
	}
	
	/**
	 * This method generates the Attribute's panel
	 */
	protected void panAttribute() {
		
		for (VisibilityEnum visibility : VisibilityEnum.values()) {
			if (visibility != VisibilityEnum.NULL) {
				visibilityList.addItem(visibility);
			}
		}
		
		attributes.setLayout(new GridLayout(1, 2));
		panAttributesLeft.setLayout(new GridLayout(2, 1));
		
		panAttributesModif.setLayout(new GridLayout(4, 2));
		panAttributesModif.add(visibility);
		panAttributesModif.add(visibilityList);
		panAttributesModif.add(attributeType);
		panAttributesModif.add(attributeTypeTF);
		panAttributesModif.add(attributeName);
		panAttributesModif.add(attributeNameTF);
		panAttributesModif.add(attributeGetters);
		panAttributesModif.add(attributeSetters);

		// panAttributesButton.setLayout(new GridLayout(1, 3));
		add.setEnabled(false);
		delete.setEnabled(false);
		applyModification.setEnabled(false);
		panAttributesButton.add(add);
		panAttributesButton.add(delete);
		panAttributesButton.add(applyModification);

		panAttributesLeft.add(panAttributesModif);
		panAttributesLeft.add(panAttributesButton);
		// attributesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// attributesList.setLayoutOrientation(JList.VERTICAL_WRAP);
		//attributesList.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// new JScrollPane(attributesList);
		// panAttributesRight.add(attributesList);
		jsp.setPreferredSize(new Dimension(240, 300));
		panAttributesRight.add(jsp);

		attributes.add(panAttributesLeft);
		// Jlist non visible !
		attributes.add(panAttributesRight);
	}
	

	void panMethod () {
		for (VisibilityEnum visibility : VisibilityEnum.values()) {
			if (visibility != VisibilityEnum.NULL){
				visibilityListMethods.addItem(visibility);
			}
		}
		
		methods.setLayout(new GridLayout(1, 2));
		panMethodsLeft.setLayout(new GridLayout(3, 1));

		panMethodsModif.setLayout(new GridLayout(4, 2));
		panMethodsModif.add(visibilityMethods);
		panMethodsModif.add(visibilityListMethods);
		panMethodsModif.add(methodType);
		panMethodsModif.add(methodTypeTF);
		panMethodsModif.add(methodName);
		panMethodsModif.add(methodNameTF);

		panMethodAttributes.setLayout(new GridLayout(2, 1));
		panMethodAttributes.setBorder(BorderFactory.createMatteBorder(1, 1, 1,
				1, Color.BLACK));
		panMAGeneral.setLayout(new GridLayout(3, 2));
		panMAGeneral.add(panMALabel);
		panMAGeneral.add(parametersList);
		panMAGeneral.add(panMAType);
		panMAGeneral.add(panMATypeTF);
		panMAGeneral.add(panMAName);
		panMAGeneral.add(panMANameTF);
		panMAButton.add(parameterAdd);
		panMAButton.add(parameterApply);
		panMAButton.add(parameterDelete);
		panMethodAttributes.add(panMAGeneral);
		panMethodAttributes.add(panMAButton);

		panMethodButton.add(methodAdd);
		panMethodButton.add(methodApply);
		panMethodButton.add(methodDelete);

		panMethodsLeft.add(panMethodsModif);
		panMethodsLeft.add(panMethodAttributes);
		panMethodsLeft.add(panMethodButton);

		jspMethod.setPreferredSize(new Dimension(240, 300));
		panMethodsRight.add(jspMethod);

		methods.add(panMethodsLeft);
		methods.add(panMethodsRight);
	}

	/**
	 * This method clear all component from the tab "Attribute"
	 */
	protected void clearAttribute () {
		visibilityList.setSelectedIndex(0);
		attributeGetters.setSelected(false);
		attributeSetters.setSelected(false);
		attributeTypeTF.setText("");
		attributeNameTF.setText("");
	}

	/**
	 * This method clear all component from the tab "Method"
	 */
	void clearMethod () {
		visibilityListMethods.setSelectedIndex(0);
		methodTypeTF.setText("");
		methodNameTF.setText("");
	}
	
	/**
	 * This method add listener.
	 */
	private void listener() {
		ok.addActionListener(new okButton());
		apply.addActionListener(new applyButton());
		cancel.addActionListener(new cancelButton());

		add.addActionListener(new addAttribute());
		delete.addActionListener(new deleteAttribute());
		attributList.addListSelectionListener(new AttributeListListener());
		
		methodAdd.addActionListener(new addMethod());
		methodApply.addActionListener(new applyMethod());
		methodDelete.addActionListener(new deleteMethod());
		parameterAdd.addActionListener(new addParameter());
		parameterApply.addActionListener(new applyParameter());
		parameterDelete.addActionListener(new delParameter());
		
		attributeNameTF.getDocument().addDocumentListener(new AttributeTextFieldListener());
		attributeTypeTF.getDocument().addDocumentListener(new AttributeTextFieldListener());
		methodNameTF.getDocument().addDocumentListener(new MethodTextFieldListener());
		methodList.addListSelectionListener(new MethodListListener());
	}
	
	// Fonctionne correctement
	private class okButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = nameTF.getText();
			ObjectPackage pack = (ObjectPackage) packagesList.getSelectedItem();

			objClass.setName(name);
			
			
			if (!pack.getAllClass().contains(objClass)){
				pack.addClass(objClass);
				
				if (objClass.getObjPackage() != null) {
					objClass.getObjPackage().getAllClass().remove(objClass);
				}
				
				objClass.setObjPackage(pack);
			}			

			objClass.setObjectWidthAndHeight();
			drawClass.repaint();
			
			if (!dataClassList.contains(objClass)){
				dataClassList.addElement(objClass);
				classModifList.setModel(dataClassList);
				classPanel.revalidate();
			}
			

			dispose();
		}
	}
	
	
	private class applyButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = nameTF.getText();
			ObjectPackage actualPack = objClass.getObjPackage();
			ObjectPackage pack = (ObjectPackage) packagesList.getSelectedItem();
			
			if (actualPack != pack){
				actualPack.getAllClass().remove(objClass);
				pack.addClass(objClass);
			}
			
			objClass.setName(name);
			objClass.setObjPackage(pack);

			objClass.setObjectWidthAndHeight();
			drawClass.repaint();
		}
	}

	/**
	 * 
	 * @author Alexandre Fourgs ActionListener du boutton cancel. Ferme la
	 *         JFrame sans apporter aucune modification (Pas de création de
	 *         classe ni de modification pris en compte, sauf si apply avant).
	 */
	// Fonctionne correctement
	private class cancelButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	// Fonctionne correctement
	private class addAttribute implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			VisibilityEnum visibility = (VisibilityEnum) visibilityList
					.getSelectedItem();
			String name = attributeNameTF.getText();
			String type ;
			
			if (attributeTypeTF.getText().length() > 0){
				type = attributeTypeTF.getText();
			}
			else {
				type = "void" ;
			}
			
			ObjectAttribute objAttribute = new ObjectAttribute(visibility,
					type, name);

			if (attributeGetters.isSelected()) {
				String nameToUpperCase = attributeNameTF.getText();
				nameToUpperCase = nameToUpperCase.replaceFirst(".", (nameToUpperCase.charAt(0)+"").toUpperCase());
				
				objAttribute.setGetter(attributeGetters.isSelected());
				
				ObjectMethod getter = new ObjectMethod(VisibilityEnum.PUBLIC,
						"get" + nameToUpperCase,
						attributeTypeTF.getText(),
						new ArrayList<ObjectAttribute>());
				objClass.addMethod(getter);
				dataMethod.addElement(getter);
			}
			if (attributeSetters.isSelected()) {
				String nameToUpperCase = attributeNameTF.getText();
				nameToUpperCase = nameToUpperCase.replaceFirst(".", (nameToUpperCase.charAt(0)+"").toUpperCase());
				
				objAttribute.setSetter(attributeSetters.isSelected());
				ObjectAttribute parameter = new ObjectAttribute(
						VisibilityEnum.NULL, attributeTypeTF.getText(),
						attributeNameTF.getText());
				ArrayList<ObjectAttribute> parameterList = new ArrayList<ObjectAttribute>();
				parameterList.add(parameter);
				ObjectMethod setter = new ObjectMethod(VisibilityEnum.PUBLIC,
						"set" + nameToUpperCase, "void",
						parameterList);
				objClass.addMethod(setter);
				dataMethod.addElement(setter);
			}

			objClass.addAttribute(objAttribute);

			dataAttribute.addElement(objAttribute);
			attributList.setModel(dataAttribute);
			methodList.setModel(dataMethod);
			panMethodsRight.revalidate();
			panAttributesRight.revalidate();

			/*
			 * Réinitialise les champs pour les attributs.
			 */
			clearAttribute();
		}
	}
	
	// Fonctionne correctement, mais ajouter la suppression des getters et setters
	private class deleteAttribute implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			objClass.delAttribute(attributList.getSelectedValue());
			
			if (attributList.getSelectedValue().getGetter()){
				String getterName = attributList.getSelectedValue().getName();
				getterName = getterName.replaceFirst(".", (getterName.charAt(0)+"").toUpperCase());
				getterName = "get" + getterName ;
				
				for (int i = 0 ; i < objClass.getMethods().size() ; i++){
					if (objClass.getMethods().get(i).getName() == getterName){
						objClass.delMethod(objClass.getMethods().get(i));
						dataMethod.removeElement(objClass.getMethods().get(i));
						methodList.setModel(dataMethod);

						panMethodsRight.revalidate();
					}
				}
			}
			
			if (attributList.getSelectedValue().getSetter()){
				String setterName = attributList.getSelectedValue().getName();
				setterName = setterName.replaceFirst(".", (setterName.charAt(0)+"").toUpperCase());
				setterName = "set" + setterName ;
				
				for (int i = 0 ; i < objClass.getMethods().size() ; i++){
					if (objClass.getMethods().get(i).getName() == setterName){
						objClass.delMethod(objClass.getMethods().get(i));
						dataMethod.removeElement(objClass.getMethods().get(i));
						methodList.setModel(dataMethod);

						panMethodsRight.revalidate();

					}
				}
			}
			
			dataAttribute.removeElement(attributList.getSelectedValue());
			
			
			attributList.setModel(dataAttribute);
			panAttributesRight.revalidate();
			attributList.clearSelection();

			clearAttribute();
		}
	}

	// Fonctionne pas
	private class applyAttribute implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectAttribute selectedAttribute = attributList.getSelectedValue();
			VisibilityEnum visibility = (VisibilityEnum) visibilityList.getSelectedItem();
			
			String name = attributeNameTF.getText();
			String type ;
			
			if (attributeTypeTF.getText().length() > 0){
				type = attributeTypeTF.getText();
			}
			else {
				type = "void" ;
			}
			
			selectedAttribute.setVisibility(visibility);
			selectedAttribute.setName(name);
			selectedAttribute.setType(type);
			
			panAttributesRight.revalidate();
		}
	}

	private class addMethod implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			VisibilityEnum visibility = (VisibilityEnum) visibilityListMethods.getSelectedItem();
			String name = methodNameTF.getText();
			String type ;
			
			if (methodTypeTF.getText().length() > 0) {
				type = methodTypeTF.getText();
			}
			else {
				type = "void" ;
			}
			
			ObjectMethod objMethod = new ObjectMethod(visibility, name, type, parameters);

			dataMethod.addElement(objMethod);
			methodList.setModel(dataMethod);

			objClass.addMethod(objMethod);

			visibilityList.setSelectedIndex(0);
			methodNameTF.setText("");
			methodTypeTF.setText("");
			
			parametersList.removeAllItems();
			parameters.clear();
			
			panMethodsRight.revalidate();
		}
	}

	private class applyMethod implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ObjectMethod selectedMethod = methodList.getSelectedValue();
			String name = methodNameTF.getText();
			String type = methodTypeTF.getText();
			VisibilityEnum visibility = (VisibilityEnum) visibilityList.getSelectedItem();
			
			selectedMethod.setVisibility(visibility);
			selectedMethod.setName(name);
			selectedMethod.setType(type);
			
			panMethodsRight.revalidate();
 		}
	}

	private class deleteMethod implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			objClass.delMethod(methodList.getSelectedValue());
			dataMethod.removeElement(methodList.getSelectedValue());
			methodList.setModel(dataMethod);

			panMethodsRight.revalidate();
			methodList.clearSelection();

			clearMethod();
		}
	}

	private class addParameter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = panMANameTF.getText();
			String type = panMATypeTF.getText();
			
			ObjectAttribute parameter = new ObjectAttribute(
					VisibilityEnum.NULL, type,
					name);
			parameters.add(parameter);
			parametersList.addItem(parameter);

			panMATypeTF.setText("");
			panMANameTF.setText("");
		}
	}

	private class applyParameter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ObjectAttribute selectedParam = (ObjectAttribute) parametersList.getSelectedItem();
			String name = panMANameTF.getText();
			String type = panMATypeTF.getText();
			
			selectedParam.setName(name);
			selectedParam.setType(type);
			
			parametersList.revalidate();
			panMethodsRight.revalidate();
			
		}
	}

	private class delParameter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ObjectAttribute selectedParam = (ObjectAttribute) parametersList.getSelectedItem();
			
			parameters.remove(selectedParam);
			parametersList.removeItem(selectedParam);
			
			panMANameTF.setText("");
			panMATypeTF.setText("");
			
			parametersList.revalidate();
			panMethodsRight.revalidate();
		}
	}
 
	/**
	 * 
	 * @author Alexandre Fourgs ListSelectionListener pour le boutton delete et
	 *         apply dans l'onglet attribute. Si l'utilisateur à selectionner un
	 *         attribut dans la list, le boutton delete et apply sont
	 *         accessible, sinon non. Ca permet d'éviter les exceptions, et de
	 *         gêrer les modifications d'attributs.
	 */
	private class AttributeListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			ObjectAttribute attributeSelected = attributList.getSelectedValue();

			if (attributList.getSelectedIndex() != -1) {
				delete.setEnabled(true);
				applyModification.setEnabled(true);
				visibilityList.setSelectedItem(attributeSelected
						.getVisibility());
				attributeNameTF.setText(attributeSelected.getName());
				attributeTypeTF.setText(attributeSelected.getType());
				attributeGetters.setSelected(attributeSelected.getSetter());
				attributeSetters.setSelected(attributeSelected.getGetter());
			}
			else {
				delete.setEnabled(false);
				applyModification.setEnabled(false);
			}
		}
	}
	
	private class MethodListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e){
			ObjectMethod selectedMethod = methodList.getSelectedValue();
			parametersList.removeAllItems();
			
			if (methodList.getSelectedIndex() != -1){
				methodApply.setEnabled(true);
				methodDelete.setEnabled(true);
				//abstractJCB.setSelected(selectedMethod.get);
				methodTypeTF.setText(selectedMethod.getType());
				methodNameTF.setText(selectedMethod.getName());
				
				ArrayList<ObjectAttribute> parameters = selectedMethod.getParameter();
				
				for (int i = 0 ; i < parameters.size() ; i++){
					parametersList.addItem(parameters.get(i));
				}
			}
			else {
				methodApply.setEnabled(false);
				methodDelete.setEnabled(false);
			}
		}
	}
	
	private class AttributeTextFieldListener implements DocumentListener {

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
	
	private class MethodTextFieldListener implements DocumentListener {

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
			methodAdd.setEnabled(enable);
		}		
	}
}
