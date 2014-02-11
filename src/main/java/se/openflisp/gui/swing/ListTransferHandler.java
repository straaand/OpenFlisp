package se.openflisp.gui.swing;
import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;


public class ListTransferHandler extends TransferHandler {


	@Override
	public boolean canImport(TransferSupport support) {
		return (support.getComponent() instanceof JButton) && support.isDataFlavorSupported(ListItemTransferable.LIST_ITEM_DATA_FLAVOR);
	}

	@Override
	public boolean importData(TransferSupport support) {
		boolean accept = false;
		if (canImport(support)) {
			try {
				ComponentView t =  (ComponentView) support.getTransferable();
				Object value = t.getTransferData(ListItemTransferable.LIST_ITEM_DATA_FLAVOR);
				if (value instanceof ComponentView) {
					Component component = support.getComponent();
					if (component instanceof JButton) {
						((JButton)component).setText(((ComponentView)value).getImage());
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
		Transferable t = null;
		if (c instanceof Transferable) {
			JList list = (JList) c;
			Object value = list.getSelectedValue();
			if (value instanceof ComponentView) {
				ComponentView li = (ComponentView) value;
				t = new ListItemTransferable(li);
			}
		}
		return t;
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		System.out.println("ExportDone");
		// Here you need to decide how to handle the completion of the transfer,
		// should you remove the item from the list or not...
	}
}
