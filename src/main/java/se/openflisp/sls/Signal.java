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
 * A base class for all outgoing and incoming signals to a Component.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 * @see Input
 * @see Output
 */
public abstract class Signal {

	/**
	 * The pin identifier that the Signal has with its owner.
	 */
	private final String identifier;
	
	/**
	 * The component which owns the signal.
	 */
	private final Component owner;
	
	/**
	 * Current state the signal is in, which defaults to floating.
	 */
	private Signal.State state = Signal.State.FLOATING;
	
	/**
	 * Creates a new Signal.
	 * 
	 * @param identifier	pin identifier used in the owner
	 * @param owner			the owning Component of this signal
	 */
	public Signal(String identifier, Component owner) {
		this.owner      = owner;
		this.identifier = identifier;
	}
	
	/**
	 * Gets the pin identifier which the Signal has with its owner.
	 * 
	 * @return pin		identifier used by owner
	 */
	public String getIdentifier() {
		return this.identifier;
	}
	
	/**
	 * Gets the Component which owns the Signal.
	 * 
	 * @return a component which owns the signal
	 */
	public Component getOwner() {
		return this.owner;
	}
	
	/**
	 * Gets the current state of the Signal.
	 * 
	 * @return current state of the Signal
	 */
	public Signal.State getState() {
		return this.state;
	}
	
	/**
	 * Changes the state of the Signal.
	 * 
	 * @param state		which state the Signal should change to
	 */
	protected void setState(Signal.State state) {
		if (!this.state.equals(state)) {
			this.state = state;
			this.owner.getEventDelegator().onSignalChange(this.owner, this);
		}
	}
	
	/**
	 * Connect two signals with each other.
	 * 
	 * @param signal	a signal which the connection should be made with
	 * @throws IllegalArgumentException if the signal connection already existed
	 * @throws IllegalArgumentException if the two signal types can not connect
	 */
	public abstract void connect(Signal signal);
	
	/**
	 * Disconnect two signals from each other.
	 * 
	 * @param signal	a signal which if found should be disconnected
	 * @throws IllegalArgumentException if the signal connection don't exists
	 * @throws IllegalArgumentException if the two signal types can not disconnect
	 */
	public abstract void disconnect(Signal signal);
	
	/**
	 * Checks if this signal is connected or not.
	 * 
	 * @return true if its connected, false otherwise
	 */
	public abstract boolean isConnected();
	
	/**
	 * Enumeration of the different states a Signal can be in.
	 * 
	 * @author Anton Ekberg <anton.ekberg@gmail.com>
	 * @version 1.0
	 */
	public enum State {
		/**
		 * Logical zero (false).
		 */
		LOW, 
		
		/**
		 * Logical one (true).
		 */
		HIGH, 
		
		/**
		 * Undefined state, floating between low and high.
		 */
		FLOATING
	}
}
