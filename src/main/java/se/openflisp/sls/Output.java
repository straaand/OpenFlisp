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
package se.openflisp.sls;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Model of a signal Output to a Component.
 * 
 * An Input can be connected to one Output, while an Output can be connected to many Inputs.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 * @see Input
 */
public class Output extends Signal {

	/**
	 * One-to-many relationship between Output and Inputs.
	 */
	private Set<Input> connections = new HashSet<Input>();
	
	/**
	 * {@inheritDoc}
	 */
	public Output(String identifier, Component owner) {
		super(identifier, owner);
	}

	/**
	 * Gets all Inputs that are connected to the Output
	 * 
	 * @return unmodifiable set of all connections to this Output
	 */
	public Set<Input> getConnections() {
		return Collections.unmodifiableSet(this.connections);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean connect(Signal signal) {
		if (!(signal instanceof Input)) {
			throw new IllegalArgumentException("An output can only be connected to an input.");
		}
		if (this.connections.add((Input) signal)) {
			signal.connect(this);
			this.getOwner().getEventDelegator().onSignalConnection((Input) signal, this);
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean disconnect(Signal signal) {
		if (!(signal instanceof Input)) {
			throw new IllegalArgumentException("An output can only be connected to an input.");
		}
		if (this.connections.remove(signal)) {
			signal.disconnect(this);
			this.getOwner().getEventDelegator().onSignalDisconnection((Input) signal, this);
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected() {
		return !this.connections.isEmpty();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("%s[Output#%s] is %s", this.getOwner(), this.getIdentifier(), this.getState());
	}
}
