

public class PrincipalMain {
	public static void main(String[] args) {
		int numPlazas = Integer.parseInt(args[0]); //Recibimos 1er argumento con el nº de plazas	
		ParkingCamion monitor = new ParkingCamion(numPlazas);
		
		int numCoches = Integer.parseInt(args[1]); //Recibimos 2º argumento con la cantidad de coches
		HiloCoche coches [] = new HiloCoche[numCoches];
		
		for (int i = 0; i < coches.length; i++) { //Creamos los coches y los lanzamos a ejecución
			coches[i] = new HiloCoche(monitor, i+1);
			coches[i].start();
		}
		
		int numCamiones = Integer.parseInt(args[2]); //Recibimos 3er argumento con la cantidad de camiones
		HiloCamion camiones [] = new HiloCamion[numCamiones];	
		
		for (int i = 0; i < camiones.length; i++) { //Creamos los camiones y los lanzamos a ejecución
			camiones[i] = new HiloCamion(monitor, i+101);
			camiones[i].start();
		}
		
		//Aplicamos el join tanto a coches como a camiones
		aplicarJoin(coches, camiones);			
		
		System.out.println("Fin del programa del parking");		
	}

	private static void aplicarJoin(HiloCoche[] coches, HiloCamion[] camiones) {
		for (int i = 0; i < coches.length; i++) {
			try {
				coches[i].join();				
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}
		}
		for (int i = 0; i < camiones.length; i++) {
			try {
				camiones[i].join();				
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}
		}
	}
}
