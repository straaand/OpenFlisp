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
import se.openflisp.sls.annotation.SourceComponent;
import se.openflisp.sls.event.ComponentAdapter;
import se.openflisp.sls.event.ComponentListener;
import se.openflisp.sls.event.ListenerContext;

/**
 * A logical circuit of Components.
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
	 * Thread that will evaluate the components if needed.
	 */
	private final CircuitSimulation simulationThread = new CircuitSimulation(this);
	
	/**
	 * Gets the Circuit simulation handler thread.
	 * 
	 * @return the circuit simulation thread
	 */
	public CircuitSimulation getSimulation() {
		return this.simulationThread;
	}
	
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
			component.getEventDelegator().addListener(this.connectionHandler, ListenerContext.MODEL);
			component.getEventDelegator().addListener(this.simulationThread.signalHandler, ListenerContext.MODEL);
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
	 * Gets all the source Components in the Circuit.
	 * 
	 * @return set of all the source Components
	 */
	public Set<Component> getSourceComponents() {
		Set<Component> components = new HashSet<Component>();
		for (Component component : this.getComponents()) {
			if (component.getClass().isAnnotationPresent(SourceComponent.class)) {
				components.add(component);
			}
		}
		return components;
	}
	
	/**
	 * Listener that will add components that have been connected to one of 
	 * the Circuits already existing components.
	 */
	private final ComponentListener connectionHandler = new ComponentAdapter() {
		@Override
		public void onSignalConnection(Input input, Output output) {
			Circuit.this.addComponent(input.getOwner());
			Circuit.this.addComponent(output.getOwner());
		}
	};
}
