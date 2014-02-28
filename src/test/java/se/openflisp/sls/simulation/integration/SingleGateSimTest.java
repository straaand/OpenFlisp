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

import static org.junit.Assert.*;

import org.junit.Before;

import se.openflisp.sls.Signal;
import se.openflisp.sls.component.*;

/**
 * Abstract class for test classes simulating single gates to extend.
 *  
 * @author Pär Svedberg <rockkuf@gmail.com>
 * @version 1.0
 */
public abstract class SingleGateSimTest extends SimulationTest {
	public String gateID;
	
	@Before
	public void setup() {
		super.setup();
		
	}
	
	/**
	 * Help method for simulation of a single gates' output. Every gate passed to
	 * the method is added to a circuit, connected and then simulated. 
	 * Checks after simulation in a circuit if the output Signal.State of the given 
	 * gateToSimulate is equal to the given value of expectedOutput.
	 * 
	 * @param fromOutputs		Each Output from a Gate[] is connected to 
	 * 							an Input of the gateToSimulate.  
	 * @param expectedOutput	A Signal.State value to compare for equality with 
	 * 							the value of gateToSimulates Output.
	 * @param gateToSimulate	The Gate which Output is to be tested
	 */
	public void helpSimulate(Gate[] fromOutputs, Signal.State expectedOutput, Gate gateToSimulate) {
		ComponentSimListener testListener = new ComponentSimListener();
		gateToSimulate.getEventDelegator().addListener(testListener);
		
		connectGates(circuit, fromOutputs, gateToSimulate);
		waitForSignalChange(gateToSimulate);
		if (DEBUG_MESSAGES)
			System.out.println("\tonSignalChange calls: " + String.valueOf(testListener.changedTimes()));
		assertEquals(expectedOutput, gateToSimulate.getOutput().getState());
	}
}
