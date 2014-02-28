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
package se.openflisp.sls.io;

import se.openflisp.sls.Component;
import se.openflisp.sls.component.*;

/**
 * A enumeration of components found in a legacy application mapped to the 
 * components used in this applications simulation. All information have been
 * reverse engineered from trial and error in the legacy application.
 * 
 * @author Anton Ekberg <anton.ekberg@gmail.com>
 * @version 1.0
 */
public enum LegacyComponent {
	
	/**
	 * Not gate with 1 input.
	 */
	NOT_GATE(1, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new NotGate(identifier), 1);
		}
	},
	
	/**
	 * And gate with 2 inputs.
	 */
	AND_GATE_2(3, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new AndGate(identifier), 2);
		}
	},
	
	/**
	 * And gate with 3 inputs.
	 */
	AND_GATE_3(14, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new AndGate(identifier), 3);
		}
	},
	
	/**
	 * And gate with 4 inputs.
	 */
	AND_GATE_4(22, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new AndGate(identifier), 4);
		}
	},
	
	/**
	 * Or gate with 2 inputs.
	 */
	OR_GATE_2(15, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new OrGate(identifier), 2);
		}
	},
	
	/**
	 * Or gate with 4 inputs.
	 */
	OR_GATE_4(24, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new OrGate(identifier), 4);
		}
	},
	
	/**
	 * Nand gate with 2 inputs.
	 */
	NAND_GATE_2(2, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new NandGate(identifier), 2);
		}
	},
	
	/**
	 * Nand gate with 3 inputs.
	 */
	NAND_GATE_3(5, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new NandGate(identifier), 3);
		}
	},
	
	/**
	 * Nand gate with 4 inputs.
	 */
	NAND_GATE_4(23, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new NandGate(identifier), 4);
		}
	},
	
	/**
	 * Nor gate with 2 inputs.
	 */
	NOR_GATE_2(4, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new NorGate(identifier), 2);
		}
	},
	
	/**
	 * Nor gate with 4 inputs.
	 */
	NOR_GATE_4(25, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new NorGate(identifier), 4);
		}
	},
	
	/**
	 * Xor gate with 2 inputs.
	 */
	XOR_GATE_2(6, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new XorGate(identifier), 1);
		}
	},
	
	/**
	 * Nxor gate with 2 inputs.
	 */
	NXOR_GATE_2(20, "u") {
		public Component toComponent(String identifier) {
			return this.initiateComponent(new NxorGate(identifier), 1);
		}
	};
	
	/**
	 * Identifier used by the legacy file format.
	 */
	public final int typeIdentifer;
	
	/**
	 * Prefix for the component identifier.
	 */
	public final String identifierPrefix;
	
	/**
	 * Creates a new LegacyComponent given identifier and prefix.
	 * 
	 * @param typeIdentifier	identifier used by the legacy file format.
	 * @param identifierPrefix	prefix for the component identifier
	 */
	private LegacyComponent(int typeIdentifier, String identifierPrefix) {
		this.typeIdentifer = typeIdentifier;
		this.identifierPrefix = identifierPrefix;
	}
	
	/**
	 * Create a new simulation Component from a legacy component identifier.
	 * 
	 * @param identifier	the component identifier for the new component
	 * @return a new simulation Component
	 */
	public abstract Component toComponent(String identifier);
	
	/**
	 * Gets the LegacyComponent for a given legacy type identifier.
	 * 
	 * @param typeIdentifer		the legacy type identifier
	 * @return corresponding LegacyComponent for the given type identifier, null if not found
	 */
	public static LegacyComponent fromTypeIdentifer(int typeIdentifer) {
		for (LegacyComponent component : LegacyComponent.values()) {
			if (component.typeIdentifer == typeIdentifer) {
				return component;
			}
		}
		return null;
	}
	
	/**
	 * Helper method to initiate inputs on a new Component. The inputs will be
	 * named numerically and if the component already have inputs with the same name
	 * a new input will not be created.
	 * 
	 * @param component		which component to initiate on
	 * @param inputCount	how many inputs that should be initiated
	 * @return the component that have been initiated
	 */
	protected Component initiateComponent(Component component, int inputCount) {
		for (int inputID = 0; inputID < inputCount; inputID++) {
			component.getInput(Integer.toString(inputID));
		}
		return component;
	}
}
