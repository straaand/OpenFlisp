package se.openflisp.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.lang.*;

import javax.swing.*;

public class OpenFlispComponents extends JPanel{
	//variables
	private JList componentList;	//ComponentView to fill the JPanel
	
	//Constructor
	public OpenFlispComponents () {
		//Initiate the list
		componentListFillerGates();
		
		//Enable drag and drop
		componentList.setCellRenderer(new Renderer());
		componentList.setDragEnabled(true);
        componentList.setTransferHandler(new ListTransferHandler());
		
        //add the componentList to the JPanel
        this.add(componentList);
	}
	
	/**
	 * Fills the JList with all the gates 
	 * 
	 */
	private void componentListFillerGates() {
		
		
		componentList = new JList (new ComponentView[] {ComponentFactory.createViewFromComponent(null)});	//TODO testPhrase
		// TODO Auto-generated method stub
		
	}

	public class Renderer implements ListCellRenderer {
		public Component getListCellRendererComponent(
				JList list, ComponentView value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			JButton btn = new JButton(new ImageIcon(value.image));
			return btn;
			
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
