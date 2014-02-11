package se.openflisp.gui.swing;

import java.awt.Component;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ComponentView implements Transferable{
	Image image;
	Component component;
	
	public ComponentView (Component component, Image image) {
		this.image = image;
		this.component = component;
		//TODO we need a component to make this class (we need to know the structure of the component)
		
	}
	public Image getImage ( ) {
		return this.image;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
