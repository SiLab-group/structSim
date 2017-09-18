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
package ch.hevs.silab.structuredsim.interfaces;

import java.util.Vector;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.Options;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;

/**
 * Name : AModifier
 * <p>
 * Description : This interface define a method to modify the value of a parameter
 * <p>
 * Date : 25 july 2017
 * @version 1.0
 * @author Caroline Taramarcaz
 *
 */
public abstract class AModifier {

	protected double probability;
	protected String name;

	public AModifier(){
		this.probability = 0.0;
		this.name  ="AModifier";
	}



	/**
	 * Method to apply like an Algorithm to modify a parameters </br>
	 * In case you must change the value from another parameters : 
	 * <ul>
	 * <li>
	 * set the probability with the method setProbability
	 * </li>
	 * <li>
	 * set the value "ValueToChange" from the object options. <p><i>Example : options.setValueToChange("val2");</i>
	 * </li>
	 * <li>
	 * use the method getParameterToModify to get the Parameter. <p><i>Example : Parameters newParam = getParameterToModify(env.getSetOfParameters(), options.getValueToChange);</i>
	 * </li>
	 * </ul>
	 * @param env : Environment. An Environment is one state of the simulation at the instant T. 
	 * @param o : options for the simulation
	 * @return : environment.
	 */
	public abstract Environment applyModifier(Environment env);

	/**
	 * Getter for probability
	 * @return probability
	 */
	public double getProbability() {
		return probability;
	}

	/**
	 * Setter of probability
	 * @param probability : probability
	 */
	public void setProbability(double probability) {
		this.probability = probability;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	




}