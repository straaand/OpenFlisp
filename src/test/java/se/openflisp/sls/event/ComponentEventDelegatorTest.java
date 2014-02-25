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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import se.openflisp.sls.Component;
import se.openflisp.sls.Signal;
import se.openflisp.sls.Input;
import se.openflisp.sls.Output;

public class ComponentEventDelegatorTest extends EventDelegatorTest<ComponentListener> {
	
	private Component component;
	private Signal signal;
	private ComponentEventDelegator delegator;
	private List<ComponentListener> listeners;
	private static final int NR_OF_DEFAULT_LISTENERS = 9;
	private static final int NR_OF_SWING_LISTENERS = 3;
	private static final int NR_OF_MODEL_LISTENERS = 7;

	@Override
	public ComponentEventDelegator getDelegatorInstance() {
		return new ComponentEventDelegator();
	}

	@Override
	public Class<ComponentListener> getListenerClass() {
		return ComponentListener.class;
	}

	@Override
	@Before
	public void setup() {
		super.setup();
		component = Mockito.mock(Component.class);
		signal = Mockito.mock(Signal.class);
		delegator = new ComponentEventDelegator();
	}

	@Test
	public void testOnSignalChange() {
		for(int i = 0; i < 10; i++) {
			component = Mockito.mock(Component.class);
			addListenersToList();
			delegator.onSignalChange(component, signal);

			for(ComponentListener l : delegator.getModelListeners()) {
				verify(l).onSignalChange(component, signal);
			}
			for(ComponentListener l : delegator.getSwingListeners()) {
				verify(l, Mockito.timeout(1000)).onSignalChange(component, signal);
			}
			for(ComponentListener l: delegator.getNormalListeners()) {
				verify(l).onSignalChange(component, signal);
			}
		}
	}

	@Test
	public void testEventDispatchThread() {
		delegator.addListener(ListenerContext.SWING, 
			createComponentListener(true));
		delegator.onSignalChange(component, signal);
	}

	@Test
	public void testNotEventDispatchThread_MODEL() {
		delegator.addListener(ListenerContext.MODEL,
			createComponentListener(false));
		delegator.onSignalChange(component, signal);
	}

	@Test
	public void testNotEventDispatchThread_DEFALUT() {
		delegator.addListener(ListenerContext.DEFAULT,
			createComponentListener(false));
		delegator.onSignalChange(component, signal);
	}

	@Test
	public void testAddListenersToList() {
		addListenersToList();
		assertThat(delegator.getModelListeners().size(), is(NR_OF_MODEL_LISTENERS));
		assertThat(delegator.getSwingListeners().size(), is(NR_OF_SWING_LISTENERS));
		assertThat(delegator.getNormalListeners().size(), is(NR_OF_DEFAULT_LISTENERS));
	}

	private ComponentListener createComponentListener(boolean isSwingListener) {
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

	private void addListenersToList() {
		listeners = new ArrayList<ComponentListener>();
		ComponentListener componentListener;

		int nrOfListeners = NR_OF_DEFAULT_LISTENERS
			+ NR_OF_SWING_LISTENERS
			+ NR_OF_MODEL_LISTENERS;

		for(int i = 0; i < nrOfListeners; i++) {
			componentListener = Mockito.mock(ComponentListener.class);
			listeners.add(componentListener);
		}
		
		int i = 0;
		for(ComponentListener l : listeners) {
			if (i < NR_OF_DEFAULT_LISTENERS) {
				delegator.addListener(ListenerContext.DEFAULT, listeners.get(i));
			} else if (i < NR_OF_DEFAULT_LISTENERS + NR_OF_SWING_LISTENERS) {
				delegator.addListener(ListenerContext.SWING, listeners.get(i));
			} else {
				delegator.addListener(ListenerContext.MODEL, listeners.get(i));
			}
			i++;
		}
	}

}
