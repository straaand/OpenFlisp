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
package se.openflisp.sls.simulation;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import se.openflisp.sls.Component;
import se.openflisp.sls.Output;
import se.openflisp.sls.component.Gate;

/**
 * Builds a 2D-aware Circuit given component definitions and connections.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 */
public class Circuit2DBuilder {

	/**
	 * Lookup table for all Components that should be built.
	 */
	private Map<String, Component> components = new HashMap<String, Component>();
	
	/**
	 * Position for all Components in the lookup table.
	 */
	private Map<String, Point> positions = new HashMap<String, Point>();
	
	/**
	 * Map over all Output pins that is connected to a Input pin.
	 */
	private Map<PinIdentifier, Set<PinIdentifier>> connections = new HashMap<PinIdentifier, Set<PinIdentifier>>();
	
	/**
	 * Adds a component to the build queue.
	 * 
	 * @param component		the component to be added
	 * @param position		the position where the component should be placed
	 */
	public void addComponent(Component component, Point position) {
		if (component == null || position == null) {
			throw new IllegalArgumentException("Neither component nor position can be null");
		}
		if (this.components.containsKey(component.getIdentifier())) {
			throw new IllegalArgumentException(
				"Found duplicated component identifier " + component.getIdentifier()
			);
		}
		this.components.put(component.getIdentifier(), component);
		this.positions.put(component.getIdentifier(), position);
	}
	
	/**
	 * Adds a connection between two components.
	 * 
	 * @param inputComponent	identifier for the component on the input side
	 * @param inputPin			identifier for the pin on the input component
	 * @param output			identifier for output component and pin, separated by a :
	 */
	public void addConnection(String inputComponent, String inputPin, String output) {
		if (output.contains(":")) {
			String[] outputParts = output.split(":", 2);
			this.addConnection(inputComponent, inputPin, outputParts[0], outputParts[1]);
		} else {
			this.addConnection(inputComponent, inputPin, output, Gate.OUTPUT);
		}
	}
	
	/**
	 * Adds a connection between two components.
	 * 
	 * @param inputComponent	identifier for the component on the input side
	 * @param inputPin			identifier for the pin on the input component
	 * @param outputComponent	identifier for the component on the output side
	 * @param outputPin			identifier for the pin on the output component
	 */
	public void addConnection(String inputComponent, String inputPin, String outputComponent, String outputPin) {
		PinIdentifier input = new PinIdentifier(inputComponent, inputPin);
		PinIdentifier output = new PinIdentifier(outputComponent, outputPin);
		if (!this.connections.containsKey(output)) {
			this.connections.put(output, new HashSet<PinIdentifier>());
		}
		this.connections.get(output).add(input);
	}
	
	/**
	 * Checks if a Component exists in the builder.
	 * 
	 * @param componentIdentifier	identifier for a component
	 * @return true if the component is in the lookup table, false otherwise
	 */
	public boolean hasComponent(String componentIdentifier) {
		return this.components.containsKey(componentIdentifier);
	}
	
	/**
	 * Gets a component from the lookup table.
	 * 
	 * @param pin		identifier for a pin on a component
	 * @return a component with a correct identifier
	 */
	protected Component lookupComponent(PinIdentifier pin) {
		if (!this.components.containsKey(pin.componentIdentifier))  {
			throw new IllegalStateException("Unknown component " + pin.componentIdentifier);
		}
		return this.components.get(pin.componentIdentifier);
	}
	
	/**
	 * Builds a circuit given the components and connections defined with
	 * {@link #addComponent(Component, Point)} and {@link #addConnection(String, String, String)}.
	 * 
	 * @return a 2D-aware Circuit.
	 */
	public Circuit2D build() {
		Circuit2D circuit = new Circuit2D();
		for (Component component : this.components.values()) {
			circuit.addComponent(component, this.positions.get(component.getIdentifier()));
		}
		for (PinIdentifier outputPin : this.connections.keySet()) {
			Output output = this.lookupComponent(outputPin).getOutput(outputPin.signalIdentifier);
			for (PinIdentifier inputPin : this.connections.get(outputPin)) {
				output.connect(this.lookupComponent(inputPin).getInput(inputPin.signalIdentifier));
			}
		}
		return circuit;
	}
	
	/**
	 * Helper struct for managing identifiers for specific pins on a Component.
	 * 
	 * @author Anton Ekberg <anton.ekberg@gmail.com>
	 * @version 1.0
	 */
	protected class PinIdentifier {
		
		/**
		 * Identifier for a Component.
		 */
		public final String componentIdentifier;
		
		/**
		 * Identifier for a Signal on the Component.
		 */
		public final String signalIdentifier;
		
		/**
		 * Creates a new PinIdentifier from identifiers of a component and a signal.
		 * 
		 * @param componentIdentifier	identifier for a component
		 * @param signalIdentifier		identifier for a signal
		 */
		protected PinIdentifier(String componentIdentifier, String signalIdentifier) {
			this.componentIdentifier = componentIdentifier;
			this.signalIdentifier = signalIdentifier;
		}
		
		/**
		 * {@inheritDoc}
		 */
		public int hashCode() {
			int hash = 5;
			hash += 89 * this.componentIdentifier.hashCode();
			hash += 89 * this.signalIdentifier.hashCode();
			return hash;
		}
	}
}
