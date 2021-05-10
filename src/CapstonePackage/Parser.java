package CapstonePackage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {

	/**
     * Parses the data into a suitable map.
     * @param file the string path to the data.
     * @return A map which relates graphs to the sentences
     */
	public Map <String, Graph> parseFile(File file){
		Map <String, Graph> graphList = new HashMap();

		//Write the maps into the arrayList
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while (br.ready()){
				String line = br.readLine();
				Data data = new ObjectMapper().readValue(line, Data.class);
				Graph g = new Graph(data);
				graphList.put(data.id+"_"+data.input, g);
			}
			br.close();
		} catch (Exception e) {
			if(e instanceof IOException){
                            JOptionPane.showMessageDialog(null, "File could not be opened.");
                        }else if(e instanceof JsonParseException){
                            JOptionPane.showMessageDialog(null, "Invalid file selected.");
                        }
		}
		return graphList;
	}
}
