package bougerMoteursVitesse;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class bougerMoteursVitesse {
	private static BougerThread hwt,hwt2;
	public static void main(String[] args) {
		RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.D);
		RegulatedMotor m2 = new EV3LargeRegulatedMotor(MotorPort.C);

		hwt = new BougerThread(300,m);
		hwt2=new BougerThread((int)m2.getMaxSpeed(),m2);
		System.out.println(m2.getMaxSpeed());
		hwt.start();
		hwt2.start();
	}

}
