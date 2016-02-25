package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import textualuml.ObjectAbstractClass;
import textualuml.ObjectAbstractMethod;
import textualuml.ObjectAttribute;
import textualuml.ObjectMethod;
import textualuml.ObjectPackage;
import textualuml.VisibilityEnum;
import engine.Project;
import graphicuml.ClassDrawing;

/**
 * This class is for the JFrame for create and modify an abstract class.
 * @author Alexandre Fourgs
 *
 */
public class AbstractClassJFrame extends JFrame {
	private Project project;
	private ObjectAbstractClass abstractClass = new ObjectAbstractClass();
	
	private ClassDrawing draw ;
	JList<ObjectAbstractClass> abstractModifList ;
	DefaultListModel<ObjectAbstractClass> dataAbstractList;
	JPanel abstractPanel ;
	/*
	 * Onglets
	 */
	private JPanel general = new JPanel();
	private JPanel attributes = new JPanel();
	private JPanel methods = new JPanel();
	private JPanel pack = new JPanel();
	private JTabbedPane onglets = new JTabbedPane();
	
	/**
	 * Composants tout onglets
	 */
	private JPanel panSouth = new JPanel();
	private GridLayout glSouth = new GridLayout(1, 3);
	private JButton ok = new JButton("Ok");
	private JButton cancel = new JButton("Cancel");
	private JButton apply = new JButton("Apply");
	
	/**
	 * Component for onglet "General"
	 */
	private JLabel className = new JLabel("Name :");
	private JTextField nameTF = new JTextField();

	/**
	 * Component for onglet "Attributes"
	 */
	private JPanel panAttributesLeft = new JPanel();
	private JPanel panAttributesModif = new JPanel();
	private JLabel visibility = new JLabel("Visibility :");
	private JComboBox<VisibilityEnum> visibilityList = new JComboBox<VisibilityEnum>();
	private JLabel attributeName = new JLabel("Name :");
	private JTextField attributeNameTF = new JTextField();
	private JLabel attributeType = new JLabel("Type :");
	private JTextField attributeTypeTF = new JTextField();
	private JCheckBox attributeGetters = new JCheckBox("Getter");
	private JCheckBox attributeSetters = new JCheckBox("Setter");
	private JPanel panAttributesButton = new JPanel();
	private JButton add = new JButton("Add");
	private JButton delete = new JButton("Delete");
	private JButton applyModification = new JButton("Apply");
	private JPanel panAttributesRight = new JPanel();
	private JList<ObjectAttribute> attributesList = new JList<ObjectAttribute>() ;
	private DefaultListModel<ObjectAttribute> dataAttribute = new DefaultListModel<ObjectAttribute>();
	private JScrollPane jspAttribute = new JScrollPane(attributesList);
	private ArrayList<ObjectAttribute> parameters = new ArrayList<ObjectAttribute>();
	
	
	/**
	 * Component for onglet "Methods"
	 */
	private JPanel panMethodsLeft = new JPanel();
	private JPanel panMethodsModif = new JPanel();
	private JLabel abstractLabel = new JLabel("Abstract");
	private JCheckBox abstractJCB = new JCheckBox();
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
	private JList<ObjectMethod> methodsList = new JList<ObjectMethod>() ;
	private JScrollPane methodsJsp = new JScrollPane(methodsList);
	private DefaultListModel<ObjectMethod> methodsData = new DefaultListModel<ObjectMethod>();
	
	/**
	 * Components for onglet package
	 */
	
	private JComboBox<ObjectPackage> packageJCB = new JComboBox<ObjectPackage>() ;
	
	/**
	 * 
	 * @param project
	 * @param draw
	 * @param abstractModifList
	 * @param dataAbstractList
	 * @param abstractPanel
	 */
	public AbstractClassJFrame(Project project, ClassDrawing draw, JList<ObjectAbstractClass> abstractModifList, DefaultListModel<ObjectAbstractClass> dataAbstractList, JPanel abstractPanel) {
		super("Abstract Class Editor");
		this.project = project ;
		this.draw = draw ;
		this.abstractModifList = abstractModifList ;
		this.dataAbstractList = dataAbstractList ;
		this.abstractPanel = abstractPanel ;
		init();
	}
	
	public AbstractClassJFrame(Project project, ClassDrawing draw, JList<ObjectAbstractClass> abstractModifList, DefaultListModel<ObjectAbstractClass> dataAbstractList, JPanel abstractPanel, ObjectAbstractClass abstractClass) {
		super("Abstract Class Editor");
		this.project = project ;
		this.draw = draw ;
		this.abstractModifList = abstractModifList ;
		this.dataAbstractList = dataAbstractList ;
		this.abstractPanel = abstractPanel ;
		this.abstractClass = abstractClass ;
		initForExistingClass();
		init();
	}
	
	private void init() {
		Container pan = this.getContentPane() ;
		this.setSize(500, 350);
		this.setLocationRelativeTo(null);
		
		pan.setLayout(new BorderLayout());
		
		panSouth.setLayout(glSouth);
		panSouth.add(ok);
		panSouth.add(apply);
		panSouth.add(cancel);
		
		onglets.add("General", general);
		onglets.add("Attributes", attributes);
		onglets.add("Methods", methods);
		onglets.add("Package", pack);
		
		nameTF.setPreferredSize(new Dimension(150, 20));
		general.add(className);
		general.add(nameTF);

		
		
		for (int i = 0 ; i < project.getProjectProgram().getAllPackages().size() ; i++){
			packageJCB.addItem(project.getProjectProgram().getAllPackages().get(i));
		}

		initPanAttribute();
		initMethodPan();
		
		
		pack.add(packageJCB);
		
		pan.add(onglets);
		pan.add(panSouth, BorderLayout.SOUTH);

		listener();
		
		this.setVisible(true);
	}
	
	private void initForExistingClass(){
		nameTF.setText(abstractClass.getName());
		
		for (int i = 0 ; i < abstractClass.getAttributes().size() ; i++){
			dataAttribute.addElement(abstractClass.getAttributes().get(i));
		}
		
		for (int i = 0 ; i < abstractClass.getMethods().size() ; i++){
			methodsData.addElement(abstractClass.getMethods().get(i));
		}
		
		attributesList.setModel(dataAttribute);
		methodsList.setModel(methodsData);
		panAttributesRight.revalidate();
		panMethodsRight.revalidate();
		
		packageJCB.setSelectedItem(abstractClass.getObjPackage());
	}
	
	private void initPanAttribute() {
		attributes.setLayout(new GridLayout(1, 2));
		panAttributesLeft.setLayout(new GridLayout(2, 1));
		
		for (VisibilityEnum visibility : VisibilityEnum.values()) {
			visibilityList.addItem(visibility);
		}
		
		panAttributesModif.setLayout(new GridLayout(4, 2));
		panAttributesModif.add(visibility);
		panAttributesModif.add(visibilityList);
		panAttributesModif.add(attributeType);
		panAttributesModif.add(attributeTypeTF);
		panAttributesModif.add(attributeName);
		panAttributesModif.add(attributeNameTF);
		panAttributesModif.add(attributeGetters);
		panAttributesModif.add(attributeSetters);

		delete.setEnabled(false);
		applyModification.setEnabled(false);
		panAttributesButton.add(add);
		panAttributesButton.add(delete);
		panAttributesButton.add(applyModification);

		panAttributesLeft.add(panAttributesModif);
		panAttributesLeft.add(panAttributesButton);

		jspAttribute.setPreferredSize(new Dimension(240, 250));
		panAttributesRight.add(jspAttribute);

		attributes.add(panAttributesLeft);
		attributes.add(panAttributesRight);
		
	}
	
	private void initMethodPan() {
		for (VisibilityEnum visibility : VisibilityEnum.values()) {
			visibilityListMethods.addItem(visibility);
		}
		
		methods.setLayout(new GridLayout(1, 2));
		panMethodsLeft.setLayout(new GridLayout(3, 1));
		panMethodsModif.setLayout(new GridLayout(4, 2));
		panMethodsModif.add(abstractLabel);
		panMethodsModif.add(abstractJCB);
		panMethodsModif.add(visibilityMethods);
		panMethodsModif.add(visibilityListMethods);
		panMethodsModif.add(methodName);
		panMethodsModif.add(methodNameTF);
		panMethodsModif.add(methodType);
		panMethodsModif.add(methodTypeTF);
		
		panMethodAttributes.setLayout(new GridLayout(2, 1));
		panMethodAttributes.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
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

		methodsJsp.setPreferredSize(new Dimension(240, 250));
		panMethodsRight.add(methodsJsp);
		
		methods.add(panMethodsLeft);
		methods.add(panMethodsRight);
		
	}
	
	private void listener() {
		
		// Listeners des trois boutons du bas
		ok.addActionListener(new OkButton());
		apply.addActionListener(new ApplyButton());
		cancel.addActionListener(new CancelButton());
		
		// Listeners de l'onglet attributs
		add.addActionListener(new AddAttributeButton());
		delete.addActionListener(new DeleteAttributeButton());
		applyModification.addActionListener(new ApplyAttributeButton());
		attributesList.addListSelectionListener(new AttributeListListener());
		
		// Listeners de l'onglet methodes
		abstractJCB.addItemListener(new AbstractCheckListener());
		parameterAdd.addActionListener(new AddParamButton());
		parameterApply.addActionListener(new ApplyParamButton());
		parameterDelete.addActionListener(new DeleteParamButton());
		methodAdd.addActionListener(new AddMethodButton());
		methodApply.addActionListener(new ApplyMethodButton());
		methodDelete.addActionListener(new DeleteMethodButton());
		methodsList.addListSelectionListener(new MethodListListener());
		
	}
	
	private void clearAttribute() {
		attributeNameTF.setText("");
		attributeTypeTF.setText("");
		attributeGetters.setSelected(false);
		attributeSetters.setSelected(false);
		visibilityList.setSelectedIndex(0);
	}
	
	private void clearMethod() {
		methodNameTF.setText("");
		methodTypeTF.setText("");
		abstractJCB.setSelected(false);
		visibilityListMethods.setSelectedItem(0);
		panMATypeTF.setText("");
		panMANameTF.setText("");
		parametersList.removeAllItems();
		parameters.clear();
	}
	
	private class OkButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String name = nameTF.getText() ;
			ObjectPackage pack = (ObjectPackage) packageJCB.getSelectedItem();
			
			abstractClass.setName(name);
			
			if (!pack.getAllClass().contains(abstractClass)){
				pack.addClass(abstractClass);
				
				if (abstractClass.getObjPackage() != null) {
					abstractClass.getObjPackage().getAllClass().remove(abstractClass);
				}
				
				abstractClass.setObjPackage(pack);
			}
			
			abstractClass.setObjectWidthAndHeight();
			draw.repaint();
			
			if (!dataAbstractList.contains(abstractClass)){
				dataAbstractList.addElement(abstractClass);
				abstractModifList.setModel(dataAbstractList);
				abstractPanel.revalidate();
			}
			
			dispose() ;
		}
	}
	
	private class ApplyButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String name = nameTF.getText() ;
			ObjectPackage pack = (ObjectPackage) packageJCB.getSelectedItem();
			
			abstractClass.setName(name);
			abstractClass.setObjPackage(pack);
			
			abstractClass.setObjectWidthAndHeight();
			draw.repaint();
		}
	}
	
	private class CancelButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			dispose() ;
		}
	}
	
	private class AddAttributeButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			VisibilityEnum visibility = (VisibilityEnum) visibilityList
					.getSelectedItem();
			ObjectAttribute objAttribute = new ObjectAttribute(visibility,
					attributeTypeTF.getText(), attributeNameTF.getText());

			if (attributeGetters.isSelected()) {
				objAttribute.setGetter(attributeGetters.isSelected());
				ObjectMethod getter = new ObjectMethod(VisibilityEnum.PUBLIC,
						"get" + attributeNameTF.getText(),
						attributeTypeTF.getText(),
						new ArrayList<ObjectAttribute>());
				abstractClass.addMethod(getter);
				methodsData.addElement(getter);
			}
			if (attributeSetters.isSelected()) {
				objAttribute.setSetter(attributeSetters.isSelected());
				ObjectAttribute parameter = new ObjectAttribute(
						VisibilityEnum.NULL, attributeTypeTF.getText(),
						attributeNameTF.getText());
				ArrayList<ObjectAttribute> parameterList = new ArrayList<ObjectAttribute>();
				parameterList.add(parameter);
				ObjectMethod setter = new ObjectMethod(VisibilityEnum.PUBLIC,
						"set" + attributeNameTF.getText(), "void",
						parameterList);
				abstractClass.addMethod(setter);
				methodsData.addElement(setter);
			}

			abstractClass.addAttribute(objAttribute);

			dataAttribute.addElement(objAttribute);
			attributesList.setModel(dataAttribute);
			methodsList.setModel(methodsData);
			panMethodsRight.revalidate();
			panAttributesRight.revalidate();
			
			clearAttribute();
		}
	}
	
	private class ApplyAttributeButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectAttribute selectedAttribute = attributesList.getSelectedValue();
			selectedAttribute.setGetter(attributeGetters.isSelected());
			selectedAttribute.setSetter(attributeSetters.isSelected());
			selectedAttribute.setName(attributeNameTF.getText());
			selectedAttribute.setType(attributeTypeTF.getText());
			
			panAttributesRight.revalidate();
			
		}
	}
	
	private class DeleteAttributeButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			abstractClass.delAttribute(attributesList.getSelectedValue());
			dataAttribute.removeElement(attributesList.getSelectedValue());

			attributesList.setModel(dataAttribute);
			panAttributesRight.revalidate();
			attributesList.clearSelection();
			
			clearAttribute();
		}
	}
	
	private class AddParamButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String type = panMATypeTF.getText();
			String name = panMANameTF.getText();
			ObjectAttribute parameter = new ObjectAttribute(VisibilityEnum.NULL, type, name);
			
			parameters.add(parameter);
			parametersList.addItem(parameter);
			
			panMATypeTF.setText("");
			panMANameTF.setText("");
		}
	}
	
	private class ApplyParamButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String name = panMANameTF.getText();
			String type = panMATypeTF.getText();
			ObjectAttribute selectedParam = (ObjectAttribute) parametersList.getSelectedItem();
			
			parameters.remove(selectedParam);
			parametersList.removeItem(selectedParam);
			
			selectedParam.setName(name);
			selectedParam.setType(type);
			
			parameters.add(selectedParam);
			parametersList.addItem(selectedParam);
			
			
			
		}
	}
	
	private class DeleteParamButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectAttribute selectedParam = (ObjectAttribute) parametersList.getSelectedItem();
			parameters.remove(selectedParam);
			parametersList.removeItem(selectedParam);
			
			panMANameTF.setText("");
			panMATypeTF.setText("");
		}
	}
	
	private class AddMethodButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectMethod objMethod ;
			String name = methodNameTF.getText();
			String type = methodTypeTF.getText();
			VisibilityEnum visibility = (VisibilityEnum) visibilityListMethods.getSelectedItem();
			
			if (abstractJCB.isSelected()){
				objMethod = new ObjectAbstractMethod(name, type, parameters);
			}
			else {
				objMethod = new ObjectMethod(visibility, name, type, parameters);
			}
			
			methodsData.addElement(objMethod);
			methodsList.setModel(methodsData);
			abstractClass.addMethod(objMethod);
			
			panMethodsRight.revalidate();
			
			clearMethod();
		}
	}
	
	private class ApplyMethodButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectMethod selectedMethod = methodsList.getSelectedValue();
			String name = methodNameTF.getText();
			String type = methodTypeTF.getText();
			
			selectedMethod.setName(name);
			selectedMethod.setType(type);
			
			panMethodsRight.revalidate();
		}
	}
	
	private class DeleteMethodButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			abstractClass.delMethod(methodsList.getSelectedValue());
			methodsData.removeElement(methodsList.getSelectedValue());
			methodsList.setModel(methodsData);
			
			panMethodsRight.revalidate();
			methodsList.clearSelection();
			
			clearMethod();
		}
	}
	
	private class AttributeListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e){
			ObjectAttribute selectedAttribute = attributesList.getSelectedValue();
			
			if (attributesList.getSelectedIndex() != -1){
				applyModification.setEnabled(true);
				delete.setEnabled(true);
				visibilityList.setSelectedItem(selectedAttribute.getVisibility());
				attributeNameTF.setText(selectedAttribute.getName());
				attributeTypeTF.setText(selectedAttribute.getType());
				attributeGetters.setSelected(selectedAttribute.getGetter());
				attributeSetters.setSelected(selectedAttribute.getSetter());
			}
			else {
				applyModification.setEnabled(false);
				delete.setEnabled(false);
			}
		}
	}
	
	private class MethodListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e){
			ObjectMethod selectedMethod = methodsList.getSelectedValue();
			parametersList.removeAllItems();
			
			if (methodsList.getSelectedIndex() != -1){
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
	
	private class AbstractCheckListener implements ItemListener {
		public void itemStateChanged(ItemEvent e){
			if (abstractJCB.isSelected()){
				visibilityListMethods.setVisible(false);
			}
			else {
				visibilityListMethods.setVisible(true);
			}
		}
	}
	
}
