package utils.resources;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class OptionsParser {
	/*
	 * integer is just number
	 * String is string
	 * float has one . or ,
	 * GVector2f has one _
	 */
	
	public static HashMap<String, Boolean> loadOnlyBooleanFile(String fileName) {
		InputStream file = ResourceLoader.load(fileName);
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();
		
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			
			if(line.startsWith("//") || line.isEmpty())
				continue;
			
			String[] lines = line.split("=");
			
			result.put(lines[0].replace(" ", ""), Boolean.valueOf(lines[1].replace(" ", "")));
		}
		scanner.close();
		return result;
	}
}
