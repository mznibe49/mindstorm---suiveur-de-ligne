package reconnaissanceColor;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.hardware.lcd.LCD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lejos.hardware.Button;
import lejos.utility.Delay;
public class Reconnaissance {
	public static void main(String[] args)
    {
		EV3ColorSensor    color = new EV3ColorSensor(SensorPort.S3);
		color.setCurrentMode("RGB");
		float[] sample = new float[color.sampleSize()];
		LCD.drawString("Reconnaissance", 0, 2);        
		Button.waitForAnyPress();
		color.fetchSample(sample, 0);
        int r = (int)(sample[0] * 255);
        int g = (int)(sample[1] * 255);
        int b = (int)(sample[2] * 255);
        int  range=5;
    	int rinf = r-range;
    	int binf = b-range;
    	int ginf = g-range;
    	int rsup = r+range;
    	int bsup = b+range;
    	int gsup = g+range;
    	File f;
        FileReader fileReader;
        boolean bool=false;
        try {
            f = new File("rgbcouleur.txt");
        	if(!f.exists())
        		f.createNewFile();
            fileReader = new FileReader(f);
            BufferedReader br = new BufferedReader(fileReader);
            String s;
    		LCD.drawString("Scanning", 0, 2);  
    		LCD.clear();
    		
            while((s = br.readLine()) != null) {
            	String [] tab = s.split(",");
            	int red = Integer.parseInt(tab[0]);
            	int green = Integer.parseInt(tab[1]);
            	int blue = Integer.parseInt(tab[2]);
            	if(blue>=binf && blue<=bsup && 
            			green>=ginf && green<=gsup && 
            			red>=rinf && red<=rsup) {
            		LCD.drawString("Couleur reconnue : ", 0, 1);
            		LCD.drawString(" r= "+r + " g= "+g + " b= "+b, 0,2); 
            		bool=true;

            	}
            }
            fileReader.close();
            if(!bool) {
        		LCD.drawString("Couleur non reconnue", 0, 1);
    		LCD.drawString(" r= "+r + " g= "+g + " b= "+b, 0,2); 
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
		Button.waitForAnyPress();
    }
}
