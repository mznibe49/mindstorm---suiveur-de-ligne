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
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		Reconnaissance r = new Reconnaissance(colorSensor);
		leftMotor.forward();
		rightMotor.forward();
		int direction=0;
		int accrocher=0;
		while(Button.DOWN.isUp()) {
		if(r.aff()!=0) {
			if(accrocher == 1) {
				accrocher = 0;
				inverser(leftMotor,rightMotor,direction);
			}else {
				leftMotor.setSpeed(300);
				rightMotor.setSpeed(600);
			}
				

			
		}
		else {
			if(accrocher == 0){
				accrocher = 1;
				inverser(leftMotor,rightMotor,direction);
				}
			
			
		}
		}
		}
		
		
		
		
      
    

	public static void inverser(RegulatedMotor leftMotor, RegulatedMotor rightMotor, int direction) {
		if(direction == 0) direction = 1 ;
		else direction = 0 ;
		if(direction ==0) {
			leftMotor.setSpeed(300);
			rightMotor.setSpeed(600);
			
			}else {
				leftMotor.setSpeed(600);
				rightMotor.setSpeed(300);
			
			}
	}

}
