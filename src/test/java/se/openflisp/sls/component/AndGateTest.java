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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import se.openflisp.sls.Signal;
import se.openflisp.sls.component.Gate;
import se.openflisp.sls.component.GateTest;
import se.openflisp.sls.event.ComponentEventDelegator;

import org.junit.Test;
import org.junit.Before;

public class AndGateTest extends GateTest {

	private Gate andGate;
	
	@Override
	protected Gate getInstance(String identifier) {
		return new AndGate(identifier);
	}

	@Override
	protected Gate getInstance(String identifier, ComponentEventDelegator delegator) {
		return new AndGate(identifier, delegator);
	}
	
	@Before
	public void before() {
		super.setup();
		andGate = new AndGate(gateName);
	}

	/* HIGH INPUT */
	
	@Test
	public void testEvalOutputHIGH_TwoHighInputs() {
		Signal.State[] states = {Signal.State.HIGH, Signal.State.HIGH};
		helpEvaluatingOutput(states, Signal.State.HIGH, andGate);
	}
	
	@Test
	public void testEvalOutputHIGH_ThreeHighInputs() {
		Signal.State[] states = {Signal.State.HIGH, Signal.State.HIGH, Signal.State.HIGH};
		helpEvaluatingOutput(states, Signal.State.HIGH, andGate);
	}
	
	/* LOW INPUT */
	
	@Test
	public void testEvalOutputLOW_TwoLowInputs() {
		Signal.State[] states = {Signal.State.LOW, Signal.State.LOW};
		helpEvaluatingOutput(states, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testEvalOutputLOW_ThreeLowInputs() {
		Signal.State[] states = {Signal.State.LOW, Signal.State.LOW, Signal.State.LOW};
		helpEvaluatingOutput(states, Signal.State.LOW, andGate);
	}
	
	/* FLOATING INPUTS */
	
	@Test
	public void testEvalOutputFLOATING_TwoFloatingInputs() {
		Signal.State[] states = {Signal.State.FLOATING, Signal.State.FLOATING};
		helpEvaluatingOutput(states, Signal.State.FLOATING, andGate);
	}
	
	@Test
	public void testEvaluatingOutputFLOATING_noInput() {
		assertThat(andGate.getInputs().size(), is(0));
		assertEquals(Signal.State.FLOATING, andGate.evaluateOutput());
		assertThat(andGate.getInputs().size(), is(0));
	}
	
	/* VARIABLE INPUTS - LOW OUTPUT */
	
	@Test
	public void testEvalOutputLOW_LowANDHigh() {
		Signal.State[] states = {Signal.State.LOW, Signal.State.HIGH};
		helpEvaluatingOutput(states, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testEvalOutputLOW_HighANDLow() {
		Signal.State[] states = {Signal.State.HIGH, Signal.State.LOW};
		helpEvaluatingOutput(states, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testEvalOutputLOW_LowANDFloat() {
		Signal.State[] states = {Signal.State.LOW, Signal.State.FLOATING};
		helpEvaluatingOutput(states, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testEvalOutputLOW_FloatANDLow() {
		Signal.State[] states = {Signal.State.FLOATING, Signal.State.LOW};
		helpEvaluatingOutput(states, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testEvalOutputLOW_HighANDFloatANDLow() {
		Signal.State[] states = {Signal.State.HIGH, Signal.State.FLOATING, Signal.State.LOW};
		helpEvaluatingOutput(states, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testEvalOutputLOW_FloatANDLowANDHigh() {
		Signal.State[] states = {Signal.State.FLOATING, Signal.State.LOW, Signal.State.HIGH};
		helpEvaluatingOutput(states, Signal.State.LOW, andGate);
	}
	
	@Test
	public void testEvalOutputLOW_LowANDHighANDFloat() {
		Signal.State[] states = {Signal.State.LOW, Signal.State.HIGH, Signal.State.FLOATING};
		helpEvaluatingOutput(states, Signal.State.LOW, andGate);
	}
	
	/* VARIABLE INPUTS - FLOATING OUTPUT */
	
	@Test
	public void testEvalOutputFLOATING_HighANDFloat() {
		Signal.State[] states = {Signal.State.HIGH, Signal.State.FLOATING};
		helpEvaluatingOutput(states, Signal.State.FLOATING, andGate);
	}
	
	@Test
	public void testEvalOutputFLOATING_FloatANDHigh() {
		Signal.State[] states = {Signal.State.FLOATING, Signal.State.HIGH};
		helpEvaluatingOutput(states, Signal.State.FLOATING, andGate);
	}
	
	/* ONE INPUT - FLOATING OUTPUT */
	
	@Test
	public void testEvalOutputFLOATING_OneFloatingInput() {
		Signal.State[] states = {Signal.State.FLOATING};
		helpEvaluatingOutput(states, Signal.State.FLOATING, andGate);
	}
	
	@Test
	public void testEvalOutputFLOATING_OneLowInput() {
		Signal.State[] states = {Signal.State.LOW};
		helpEvaluatingOutput(states, Signal.State.FLOATING, andGate);
	}
	
	@Test
	public void testEvalOutputFLOATING_OneHighInput() {
		Signal.State[] states = {Signal.State.HIGH};
		helpEvaluatingOutput(states, Signal.State.FLOATING, andGate);
	}
}
