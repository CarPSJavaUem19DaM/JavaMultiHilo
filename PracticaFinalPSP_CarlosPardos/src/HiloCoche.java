

import java.util.Random;

public class HiloCoche extends Thread {
	private static final int TIEMPO_ESPERA = 2000; //Definimos la constante con el tiempo de espera aletorio para entrar o salir
	private ParkingCamion monitor;
	private int idCoche;

	public HiloCoche(ParkingCamion monitor, int idCoche) {
		this.monitor = monitor;
		this.idCoche = idCoche;
	}
	
	public void run() {
		try {
			sleep(new Random().nextInt(TIEMPO_ESPERA)); 
			int plaza = monitor.entradaCoche(idCoche); //Llamamos al método para entrar, y si hay plaza, retornará el índice de la 1º
			sleep(new Random().nextInt(TIEMPO_ESPERA)); //Permanece cierto tiempo y luego llama al método de salir indicando la plaza q deja
			monitor.salidaCoche(plaza, idCoche); //sale de esa plaza
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
	}
	
}
