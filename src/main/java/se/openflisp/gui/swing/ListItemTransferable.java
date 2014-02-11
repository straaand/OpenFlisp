package se.openflisp.gui.swing;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public static class ListItemTransferable implements Transferable {

    public static final DataFlavor LIST_ITEM_DATA_FLAVOR = new DataFlavor(ComponentView.class, "java/ListItem");
    private ComponentView listItem;

    public ListItemTransferable(ComponentView listItem) {
        this.listItem = listItem;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{LIST_ITEM_DATA_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(LIST_ITEM_DATA_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

        return listItem;

    }
}
