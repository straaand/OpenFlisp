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

import se.openflisp.sls.Signal;
import se.openflisp.sls.component.*;

/**
 * Tests the functions of a single NandGate in a Circuit simulation.
 * 
 * @author Pär Svedberg <rockkuf@gmail.com>
 *
 */
public class SingleNandGateSimTest extends SingleGateSimTest {
	private NandGate nandGate;
	
	@Before
	public void setup() {
		super.setup();
		gateID = "NandGate";
		nandGate = new NandGate(gateID);
	}

	/* LOW OUTPUT */
	
	@Test
	public void testNandGateOutputLOW_TwoHighInputs() {
		ConstantGate[] inputGates = {constantHigh, constantHigh};
		helpSimulate(inputGates, Signal.State.LOW, nandGate);
	}
	
	/* HIGH OUTPUT */
	
	@Test
	public void testNandGateOutputHIGH_TwoLowInputs() {
		ConstantGate[] inputGates = {constantLow, constantLow};
		helpSimulate(inputGates, Signal.State.HIGH, nandGate);
	}
	
	@Test
	public void testNandGateOutputHIGH_LowNANDHigh() {
		ConstantGate[] inputGates = {constantLow, constantHigh};
		helpSimulate(inputGates, Signal.State.HIGH, nandGate);
	}
	
	@Test
	public void testNandGateOutputHIGH_HighNANDLow() {
		ConstantGate[] inputGates = {constantHigh, constantLow};
		helpSimulate(inputGates, Signal.State.HIGH, nandGate);
	}
	
	@Test
	public void testNandGateOutputHIGH_LowNANDFloat() {
		ConstantGate[] inputGates = {constantLow, constantFloating};
		helpSimulate(inputGates, Signal.State.HIGH, nandGate);
	}
	
	@Test
	public void testNandGateOutputHIGH_FloatNANDLow() {
		ConstantGate[] inputGates = {constantFloating, constantLow};
		helpSimulate(inputGates, Signal.State.HIGH, nandGate);
	}
	
	/* FLOATING OUTPUT */
	
	@Test
	public void testNandGateOutputFLOATING_NoInputs() {
		helpSimulate(new Gate[0], Signal.State.FLOATING, nandGate);
	}
	
	@Test
	public void testNandGateOutputFLOATING_OneFloatingInput() {
		ConstantGate[] inputGates = {constantFloating};
		helpSimulate(inputGates, Signal.State.FLOATING, nandGate);
	}
	
	@Test
	public void testNandGateOutputFLOATING_OneLowInput() {
		ConstantGate[] inputGates = {constantLow};
		helpSimulate(inputGates, Signal.State.FLOATING, nandGate);
	}
	
	@Test
	public void testNandGateOutputFLOATING_OneHighInput() {
		ConstantGate[] inputGates = {constantHigh};
		helpSimulate(inputGates, Signal.State.FLOATING, nandGate);
	}
	
	@Test
	public void testNandGateOutputFLOATING_TwoFloatingInputs() {
		ConstantGate[] inputGates = {constantFloating, constantFloating};
		helpSimulate(inputGates, Signal.State.FLOATING, nandGate);
	}
	
	@Test
	public void testNandGateOutputFLOATING_FloatNANDHigh() {
		ConstantGate[] inputGates = {constantFloating, constantHigh};
		helpSimulate(inputGates, Signal.State.FLOATING, nandGate);
	}
	
	@Test
	public void testNandGateOutputFLOATING_HighNANDFloat() {
		ConstantGate[] inputGates = {constantHigh, constantFloating};
		helpSimulate(inputGates, Signal.State.FLOATING, nandGate);
	}

}
