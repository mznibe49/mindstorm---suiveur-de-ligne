package helloworld;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class helloworld {

	public static void main(String[] args) {
		LCD.drawString("HelloWorld", 0, 4);
		Delay.msDelay(5000);


	}

}
