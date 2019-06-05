package suivreLigne;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class Reconnaissance {
	private  EV3ColorSensor color;
	
	public Reconnaissance(EV3ColorSensor color) {
		this.color=color;
		
	}
	public int aff()
    {
		color.setCurrentMode("RGB");
		float[] sample = new float[color.sampleSize()];
		color.fetchSample(sample, 0);
        int r = (int)(sample[0] * 255);
        int g = (int)(sample[1] * 255);
        int b = (int)(sample[2] * 255);	
        int distance=0;
        int numcouleur=-1;
        int  range=30;
    	File f;
        FileReader fileReader;
        try {
            f = new File("rgbcouleur.txt");
        	if(!f.exists())
        		f.createNewFile();
            fileReader = new FileReader(f);
            BufferedReader br = new BufferedReader(fileReader);
            String s;
            while((s = br.readLine()) != null) {
            	String [] tab = s.split(",");
            	int red = Integer.parseInt(tab[0]);
            	int green = Integer.parseInt(tab[1]);
            	int blue = Integer.parseInt(tab[2]);
            	int num_couleur = Integer.parseInt(tab[3]);
            	int distancetmp=0;
            	if(red>r)
            		distancetmp+= red-r;
            	else
            		distancetmp+= r-red;
            	if(blue>b)
            		distancetmp+= blue-b;
            	else
            		distancetmp+= b-blue;
            	if(green>g)
            		distancetmp+= green-g;
            	else
            		distancetmp+= g-green;
            	
            	if((distancetmp-distance)<range) {
            		distance=distancetmp;
            		numcouleur=num_couleur;
            	}
            	
            		
            	
            }
            fileReader.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
        		
		return numcouleur;
    }
	
}
