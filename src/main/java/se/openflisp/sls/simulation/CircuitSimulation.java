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
package se.openflisp.sls.simulation;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import se.openflisp.sls.Component;
import se.openflisp.sls.Input;
import se.openflisp.sls.Signal;
import se.openflisp.sls.event.ComponentAdapter;
import se.openflisp.sls.event.ComponentListener;

/**
 * Thread for simulating a logical Circuit.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 */
public class CircuitSimulation extends Thread {

	/**
	 * The Circuit that is being simulated.
	 */
	private final Circuit circuit;
	
	/**
	 * Queue for all changed inputs in the Circuit.
	 */
	private BlockingQueue<Input> inputQueue = new LinkedBlockingQueue<Input>();
	
	/**
	 * Queue for all components that needs to be evaluated.
	 */
	private Queue<Component> componentQueue = new ConcurrentLinkedQueue<Component>();
	
	/**
	 * Time to wait before simulating source components, expressed in milliseconds.
	 */
	private long pollTime = 1000;
	
	/**
	 * Creates a new Circuit simulation daemon thread.
	 * 
	 * @param circuit		the circuit that should be handled by this thread
	 */
	public CircuitSimulation(Circuit circuit) {
		this.circuit = circuit;
		this.setDaemon(true);
	}
	
	/**
	 * Runs the simulation until interrupted. Will sleep if the input queue is 
	 * empty. Will wake up, with every {@link #pollTime} millisecond, and simulate 
	 * all sources.
	 */
	@Override
	public void run() {
		do {
			try {
				for (Component component : this.circuit.getSourceComponents()) {
					component.evaluate();
				}
				while (this.inputQueue.poll(this.pollTime, TimeUnit.MILLISECONDS) != null) {
					while (!this.inputQueue.isEmpty()) {
						Component component = this.inputQueue.poll().getOwner();
						if (this.componentQueue.contains(component)) {
							this.componentQueue.add(component);
						}
					}
					while (!this.componentQueue.isEmpty()) {
						this.componentQueue.poll().evaluate();
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		} while (!Thread.currentThread().isInterrupted());
	}
	
	/**
	 * Listener that will put changed inputs into the input working queue.
	 */
	protected final ComponentListener signalHandler = new ComponentAdapter() {
		@Override
		public void onSignalChange(Component component, Signal signal) {
			if (signal instanceof Input) {
				CircuitSimulation.this.inputQueue.add((Input) signal);
			}
		}
	};
}
