package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import engine.Project;
import textualuml.Link;
import textualuml.ObjectClass;
import textualuml.ObjectInterface;
import graphicuml.ClassDrawing;

public class LinksJFrame extends JFrame {
	private ImageIcon linkIcon ;
	private ClassDrawing drawClass;
	
	private JPanel panelContainer = new JPanel();
	private GridLayout glPanels = new GridLayout(3, 1);
	
	private JPanel top = new JPanel();
	private GridLayout glTop = new GridLayout(1, 3);
	private boolean implementation ;
	private JComboBox<ObjectInterface> interfaceImplements = new JComboBox<ObjectInterface>();
	private JComboBox<Object> classFrom = new JComboBox<Object>() ;
	private JComboBox<Object> classTo = new JComboBox<Object>() ;
	private JLabel linkLabel ;
	
	private JPanel middle = new JPanel();
	private GridLayout glMiddle = new GridLayout(1, 2);
	private JButton add = new JButton("Add");
	private JButton del = new JButton("Delete");
	
	private JPanel bottom = new JPanel();
	private JList<Link> links = new JList<Link>();
	private DefaultListModel<Link> dataLinks = new DefaultListModel<Link>();
	private JScrollPane jsp = new JScrollPane(links, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	private JPanel button = new JPanel();
	private JButton ok = new JButton("Ok");
	
	
	
	public LinksJFrame(String name, ClassDrawing drawClass, String icon, boolean implementation) {
		super(name + " Editor") ;
		linkIcon = new ImageIcon(icon);
		linkLabel = new JLabel(linkIcon);
		this.drawClass = drawClass;
		this.implementation = implementation;
		init() ;
	}
	
	private void init(){
		Container pan = this.getContentPane();
		this.setSize(500, 250);
		this.setLocationRelativeTo(null);
		
		
		top.setLayout(glTop);
		top.add(classFrom);
		top.add(linkLabel);
		if (implementation){
			top.add(interfaceImplements);
		}
		else {
			top.add(classTo);
		}		
		
		middle.setLayout(glMiddle);
		middle.add(add);
		del.setEnabled(false);
		middle.add(del);
		
		bottom.add(jsp);
		
		button.add(ok);
			
		panelContainer.setLayout(glPanels);
		panelContainer.add(top);
		panelContainer.add(middle);
		panelContainer.add(bottom);
		
		pan.add(panelContainer);
		pan.add(button, BorderLayout.SOUTH);	
		
		links.addListSelectionListener(new linksListener());
		ok.addActionListener(new OkListener());
		
		this.setVisible(true);
	}
	
	public JComboBox<Object> getClassFrom (){
		return classFrom;
	}
	
	public JComboBox<Object> getClassTo(){
		return classTo ;
	}
	
	public JComboBox<ObjectInterface> getInterfaceImplements(){
		return interfaceImplements;
	}
	
	public JButton getAdd(){
		return add ;
	}
	
	public JButton getDel(){
		return del ;
	}
	
	public DefaultListModel<Link> getDataLinks () {
		return dataLinks ;
	}
	
	public JList<Link> getLinks () {
		return links ;
	}
	
	public JPanel getBottom (){
		return bottom ;
	}
	
	private class linksListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e){
			
			if(links.getSelectedIndex() != -1){
				del.setEnabled(true);
			}
			else {
				del.setEnabled(false);
			}
		}
	}
	
	private class OkListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			drawClass.repaint();
			dispose();
		}
	}
}
