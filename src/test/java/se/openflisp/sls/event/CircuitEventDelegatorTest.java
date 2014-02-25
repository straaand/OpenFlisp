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

import java.awt.Point;
import java.util.List;
import javax.swing.SwingUtilities;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import se.openflisp.sls.Component;
import se.openflisp.sls.Signal;
import se.openflisp.sls.Input;
import se.openflisp.sls.Output;

public class CircuitEventDelegatorTest extends EventDelegatorTest<CircuitListener> {

	private Component component;
	private Signal signal;
	private CircuitEventDelegator delegator;
	private List<CircuitListener> listeners;
	private Point point;
	private static final int NR_OF_DEFAULT_LISTENERS = 9;
	private static final int NR_OF_SWING_LISTENERS = 3;
	private static final int NR_OF_MODEL_LISTENERS = 7;

	@Override
	public CircuitEventDelegator getDelegatorInstance() {
		return new CircuitEventDelegator();
	}

	@Override
	public Class<CircuitListener> getListenerClass() {
		return CircuitListener.class;
	}

	@Override
	public CircuitListener createListener(boolean isSwingListener) {
		if(isSwingListener) {
			return new CircuitListener() {
				@Override
				public void onComponentAdded(Component component) {
					assertTrue(SwingUtilities.isEventDispatchThread());
				}
				@Override
				public void onComponentRemoved(Component component) {
					assertTrue(SwingUtilities.isEventDispatchThread());

				}
				@Override
				public void onComponentMoved(Component component, Point from, Point to) {
					assertTrue(SwingUtilities.isEventDispatchThread());
				}
			};
		}
		return new CircuitListener() {
			@Override
			public void onComponentAdded(Component component) {
				assertFalse(SwingUtilities.isEventDispatchThread());
			}
			@Override
			public void onComponentRemoved(Component component) {
				assertFalse(SwingUtilities.isEventDispatchThread());
			}
			@Override
			public void onComponentMoved(Component component, Point from, Point to) {
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
		point = Mockito.mock(Point.class);
		delegator = new CircuitEventDelegator();
	}

	@Test
	public void testOnComponentAdded() {
		for(int i = 0; i < 10; i++) {
			delegator = new CircuitEventDelegator();
			addListeners(delegator, getListenerClass());
			delegator.onComponentAdded(component);

			for(CircuitListener l : delegator.getModelListeners()) {
				verify(l).onComponentAdded(component);
			}
			for(CircuitListener l : delegator.getSwingListeners()) {
				verify(l, Mockito.timeout(1000)).onComponentAdded(component);
			}
			for(CircuitListener l : delegator.getNormalListeners()) {
				verify(l).onComponentAdded(component);
			}
		}
	}

	@Test
	public void testOnComponentRemoved() {
		for(int i = 0; i < 10; i++) {
			delegator = new CircuitEventDelegator();
			addListeners(delegator, getListenerClass());
			delegator.onComponentRemoved(component);

			for(CircuitListener l : delegator.getModelListeners()) {
				verify(l).onComponentRemoved(component);
			}
			for(CircuitListener l : delegator.getSwingListeners()) {
				verify(l, Mockito.timeout(1000)).onComponentRemoved(component);
			}
			for(CircuitListener l : delegator.getNormalListeners()) {
				verify(l).onComponentRemoved(component);
			}
		}
	}

	@Test
	public void testOnComponentMoved() {
		for(int i = 0; i < 10; i++) {
			delegator = new CircuitEventDelegator();
			addListeners(delegator, getListenerClass());
			delegator.onComponentMoved(component, point, point);

			for(CircuitListener l : delegator.getModelListeners()) {
				verify(l).onComponentMoved(component, point, point);
			}
			for(CircuitListener l : delegator.getSwingListeners()) {
				verify(l, Mockito.timeout(1000)).onComponentMoved(component, point, point);
			}
			for(CircuitListener l : delegator.getNormalListeners()) {
				verify(l).onComponentMoved(component, point, point);
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
	public void testNotEventDispatchThread_DEFAULT() {
		delegator.addListener(ListenerContext.DEFAULT,
			createListener(false));
		callListenerMethods(delegator);
	}

	private void callListenerMethods(CircuitEventDelegator cDelegator) {
		cDelegator.onComponentAdded(component);
		cDelegator.onComponentRemoved(component);
		cDelegator.onComponentMoved(component, point, point);
	}
}
