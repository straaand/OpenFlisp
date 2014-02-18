package se.openflisp.gui.swing;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class CircuitBordTransferHandler extends TransferHandler{

	
	
	public boolean canImport(TransferHandler.TransferSupport support){
		System.out.println("Can inmport 1");
		return true;
	}
	
	public boolean importData (JComponent comp, Transferable t){
		System.out.println("import Data 1");
		return true;
	}

	@Override
	public int getSourceActions(JComponent c) {	
		return DnDConstants.ACTION_COPY_OR_MOVE;
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		System.out.println("ExportDone OSKAR ÄR FAN KUNG, men i detta fall kom det upp en ruta");
		// Here you need to decide how to handle the completion of the transfer,
		if(source instanceof CircuitBord){
			System.out.println("Du har släppt i CircuitBord");

		}
		// should you remove the item from the list or not...
	}
	
	
}
