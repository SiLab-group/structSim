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

import java.util.List;

/**
 * Name : IManageModifier
 * <p>
 * Description : Class to manage the modifier
 * <p>
 * Date : 25 July 2017
 * @version 1.0
 * @author Audrey Dupont
 */

public interface IManageModifier {
	
	/**
	 * Method to initiate the modifier class inside a List
	 */
	public List<AModifier> initiateModifierList();

}
