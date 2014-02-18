package se.openflisp.gui.swing;
import java.awt.Component;
import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.TransferHandler;


public class ListTransferHandler extends TransferHandler {


	@Override
	public boolean canImport(TransferSupport support) {
		return (support.getComponent() instanceof JButton) && support.isDataFlavorSupported(ListItemTransferable.LIST_ITEM_DATA_FLAVOR);
	}

	@Override
	public boolean importData(TransferSupport support) {
		System.out.println("importdata");
		boolean accept = false;
		if (canImport(support)) {
			try {
				ComponentView t =  (ComponentView) support.getTransferable();
				Object value = t.getTransferData(ListItemTransferable.LIST_ITEM_DATA_FLAVOR);
				System.out.println(value);
				if (value instanceof ComponentView) {
					Component component = support.getComponent();
					System.out.println(component);
					if (component instanceof JLabel) {
						//((JButton)component).setText(((ComponentView)value).getImage());
					}
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
		return accept;
	}

	@Override
	public int getSourceActions(JComponent c) {
		
		return DnDConstants.ACTION_COPY_OR_MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		System.out.println("c: " + c);
		Transferable t = null;
		if (c instanceof JList) {
			JList list = (JList) c;
			Object value = list.getSelectedValue();
			
			System.out.println("value: " + value);
			
			if (value instanceof ComponentView) {
				ComponentView li = (ComponentView) value;
				t = new ListItemTransferable(li);
			}
		}
		System.out.println("t: " + t);
		return t;
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		System.out.println("ExportDone OSKAR ÄR FAN KUNG, Danie och Anton är helt OK också men Oskar är bäst");
		// Here you need to decide how to handle the completion of the transfer,
		if(source instanceof CircuitBord){
			System.out.println("Du har släppt i CircuitBord");

		}
		// should you remove the item from the list or not...
	}

}

	
	
	
	
	
	
	
	
	
	
	
