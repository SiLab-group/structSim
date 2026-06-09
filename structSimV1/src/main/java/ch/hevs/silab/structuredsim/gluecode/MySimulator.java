package ch.hevs.silab.structuredsim.gluecode;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MySimulator {

    public static void run(String pathToInputFile, String pathToResultFile) {
        Map<String, Double> params = new HashMap<>();

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(pathToInputFile), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                int pos = line.indexOf("=");
                String key = line.substring(0, pos).trim();
                double value = Double.parseDouble(line.substring(pos + 1).trim());
                params.put(key, value);
            }
            in.close();

            double result = params.get("val1") * params.get("val2");

            BufferedWriter bw = new BufferedWriter(new FileWriter(pathToResultFile));
            bw.write("result=" + result);
            bw.newLine();
            bw.flush();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}