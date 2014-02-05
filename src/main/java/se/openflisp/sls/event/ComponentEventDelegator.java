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
package se.openflisp.sls.event;

import java.util.LinkedList;
import java.util.List;

import se.openflisp.sls.Component;
import se.openflisp.sls.Input;
import se.openflisp.sls.Output;
import se.openflisp.sls.Signal;

/**
 * Delegates events from Components to passive listeners.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 */
public class ComponentEventDelegator implements ComponentListener {

	/**
	 * All listeners that should be notified when a event happens.
	 */
	private List<ComponentListener> listeners = new LinkedList<ComponentListener>();
	
	/**
	 * Adds a listener that should be notified when a event happens.
	 * 
	 * @param listener	listener that should be notified when a event happens
	 * @return true if the listener was added, false otherwise
	 */
	public boolean addListener(ComponentListener listener) {
		return this.listeners.add(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
  	@Override
	public void onSignalChange(Component component, Signal signal) {
		for (ComponentListener listener : this.listeners) {
			listener.onSignalChange(component, signal);
		}
	}

  	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onSignalConnection(Input input, Output output) {
		for (ComponentListener listener : this.listeners) {
			listener.onSignalConnection(input, output);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onSignalDisconnection(Input input, Output output) {
		for (ComponentListener listener : this.listeners) {
			listener.onSignalDisconnection(input, output);
		}
	}
}
