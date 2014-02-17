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

import static org.junit.Assert.*;

import se.openflisp.sls.Signal;
import se.openflisp.sls.component.Gate;
import se.openflisp.sls.component.GateTest;
import se.openflisp.sls.event.ComponentEventDelegator;
import org.junit.Test;
import org.junit.Before;

public class OrGateTest extends GateTest {

	private Gate orGate;

	@Override
	protected Gate getInstance(String identifier) {
		return new OrGate(identifier);
	}

	@Override
	protected Gate getInstance(String identifier, ComponentEventDelegator delegator) {
		return new OrGate(identifier, delegator);
	}

	@Before
	public void before() {
		super.setup();
		orGate = new OrGate(gateName);
	}

	/* HIGH INPUT */

	@Test
	public void testEvaluatingOutputHIGH() {
		Signal.State[] state = {Signal.State.HIGH};
		helpEvaluatingOutputs(state, Signal.State.HIGH, orGate);
	}

	@Test
	public void testEvaluatingOutputHIGH_TwoInputs() {
		Signal.State[] states = {Signal.State.HIGH, Signal.State.HIGH};
		helpEvaluatingOutputs(states, Signal.State.HIGH, orGate);
	}

	/* LOW INPUT */

	@Test
	public void testEvaluatingOutputLOW() {
		Signal.State[] state = {Signal.State.LOW};
		helpEvaluatingOutputs(state, Signal.State.LOW, orGate);
	}

	@Test
	public void testEvaluatingOutputLOW_TwoInputs() {
		Signal.State[] states = {Signal.State.LOW, Signal.State.LOW};
		helpEvaluatingOutputs(states, Signal.State.LOW, orGate);
	}

	/* FLOATING INPUT */

	@Test
	public void testEvaluatingOutputFLOATING() {
		Signal.State[] state = {Signal.State.FLOATING};
		helpEvaluatingOutputs(state, Signal.State.FLOATING, orGate);
	}

	@Test
	public void testEvaluatingOutputFLOATING_TwoInputs() {
		Signal.State[] states = {Signal.State.FLOATING, Signal.State.FLOATING};
		helpEvaluatingOutputs(states, Signal.State.FLOATING, orGate);
	}

	/* VARIABLE INPUT - HIGH OUTPUT */

	@Test
	public void testEvaluatingOutputHIGHandLOW() {
		Signal.State[] states = {Signal.State.HIGH, Signal.State.LOW};
		helpEvaluatingOutputs(states, Signal.State.HIGH, orGate);
	}
	
	@Test
	public void testEvaluatingOutputLOWandHigh() {
		Signal.State[] states = {Signal.State.LOW, Signal.State.HIGH};
		helpEvaluatingOutputs(states, Signal.State.HIGH, orGate);
	}
	
	@Test
	public void testEvaluatingOutputHIGHandFLOATING() {
		Signal.State[] states = {Signal.State.HIGH, Signal.State.FLOATING};
		helpEvaluatingOutputs(states, Signal.State.HIGH, orGate);
	}

	@Test
	public void testEvaluatingOutputFLOATINGandHIGH() {
		Signal.State[] states = {Signal.State.FLOATING, Signal.State.HIGH};
		helpEvaluatingOutputs(states, Signal.State.HIGH, orGate);
	}

	/* VARIABLE INPUT - FLOATING OUTPUT */

	@Test
	public void testEvaluatingOutputLOWandFLOATING() {
		Signal.State[] states = {Signal.State.LOW, Signal.State.FLOATING};
		helpEvaluatingOutputs(states, Signal.State.FLOATING, orGate);
	}

	@Test
	public void testEvaluatingOutputFLOATINGandLOW() {
		Signal.State[] states = {Signal.State.FLOATING, Signal.State.LOW};
		helpEvaluatingOutputs(states, Signal.State.FLOATING, orGate);
	}

}
