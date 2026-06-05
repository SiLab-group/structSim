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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Name : Environment 
 * <p>
 * Description : An environment is the state of the simulation in the instant T. This class
 * contains an ID, a list of Parameters and a probability that this state can
 * occur.
 * <p>
 * Date : 25 july 2017
 * @version 1.0
 * @author Caroline Taramarcaz
 *
 */
public class Environment implements Comparable<Environment> {

	protected int id;
	public Vector<Parameter> setOfParameters;
	protected double probability;
	protected String pathSaveResult;
	protected List<String> trace = new ArrayList<String>();

	/**
	 * Constructor
	 * 
	 * @param id : ID
	 * @param setOfParameters : List of Parameters of this Environment
	 * @param probability : Probability
	 */
	public Environment(int id, Vector<Parameter> setOfParameters, double probability) {
		this.id = id;
		this.setOfParameters = setOfParameters;
		this.probability = probability;
	}

	/**
	 * Constructor to create a new Environment by copy another
	 * @param id : ID
	 * @param e : Environment
	 */
	public Environment(int id, Environment e){
		this.id = id;
		// this.setOfParameters = e.setOfParameters;

		/* When creating a child from a parent environment,
		the constructor was using a reference of the parent environment's parameters.
		That means that when a modifier was applied to a child,
		it was also implicitly applied to the parent.
		This was inducing incorrect simulations when multiple modifiers were involved.
		That's why I changed this constructor to take a copy of the parameters
		instead of references
		 */
		this.setOfParameters = new Vector<Parameter>();
		for (Parameter p : e.setOfParameters) {
			this.setOfParameters.add(new Parameter(p)); // Constructor by copy
		}

		this.probability = e.probability;
		this.trace = new ArrayList<String>();
		for(String s :e.trace){
			trace.add(s);
		}
	}

	/**
	 * Made this default constructor to facilitate the writing
	 * of the units tests of the FileManagement class
	 */
	public Environment() {
		this(1, new Vector<Parameter>(), 1);
	}

	/**
	 * Getter for id
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter for setOfParameters
	 * @return set of parameters
	 */
	public Vector<Parameter> getSetOfParameters() {
		return setOfParameters;
	}

	/**
	 * Setter for setOfParameters
	 * @param setOfParameters : set of parameters
	 */
	public void setSetOfParameters(Vector<Parameter> setOfParameters) {
		this.setOfParameters = setOfParameters;
	}

	/**
	 * Getter for probability
	 * @return probability
	 */
	public double getProbability() {
		return probability;
	}

	/**
	 * Setter for probability
	 * @param probability : probability
	 */
	public void setProbability(double probability) {
		this.probability = probability;
	}

	/**
	 * Getter for pathSaveResult
	 * @return path where the result are saved
	 */
	public String getPathSaveResult() {
		return pathSaveResult;
	}

	/**
	 * Setter for pathSaveResult
	 * @param pathSaveResult : path where the result are savedt 
	 */
	public void setPathSaveResult(String pathSaveResult) {
		this.pathSaveResult = pathSaveResult;
	}




	public String toStringModifier() {
		String result ="" ;
		for(String s : trace){
			result += "   " + s;
		}
		return "Simulation ID : " + id + "\t Probability : " + probability + "\t Modifier implemented : "  + result;
	}

	public List<String> getTrace() {
		return trace;
	}

	public void setTrace(List<String> trace) {
		this.trace = trace;
	}

	/**
	 Allow to order environments by ascending order of probability
	 */
	@Override
	public int compareTo(Environment other) {
		/*
		Changed the method because with the previous implementation,
		it casted every value to 0 if they were between -1 and 1
		(which would be the case for probabilities values)
		With a Double.compare, it correcly returns the corresponding value : -1, 1 or 0.
		 */
		return Double.compare(this.probability, other.probability);
	}

}