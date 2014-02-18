package se.openflisp.gui.swing;

import javax.swing.JFrame;
import javax.swing.JList;

public class ComponentFrame extends JFrame{
	private JList componentList;
	
	protected ComponentFrame() {
		this.componentList = new JList();
		listSetup();
		
	}
	
	private void listSetup () {
		
	}
}
