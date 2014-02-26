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

import se.openflisp.sls.Signal;
import se.openflisp.sls.event.ComponentEventDelegator;

public class NotGateTest extends GateTest {

	@Override
	protected Gate getInstance(String identifier) {
		return new NotGate(identifier);
	}

	@Override
	protected Gate getInstance(String identifier, ComponentEventDelegator delegator) {
		return new NotGate(identifier, delegator);
	}

	@Override
	public TruthTable generateTruthTable() {
		TruthTable table = new TruthTable();
		table.add(SignalConfiguration.ONE_LOW, Signal.State.HIGH);
		table.add(SignalConfiguration.ONE_HIGH, Signal.State.LOW);
		table.add(SignalConfiguration.ONE_FLOATING, Signal.State.FLOATING);
		table.add(SignalConfiguration.NO_SIGNALS, Signal.State.FLOATING);	
		table.add(SignalConfiguration.TWO_HIGH, Signal.State.FLOATING);
		table.add(SignalConfiguration.TWO_LOW, Signal.State.FLOATING);
		table.add(SignalConfiguration.TWO_FLOATING, Signal.State.FLOATING);
		return table;
	}
}
