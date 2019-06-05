import lejos.utility.Delay;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.lcd.LCD;

public class Main {
	  static RegulatedMotor motorA = Motor.A;
	  static RegulatedMotor motorD = Motor.D;
  private static EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S4);

  // pour recuperer le taux de luminosité
  private static SensorMode brightness;

  public static void main(String[] args) {
	    int targetPower= 550;

	  double kp, ki, kd;
	    kp = 440;
	    ki = 1;
	    kd = 0;
	    
	    LCD.drawString("Veuillez regler le kp", 1, 2);
	    LCD.drawString("kp="+kp, 1, 3);
	    Button.waitForAnyPress();
	    LCD.clear();
	    while(true) {
	    	
	    	if(Button.UP.isDown()) {
	    		kp+=1;
	    	    LCD.drawString("kp="+kp, 2, 2);
	    		
	    	}
	    	else if(Button.DOWN.isDown()) {
	    		kp-=1;
	    	    LCD.drawString("kp="+kp, 2, 2);
	    		
	    	}
	    	else if(Button.ESCAPE.isDown()) {
	    		break;
	    	}
	    }
	    LCD.clear();
	  
    sensor.setFloodlight(false); // on teste que sur noir/blanc
    brightness = sensor.getRedMode();
    float[] sample1 = new float[brightness.sampleSize()];

    LCD.drawString("WHITE ?", 2, 2);
    Button.waitForAnyPress();

    brightness.fetchSample(sample1, 0);
    double white = (double) sample1[0];
    LCD.drawString("" + white, 3, 3);

    LCD.drawString("BLACK ?", 2, 2);
    Button.waitForAnyPress();

    brightness.fetchSample(sample1, 0);
    double black = (double) sample1[0];
    LCD.drawString("" + black, 3, 3);

    /*
     integral = la somme de tout les erreurs (sur une ligne droite l'erreur est 0 donc l'integral est 0 aussi)
     si le robot est partie un peu sur la droite l'erreur va etre posivif sin elle va etre negatif
     le but d'avoir cette variable est de garder le robot au milieu ! (correction de l'accumulation d'erreur)
    */

    double midCol = (white + black) / 2;
    double lightValue = 0;
    double correction = 0;
    double error = 0, lastError = 0;
    double integral = 0;
    /*
     L'idée est de prédire le futur. On compare l'erreur actuelle à la dernière erreur
     pour prédire la prochaine.
    */
    double deriv = 0;

    int powerA = 0;
    int powerD = 0;

    /*
     valeur proportionnelle, v. integrale, v. dérivé
     kp est une valeur arbitraire :
     en gros kp est un facteur utilisé pour redimensionner la correction du robot,
     c.a.d si la value de kp est auguementé le robot va tourné vite !!
     si on baisse la valeur de kp le robot va tourné doucement
     L'idée est de multiplier l'erreur le gain proportionnel KP pour que
     le robot suive effectivement la ligne. KP corrige l'erreur.

     ki est la valeur arbitraire de l'integrale

     kd est la valeur arbitraire de la deriv

     on modifie nos valeur arbitraire jusqu a ce qu'on a un meilleur resultat
     PS :  a ne pas tres auguementé le KP !
    */

  
    /* double factor = 0; // umm.. a revoir cette variable (longue distance) ? */
    int dt = 1;


    LCD.drawString("Ready ! Press any key to start.", 2, 2);

    Button.waitForAnyPress();

    LCD.drawString("Press DOWN to stop.", 2, 2);

    while (Button.DOWN.isUp()) {

        brightness.fetchSample(sample1, 0);
        lightValue = (double) sample1[0];

        error = (midCol - lightValue);
        integral = /* factor * integral + */ error * dt;
        // anticipé la prochaine erreur
        deriv = (error - lastError) / dt;

        correction = kp * error + ki * integral + kd * deriv;

        // ajuster le suiveur sur la ligne avec une correction
        powerA = targetPower + (int) correction;
        powerD = targetPower - (int) correction;

        if(powerA > 0){
          motorA.setSpeed(powerA);
          motorA.forward();
        }
        else{
          powerA = powerA * (-1); // tourné dans le sense contraire
          motorA.setSpeed(powerA);
          motorA.backward();
        }
        if(powerD > 0){
          motorD.setSpeed(powerD);
          motorD.forward();
        }
        else{
          powerD = powerD * (-1);
          motorD.setSpeed(powerD);
          motorD.backward();
        }

        lastError = error;
    }

    motorA.stop();
    motorD.stop();

    motorA.close();
    motorD.close();

    sensor.close();

  }

}
