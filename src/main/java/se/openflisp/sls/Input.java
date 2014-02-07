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

/**
 * Model of a signal Input to a Component.
 * 
 * An Input can be connected to one Output, while an Output can be connected to many Inputs.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 * @see Output
 */
public class Input extends Signal {

	/**
	 * One-to-one relationship between the Input and Output.
	 */
	private Output connection;
	
	/**
	 * {@inheritDoc}
	 */
	public Input(String identifier, Component owner) {
		super(identifier, owner);
	}

	/**
	 * Gets the Output that is connected to the Input.
	 * 
	 * @return the output that is connected to the input, or null if not connected
	 */
	public Output getConnection() {
		return this.connection;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean connect(Signal signal) {
		if (!(signal instanceof Output)) {
			throw new IllegalArgumentException("An input can only be connected to an output.");
		}
		if (this.isConnected()) {
			if (!this.connection.equals(signal)) {
				throw new IllegalArgumentException("This input is already connected to " + this.connection);
			}
			return false;
		}
		this.connection = (Output) signal;
		signal.connect(this);
		this.getOwner().getEventDelegator().onSignalConnection(this, this.connection);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean disconnect(Signal signal) {
		if (!(signal instanceof Output)) {
			throw new IllegalArgumentException("An input can only be connected to an output.");
		}
		if (this.isConnected()) {
			if (!this.connection.equals(signal)) {
				throw new IllegalArgumentException("This input is not connected to " + signal);
			}
			this.connection = null;
			signal.disconnect(this);
			this.getOwner().getEventDelegator().onSignalDisconnection(this, (Output) signal);
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected() {
		return this.connection != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("%s[Input#%s] is %s", this.getOwner(), this.getIdentifier(), this.getState());
	}
}
