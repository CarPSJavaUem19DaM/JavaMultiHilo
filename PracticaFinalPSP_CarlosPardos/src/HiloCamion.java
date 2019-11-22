

import java.util.Random;

public class HiloCamion extends Thread {
	private static final int TIEMPO_ESPERA = 2000; //Definimos la constante con el tiempo de espera aletorio para entrar o salir
	private ParkingCamion monitor;
	private int idCamion;

	public HiloCamion(ParkingCamion monitor, int idCamion) {
		this.monitor = monitor;
		this.idCamion = idCamion;
	}
	
	public void run() {
		try {
			sleep(new Random().nextInt(TIEMPO_ESPERA)); 
			int plaza = monitor.entradaCamion(idCamion); //Llamamos al m�todo para entrar, y si hay plaza, retornar� el �ndice de la 1�
			sleep(new Random().nextInt(TIEMPO_ESPERA)); //Permanece cierto tiempo y luego llama al m�todo de salir indicando la plaza q deja
			monitor.salidaCamion(plaza, idCamion); 
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
	}
	
}
