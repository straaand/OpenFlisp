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
 * Tests the functions of a single NotGate in a Circuit simulation.
 * 
 * @author Pär Svedberg <rockkuf@gmail.com>
 *
 */
public class SingleNotGateSimTest extends SingleGateSimTest {
	private NotGate notGate;
	private String notGateID;
	
	@Before
	public void setup() {
		super.setup();
		notGateID = "NotGate";
		notGate = new NotGate(notGateID);
	}
	
	/* LOW OUTPUT */
	
	@Test
	public void testNotGateOutputLOW_OneHighInput() {
		ConstantGate[] inputGates = {constantHigh};
		helpSimulate(inputGates, Signal.State.LOW, notGate);
	}
	
	/* HIGH OUTPUT */
	
	@Test
	public void testNotGateOutputHIGH_OneLowInput() {
		ConstantGate[] inputGates = {constantLow};
		helpSimulate(inputGates, Signal.State.HIGH, notGate);
	}
	
	/* FLOATING OUTPUT */
	
	@Test
	public void testNotGateOutputFLOATING_NoInput() {
		helpSimulate(new Gate[0], Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_OneFloatingInput() {
		ConstantGate[] inputGates = {constantFloating};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_TwoFloating() {
		ConstantGate[] inputGates = {constantFloating, constantFloating};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_TwoHigh() {
		ConstantGate[] inputGates = {constantHigh, constantHigh};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_TwoLow() {
		ConstantGate[] inputGates = {constantLow, constantLow};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_HighLow() {
		ConstantGate[] inputGates = {constantHigh, constantLow};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_LowHigh() {
		ConstantGate[] inputGates = {constantLow, constantHigh};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_LowFloat() {
		ConstantGate[] inputGates = {constantLow, constantFloating};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_FloatLow() {
		ConstantGate[] inputGates = {constantFloating, constantLow};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_HighFloat() {
		ConstantGate[] inputGates = {constantHigh, constantFloating};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
	
	@Test
	public void testNotGateOutputFLOATING_FloatHigh() {
		ConstantGate[] inputGates = {constantFloating, constantHigh};
		helpSimulate(inputGates, Signal.State.FLOATING, notGate);
	}
}
