package bougerMoteursVitesse;

import lejos.robotics.RegulatedMotor;

public class BougerThread extends Thread{
    private int spd;
    private RegulatedMotor rm;
    public BougerThread(int spd, RegulatedMotor rm){
        this.spd=spd;
        this.rm=rm;
    }
public void run() {
	rm.setSpeed(spd);
	rm.rotate(1200);
}
}
