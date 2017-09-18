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

import java.util.Vector;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hevs.silab.structuredsim.interfaces.ASimulationSystemHandler;
import ch.hevs.silab.structuredsim.util.FileManagement;

/**
 * Name : ExperimentResultHandler
 * <p>
 * Description : This thread is for displaying where the results are save
 * <p>
 * Date : 25 july 2017
 * 
 * @version 1.0
 * @author Caroline Taramarcaz
 *
 */
public class ExperimentResultHandler implements Runnable {
	// Variable
	private final BlockingQueue<String> resultsQueue;
	protected ASimulationSystemHandler glueCode;
	private FileManagement fm;
	private static final Logger logger = LogManager.getLogger(ExperimentResultHandler.class.getName());
	protected Options options;



	/**
	 * Third Thread Constructor.
	 * 
	 * @param resultsQueue
	 *            : BlockingQueue to get the list fulfilled
	 * @param glueCode
	 *            : Class of the glueCode
	 * @param options
	 *            : options object
	 */
	public ExperimentResultHandler(BlockingQueue<String> resultsQueue, Object glueCode, FileManagement fm, Options o) {
		this.resultsQueue = resultsQueue;
		this.glueCode = (ASimulationSystemHandler) glueCode;
		this.fm = fm;
		this.options = o;
	}

	@Override
	public void run() {
			/**
			 * fm.saveSummaryFile(resultsQueue); code to create summaryFile...
			 * Need to be modify
			 * 
			 * // Print the path for (Environment env : resultsQueue) {
			 * System.out.println("->" + env.getPathSaveResult()); Path path =
			 * Paths.get(env.getPathSaveResult()); path = path.getParent(); File
			 * content[] = new File(path.toString()).listFiles(); for (File f :
			 * content) { File newFileResult = new
			 * File(options.getPathSimulator() + "/" + "_sim" + env.getId() +
			 * "/results.txt"); try { if (f.getName().contains("results")) {
			 * Files.copy(f.toPath(), newFileResult.toPath(),
			 * StandardCopyOption.REPLACE_EXISTING); } } catch (IOException e) {
			 * e.printStackTrace(); } }
			 * 
			 * }
			 */
		if(!resultsQueue.isEmpty()){
			for (String str : resultsQueue) {
				// System.out.println("result queue string : " + str);
				logger.debug("Result queue string : " + str);
				Vector<Measure> measures = glueCode.extractMeasures(str);
				int positionOfLastSlash = str.lastIndexOf("/");
				String folderToSave = str.substring(0, positionOfLastSlash);
				logger.debug("Folder where it's saved : " + folderToSave);
				int positionLastSlash = folderToSave.lastIndexOf("/");
				String nameSimulation = folderToSave.substring(positionLastSlash + 1, folderToSave.length());
				logger.debug("Name Simulation : " + nameSimulation) ;
				fm.createMeasuresFile(measures, folderToSave + "/measures.txt");
				fm.copyFile(folderToSave + "/measures.txt", options.pathSimulator +"/"+ nameSimulation + "/measures.txt" );
			}
		}else{
			Thread.currentThread().interrupt();
		}

	}
}