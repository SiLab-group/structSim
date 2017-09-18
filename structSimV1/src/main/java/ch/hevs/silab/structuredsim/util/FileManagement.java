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
package ch.hevs.silab.structuredsim.util;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.Measure;
import ch.hevs.silab.structuredsim.experimenthandling.Options;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.interfaces.ASimulationSystemHandler;

/**
 * The goal of this class is to manage parameters and/or results files.
 * <ul>
 * <li>read data of the file</li>
 * <li>put <key,value> data in an hashmap</li>
 * <li>save a file</li>
 * <li>get a value : give the key and receive back the corresponding value</li>
 * </ul>
 * Created on 09-07-2015. </br>
 * Inspired by a class written by Marco Bruchmann and Rene Schumann
 * 
 * @author Caroline_Taramarcaz
 * 
 */

public class FileManagement {

	// Variables
	protected String filename;
	protected Options options;
	private String pathResult, pathSimulator;
	private static final Logger logger = LogManager.getLogger(FileManagement.class.getName());

	/**
	 * Empty Constructor
	 */

	public FileManagement() {
		this.options = new Options();
	}

	/**
	 * This method return a String with the content of a file
	 * 
	 * @return String
	 * @throws IOException
	 */
	public String contentOfAFile() throws IOException {
		String contentFile = "";

		if (!(new File(filename).isFile())) {
			return "this is not a file";
		}

		try {
			BufferedReader in = new BufferedReader(new FileReader(this.filename));
			String line = "";
			while ((line = in.readLine()) != null) {
				contentFile = contentFile + line;
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.info("Just a stack trace", e);
		}

		return contentFile;
	}

	/**
	 * This method move a file from a folder to another folder.
	 * 
	 * @param originFile
	 *            : Origin path folder
	 * @param destinationFile
	 *            : destination path folder
	 */
	public void moveFile(String originFile, String destinationFile) {
		Path originPath = Paths.get(originFile);
		Path destinationPath = Paths.get(destinationFile);

		try {
			Files.move(originPath, destinationPath, REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error("Impossible to move this file");
			//System.out.println("Impossible to move this file");
			e.printStackTrace();
		}
	}

	/**
	 * This method copy a file to another folder
	 * 
	 * @param file
	 *            : Origin File to copy
	 * @param destination
	 *            : Destination folder
	 */
	public void copyFile(String file, String destination) {
		File fileToCopy = new File(file);
		File dest = new File(destination);

		try {
			Files.copy(fileToCopy.toPath(), dest.toPath());
		} catch (Exception e) {
			logger.error("This file in this folder already exist");
			//e.printStackTrace();
		}

	}

	/**
	 * This method create a new empty file in a specific folder. If the folder
	 * is not existing, the method create it.
	 * 
	 * @param folderPath
	 *            : The path to the folder
	 * @param filename
	 *            : The name of the file to save.
	 */
	/*
	 * public void createFile(String folderPath, String filename) { File file =
	 * new File(folderPath + filename); file.getParentFile().mkdir(); try {
	 * file.createNewFile(); } catch (IOException e) {
	 * System.out.println("Sorry but this folder already exist!!!!");
	 * e.printStackTrace(); } }
	 * 
	 */

	/** Method to create an empty Folder
	 * 
	 * @param folderPath : Path
	 */
	public void createFolder(String folderPath) {
		new File(folderPath).mkdir();
	}

	/**
	 * Method to save the simulation result in a file
	 * 
	 * @param result : result to save
	 * @param env : environment of the simulation
	 * @return <b>String</b> : Path of the file where is saved the result file
	 * @throws IOException
	 */
	public String saveSimultationResult(String result, Environment env) throws IOException {
		Properties properties = new Properties();
		properties.put("Result", result);

		String filePath = options.getFolderPathOUT() + "/" +  "_sim" + env.getId()
		+ "/results.txt";

		FileOutputStream fops;
		try {
			fops = new FileOutputStream(filePath, true);
			properties.store(fops, null);
			fops.flush();
			fops.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("File not Found");
		}

		return filePath;

	}

	/**
	 * Method to save a summaryFile where all the result will be summarized.
	 * 
	 * @param resultQueue : the queue with results of simulations
	 */
	/*	public void saveSummaryFile(BlockingQueue<Environment> resultQueue) {

		do {

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(options.getFolderPathOUT() + "/SummaryFile.txt"));
				bw.write("RunId \t ParameterChanged \t Value \t Probability \t Path");
				bw.newLine();

				for (Environment env : resultQueue) {
					String line = env.toString();
					bw.write(line);
					bw.newLine();
				}

				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Error to save the summary File");
			}

		} while (resultQueue.isEmpty());

	}
	 */

	/**
	 * Method to load data from a properties file and put in an Hashmap
	 * 
	 * @param filePath
	 *            : Path of the file
	 * @return Options
	 */
	public Options loadDataFromPropertiesFile(String filePath) {

		Properties properties = new Properties();

		try {
			InputStream ips = new FileInputStream(filePath);
			properties.load(ips);
			ips.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error to load Data from Properties file");
		}
		String cuttOfValue = "";

		for (String key : properties.stringPropertyNames()) {

			switch (key) {
			case "pathParameters":
				options.setPathParameters(properties.getProperty(key));
				break;
			case "pathOUT":
				options.setFolderPathOUT(properties.getProperty(key));
				break;
			case "pathSimulator":
				options.setPathSimulator(properties.getProperty(key));
				break;
			case "pathToSimulatorResultFile":
				options.setPathToSimulatorResultFile(properties.getProperty(key));
				break;
			case "cuttOfPlanning":
				cuttOfValue = properties.getProperty(key);
				break;
			case "typeCuttOfPlanning":
				options.setTypeOfCuttOfPlanning(properties.getProperty(key));
				switch (properties.getProperty(key)) {
				case "INT":
					options.setCuttOfPlanning(Integer.parseInt(cuttOfValue));
					break;
				case "DAY":
					Calendar cal1 = Calendar.getInstance();
					cal1.set(Calendar.DATE, Integer.parseInt(cuttOfValue));
					options.setCuttOfPlanningH(cal1);
					break;
				case "HOURS":
					Calendar cal2 = Calendar.getInstance();
					cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(cuttOfValue));
					options.setCuttOfPlanningH(cal2);
					break;
				case "MINUTES":
					Calendar cal3 = Calendar.getInstance();
					cal3.set(Calendar.MINUTE, Integer.parseInt(cuttOfValue));
					options.setCuttOfPlanningH(cal3);
					break;
				case "CRITERIA":
					options.setStopCriteria(Double.parseDouble(cuttOfValue));
					break;
				}
				break;

			}
		}

		return options;
	}

	/**
	 * Method to write something in a properties file parameters :
	 * <ul>
	 * <li>String resultToWrite : The string to write in the file</li>
	 * <li>String resultsFilePath : The path of the file</li>
	 * <li>boolean keepPreviousResults
	 * <ul>
	 * <li>true if the new results has to be written at the end of the existing
	 * file</li>
	 * <li>false if the text of the file has to be overwritten.</li>
	 * </ul>
	 * </ul>
	 * @param dataToWrite : an hashmap of data to write
	 * @param filePath : path to the file
	 * @param keepPreviousResults : to keep previous result or not?
	 * 
	 */
	public void writeDataInPropertiesFile(LinkedHashMap<String, String> dataToWrite, String filePath,
			boolean keepPreviousResults) {
		try {
			Properties properties = new Properties();
			properties.putAll(dataToWrite);

			// Write the results in the existing file, at the end of the file,
			// don't deleted what is already in the file.
			FileOutputStream fops = new FileOutputStream(filePath, keepPreviousResults);
			properties.store(fops, null);
			fops.close();

		} catch (IOException e) {
			//System.out.println(e.toString());
			logger.error(e.toString());
		}
	}


	/**
	 * Method to create a new folder for each simulation
	 * @param e : current environment
	 * @return return the string path where the folder is created
	 */
	public String createNewFolder(Environment e){
		pathResult = options.getFolderPathOUT() + "/" + "_sim" + e.getId();
		//create folder
		createFolder(pathResult);

		pathSimulator = options.getPathSimulator() + "/" + "_sim" + e.getId() +"/";
		createFolder(pathSimulator);
		return pathResult;
	}

	/**
	 * Method to create a new Folder for the simulation
	 * @param env : Environment
	 * @exception IOException if the file can't be copied
	 */
	public void createNewFolderSimulation (Environment env, ASimulationSystemHandler glueCode){
		String pathNewFolder = createNewFolder(env);
		env.setPathSaveResult(pathNewFolder);
		//Save the new parameters file
		glueCode.writeParametersFile(env.getSetOfParameters(), pathNewFolder);

		File content [] = new File(pathResult).listFiles();
		for (File f : content){
			pathSimulator += f.getName();
			File newFile = new File(pathSimulator);
			try {
				Files.copy(f.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Error when we create the new Folder of the simulation. "
						+ "Is the path in the config properties right ? ");
			}

		}
	}

	public void createMeasuresFile(Vector<Measure> measures, String measuresFilePath) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(measuresFilePath));
			for(Measure m : measures){
				bw.write(m.getKey() + "=" + m.getValue());
				bw.newLine(); 
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			logger.error("Error whent we create de measures file");
		}
	}

	public void createModifierFile(String path, String modifier){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/SummaryFile.txt"));
			bw.write(modifier);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error to save the summary File");
		}

	}



}
