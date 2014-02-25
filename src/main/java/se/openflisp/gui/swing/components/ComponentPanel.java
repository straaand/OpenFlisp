/*
 * Copyright (C) 2014- See AUTHORS file.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.openflisp.gui.swing.components;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;


import se.openflisp.sls.Component;
import se.openflisp.sls.component.AndGate;
import se.openflisp.sls.component.NotGate;

/**	
 * Component for showing all components and enabling drag and drop creation.
 * 
 * @author Daniel Svensson <daniel@dsit.se>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ComponentPanel extends JPanel {
	
	// All components to show
	//TODO Add more
	ComponentView andGate;
	ComponentView notGate;
	
	/**
	 * Creates the component panel
	 */
	public ComponentPanel() {
		setLayout(new GridBagLayout());
		
		andGate = new GateView(new AndGate("AndGate"));
		notGate = new GateView(new NotGate("NotGate"));
		andGate.setMaximumSize(new Dimension(ComponentView.componentSize,2));
		notGate.setMaximumSize(new Dimension(ComponentView.componentSize,ComponentView.componentSize/2));
		
		add( Box.createVerticalGlue() );
		add(andGate);
		add( Box.createVerticalGlue() );
		add(notGate);
		add( Box.createVerticalGlue() );
		/**
		 * Enable drag and drop listener, by sending a string to the reciever
		 */
		this.notGate.ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, new DragGestureListener() {
			
			@Override
			public void dragGestureRecognized(DragGestureEvent event) {
				try {
					GateView view = (GateView)getComponentAt(event.getDragOrigin());
					if (view.component instanceof AndGate)
						event.startDrag(ComponentPanel.this.notGate.ds.DefaultMoveDrop, new StringSelection("AndGate"));
					else if (view.component instanceof NotGate)
						event.startDrag(ComponentPanel.this.notGate.ds.DefaultMoveDrop, new StringSelection("NotGate"));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
