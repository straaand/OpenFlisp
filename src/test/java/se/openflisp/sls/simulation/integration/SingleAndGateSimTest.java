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

import org.junit.Before;
import org.junit.Test;

import se.openflisp.sls.component.*;
import se.openflisp.sls.*;
/**
 * Tests the functions of an AndGate in a Circuit simulation.
 * 
 * @author Pär Svedberg <rockkuf@gmail.com>
 * @version 1.0
 */
public class SingleAndGateSimTest extends SingleGateSimTest {
	
	private AndGate andGate;

	@Before
	public void setup() {
		super.setup();
		gateID = "AndGate";
		andGate = new AndGate(gateID);
	}
	
	/* LOW OUTPUT */
	
	@Test
	public void testAndGateOutputLOW_TwoLow() {
		ConstantGate[] inputGates = {constantLow, constantLow};
		helpSimulate(inputGates, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testAndGateOutputLOW_LowANDHigh() {
		ConstantGate[] inputGates = {constantLow, constantHigh};
		helpSimulate(inputGates, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testAndGateOutputLOW_HighANDLow() {
		ConstantGate[] inputGates = {constantHigh, constantLow};
		helpSimulate(inputGates, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testAndGateOutputLOW_FloatANDLow() {
		ConstantGate[] inputGates = {constantFloating, constantLow};
		helpSimulate(inputGates, Signal.State.LOW, andGate);
	}
	
	// This one got a few fails
	@Test
	public void testAndGateOutputLOW_LowANDFloat() {
		ConstantGate[] inputGates = {constantLow, constantFloating};
		helpSimulate(inputGates, Signal.State.LOW, andGate);
	}
	
	/* HIGH OUTPUT */
	
	@Test
	public void testAndGateOutputHIGH_TwoHigh() {
		ConstantGate[] inputGates = {constantHigh, constantHigh};
		helpSimulate(inputGates, Signal.State.HIGH, andGate);
	}
	
	/* FLOATING OUTPUT */
	
	@Test
	public void testAndGateOutputFLOATING_NoInputs() {
		helpSimulate(new Gate[0], Signal.State.FLOATING, andGate);
	}
	
	@Test
	public void testAndGateOutputFLOATING_OneLowInput() {
		ConstantGate[] inputGates = {constantLow};
		helpSimulate(inputGates, Signal.State.FLOATING, andGate);
	}
	
	@Test
	public void testAndGateOutputFLOATING_OneHighInput() {
		ConstantGate[] inputGates = {constantHigh};
		helpSimulate(inputGates, Signal.State.FLOATING, andGate);
	}
	
	@Test
	public void testAndGateOutputFLOATING_FloatANDHigh() {
		ConstantGate[] inputGates = {constantFloating, constantHigh};
		helpSimulate(inputGates, Signal.State.FLOATING, andGate);
	}
	
	@Test
	public void testAndGateOutputFLOATING_HighANDFloat() {
		ConstantGate[] inputGates = {constantHigh, constantFloating};
		helpSimulate(inputGates, Signal.State.FLOATING, andGate);
	}
	
	@Test
	public void testAndGateOutputFLOATING_TwoFloat() {
		ConstantGate[] inputGates = {constantFloating, constantFloating};
		helpSimulate(inputGates, Signal.State.FLOATING, andGate);
	}
	
}
