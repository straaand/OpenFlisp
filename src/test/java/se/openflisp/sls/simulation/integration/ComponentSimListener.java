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
package se.openflisp.sls.simulation.integration;

import se.openflisp.sls.Component;
import se.openflisp.sls.Signal;
import se.openflisp.sls.event.ComponentAdapter;

/**
 * Listener used for simulation tests of components.
 * @author Pär Svedberg <rockkuf@gmail.com>
 * @version 1.0
 *
 */
public class ComponentSimListener extends ComponentAdapter {
	private boolean outputChanged = false;
	private int changedCounter = 0;
	
	@Override
	public void onSignalChange(Component component, Signal signal) {
			this.outputChanged = true;
			changedCounter++;
	}
	
	public boolean isOutputChanged() {
		return outputChanged;
	}
	
	public int changedTimes() {
		return changedCounter;
	}

}
