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

import java.util.Calendar;

/**
 * Name : Options
 * <p>
 * Description : Class to specify the options to run the simulation
 * <p>
 * Date : 25 july 2017
 * @version 1.0
 * @author Audrey Dupont
 *
 */
public class Options {

	protected String pathParameters;
	protected String folderPathOUT;
	protected String pathSimulator;
	protected int cuttOfPlanning;
	protected Calendar cuttOfPlanningH;
	protected String typeOfCuttOfPlanning;
	protected double stopCriteria;
	protected String pathToSimulatorResultFile;

	

	public Options() {
	}

	/**
	 * Getter for pathParameters
	 * @return path of the parameters
	 */
	public String getPathParameters() {
		return pathParameters;
	}

	/**
	 * Setter for pathParameters
	 * @param pathParameters : path of the parameters
	 */
	public void setPathParameters(String pathParameters) {
		this.pathParameters = pathParameters;
	}

	/**
	 * Getter for folderPathOut
	 * @return path of the folder where the result will be save
	 */
	public String getFolderPathOUT() {
		return folderPathOUT;
	}

	/**
	 * Setter for folderPathOut
	 * @param folderPathOUT : path of the folder where the result will be save
	 */
	public void setFolderPathOUT(String folderPathOUT) {
		this.folderPathOUT = folderPathOUT;
	}

	/**
	 * Getter for pathSimulator
	 * @return path of Simulator
	 */
	public String getPathSimulator() {
		return pathSimulator;
	}

	/**
	 * Setter for pathSimulator
	 * @param pathSimulator : path of the simulator
	 */
	public void setPathSimulator(String pathSimulator) {
		this.pathSimulator = pathSimulator;
	}

	/**
	 * Getter for cuttOfPlanning
	 * @return when to stop the planning simulation
	 */
	public int getCuttOfPlanning() {
		return cuttOfPlanning;
	}

	/**
	 * Setter for cuttOfPlanning
	 * @param cuttOfPlanning : when to stop the planning simulation
	 */
	public void setCuttOfPlanning(int cuttOfPlanning) {
		this.cuttOfPlanning = cuttOfPlanning;
	}

	/**
	 * Getter for cuttOfPlanningH
	 * @return time when to stop the planning simulation
	 */
	public Calendar getCuttOfPlanningH() {
		return cuttOfPlanningH;
	}

	/**
	 * Setter for cuttOfPlanningH 
	 * @param cuttOfPlanningH : time when to stop the planning simulation
	 */
	public void setCuttOfPlanningH(Calendar cuttOfPlanningH) {
		this.cuttOfPlanningH = cuttOfPlanningH;
	}

	/**
	 * Getter for typeOfCuttOfPlanning
	 * @return the type of cuttOfPlanning
	 */
	public String getTypeOfCuttOfPlanning() {
		return typeOfCuttOfPlanning;
	}

	/**
	 * Setter for typeOfCuttOfPlanning
	 * @param typeOfCuttOfPlanning : the type of cuttOfPlanning
	 */
	public void setTypeOfCuttOfPlanning(String typeOfCuttOfPlanning) {
		this.typeOfCuttOfPlanning = typeOfCuttOfPlanning;
	}


	/**
	 * Getter for stopCriteria
	 * @return stop criteria
	 */
	public double getStopCriteria() {
		return stopCriteria;
	}

	/**
	 * Setter for stopCriteria
	 * @param stopCriteria : stop criteria
	 */
	public void setStopCriteria(double stopCriteria) {
		this.stopCriteria = stopCriteria;
	}
	
	/**
	 * Getter for pathToSimulatorResultFolder
	 * @return pathToSimulatorResultFolder
	 * */
	public String getPathToSimulatorResultFile() {
		return pathToSimulatorResultFile;
	}

	/**
	 * Setter for pathToSimulatorResultFolder
	 * @param pathToSimulatorResultFolder
	 * */
	public void setPathToSimulatorResultFile(String pathToSimulatorResultFile) {
		this.pathToSimulatorResultFile = pathToSimulatorResultFile;
	}


}