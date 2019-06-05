package suivreLigne;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;


public class suivreLigne {

	public static void main(String[] args)
    {
		
		RegulatedMotor leftMotor = Motor.C;
		RegulatedMotor rightMotor = Motor.D;
		int spd1=300;
		int spd2=600;
	    LCD.drawString(String.valueOf(spd1), 0, 2);
		while(Button.ESCAPE.isUp()) {
			if(Button.LEFT.isDown()) {
			    LCD.clear();
				spd1-=1;
			    LCD.drawString(String.valueOf(spd1), 0, 2);

			}else
			if(Button.RIGHT.isDown()) {
				LCD.clear();
				spd1+=1;
			    LCD.drawString(String.valueOf(spd1), 0, 2);
			    }
		
			
		}
		double t=spd1*2.5;
		spd2=(int)t;
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		Reconnaissance r = new Reconnaissance(colorSensor);
		leftMotor.setSpeed(spd1);
		rightMotor.setSpeed(spd2);
		leftMotor.forward();
		rightMotor.forward();
		int direction=0;
		int accrocher=0;
		int trav = 0;
		while(Button.DOWN.isUp()) { 
			if(r.aff()!=0) { // si la couleur est différente de la ligne
				if(accrocher == 1) { //si il a trouvé la ligne
					if(trav == 1) { // si il a traversé la ligne 
						trav =0 ; 
						if(direction == 0) direction = 1 ; // changement de direction
						else direction = 0 ;
						inverser(leftMotor,rightMotor,direction,spd1,spd2); //inverser la vitesse des moteurs
					}
				}else {//si il n'a pas trouvé la ligne
						leftMotor.setSpeed(spd1);
						rightMotor.setSpeed(spd2);
				}
				
			
				
			}else {//si la couleur est la couleur de la ligne
				if(trav ==0) { //si il n'a pas encore traversé
					trav = 1; 
					accrocher=1;	
				}
			}
		}
	}
	public static void inverser(RegulatedMotor leftMotor, RegulatedMotor rightMotor, int direction, int spd1, int spd2) {
		
		if(direction ==0) {
			leftMotor.setSpeed(spd1);
			rightMotor.setSpeed(spd2);
			
			}else {
				leftMotor.setSpeed(spd2);
				rightMotor.setSpeed(spd1);
			
			}
	}

}
