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
package se.openflisp.sls.component;

import se.openflisp.sls.*;
import se.openflisp.sls.event.ComponentEventDelegator;
import java.util.Collection;

/**
 * Base class for all outgoing and incoming signals to a Component.
 * 
 * @author Hannes Elvemyr <hannes88@gmail.com>
 * @version 1.0
 */
public class NotGate extends Gate {
	
	/**
	 * {@inheritDoc}
	 */
	public NotGate(String identifier) {
		super(identifier);
	}

	/**
	 * {@inheritDoc}
	 */
	public NotGate(String identifier, ComponentEventDelegator delegator) {
		super(identifier, delegator);
	}

	/**
	 * {@inheritDoc}
	 */
	public Signal.State evaluateOutput() {
		Collection<Input> inputCollection = getInputs();
		if(inputCollection.size() > 1) {
			return Signal.State.FLOATING;
		}

		for(Input i : inputCollection) {
			Signal.State currentState = i.getState();
			if(currentState == Signal.State.LOW) {
				return Signal.State.HIGH;
			} else if(currentState == Signal.State.HIGH) {
				return Signal.State.LOW;
			}
		}
		return Signal.State.FLOATING;
	}

}
