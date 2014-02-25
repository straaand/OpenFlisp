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
package se.openflisp.sls.event;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.swing.SwingUtilities;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import se.openflisp.sls.Component;
import se.openflisp.sls.Input;
import se.openflisp.sls.Output;
import se.openflisp.sls.Signal;

public class ComponentEventDelegatorTest extends EventDelegatorTest<ComponentListener> {
	
	private ComponentEventDelegator delegator;

	@Override
	public ComponentEventDelegator getDelegatorInstance() {
		return new ComponentEventDelegator();
	}

	@Override
	public Class<ComponentListener> getListenerClass() {
		return ComponentListener.class;
	}

	@Override
	public ComponentListener createListener(boolean isSwingListener) {
		if(isSwingListener) {
			return new ComponentListener() {
				@Override
				public void onSignalChange(Component component, Signal signal) {
					assertTrue(SwingUtilities.isEventDispatchThread());
				}
				@Override
				public void onSignalDisconnection(Input input, Output output) {
					assertTrue(SwingUtilities.isEventDispatchThread());
				}
				@Override
				public void onSignalConnection(Input input, Output output) {
					assertTrue(SwingUtilities.isEventDispatchThread());
				}
			};
		}
		return new ComponentListener() {
			@Override
			public void onSignalChange(Component component, Signal signal) {
				assertFalse(SwingUtilities.isEventDispatchThread());
			}
			@Override
			public void onSignalDisconnection(Input input, Output output) {
				assertFalse(SwingUtilities.isEventDispatchThread());
			}
			@Override
			public void onSignalConnection(Input input, Output output) {
				assertFalse(SwingUtilities.isEventDispatchThread());
			}
		};
	}

	@Override
	@Before
	public void setup() {
		super.setup();
		component = Mockito.mock(Component.class);
		signal = Mockito.mock(Signal.class);
		input = Mockito.mock(Input.class);
		output = Mockito.mock(Output.class);
		delegator = new ComponentEventDelegator();
	}

	@Test
	public void testOnSignalChange() {
		for(int i = 0; i < 10; i++) {
			delegator = new ComponentEventDelegator();
			addListeners(delegator, getListenerClass());
			delegator.onSignalChange(component, signal);

			for(ComponentListener l : delegator.getModelListeners()) {
				verify(l).onSignalChange(component, signal);
			}
			for(ComponentListener l : delegator.getSwingListeners()) {
				verify(l, Mockito.timeout(1000)).onSignalChange(component, signal);
			}
			for(ComponentListener l : delegator.getNormalListeners()) {
				verify(l).onSignalChange(component, signal);
			}
		}
	}

	@Test
	public void testOnSignalConnection() {
		for(int i = 0; i < 10; i++) {
			delegator = new ComponentEventDelegator();
			addListeners(delegator, getListenerClass());
			delegator.onSignalConnection(input, output);

			for(ComponentListener l : delegator.getModelListeners()) {
				verify(l).onSignalConnection(input, output);
			}
			for(ComponentListener l : delegator.getSwingListeners()) {
				verify(l, Mockito.timeout(1000)).onSignalConnection(input, output);
			}
			for(ComponentListener l : delegator.getNormalListeners()) {
				verify(l).onSignalConnection(input, output);
			}
		}
	}

	@Test
	public void testOnSignalDisconnection() {
		for(int i = 0; i < 10; i++) {
			delegator = new ComponentEventDelegator();
			addListeners(delegator, getListenerClass());
			delegator.onSignalDisconnection(input, output);

			for(ComponentListener l : delegator.getModelListeners()) {
				verify(l).onSignalDisconnection(input, output);
			}
			for(ComponentListener l : delegator.getSwingListeners()) {
				verify(l, Mockito.timeout(1000)).onSignalDisconnection(input, output);
			}
			for(ComponentListener l : delegator.getNormalListeners()) {
				verify(l).onSignalDisconnection(input, output);
			}
		}
	}

	@Test
	public void testEventDispatchThread() {
		delegator.addListener(ListenerContext.SWING, 
			createListener(true));
		callListenerMethods(delegator);
	}

	@Test
	public void testNotEventDispatchThread_MODEL() {
		delegator.addListener(ListenerContext.MODEL,
			createListener(false));
		callListenerMethods(delegator);
	}

	@Test
	public void testNotEventDispatchThread_DEFALUT() {
		delegator.addListener(ListenerContext.DEFAULT,
			createListener(false));
		callListenerMethods(delegator);
	}

	private void callListenerMethods(ComponentEventDelegator eDelegator) {
		eDelegator.onSignalChange(component, signal);
		eDelegator.onSignalConnection(input, output);
		eDelegator.onSignalDisconnection(input, output);
	}

}
