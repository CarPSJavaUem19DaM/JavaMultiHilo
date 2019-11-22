
public class ParkingCamion { //Atributos del parking
	private int plazas[];
	private int numPlazas; //Realmente sólo se va  autilizar para inicializar los demás argumentos
	private int libres;
	
	public ParkingCamion(int numPlazas) {
		this.numPlazas = numPlazas;
		libres = numPlazas;
		plazas = new int[numPlazas];		
	} 
	
	//Método q recibe id del coche y cuando consigue aparcar devuelve la plaza donde esté	
	public synchronized int entradaCoche(int coche) { 	
		System.out.println("El coche " + coche + " intenta entrar en el parking");
		imprimir(); //Mostramos el contenido del parking
		
		int plaza = 0;
		while (libres <= 1) {
			System.out.println("Coche " + coche + " esperando");
			try {
				wait(); //El coche queda dormido en espera de q haya plazas libres
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
		while (plazas[plaza] != 0) {//Buscamos la 1º plaza libre del parking
			plaza++;
		}		
		plazas[plaza] = coche;  //Asignamos a esa plaza el id del coche para indicar q está ahí aparcado
		libres--;  //Descontamos esa plaza del nº de plazas libres
		System.out.println("Coche " + coche + " aparcado plaza " + plaza);
		imprimir();  //Imprimimos cómo ha quedado el parking y retornamos la plaza donde está
		return plaza;
	}
	
	//Recibe id del camión y cuando consigue aparcar devuelve las plazas donde está
	public synchronized int entradaCamion(int camion) {		
		System.out.println("El camión " + camion + " intenta entrar en el parking");
		imprimir();  //Mostramos el contenido actual del parking		
		
		while (libres < 2) {  //El camión queda bloqueado esperando mientras no haya 2 plazas disponibles
			System.out.println("El camión " + camion + " está esperando para entrar");
			try {
				wait();
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
		
		int plaza;
		boolean hayHueco = false;  //Booleano para para poder cortar el bucle cuando se hayan encontrado 2 plazas juntas
		
		for (plaza = 0; plaza < plazas.length -1 & !hayHueco; plaza++) {  //Recorremos hasta el índice 3
			if (plazas[plaza] == 0 & plazas[plaza +1] == 0) {  //Buscamos que haya una plaza libre y la siguiente también				
				plazas[plaza] = camion;  //Asignamos esa plaza y la siguiente al camión
				plazas[plaza + 1] = camion;
				hayHueco = true;  //Cortamos el bucle
				libres-= 2;  //Descontamos las 2 plazas que ocupa el camión
				System.out.println("Camión " + camion + " aparcado en las plazas " + plaza + " y " + (plaza +1));
				imprimir();  //Mostramos de nuevo cómo está el parking
			}
		}
		if (!hayHueco) {
			try {
				wait(); //Si no hemos encontrado 2 plazas contiguas libres lo dormimos hasta que las haya
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}		
		return plaza -1; //Retornamos el índice de la primera plaza de las 2 que ocupa el camión
	}
	
	//Método para sacar coche, recibe la plaza donde está y el id del coche
	public synchronized void salidaCoche(int plaza, int coche) {
		plazas[plaza] = 0; //volvemos a poner en 0 la plaza donde estaba y aumentamos el nº de plazas libres
		libres++;		
		System.out.println("Coche " + coche + " saliendo del parking ");
		imprimir(); //Mostramos de nuevo el estado del parking
		
		notify(); //Despertamos aleatoriamente a alguno que esté en espera para que compruebe si puede entrar
	}
	
	//Salida del camión, recibe su id y el índice de la 1ª plaza q ocupa	
	public synchronized void salidaCamion(int plaza, int camion) {	
		plazas[plaza] = 0; //Volvemos a poner las posicione que deja libres en 0 e incrementamos las 2 plazas
		plazas[plaza + 1] = 0;
		libres+= 2;
		
		System.out.println("Camión " + camion + " saliendo del parking ");
		imprimir();  //Mostramos de nuevo el estado del parking		
		notify();  //Despertamos aleatoriamente a alguno que esté en espera para que compruebe si puede entrar
	}

	private void imprimir() { //Método que imprime el estado del parking
		System.out.println("Parking");
		for (int i = 0; i < plazas.length; i++) {
			System.out.print("[" + plazas[i] + "]");
		}
		System.out.println("");		
	}
	
	
}
