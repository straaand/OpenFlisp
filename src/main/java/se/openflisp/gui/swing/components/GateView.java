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
 * A Viewier for gates
 * 
 * @author Daniel Svensson <daniel@dsit.se>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GateView extends ComponentView {
	
	/**
	 * In order to make the input and output panels transparent we need to put them in diffrent jpanels
	 */
	private JPanel identifierPanel;
	private JPanel	inputPanel;
	private	 JPanel	outputPanel;
	private JLabel	identifier;
	private List<OutputSignal>	outputSignals;
	private List<InputSignal>	inputSignals;

	/**
	 * Creates a new gateview given a component
	 * @param component		component to create a view for
	 */
	public GateView(Component component) {
		super(component);
		setSize(new Dimension(componentSize,componentSize/2));
		setLayout(new BorderLayout());
		this.identifier = new JLabel("", JLabel.CENTER);
		this.identifierPanel = new JPanel();
		this.identifierPanel.setLayout(new FlowLayout());
		this.identifierPanel.add(identifier);
		
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
		
		this.identifier.setOpaque(true);
		this.identifier.setBorder(BorderFactory.createLineBorder(Color.black));
		this.identifier.setBackground(Color.WHITE);
		this.identifier.setMaximumSize(new Dimension(componentSize/2 , componentSize));
		
		
		this.inputSignals = new ArrayList<InputSignal>();
		this.outputSignals = new ArrayList<OutputSignal>();
		
		this.inputPanel = new JPanel();
		this.inputPanel.setLayout(new BoxLayout(this.inputPanel, BoxLayout.Y_AXIS));
		
		this.outputPanel = new JPanel();
		this.outputPanel.setLayout(new BoxLayout(this.outputPanel, BoxLayout.Y_AXIS));
		
		this.inputPanel.setPreferredSize(new Dimension(componentSize/4, componentSize/2));
		this.outputPanel.setPreferredSize(new Dimension(componentSize/4,componentSize/2));
		
		for(Output output : component.getOutputs()) {
			OutputSignal out = new OutputSignal(output);
			this.outputSignals.add(out);
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
		
		
		this.outputPanel.setBorder(new LineBorder(Color.BLACK));
		this.inputPanel.setOpaque(false);
		this.inputPanel.setBorder(new LineBorder(Color.BLACK));
		this.outputPanel.setOpaque(false);
		
		
		add(inputPanel, BorderLayout.WEST);
		add(identifier, BorderLayout.CENTER);
		add(outputPanel, BorderLayout.EAST);
	}

	/**
	 * Add additional signal to this gate
	 * @param signal
	 */
	public void addSignal(Signal signal) {
		if (signal instanceof Output) 
			this.outputSignals.add(new OutputSignal(signal));
		if (signal instanceof Input)
			this.inputSignals.add(new InputSignal(signal));
	}
}
