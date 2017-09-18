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
 * Name : Parameters
 * <p>
 * Description : This class define characteristics of Parameters
 * </p>
 * Date : 15 july 2015
 * @version 1.0
 * @author Caroline Taramarcaz.
 */

public class Parameter {

	public String key;
	protected double value;

	/**
	 * Constructor of the class Parameters
	 * 
	 * @param key : key of the parameter
	 * @param value : value of the parameter
	 */
	public Parameter(String key, double value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Constructor
	 * @param p : parameters
	 */
	public Parameter (Parameter p){
		this.key = p.key;
		this.value = p.value;
	}



	@Override
	public String toString() {
		return "key : " + key + " value : " + value;
	}

	/**
	 * Getter for the key
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Setter for the key
	 * @param key : key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Getter for the value
	 * @return value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Setter for the value
	 * @param value : value
	 */
	public void setValue(double value) {
		this.value = value;
	}



}
