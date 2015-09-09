package utils;

import java.awt.Color;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class Data{
	public HashMap<String, GVector2f> dataGVector = new HashMap<String, GVector2f>();
	public HashMap<String, Float> dataFloat = new HashMap<String, Float>();
	public HashMap<String, Color> dataColor = new HashMap<String, Color>();
	public HashMap<String, Integer> dataInt = new HashMap<String, Integer>();
	public HashMap<String, String> dataString = new HashMap<String, String>();
	public HashMap<String, Boolean> dataBoolean = new HashMap<String, Boolean>();
	
	private String fileName;
	
	public Data(String fileName){
		this.fileName = fileName;
		InputStream file = ResourceLoader.load(fileName);
		
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()){
			
			String line = scanner.nextLine().replace(" ", "");

			if(line.startsWith("//") || line.isEmpty() || !line.contains("="))
				continue;
			
			String[] lines = line.split("=");

			if(isInt(lines[1]))
				dataInt.put(lines[0], Integer.parseInt(lines[1]));
			else if(isFloat(lines[1]))
				dataFloat.put(lines[0], (float)Double.parseDouble(lines[1]));
			else if(isColor(lines[1]))
				dataColor.put(lines[0], stringToColor(lines[1]));
			else if(isGVector2f(lines[1]))
				dataGVector.put(lines[0], new GVector2f(lines[1]));
			else
				dataString.put(lines[0], lines[1]);
		}
		scanner.close();
	}
	
	public void analizeFile(){
		InputStream file = ResourceLoader.load(fileName);
		
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()){
			
			String line = scanner.nextLine().replace(" ", "").replace(";", "");

			if(line.startsWith("//") || line.isEmpty())
				continue;
			
			String[] lines = line.split("=");

			if(isInt(lines[1]))
				System.out.println(lines[0] + ": int");
			else if(isFloat(lines[1]))
				System.out.println(lines[0] + ": float");
			else if(isColor(lines[1]))
				System.out.println(lines[0] + ": color");
			else if(isGVector2f(lines[1]))
				System.out.println(lines[0] + ": vector");
			else if(isBoolean(lines[1]))
				System.out.println(lines[0] + ": boolean");
			else
				System.out.println(lines[0] + ": string");
		}
		scanner.close();
	}
	//GETTERS
	
	public String getString(String value){
		return dataString.get(value);
	}
	
	public int getInt(String value){
		return dataInt.get(value);
	}
	
	public float getFloat(String value){
		return dataFloat.get(value);
	}
	
	public GVector2f getVector2f(String value){
		return dataGVector.get(value);
	}
	
	public boolean getBoolean(String value){
		return dataBoolean.get(value);
	}
	
	//CONVERTERS
	
	private Color stringToColor(String value){
		return dataColor.get(value);
	}
	
	//CHECKERS
	
	private static boolean isBoolean(String value){
		value = value.toLowerCase();
		return value.equals("true") || value.equals("false");
	}
	
	private static boolean isInt(String value){
		try{
			Integer.valueOf(value);
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
	private static boolean isFloat(String value){
		if(value.contains("f\n"))
			return true;
		try{
			Double.valueOf(value);
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
	private static boolean isGVector2f(String value){
		try{
			String[] vals = value.split("_");
			Integer.valueOf(vals[0]);
			Integer.valueOf(vals[1]);
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
	private static boolean isColor(String value){
		try{
			String[] vals = value.split("_");
			if(vals.length < 3)
				return false;
			
			Integer.valueOf(vals[0]);
			Integer.valueOf(vals[1]);
			Integer.valueOf(vals[2]);
			return true;
		}catch (Exception e){
			return false;
		}
	}
}
