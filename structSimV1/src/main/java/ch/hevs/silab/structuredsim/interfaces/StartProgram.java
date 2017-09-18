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


import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.ExperimentPlanGenerator;
import ch.hevs.silab.structuredsim.experimenthandling.ExperimentResultHandler;
import ch.hevs.silab.structuredsim.experimenthandling.ExperimentSimulatorHandler;
import ch.hevs.silab.structuredsim.experimenthandling.Options;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.util.FileManagement;

/**
 * Name : StartProgram
 * <p>
 * Description : Class to start the program. Need to use the method startProgram.
 * <p>
 * Date : 25 july 2017
 * @version 1.0
 * @author Audrey Dupont
 */

public class StartProgram {

	/**
	 * Method to start the program
	 * 
	 * @param pathConfigFile : Path to the confi file
	 * @param glueCode : glue code object
	 * @throws IOException
	 */
	public static void startProgram(String pathConfigFile, Object glueCode) throws IOException {

		FileManagement fm = new FileManagement();

		// Create an instance of the "GlueCode"
		ASimulationSystemHandler glueCodeClass = (ASimulationSystemHandler) glueCode;
//		glueCodeClass.fileManagement = fm;

		// Load the configuration properties file
		Options o = fm.loadDataFromPropertiesFile(pathConfigFile);

		// Parameter
		// Get the List of the Parameters
		Vector<Parameter> listParam = null;

		listParam = glueCodeClass.readParametersFile(o.getPathParameters());


		// Select the Parameter to change
		Environment baseEnv = new Environment(0, listParam, 1);

		BlockingQueue<Environment> queue = new PriorityBlockingQueue<Environment>();
		BlockingQueue<String> resultQueue = new PriorityBlockingQueue<String>();

		glueCodeClass.setOptions(o);
		
		ExperimentPlanGenerator planning = new ExperimentPlanGenerator(queue, baseEnv, o, glueCodeClass, fm);
		Thread planningThread = new Thread(planning);
		planningThread.setName("Planning Thread");
		planningThread.start();
		
		ExperimentSimulatorHandler simulator = new ExperimentSimulatorHandler(queue, resultQueue, o, glueCodeClass, fm, planning);
		Thread simultationThread = new Thread(simulator);
		simultationThread.setName("Simulation Thread");
		simultationThread.start();
		
		/***
		 * This thread was moved in the run method of ExperimentSimulatorHandler.
		 * Because all results need to be analyse and measures extracted only after simulations done.
		 * ExperimentResultHandler result = new ExperimentResultHandler(resultQueue, glueCodeClass, fm, o);
		 * Thread resultThread = new Thread(result);
		 * resultThread.setName("Result Thread");
		 * resultThread.start();
		 */
		
	}
}