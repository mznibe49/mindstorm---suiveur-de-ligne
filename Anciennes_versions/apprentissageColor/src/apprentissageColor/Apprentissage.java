package apprentissageColor;

import java.io.BufferedReader;
import java.io.File;
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
		EV3ColorSensor    color = new EV3ColorSensor(SensorPort.S3);
		color.setCurrentMode("RGB");
		float[] sample = new float[color.sampleSize()];
        Color rgb;
        LCD.drawString("Apprentissage", 0, 1);        
        Button.waitForAnyPress();
        
        	color.fetchSample(sample, 0);
            int r = (int)(sample[0] * 255);
            int g = (int)(sample[1] * 255);
            int b = (int)(sample[2] * 255);
            Delay.msDelay(250);
            LCD.drawString("r"+r+"g"+g+"b"+b,0,1);
        File f;
        FileWriter fileWriter;
        BufferedReader reader;
        try {
            f = new File("rgbcouleur.txt");
        	if(!f.exists())
        		f.createNewFile();
            fileWriter = new FileWriter(f, true);
            fileWriter.write(r+","+g+","+b+"\n");
            fileWriter.flush();
            fileWriter.close();
            
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

}
