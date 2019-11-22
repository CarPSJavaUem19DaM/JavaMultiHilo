
public class ParkingCamion { //Atributos del parking
	private int plazas[];
	private int numPlazas; //Realmente s�lo se va  autilizar para inicializar los dem�s argumentos
	private int libres;
	
	public ParkingCamion(int numPlazas) {
		this.numPlazas = numPlazas;
		libres = numPlazas;
		plazas = new int[numPlazas];		
	} 
	
	//M�todo q recibe id del coche y cuando consigue aparcar devuelve la plaza donde est�	
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
		while (plazas[plaza] != 0) {//Buscamos la 1� plaza libre del parking
			plaza++;
		}		
		plazas[plaza] = coche;  //Asignamos a esa plaza el id del coche para indicar q est� ah� aparcado
		libres--;  //Descontamos esa plaza del n� de plazas libres
		System.out.println("Coche " + coche + " aparcado plaza " + plaza);
		imprimir();  //Imprimimos c�mo ha quedado el parking y retornamos la plaza donde est�
		return plaza;
	}
	
	//Recibe id del cami�n y cuando consigue aparcar devuelve las plazas donde est�
	public synchronized int entradaCamion(int camion) {		
		System.out.println("El cami�n " + camion + " intenta entrar en el parking");
		imprimir();  //Mostramos el contenido actual del parking		
		
		while (libres < 2) {  //El cami�n queda bloqueado esperando mientras no haya 2 plazas disponibles
			System.out.println("El cami�n " + camion + " est� esperando para entrar");
			try {
				wait();
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
		
		int plaza;
		boolean hayHueco = false;  //Booleano para para poder cortar el bucle cuando se hayan encontrado 2 plazas juntas
		
		for (plaza = 0; plaza < plazas.length -1 & !hayHueco; plaza++) {  //Recorremos hasta el �ndice 3
			if (plazas[plaza] == 0 & plazas[plaza +1] == 0) {  //Buscamos que haya una plaza libre y la siguiente tambi�n				
				plazas[plaza] = camion;  //Asignamos esa plaza y la siguiente al cami�n
				plazas[plaza + 1] = camion;
				hayHueco = true;  //Cortamos el bucle
				libres-= 2;  //Descontamos las 2 plazas que ocupa el cami�n
				System.out.println("Cami�n " + camion + " aparcado en las plazas " + plaza + " y " + (plaza +1));
				imprimir();  //Mostramos de nuevo c�mo est� el parking
			}
		}
		if (!hayHueco) {
			try {
				wait(); //Si no hemos encontrado 2 plazas contiguas libres lo dormimos hasta que las haya
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}		
		return plaza -1; //Retornamos el �ndice de la primera plaza de las 2 que ocupa el cami�n
	}
	
	//M�todo para sacar coche, recibe la plaza donde est� y el id del coche
	public synchronized void salidaCoche(int plaza, int coche) {
		plazas[plaza] = 0; //volvemos a poner en 0 la plaza donde estaba y aumentamos el n� de plazas libres
		libres++;		
		System.out.println("Coche " + coche + " saliendo del parking ");
		imprimir(); //Mostramos de nuevo el estado del parking
		
		notify(); //Despertamos aleatoriamente a alguno que est� en espera para que compruebe si puede entrar
	}
	
	//Salida del cami�n, recibe su id y el �ndice de la 1� plaza q ocupa	
	public synchronized void salidaCamion(int plaza, int camion) {	
		plazas[plaza] = 0; //Volvemos a poner las posicione que deja libres en 0 e incrementamos las 2 plazas
		plazas[plaza + 1] = 0;
		libres+= 2;
		
		System.out.println("Cami�n " + camion + " saliendo del parking ");
		imprimir();  //Mostramos de nuevo el estado del parking		
		notify();  //Despertamos aleatoriamente a alguno que est� en espera para que compruebe si puede entrar
	}

	private void imprimir() { //M�todo que imprime el estado del parking
		System.out.println("Parking");
		for (int i = 0; i < plazas.length; i++) {
			System.out.print("[" + plazas[i] + "]");
		}
		System.out.println("");		
	}
	
	
}
