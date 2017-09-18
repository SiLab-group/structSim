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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hevs.silab.structuredsim.interfaces.ASimulationSystemHandler;
import ch.hevs.silab.structuredsim.util.FileManagement;

/**
 * Name : ExperimentSimulatorHandler
 * <p>
 * Description : This is the simulation thread
 * <p>
 * Date : 25 july 2017
 * @version 1.0
 * @author Caroline Taramarcaz
 **/
public class ExperimentSimulatorHandler implements Runnable {

	// Variable
	protected BlockingQueue<Environment> environnmentQueue;
	protected BlockingQueue<String> resultsQueue;
	protected ASimulationSystemHandler glueCode;
	protected Options options;
	private FileManagement fm;
	private static final Logger logger = LogManager.getLogger(ExperimentSimulatorHandler.class.getName());
	private ExperimentPlanGenerator plan;


	/**
	 * Constructor of the second thread for the simulation
	 * 
	 * @param environnementQueue
	 *            : The BlockignQueue where the environment will be saved
	 * @param simulationHandler
	 *            : Thread of the simulation
	 * @param o
	 *            : options object
	 * @param glueCode
	 *            : Class of the glueCode
	 */
	public ExperimentSimulatorHandler(BlockingQueue<Environment> environnementQueue, BlockingQueue<String> resultsQueue,
			Options o, Object glueCode, FileManagement fm, ExperimentPlanGenerator plan) {
		this.environnmentQueue = environnementQueue;
		this.options = o;
		this.glueCode = (ASimulationSystemHandler) glueCode;
		this.resultsQueue = resultsQueue;
		// resultsQueue = new PriorityBlockingQueue<String>();
		this.fm = fm;
		this.plan = plan;
	}

	/**
	 * Method to save the result
	 * @param e : Environment
	 * @return path where the file will be save
	 */
	@Override
	public void run() {

		ExperimentResultHandler result = new ExperimentResultHandler(resultsQueue, glueCode, fm, options);
		Thread resultThread = new Thread(result);
		resultThread.setName("Result Thread");

		do {
			try {
				System.out.println("Size of the Simulation Queue : " + environnmentQueue.size());
				Environment env = environnmentQueue.take();
				//glueCode.startSimulation(env);
				glueCode.startSimulation(options.getPathParameters());
				String resultPathForThisSimulation = env.pathSaveResult+"/results_sim"+ env.getId()+ ".txt";
				System.out.println(resultPathForThisSimulation);
				fm.copyFile(options.pathToSimulatorResultFile,resultPathForThisSimulation);
				fm.copyFile(options.pathToSimulatorResultFile, options.pathSimulator + "/"+env.pathSaveResult.substring(env.pathSaveResult.lastIndexOf("/")+1, env.pathSaveResult.length())+"/results_sim"+ env.id +".txt");


				//String pathResult = saveResult(env);
				//env.setPathSaveResult(pathResult);
				resultsQueue.add(resultPathForThisSimulation);



				// to get out of the loop
				if (plan.isFinish) {
					if (environnmentQueue.isEmpty()) {
						logger.debug("It's empty ! and it's not the first time that we try to read the queue !");
						break;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in the run of the Thread Simulator");
			}
		} while (true);
		
		resultThread.start();

	}

}
