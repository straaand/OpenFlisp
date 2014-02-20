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

import se.openflisp.sls.simulation.Circuit;

/**
 * Abstract class for simulation test classes to extend.
 * 
 * @author Pär Svedberg <rockkuf@gmail.com>
 * @version 1.0
 */
public abstract class SimulationTest {
	public Circuit circuit;
	
	@Before
	public void setup() {
		circuit = new Circuit();
		circuit.getSimulation().start();
	}
}
