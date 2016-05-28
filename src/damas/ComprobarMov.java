package damas;

import damas.TableroJuego;

public class ComprobarMov extends TableroJuego{
	boolean segundoClick;
	int filaOrig, colOrig, filaDest, colDest, ficha;
	public boolean jugador,ordenador;
	int filaComer,colComer;
	String displayStr;
	int profundidad;
	boolean esGanador,bloqueado;
	boolean seguirTirando,comeOrd; //seguir comiendo
	int tam;
	int nivel;
	boolean defensiva,ofensiva,miEstrategia;
	
	public ComprobarMov(int t) {
		super(t);
		filaOrig = -1;
		colOrig = -1;
		tam = t;
		super.inicializar();
		seguirTirando=false;
		ordenador = true;
		displayStr="";
	}
	public boolean getPrimerClick()
	{
		return !segundoClick;
	}
	public String getSTR()
	{
		return displayStr;
	}
	public int getFilaOrigen()
	{
		return filaOrig;
	}
	public int getColOrigen()
	{
		return colOrig;
	}
	public void procesarMovimiento(int fila, int columna){ // x e y posicion en el tablero
		if(!esGanador){
		if (ordenador && !jugador){//el ordenador ees el primer jugador
			movOrd();
			//jugador = true;
		}
		else
		if (!segundoClick){
			ficha = dameCasilla(fila, columna);
			if ((!jugador && (ficha == 1 || ficha == 2)) || (jugador && (ficha == 3 || ficha == 4))){
				displayStr = "Seleccionado" + fila + columna;
				filaOrig = fila;
				colOrig = columna;
				segundoClick = true;
			}
			else displayStr = "Error no hay ficha del jugador 2";		
		}
		else {
			boolean legal = comprobarMovimiento(fila,columna);
			if (esGanador || (legal && !seguirTirando)){
				segundoClick = false;
			//	jugador = !jugador;
			}
		}
		
	}
	}
	
	private boolean comprobarMovimiento(int fila, int columna) {

		
		if (columna == colOrig && fila == filaOrig && !seguirTirando){
			displayStr = "Deseleccionado";
			return true;
		}
		else if (dameCasilla(fila, columna) != 0){
			displayStr = "Error Casilla Ocupada";
			return false;
		}
		else if (colOrig - 2 > columna || columna > colOrig + 2 || filaOrig - 2 > fila || fila > filaOrig + 2 ){
			displayStr = "Error la ficha no puede ir ahi";
			return false;
		}
		else if ((!jugador && ficha == 1 && (fila <= filaOrig || colOrig == columna)) || (jugador && ficha == 3 && (fila >= filaOrig || colOrig == columna))){ 
			displayStr = "Movimiento invalido";
			return false;
		}
		else if (!seguirTirando && ((fila == filaOrig + 1 || fila == filaOrig - 1) && (columna == colOrig + 1 || columna == colOrig -1))){
			realizarMovimiento(fila,columna,false);
			//jugador = !jugador;
			return true;
		}
		else if (comprobarAtaque(filaOrig,colOrig,fila,columna)){
			realizarMovimiento(fila,columna,true);
			//jugador = !jugador;
			return true;
		}
		else return false;	
	}

	

	private void realizarMovimiento(int fila, int columna, boolean ataque) {
		/*filaOrig = -1;
		colOrig = -1;*/
		if (!ataque){
			ponCasilla(filaOrig, colOrig, 0);
			if (!jugador && fila == tam-1)
				ponCasilla(fila, columna, 2);
			else if (jugador && fila == 0)
				ponCasilla(fila,columna, 4);
			else{
				ponCasilla(fila,columna, ficha);		
			}
		}
		else{
			ponCasilla(filaOrig, colOrig, 0);
			ponCasilla(filaComer,colComer,0);
			if (!jugador && fila == tam-1)
				ponCasilla(fila,columna, 2);
			else if (jugador && fila == 0)
				ponCasilla(fila,columna, 4);
			else ponCasilla(fila,columna, ficha);		
			
			seguirTirando = esAtaqueMultiple(fila,columna);
			
			if (seguirTirando) {
				filaOrig = fila;
				colOrig = columna;
			}
		}
		
		if (haGanado(jugador)) 
			if (!jugador) displayStr = "A ganado JUGADOR 1";
			else displayStr = "A ganado JUGADOR 2";
		else if (!seguirTirando) jugador = !jugador;
		
		if (!esGanador && (esBloque(jugador) || esBloque(!jugador)))
			if (!jugador) displayStr = "Bloqueo JUGADOR 1";
			else displayStr = "Bloque JUGADOR 2";
	}

	private boolean comprobarAtaque(int filaOrig, int colOrig, int fila, int columna) {
		
		if (0 <= filaOrig && filaOrig < tam && 0 <= colOrig && colOrig < tam && 0 <= fila && fila < tam && 0 <= columna && columna < tam) 
			if (fila == filaOrig + 1 || columna == colOrig + 1 || fila == filaOrig - 1 || columna == colOrig - 1 ) 
				return false;
			else{
				if (columna - colOrig == -2) colComer = colOrig -1;
		    	else colComer = colOrig + 1;
				if (fila - filaOrig == -2) filaComer = filaOrig -1;
				else filaComer = filaOrig + 1;
				
				int fichaComer = dameCasilla(filaComer,colComer);
				return  dameCasilla(fila,columna) == 0 && ((!jugador && (fichaComer == 3 || fichaComer == 4)) || (jugador && (fichaComer == 1 || fichaComer == 2)));		
			}
		return false;
			
	}	
	
	private boolean haGanado(boolean jugador) {
		int fichas;
			for (int i = 0; i < tam; i++)
				for (int j = 0; j < tam; j++){
					fichas = dameCasilla(i,j);
					if (!jugador && (fichas == 3 || fichas == 4))
						return false;
					if (jugador && (fichas == 1 || fichas == 2))
						return false;
				}
			esGanador = true;
			return true;
	}
	
	private boolean esAtaqueMultiple(int fila, int columna) {
		if (!jugador && ficha == 1 && (comprobarAtaque(fila,columna,fila + 2,columna - 2) || comprobarAtaque(fila,columna,fila + 2,columna + 2)))
				return true;
		else if (jugador && ficha == 3 && (comprobarAtaque(fila,columna,fila - 2,columna - 2) || comprobarAtaque(fila,columna,fila - 2,columna + 2)))
				return true;
		else if ((ficha == 2 || ficha == 4) && (comprobarAtaque(fila,columna,fila + 2,columna - 2) || comprobarAtaque(fila,columna,fila + 2,columna + 2) ||
				comprobarAtaque(fila,columna,fila - 2,columna - 2) || comprobarAtaque(fila,columna,fila - 2,columna + 2)))
			return true;
		return false;
	}
	
	private boolean esBloque(boolean jugador) {
		for(int fila = 0; fila < tam; fila++)
			for(int columna = 0; columna < tam; columna++){
				if (movPosible(tablero,fila + 1,columna - 1)) return false;
				if (comprobarAtaque(fila,columna,fila + 2, columna - 2)) return false;
				if (movPosible(tablero,fila + 1,columna + 1)) return false;
				if (comprobarAtaque(fila,columna,fila + 2, columna  + 2)) return false;
				if (!jugador){
					if (tablero[fila][columna] == 2 && movPosible(tablero,fila - 1,columna - 1)) return false;
					if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna - 2)) return false;
					if (tablero[fila][columna] == 2 && movPosible(tablero,fila - 1,columna + 1)) return false;
					if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna + 2)) return false;
				}
				if (jugador){
					if (tablero[fila][columna] == 4 && movPosible(tablero,fila - 1,columna - 1)) return false;
					if (tablero[fila][columna] == 4 && comprobarAtaque(fila,columna,fila - 2, columna - 2)) return false;
					if (tablero[fila][columna] == 4 && movPosible(tablero,fila - 1,columna + 1)) return false;
					if (tablero[fila][columna] == 4 && comprobarAtaque(fila,columna,fila - 2, columna + 2)) return false;
				}
			}
		return true;
	}
	
	public void movOrd(){
		MovOrd m;// = new MovOrd();
		//m.setValues(profundidad,-100,-1,-1,-1,-1);
		m =mueveOrd(nivel,1000);
		if (m.hayMovimiento()){
			ejecutarMovOrd(m.filaOrig,m.colOrig,m.filaDest,m.colDest);
		}
		else bloqueado = true;
	}
	
	public boolean esFinalJuego(boolean jugador){
		return haGanado(jugador) || esBloque(jugador);
	}
	private  MovOrd mueveOrd(int nivel,int beta) {
		MovOrd m = new MovOrd();
		if (nivel <= 0){// || esFinalJuego(false)){
			m.valor = heuristica(comeOrd);
			m.setMov(-1, -1, -1, -1);
			return m;
		}
		else {
			m.valor = -10000;
		for (int fila = tam -1; fila >= 0 && m.valor < beta; fila--)
			for (int columna = 0; columna < tam && m.valor < beta; columna++)
				if (tablero[fila][columna] == 1 || tablero[fila][columna] == 2){ 
					
					if (movPosible(tablero,fila + 1,columna - 1)){//oeste
						
						tablero[fila + 1][columna - 1] = tablero[fila][columna];
						tablero[fila][columna] = 0;
						comeOrd = false;
						
						MovOrd mAux = mueveUsr(nivel - 1, m.dameValor());
 						//int h = heuristica(false);
						//||(mAux.dameValor() == m.dameValor() && mAux.getProfundidad() < m.getProfundidad())
						if (mAux.dameValor() > m.dameValor()){
							m.setValor(mAux.dameValor());
							m.setMov(fila,columna,fila + 1,columna - 1);
						}
						
						tablero[fila][columna] = tablero[fila + 1][columna - 1];
						tablero[fila + 1][columna - 1] = 0;
					}
					else if (comprobarAtaque(fila,columna,fila + 2, columna - 2)){
						
						tablero[fila + 2][columna - 2] = tablero[fila][columna];
						int fichaComida = tablero[fila + 1][columna - 1];
						tablero[fila + 1][columna - 1] = 0;
						tablero[fila][columna] = 0;
						//int h = heuristica(true);
						comeOrd = true;
						MovOrd mAux = mueveUsr(nivel - 1,m.dameValor());
						
						if (mAux.dameValor() > m.dameValor()){
							m.setValor(mAux.dameValor());
							m.setMov(fila,columna,fila + 2,columna - 2);
						}
						
						tablero[fila][columna] = tablero[fila + 2][columna - 2];
						tablero[fila + 2][columna - 2] = 0;
						tablero[fila + 1][columna - 1] = fichaComida;
					}
					
					if (movPosible(tablero,fila + 1,columna + 1)){//este
						tablero[fila + 1][columna + 1] = tablero[fila][columna];
						tablero[fila][columna] = 0;
						comeOrd = false;
						MovOrd mAux = mueveUsr(nivel - 1,m.dameValor());
						
 						//int h = heuristica(false);
						
						if (mAux.dameValor() > m.dameValor()){
							m.setValor(mAux.dameValor());
							m.setMov(fila,columna,fila + 1,columna + 1);
						}
						
						tablero[fila][columna] = tablero[fila + 1][columna + 1];
						tablero[fila + 1][columna + 1] = 0;
					}	
					else if (comprobarAtaque(fila,columna,fila + 2, columna  + 2)){
						tablero[fila + 2][columna + 2] = tablero[fila][columna];
						int fichaComida = tablero[fila + 1][columna + 1];
						tablero[fila + 1][columna + 1] = 0;
						tablero[fila][columna] = 0;
						
						comeOrd = true;
						MovOrd mAux = mueveUsr(nivel - 1,m.dameValor());
 						//int h = heuristica(true);
						
						if (mAux.dameValor() > m.dameValor()){
							m.setValor(mAux.dameValor());
							m.setMov(fila,columna,fila + 2,columna + 2);
						}
						
						tablero[fila][columna] = tablero[fila + 2][columna + 2];
						tablero[fila + 2][columna + 2] = 0;
						tablero[fila + 1][columna + 1] = fichaComida;
					}
					
					if (tablero[fila][columna] == 2 && movPosible(tablero,fila - 1,columna - 1)){ //oeste Dama
						tablero[fila - 1][columna - 1] = tablero[fila][columna];
						tablero[fila][columna] = 0;
						
						comeOrd = false;
						MovOrd mAux = mueveUsr(nivel - 1,m.dameValor());
 						//int h = heuristica(false);
						
						if (mAux.dameValor() > m.dameValor()){
							m.setValor(mAux.dameValor());
							m.setMov(fila,columna,fila - 1,columna - 1);
						}
						
						tablero[fila][columna] = tablero[fila - 1][columna - 1];
						tablero[fila - 1][columna - 1] = 0;
					
					}
					else if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna - 2)){
						tablero[fila - 2][columna - 2] = tablero[fila][columna];
						int fichaComida = tablero[fila - 1][columna - 1];
						tablero[fila - 1][columna - 1] = 0;
						tablero[fila][columna] = 0;
						
						comeOrd = true;
						MovOrd mAux = mueveUsr(nivel - 1,m.dameValor());
 						//int h = heuristica(true);
						
						if (mAux.dameValor() > m.dameValor()){
							m.setValor(mAux.dameValor());
							m.setMov(fila,columna,fila - 2,columna - 2);
						}
						
						tablero[fila][columna] = tablero[fila - 2][columna - 2];
						tablero[fila - 2][columna - 2] = 0;
						tablero[fila - 1][columna - 1] = fichaComida;
					}
					
					if (tablero[fila][columna] == 2 && movPosible(tablero,fila - 1,columna + 1)){
						tablero[fila - 1][columna + 1] = tablero[fila][columna];
						tablero[fila][columna] = 0;
						
						comeOrd = true;
						MovOrd mAux = mueveUsr(nivel - 1,m.dameValor());
 						//int h = heuristica(false);
						
						if (mAux.dameValor() > m.dameValor()){
							m.setValor(mAux.dameValor());
							m.setMov(fila,columna,fila - 1,columna + 1);
						}
						
						tablero[fila][columna] = tablero[fila - 1][columna + 1];
						tablero[fila - 1][columna + 1] = 0;
					}
					else if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna + 2)){
						tablero[fila - 2][columna + 2] = tablero[fila][columna];
						int fichaComida = tablero[fila - 1][columna + 1];
						tablero[fila - 1][columna + 1] = 0;
						tablero[fila][columna] = 0;
						
						comeOrd = true;
						MovOrd mAux = mueveUsr(nivel - 1,m.dameValor());
 						//int h = heuristica(true);
						
						if (mAux.dameValor() > m.dameValor()){
							m.setValor(mAux.dameValor());
							m.setMov(fila,columna,fila - 2,columna + 2);
						}
						
						tablero[fila][columna] = tablero[fila - 2][columna + 2];
						tablero[fila - 2][columna + 2] = 0;
						tablero[fila - 1][columna + 1] = fichaComida;
					}
					
				}
			return m;
		}	
		
	}
	
	public MovOrd mueveUsr(int nivel,int alfa){
		MovOrd m = new MovOrd();
		if (nivel <= 0){//  || esFinalJuego(true)){
			m.valor = heuristica(comeOrd);
			m.setMov(-1, -1, -1, -1);
			return m;
		}
		else{
			m.valor = 1000;
			for (int fila = 0; fila < tam && m.valor > alfa; fila++)
				for (int columna = 0; columna < tam && m.valor > alfa; columna++)
					if (tablero[fila][columna] == 1 || tablero[fila][columna] == 2){ 
						
						if (movPosible(tablero,fila + 1,columna - 1)){//oeste
							
							tablero[fila + 1][columna - 1] = tablero[fila][columna];
							tablero[fila][columna] = 0;
							
							MovOrd mAux = mueveOrd(nivel - 1, m.dameValor());
	 						//int h = heuristica(false);
							
							if (mAux.dameValor() < m.dameValor()){
								m.setValor(mAux.dameValor());
								m.setMov(fila,columna,fila + 1,columna - 1);
							}
							
							tablero[fila][columna] = tablero[fila + 1][columna - 1];
							tablero[fila + 1][columna - 1] = 0;
						}
						else if (comprobarAtaque(fila,columna,fila + 2, columna - 2)){
							
							tablero[fila + 2][columna - 2] = tablero[fila][columna];
							int fichaComida = tablero[fila + 1][columna - 1];
							tablero[fila + 1][columna - 1] = 0;
							tablero[fila][columna] = 0;
							//int h = heuristica(true);
							MovOrd mAux = mueveOrd(nivel - 1,m.dameValor());
							
							if (mAux.dameValor() < m.dameValor()){
								m.setValor(mAux.dameValor());
								m.setMov(fila,columna,fila + 2,columna - 2);
							}
							
							tablero[fila][columna] = tablero[fila + 2][columna - 2];
							tablero[fila + 2][columna - 2] = 0;
							tablero[fila + 1][columna - 1] = fichaComida;
						}
						
						if (movPosible(tablero,fila + 1,columna + 1)){//este
							tablero[fila + 1][columna + 1] = tablero[fila][columna];
							tablero[fila][columna] = 0;
							MovOrd mAux = mueveOrd(nivel - 1,m.dameValor());
	 						//int h = heuristica(false);
							
							if (mAux.dameValor() < m.dameValor()){
								m.setValor(mAux.dameValor());
								m.setMov(fila,columna,fila + 1,columna + 1);
							}
							
							tablero[fila][columna] = tablero[fila + 1][columna + 1];
							tablero[fila + 1][columna + 1] = 0;
						}	
						else if (comprobarAtaque(fila,columna,fila + 2, columna  + 2)){
							tablero[fila + 2][columna + 2] = tablero[fila][columna];
							int fichaComida = tablero[fila + 1][columna + 1];
							tablero[fila + 1][columna + 1] = 0;
							tablero[fila][columna] = 0;
							MovOrd mAux = mueveOrd(nivel - 1,m.dameValor());
	 						//int h = heuristica(true);
							
							if (mAux.dameValor() < m.dameValor()){
								m.setValor(mAux.dameValor());
								m.setMov(fila,columna,fila + 2,columna + 2);
							}
							
							tablero[fila][columna] = tablero[fila + 2][columna + 2];
							tablero[fila + 2][columna + 2] = 0;
							tablero[fila + 1][columna + 1] = fichaComida;
						}
						
						if (tablero[fila][columna] == 2 && movPosible(tablero,fila - 1,columna - 1)){ //oeste Dama
							tablero[fila - 1][columna - 1] = tablero[fila][columna];
							tablero[fila][columna] = 0;
							MovOrd mAux = mueveOrd(nivel - 1,m.dameValor());
	 						//int h = heuristica(false);
							
							if (mAux.dameValor() < m.dameValor()){
								m.setValor(mAux.dameValor());
								m.setMov(fila,columna,fila - 1,columna - 1);
							}
							
							tablero[fila][columna] = tablero[fila - 1][columna - 1];
							tablero[fila - 1][columna - 1] = 0;
						
						}
						else if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna - 2)){
							tablero[fila - 2][columna - 2] = tablero[fila][columna];
							int fichaComida = tablero[fila - 1][columna - 1];
							tablero[fila - 1][columna - 1] = 0;
							tablero[fila][columna] = 0;
							MovOrd mAux = mueveOrd(nivel - 1,m.dameValor());
	 						//int h = heuristica(true);
							
							if (mAux.dameValor() < m.dameValor()){
								m.setValor(mAux.dameValor());
								m.setMov(fila,columna,fila - 2,columna - 2);
							}
							
							tablero[fila][columna] = tablero[fila - 2][columna - 2];
							tablero[fila - 2][columna - 2] = 0;
							tablero[fila - 1][columna - 1] = fichaComida;
						}
						
						if (tablero[fila][columna] == 2 && movPosible(tablero,fila - 1,columna + 1)){
							tablero[fila - 1][columna + 1] = tablero[fila][columna];
							tablero[fila][columna] = 0;
							MovOrd mAux = mueveOrd(nivel - 1,m.dameValor());
	 						//int h = heuristica(false);
							
							if (mAux.dameValor() < m.dameValor()){
								m.setValor(mAux.dameValor());
								m.setMov(fila,columna,fila - 1,columna + 1);
							}
							
							tablero[fila][columna] = tablero[fila - 1][columna + 1];
							tablero[fila - 1][columna + 1] = 0;
						}
						else if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna + 2)){
							tablero[fila - 2][columna + 2] = tablero[fila][columna];
							int fichaComida = tablero[fila - 1][columna + 1];
							tablero[fila - 1][columna + 1] = 0;
							tablero[fila][columna] = 0;
							MovOrd mAux = mueveOrd(nivel - 1,m.dameValor());
	 						//int h = heuristica(true);
							
							if (mAux.dameValor() < m.dameValor()){
								m.setValor(mAux.dameValor());
								m.setMov(fila,columna,fila - 2,columna + 2);
							}
							
							tablero[fila][columna] = tablero[fila - 2][columna + 2];
							tablero[fila - 2][columna + 2] = 0;
							tablero[fila - 1][columna + 1] = fichaComida;
						}
						
					}
			return m;
		}
	}

 	private int heuristica(boolean ataque) {
 		int h = 0;
 	if (ofensiva){
 		h = heuristicaOfensiva();
 		if (ataque) h += 5;
 		}
 	else if (defensiva) h = heuristicaDefensiva();
 
 	else if (miEstrategia){
 		int d = heuristicaDefensiva();
 		int o = heuristicaOfensiva();	
 		if (ataque) o += 5;
 		h = o + d;
 		}
 		
		return h;
 	
	}
 	
 	/*public boolean puedeSerComido(boolean defensa, int fila, int columna){
 		boolean j = jugador;
 		if (dameCasilla(fila,columna) == 3 || dameCasilla(fila,columna) == 4){
 			jugador = false;
 		}
 		if (dameCasilla(fila,columna) == 1 || dameCasilla(fila,columna) == 2){
 			jugador = true;
 		}
 		
 		if (!defensa){
 			if (comprobarAtaque(fila,columna,fila + 2, columna - 2)) return true;
 			else if (comprobarAtaque(fila,columna,fila + 2, columna  + 2)) return true;
 			else if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna - 2)) return true;
 			else if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna + 2)) return true;
 		}
 		else {
 			if (comprobarAtaque(fila,columna,fila - 2, columna - 2)) return true;
 			else if (comprobarAtaque(fila,columna,fila - 2, columna  + 2)) return true;
 			else if (tablero[fila][columna] == 4 && comprobarAtaque(fila,columna,fila + 2, columna - 2)) return true;
 			else if (tablero[fila][columna] == 4 && comprobarAtaque(fila,columna,fila + 2, columna + 2)) return true;
 		}
 		jugador = j;
 		return false;
 	}*/
 	
 	public int heuristicaOfensiva(){
 		int h = 0;
 		for(int fila = 0; fila < tam; fila++)
			for(int columna = 0; columna < tam; columna++){
				if (comprobarAtaque(fila,columna,fila + 2, columna - 2)) h++;
				else if (comprobarAtaque(fila,columna,fila + 2, columna  + 2)) h++;
				else if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna - 2)) h++;
				else if (tablero[fila][columna] == 2 && comprobarAtaque(fila,columna,fila - 2, columna + 2)) h++;
			}
 	return h;
 	}
 	
	public int heuristicaDefensiva(){
 		int h = 0;
 		boolean j = jugador;
			jugador = true;
			for(int fila = 0; fila < tam; fila++)
				for(int columna = 0; columna < tam; columna++){
					if (comprobarAtaque(fila,columna,fila - 2, columna - 2)) h--;
					else if (comprobarAtaque(fila,columna,fila - 2, columna  + 2)) h--;
					else if (tablero[fila][columna] == 4 && comprobarAtaque(fila,columna,fila + 2, columna - 2)) h--;
					else if (tablero[fila][columna] == 4 && comprobarAtaque(fila,columna,fila + 2, columna + 2)) h--;
				}
			jugador = j;
 		return h;
 	}

	private boolean movPosible(int[][] tablero,int fila, int columna) {
		if (0 <= fila && fila < tam && 0 <= columna && columna < tam)
			return tablero[fila][columna] == 0;
		return false;
	}
	
/*private boolean ataquePosible(int[][] tablero,int fila, int columna) {
		return  tablero[fila][columna] == 0;
	}*/	

	private void ejecutarMovOrd(int fila, int col, int filaDest, int colDest) {
		
		filaOrig = fila;
		colOrig = col;
		ficha = dameCasilla(fila,col);
	/*	if (filaDest - fila == 1 )//|| filaDest - fila == -1)
			realizarMovimiento(filaDest,colDest,false);
		else realizarMovimiento(filaDest,colDest,true);*/
		
		if ((filaDest == filaOrig + 1 || filaDest == filaOrig - 1) && (colDest == colOrig + 1 || colDest == colOrig -1))
			realizarMovimiento(filaDest,colDest,false);
		else if (comprobarAtaque(filaOrig,colOrig,filaDest,colDest)){
			//ficha = dameCasilla(filaDest,colDest);
			realizarMovimiento(filaDest,colDest,true);
			jugador = false;
			seguirTirando = true;
		
			while (seguirTirando){
				int fO = filaDest;
				int cO = colDest;
				//ficha = dameCasilla(fO,cO);
				if (comprobarAtaque(filaDest,colDest,filaDest + 2, colDest - 2)){
					filaDest += 2;
					colDest -= 2;
				}
				else if (comprobarAtaque(filaDest,colDest,filaDest + 2, colDest  + 2)){
					filaDest += 2;
					colDest += 2;
				}
				else if(tablero[filaDest][colDest] == 2 && comprobarAtaque(filaDest,colDest,filaDest - 2,colDest - 2)){
					filaDest -= 2;
					colDest -= 2;
				}
				else if(tablero[filaDest][colDest] == 2 && comprobarAtaque(filaDest,colDest,filaDest - 2,colDest + 2)){
					filaDest -= 2;
					colDest += 2;
				}
				else {seguirTirando = false;
					jugador = true;
				}
				
				if (seguirTirando)
					ejecutarMovOrd(fO,cO,filaDest,colDest);
				
			}
		}
		
	}
	
	public void setEstrategia(boolean d, boolean o, boolean m){
		defensiva = d;
		ofensiva = o;
		miEstrategia = m;
	}
	
	public void setNivel(int n){
		nivel = n;
	}


}
