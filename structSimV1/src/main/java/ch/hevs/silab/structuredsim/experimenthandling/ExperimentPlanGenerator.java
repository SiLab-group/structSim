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

import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hevs.silab.structuredsim.interfaces.AModifier;
import ch.hevs.silab.structuredsim.interfaces.ASimulationSystemHandler;
import ch.hevs.silab.structuredsim.interfaces.IManageModifier;
import ch.hevs.silab.structuredsim.util.FileManagement;

/**
 * Name : ExperimentPlanGenerator
 * <p>
 * Description : First thread that create the "planning" queue
 *<p>
 * Date : 25 July 2017
 * @version 1.0
 * @author Caroline Taramarcaz
 *
 */
public class ExperimentPlanGenerator implements Runnable {

	//Variable
	protected Environment baseEnvironment;
	protected BlockingQueue<Environment> planningQueue;
	protected Options options;
	protected ASimulationSystemHandler glueCode;
	private long endTime =0  ;
	private String cuttOfFormat="", result ="";
	private FileManagement fm;
	private static final Logger logger = LogManager.getLogger(ExperimentPlanGenerator.class.getName());
	public boolean isFinish = false;

	/**
	 * First Thread constructor
	 * @param planningQueue : The BlockignQueue where the environment will be saved
	 * @param baseEnv : the base environment. State 0
	 * @param o = Options file
	 * @param glueCode = Class of the glueCode
	 */
	public ExperimentPlanGenerator(BlockingQueue<Environment> planningQueue, Environment baseEnv, Options o, Object glueCode, FileManagement fm) {
		this.baseEnvironment = baseEnv;
		this.planningQueue = planningQueue;
		this.options = o;
		this.glueCode = (ASimulationSystemHandler) glueCode;
		this.fm = fm;

		((IManageModifier) glueCode).initiateModifierList();
	}

	/**
	 * Creating a new Environment based on the first Environment (Base)
	 * @param baseEnv : Base Environment
	 * @return The new Environment created
	 */
	private void createNextEnvironments (Environment baseEnv){
		List<Environment> toExplore = new ArrayList<Environment>();
		fm.createNewFolderSimulation(baseEnv, glueCode);

		List<AModifier> listModifiers = glueCode.getListModifierClass();
		toExplore.add(baseEnv);
		addEnvToQueue(baseEnv);

		Environment parentEnv, currentEnv;
		int idCpt = baseEnv.getId();
		int cpt = 0;


		while(!toExplore.isEmpty()){

			parentEnv = toExplore.remove(0);

			for (AModifier modifier : listModifiers) {
				idCpt ++;
				currentEnv = new Environment(idCpt, parentEnv);
				currentEnv = modifier.applyModifier(currentEnv);
				currentEnv.getTrace().add(modifier.getName() );
				//for(String str : currentEn)
				System.out.println("--------------------------------------------" +currentEnv.id + " " +  currentEnv.trace.toString());
				currentEnv.setProbability(parentEnv.getProbability() * modifier.getProbability());

				if(options.getTypeOfCuttOfPlanning().equals("CRITERIA") && 
						currentEnv.getProbability() > options.getStopCriteria()){
					toExplore.add(currentEnv);

				}

				if(options.getTypeOfCuttOfPlanning().equals("INT")&&
						cpt <= options.getCuttOfPlanning()){
					toExplore.add(currentEnv);
				}
				else{
					break;
				}

				fm.createNewFolderSimulation(currentEnv, glueCode);
				addEnvToQueue(currentEnv);
				createModifierFile(currentEnv);

			}
			cpt ++;
			System.out.println("CPT : " + cpt);
			Collections.sort(toExplore);
		}
	}

	private void createModifierFile(Environment e){
		String newLine = System.getProperty("line.separator"); 
		result += e.toStringModifier() + newLine;
		
		fm.createModifierFile(options.folderPathOUT, result);
	}



	/**
	 * Add an Environment to the BlockingQueue. A trace is printed to indicate if the environment has been added.
	 * @param e : Environment to Add
	 */
	private void addEnvToQueue(Environment e){
		boolean wasAdded = false ;

		wasAdded = planningQueue.add(e);

		if(wasAdded)
			logger.debug("Event : " + e.id +" is added !");
		else
			logger.error("Event : " + e.id +" is not added !");

	}

	@Override
	public void run() {



		//Generate the number of Environment that we want
		long currentTime = System.currentTimeMillis();


		switch(options.getTypeOfCuttOfPlanning()){
		case "INT" :
			createNextEnvironments(baseEnvironment);
			break;
		case "CRITERIA" : 
			createNextEnvironments(baseEnvironment);
			break;
		case "DAY":
			endTime = currentTime + TimeUnit.DAYS.toMillis(options.getCuttOfPlanningH().get(Calendar.DATE));
			cuttOfFormat = "TIME";
			break;
		case "HOURS" :
			endTime = currentTime + TimeUnit.HOURS.toMillis(options.getCuttOfPlanningH().get(Calendar.HOUR_OF_DAY));
			cuttOfFormat = "TIME";
			break;
		case "MINUTES" :
			endTime = currentTime + TimeUnit.MINUTES.toMillis(options.getCuttOfPlanningH().get(Calendar.MINUTE));
			cuttOfFormat = "TIME";
			break;

		}

		if(cuttOfFormat.contains("TIME")){
			while(System.currentTimeMillis() < endTime){	
				createNextEnvironments(baseEnvironment);
			}
		}

		isFinish = true;
		
		fm.copyFile(options.folderPathOUT + "/SummaryFile.txt", options.pathSimulator +"/SummaryFile.txt" );
		

	}
}