package gui ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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

import textualuml.ObjectAbstractMethod;
import textualuml.ObjectAttribute;
import textualuml.ObjectInterface;
import textualuml.ObjectPackage;
import textualuml.VisibilityEnum;
import engine.Project;
import graphicuml.ClassDrawing;


/**
 * 
 * @author Alexandre Fourgs & Florian Astori Classe correspondant à la JFrame de
 *         modification et création d'une classe.
 */
public class InterfaceJFrame extends JFrame {
	private Project project;
	private ObjectInterface objInterface = new ObjectInterface();
	
	private ClassDrawing drawClass;
	JList<ObjectInterface> interfaceModifList ;
	DefaultListModel<ObjectInterface> dataInterfaceList ;
	JPanel interfacePanel ;

	/**
	 * Onglets
	 */
	private JPanel general = new JPanel();
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
	 * Component for onglet "Methods"
	 */
	private JPanel panMethodsLeft = new JPanel();
	private JPanel panMethodsModif = new JPanel();
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
	protected JList<ObjectAbstractMethod> methodList = new JList<ObjectAbstractMethod>();
	protected JScrollPane jspMethod = new JScrollPane(methodList);
	protected DefaultListModel<ObjectAbstractMethod> dataMethod = new DefaultListModel<ObjectAbstractMethod>();


	public InterfaceJFrame(Project project, ClassDrawing drawClass, JList<ObjectInterface> interfaceModifList, DefaultListModel<ObjectInterface> dataInterfaceList, JPanel interfacePanel) {
		super("Interface Editor");
		this.project = project;
		this.drawClass = drawClass;
		this.interfaceModifList = interfaceModifList ;
		this.dataInterfaceList = dataInterfaceList ;
		this.interfacePanel = interfacePanel ;
		init();
	}
	
	public InterfaceJFrame(Project project, ClassDrawing drawClass, JList<ObjectInterface> interfaceModifList, DefaultListModel<ObjectInterface> dataInterfaceList, JPanel interfacePanel, ObjectInterface objInterface) {
		super("Interface Editor");
		this.project = project;
		this.drawClass = drawClass;
		this.interfaceModifList = interfaceModifList ;
		this.dataInterfaceList = dataInterfaceList ;
		this.interfacePanel = interfacePanel ;
		this.objInterface = objInterface ;
		initForExistingInterface();
		init();
	}

	private void init() {
		Container pan = this.getContentPane();
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);

		pan.setLayout(new BorderLayout());
		
		southButton();
		
		onglets.add("General", general);
		onglets.add("Abstract Methods", methods);
		onglets.add("Package", classPackage);

		nameTF.setPreferredSize(new Dimension(150, 20));
		general.add(className);
		general.add(nameTF);


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
	
	private void initForExistingInterface(){
		nameTF.setText(objInterface.getName());
		
		for(int i = 0 ; i < objInterface.getAbstractMethods().size() ; i++){
			dataMethod.addElement((ObjectAbstractMethod) objInterface.getAbstractMethods().get(i));
		}
		
		methodList.setModel(dataMethod);
		panMethodsRight.revalidate();
		
		packagesList.setSelectedItem(objInterface.getObjPackage());
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
	
	
	void panMethod () {		
		methods.setLayout(new GridLayout(1, 2));
		panMethodsLeft.setLayout(new GridLayout(3, 1));

		panMethodsModif.setLayout(new GridLayout(3, 2));
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

		methodAdd.setEnabled(false);
		methodApply.setEnabled(false);
		methodDelete.setEnabled(false);
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
	 * This method clear all component from the tab "Method"
	 */
	void clearMethod () {
		methodTypeTF.setText("");
		methodNameTF.setText("");
	}
	
	/**
	 * This method add listener.
	 */
	private void listener() {
		ok.addActionListener(new OkButton());
		apply.addActionListener(new ApplyButton());
		cancel.addActionListener(new CancelButton());
		
		methodAdd.addActionListener(new AddMethod());
		methodNameTF.getDocument().addDocumentListener(new MethodNameTextFieldListener());
		methodApply.addActionListener(new ApplyMethod());
		methodDelete.addActionListener(new DelMethod());
		parameterAdd.addActionListener(new AddParameter());
		parameterApply.addActionListener(new ApplyParameter());
		parameterDelete.addActionListener(new DelParameter());
		methodList.addListSelectionListener(new MethodListListener());
		
		/*attributeNameTF.getDocument().addDocumentListener(new AttributeTextFieldListener());
		attributeTypeTF.getDocument().addDocumentListener(new AttributeTextFieldListener());*/
	}
	
	private class OkButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String name = nameTF.getText();
			ObjectPackage pack = (ObjectPackage) packagesList.getSelectedItem();
			
			objInterface.setName(name);
			
			if (!pack.getAllInterface().contains(objInterface)){
				pack.addInterface(objInterface);
				
				if(objInterface.getObjPackage() != null) {
					objInterface.getObjPackage().getAllInterface().remove(objInterface);
				}
				
				objInterface.setObjPackage(pack);
			}
			
			
			objInterface.setObjectWidthAndHeight();
			drawClass.repaint();
			
			if (!dataInterfaceList.contains(objInterface)){
				dataInterfaceList.addElement(objInterface);
				interfaceModifList.setModel(dataInterfaceList);
				interfacePanel.revalidate();
			}
			
			dispose();
		}
	}
	
	private class ApplyButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String name = nameTF.getText();
			ObjectPackage actualPack = objInterface.getObjPackage();
			ObjectPackage newPack = (ObjectPackage) packagesList.getSelectedItem();
			
			if (newPack != actualPack){
				actualPack.getAllInterface().remove(objInterface);
				newPack.addInterface(objInterface);
			}
			
			objInterface.setName(name);
			objInterface.setObjPackage(newPack);
			
			
			objInterface.setObjectWidthAndHeight();
			drawClass.repaint();
		}
	}
	
	private class CancelButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			dispose() ;
		}
	}
	
	private class AddParameter implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String type = panMATypeTF.getText();
			String name = panMANameTF.getText();
			
			ObjectAttribute parameter = new ObjectAttribute(VisibilityEnum.NULL, type, name);
			
			parametersList.addItem(parameter);
			parameters.add(parameter);
			
			panMATypeTF.setText("");
			panMANameTF.setText("");
		}
	}
	
	private class ApplyParameter implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectAttribute selectedParam = (ObjectAttribute) parametersList.getSelectedItem();
			
			selectedParam.setType(panMATypeTF.getText());
			selectedParam.setName(panMANameTF.getText());
			//parametersList.removeItem(selectedParam);
		}
	}
	
	private class DelParameter implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectAttribute selectedParam = (ObjectAttribute) parametersList.getSelectedItem();
			
			parametersList.removeItem(selectedParam);
			parameters.remove(selectedParam);
			
			panMATypeTF.setText("");
			panMANameTF.setText("");
		}
	}
	
	// Fonctionne correctement
	private class AddMethod implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String type = methodTypeTF.getText();
			String name = methodNameTF.getText();
			
			ObjectAbstractMethod method = new ObjectAbstractMethod(name, type, parameters);
			
			objInterface.addAbstractMethod(method);
			dataMethod.addElement(method);
			methodList.setModel(dataMethod);
			panMethodsRight.revalidate();
			
			
			clearMethod();
		}
	}
	
	private class ApplyMethod implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectAbstractMethod abstractMethod = methodList.getSelectedValue();
			String name = methodNameTF.getText();
			String type = methodTypeTF.getText();
			
			abstractMethod.setName(name);
			abstractMethod.setType(type);
			
			panMethodsRight.revalidate();
			
		}
	}
	
	private class DelMethod implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectAbstractMethod method = methodList.getSelectedValue();
			
			dataMethod.removeElement(method);
			methodList.setModel(dataMethod);
			
			objInterface.delAbstractMethod(method);
			
		}
	}
	
	private class MethodListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e){
			ObjectAbstractMethod selectedMethod = methodList.getSelectedValue();
			
			if (methodList.getSelectedIndex() != -1){
				methodApply.setEnabled(true);
				methodDelete.setEnabled(true);
				
				methodNameTF.setText(selectedMethod.getName());
				methodTypeTF.setText(selectedMethod.getType());
				
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
	
	private class MethodNameTextFieldListener implements DocumentListener {

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
		
		private void changeButtonAdd(DocumentEvent e){
			boolean enable = e.getDocument().getLength() > 0 ;
			methodAdd.setEnabled(enable);
		}
		
	}
	
}