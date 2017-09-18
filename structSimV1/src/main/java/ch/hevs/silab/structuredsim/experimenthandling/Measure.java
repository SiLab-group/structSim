/**
* Copyright (c) 2017 HES-SO Valais - Smart Infrastructure Laboratory (http://silab.hes.ch)
*
* This file is part of StructuredSimulationFramework.
*
* The StructuredSimulationFramework is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* The StructuredSimulationFramework is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
*
 * You should have received a copy of the GNU General Public License
* along with StructuredSimulationFramework.
* If not, see <http://www.gnu.org/licenses/>.
* */
package ch.hevs.silab.structuredsim.experimenthandling;

/**
 * Name : Measure
 * <p>
 * Description : This class define characteristics of Measure that can be extract from simulation's results files.
 * <p>
 * Date : 25 july 2017
 * @version 1.0
 * @author Caroline Taramarcaz
 *
 */


public class Measure {

	// Variable
	protected String key;
	protected String value;

	/**
	 * Constructor of the class Measure
	 * 
	 * @param key : key of the measure
	 * @param value : value of the measure
	 */
	public Measure(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Getter for value
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter for value
	 * @param value : value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Getter for key
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Setter for key
	 * @param key : key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Key : " + key + " Value : " + value;
	}

}
