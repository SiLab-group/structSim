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

import ch.hevs.silab.structuredsim.experimenthandling.Measure;

/**
 * Name : IExtractMeasures
 * <p>
 * Description : Interface to manage the extraction of measures
 * <p>
 * Date 25 July 2017
 * @version 1.0
 * @author Caroline Taramarcaz
 *
 */

public interface IExtractMeasures {
	
	/**
	 * Method to extract the measures from a String
	 * @param resultsFilePath : String that contains all the measures
	 * @return List of measure
	 * @see Measure
	 */
	public Vector<Measure> extractMeasures(String resultsFilePath);

}
