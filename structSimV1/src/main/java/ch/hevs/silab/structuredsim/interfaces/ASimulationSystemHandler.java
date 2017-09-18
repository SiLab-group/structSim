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

import java.util.ArrayList;
import java.util.List;

import ch.hevs.silab.structuredsim.experimenthandling.Options;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.util.FileManagement;

/**
 * Name : SimultationSystemHandler
 * <p>
 * Description : Abstract class to manage all the implementation need in the glue Code.
 * <p>
 * Date :  06 July 2015
 * @version 1.0 
 * @author Rene Schumann
 * 
 */
public abstract class ASimulationSystemHandler implements IStartSimulation, IStopProgram, IManageParametersFile, IExtractMeasures, IManageModifier{


	protected Options options;
	protected List<AModifier> listModifierClass = new ArrayList<AModifier>();


	/**
	 * Getter for the Options
	 * @return : options
	 */
	public Options getOptions() {
		return options;
	}
	/**
	 * Setter for the options
	 * @param options : options
	 */
	public void setOptions(Options options) {
		this.options = options;
	}


	/**
	 * Getter for listModifierClass
	 * @return List of the modifier Class
	 */
	public List<AModifier> getListModifierClass() {
		return listModifierClass;
	}
	/**
	 * Setter for listModifierClass
	 * @param listModifierClass  :List of the modifier Class
	 */
	public void setListModifierClass(List<AModifier> listModifierClass) {
		this.listModifierClass = listModifierClass;
	}
}
