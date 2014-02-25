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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import se.openflisp.sls.Component;
import se.openflisp.sls.Input;
import se.openflisp.sls.Output;
import se.openflisp.sls.Signal;
import se.openflisp.gui.swing.components.ComponentView;
import se.openflisp.sls.component.NotGate;
import se.openflisp.sls.component.*;

/**	
 * A gateView will paint a gate
 * 
 * @author Daniel Svensson <daniel@dsit.se>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GateView extends ComponentView {

	//JPanel for identifier, this is the main body of our component
	private JPanel identifierPanel;

	// JLabel to fill identifierPanel
	private JLabel	identifier;

	// Panels to fill Input and Output signals
	private JPanel	inputPanel;
	private	 JPanel	outputPanel;

	// Lists with Input and Output signals
	private List<OutputSignal>	outputSignals;
	private List<InputSignal>	inputSignals;

	/*
	 * Creates a new GateView
	 * @param	Component	The component to link with this view
	 */
	public GateView(Component component) {
		super(component);

		// Scale this Gate
		setSize(new Dimension(componentSize,componentSize/2));
		setLayout(new BorderLayout());

		//Center JLabel
		this.identifier = new JLabel("", JLabel.CENTER);
		this.identifierPanel = new JPanel();
		this.identifierPanel.setLayout(new FlowLayout());

		//Add the JLabel to our IdentifierPanel
		this.identifierPanel.add(identifier);

		//Check instance and change identifier accordingly 
		if (component instanceof NotGate){
			this.identifier.setText("=1");
		}		
		else if (component  instanceof ConstantGate) {
			this.identifier.setText("1");
		}	
		else if (component instanceof OrGate) {
			this.identifier.setText(">");
		}
		else if ( (component instanceof AndGate) || (component instanceof NandGate) ) {
			this.identifier.setText("&");
		}

		this.identifierPanel.setOpaque(true);

		// Make a black border around the gate
		this.identifierPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.identifierPanel.setBackground(Color.WHITE);
		this.identifierPanel.setMaximumSize(new Dimension(componentSize/2 , componentSize));

		// Instansiate the lists
		this.inputSignals = new ArrayList<InputSignal>();
		this.outputSignals = new ArrayList<OutputSignal>();

		/*
		 * Instantiate Input- and Output- panels, create a BoxLayout in order to place signals
		 * in center of Y-axis
		 */
		this.inputPanel = new JPanel();
		this.inputPanel.setLayout(new BoxLayout(this.inputPanel, BoxLayout.Y_AXIS));

		this.outputPanel = new JPanel();
		this.outputPanel.setLayout(new BoxLayout(this.outputPanel, BoxLayout.Y_AXIS));

		this.inputPanel.setPreferredSize(new Dimension(componentSize/4, componentSize/2));
		this.outputPanel.setPreferredSize(new Dimension(componentSize/4,componentSize/2));

		// Add outputs
		for(Output output : component.getOutputs()) {
			OutputSignal out = new OutputSignal(output);
			this.outputSignals.add(out);

			// Vertical glue will make the component center in Y-AXIS
			this.outputPanel.add( Box.createVerticalGlue() );
			out.setMaximumSize(new Dimension(componentSize/4,componentSize/5));
			this.outputPanel.add(out);
			this.outputPanel.add( Box.createVerticalGlue() );
		}

		for(Input input : component.getInputs()) {
			InputSignal in = new InputSignal(input);
			this.inputSignals.add(in);
			this.inputPanel.add( Box.createVerticalGlue() );
			in.setMaximumSize(new Dimension(componentSize/4,componentSize/5));
			this.inputPanel.add(in);
			this.inputPanel.add( Box.createVerticalGlue() );
		}

		//Make Input- and Output- opaque
		this.inputPanel.setOpaque(false);
		this.outputPanel.setOpaque(false);

		//Add all panels
		add(inputPanel, BorderLayout.WEST);
		add(identifier, BorderLayout.CENTER);
		add(outputPanel, BorderLayout.EAST);
	}
	/**
	 * Add a new signal to our GateVie
	 * @param signal
	 */
	public void addSignal(Signal signal) {
		if (signal instanceof Output) 
			this.outputSignals.add(new OutputSignal(signal));
		if (signal instanceof Input)
			this.inputSignals.add(new InputSignal(signal));
	}
}
