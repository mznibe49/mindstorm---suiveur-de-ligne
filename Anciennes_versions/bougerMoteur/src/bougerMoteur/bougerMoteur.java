package bougerMoteur;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class bougerMoteur {
	public static void main(String[] args) {
		RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.D);
		LCD.drawString("Avancer", 0, 4);
		m.rotate(5000);
		LCD.drawString("Reculer", 0, 4);
		m.rotate(-5000);
	}

}
