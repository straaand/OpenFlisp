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
package se.openflisp.sls.util;

import java.util.Collection;

import se.openflisp.sls.Signal;

/**
 * Helper class for collections of Signals.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 */
public class SignalCollection {

	/**
	 * Checks if a collection of Signals contains a specific state.
	 * 
	 * @param signals	a collection of signals to check
	 * @param state		the state that should be checked
	 * @return true if the state is in the collection, false otherwise
	 * @throws IllegalArgumentException if one of the parameters is null
	 */
	public static boolean containsState(Collection<? extends Signal> signals, Signal.State state) {
		if (signals == null || state == null) {
			throw new IllegalArgumentException("Requires a non-null collection and state.");
		}
		for (Signal signal : signals) {
			if (signal.getState().equals(state)) {
				return true;
			}
		}
		return false;
	}
}
