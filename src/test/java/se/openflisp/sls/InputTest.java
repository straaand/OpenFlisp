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
package se.openflisp.sls;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class InputTest extends SignalTest {

	public Input instance;
	public Output output1, output2;
	public Signal signal;
	
	@Override
	protected Signal getInstance(String identifier, Component owner) {
		return new Input(identifier, owner);
	}
	
	@Before
	public void setup() {
		super.setup();
		instance = new Input("identifier", owner);
		output1  = Mockito.mock(Output.class);
		output2  = Mockito.mock(Output.class);
		signal   = Mockito.mock(Signal.class);
	}
	
	@Test
	public void testGettingUnsetConnection() {
		assertNull(instance.getConnection());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConnectingInvalidSignalType() {
		instance.connect(Mockito.mock(Signal.class));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConnectingNull() {
		instance.connect(null);
	}
	
	@Test
	public void testConnect() {
		instance.connect(output1);
		assertSame(output1, instance.getConnection());
	}
	
	@Test
	public void testConnectReturnsTrue() {
		assertTrue(instance.connect(output1));
	}
	
	@Test
	public void testConnectCallsOutput() {
		instance.connect(output1);
		verify(output1).connect(instance);
	}
	
	@Test
	public void testConnectDontCallOutputWhenConnected() {
		instance.connect(output1);
		instance.connect(output1);
		verify(output1, times(1)).connect(instance);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConnectToAlreadyConnected() {
		instance.connect(output1);
		instance.connect(output2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDisconnectingInvalidSignalType() {
		instance.disconnect(signal);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDisconnectingNull() {
		instance.disconnect(null);
	}
	
	@Test
	public void testDisconnect() {
		instance.connect(output1);
		instance.disconnect(output1);
		assertNull(instance.getConnection());
	}
	
	@Test
	public void testDisconnectReturnsTrue() {
		instance.connect(output1);
		assertTrue(instance.disconnect(output1));
	}
	
	@Test
	public void testDisconnectWhenNotConnected() {
		assertFalse(instance.disconnect(output1));
	}
	
	@Test
	public void testDisconnectCallsOutput() {
		instance.connect(output1);
		instance.disconnect(output1);
		verify(output1).disconnect(instance);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDisconnectToNotConnectedOutput() {
		instance.connect(output1);
		instance.disconnect(output2);
	}
	
	@Test
	public void testIsConnectedInitial() {
		assertFalse(instance.isConnected());
	}
	
	@Test
	public void testIsConnectedAfterConnect() {
		instance.connect(output1);
		assertTrue(instance.isConnected());
	}
	
	@Test
	public void testIsConnectedAfterDisconnect() {
		instance.connect(output1);
		instance.disconnect(output1);
		assertFalse(instance.isConnected());
	}
	
	@Test
	public void testConnectTriggersEvent() {
		instance.connect(output1);
		verify(delegator).onSignalConnection(instance, output1);
	}
	
	@Test
	public void testDisconnectTriggersEvent() {
		instance.connect(output1);
		instance.disconnect(output1);
		verify(delegator).onSignalDisconnection(instance, output1);
	}
}
