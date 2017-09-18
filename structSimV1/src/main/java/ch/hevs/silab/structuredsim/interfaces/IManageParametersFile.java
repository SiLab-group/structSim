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

import ch.hevs.silab.structuredsim.experimenthandling.Parameter;

/**
 * Name : IManageParametersFile
 * <p>
 * Description : This interface define methods to manage parameters files .
 * <p>
 * Date : 25 July 2017
 * @version 1.0 
 * @author Caroline Taramarcaz
 */

public interface IManageParametersFile {

	/**
	 * Method to read a parameters File used by your simulator.
	 * 
	 * @param parametersFilePath : Path where the parameters File is saved. This path is describe on the config.properties file.
	 * @return :The return variable must be a vector of Parameter.
	 * @see Parameter
	 */
	public Vector<Parameter> readParametersFile(String parametersFilePath);

	/**
	 * Method to write a new file of parameters that will be used by your
	 * simulator EACH TIME that a Parameter change !
	 * 
	 * @param setOfParameters : Vector of Parameters that must be saved.
	 * @param locationToStore
	 *            : Path where the parameters file we'll be saved. </br>
	 *            Each new parameters file are saved in the corresponding
	 *            simulation folder. </br>
	 *            <i>Example : {pathOUT}/sim1 = Folder of the 1st
	 *            simulation.</i>
	 *            <p>
	 *            Don't forget to insert the name of your file. </br>
	 *            Example : {registrationPath} + "/MyParametersFile.txt"
	 *            </p>
	 * @see Parameter
	 */
	public void writeParametersFile(Vector<Parameter> setOfParameters, String locationToStore);
}
