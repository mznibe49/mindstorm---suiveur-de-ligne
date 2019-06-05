package apprentissageColor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Apprentissage {

	public static void main(String[] args)
    {
		File f = new File("rgbcouleur.txt");
		int num_couleur=0;
		try {
		if(!f.exists()) {
    		f.createNewFile();
		}else {
		FileReader fileReader = new FileReader(f);
		BufferedReader input = new BufferedReader(fileReader);		
	    String line;
	    while ((line = input.readLine()) != null) { 
        	String [] tab = line.split(",");
        	num_couleur=Integer.parseInt(tab[3])+1;
	    }
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		EV3ColorSensor  color = new EV3ColorSensor(SensorPort.S3);
		color.setCurrentMode("RGB");
		float[] sample = new float[color.sampleSize()];
        Color rgb;        
        int nombre_scan=7;

        LCD.drawString("Appuyez pour commencer", 0, 1);  
        while(nombre_scan!=0) {

    		Button.waitForAnyPress();	
        	LCD.clear();
        	color.fetchSample(sample, 0);
            int r = (int)(sample[0] * 255);
            int g = (int)(sample[1] * 255);
            int b = (int)(sample[2] * 255);
            Delay.msDelay(250);
        FileWriter fileWriter;
        BufferedReader reader;
        try {
            fileWriter = new FileWriter(f, true);
            fileWriter.write(r+","+g+","+b+","+num_couleur+"\n");
            fileWriter.flush();
            fileWriter.close();
            LCD.drawString("Terminé, recommencez", 0, 1);  
            
        } catch (IOException e) {
			e.printStackTrace();
		}
        nombre_scan-=1;
        }
        LCD.clear();
        LCD.drawString("Couleur numéro:", 0, 1);
        LCD.drawString(String.valueOf(num_couleur), 0,2);
		Button.waitForAnyPress();	
    }

}
