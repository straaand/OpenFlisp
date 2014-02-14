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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import se.openflisp.sls.Component;
import se.openflisp.sls.Input;
import se.openflisp.sls.Output;
import se.openflisp.sls.Signal;
import se.openflisp.sls.event.ComponentListener;

/**
 * A logical cricuit of Components.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 */
public class Circuit {

	/**
	 * Set of components that the circuit contains.
	 */
	private Set<Component> components = new HashSet<Component>();
	
	/**
	 * Recursively adds a Component and its connected components to the Circuit.
	 * 
	 * @param component		base component that should be added
	 */
	public void addComponent(Component component) {
		if (component == null) {
			throw new IllegalArgumentException("Component can not be null");
		}
		if (this.components.add(component)) {
			component.getEventDelegator().addListener(this.connectionHandler);
			for (Input input : component.getInputs()) {
				if (input.isConnected()) {
					this.addComponent(input.getConnection().getOwner());
				}
			}
			for (Output output : component.getOutputs()) {
				for (Input input : output.getConnections()) {
					this.addComponent(input.getOwner());
				}
			}
		}
	}
	
	/**
	 * Gets all the Components in the Circuit.
	 * 
	 * @return unmodifiable set of all the Components
	 */
	public Set<Component> getComponents() {
		return Collections.unmodifiableSet(this.components);
	}
	
	/**
	 * Listener that will add components that have been connected to one of 
	 * the Circuits already existing components.
	 */
	private final ComponentListener connectionHandler = new ComponentListener() {
		@Override
		public void onSignalDisconnection(Input input, Output output) {}
		
		@Override
		public void onSignalConnection(Input input, Output output) {
			Circuit.this.addComponent(input.getOwner());
			Circuit.this.addComponent(output.getOwner());
		}
		
		@Override
		public void onSignalChange(Component component, Signal signal) {}
	};
}
