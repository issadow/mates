package damas;

public class TableroJuego {
	protected int tablero[][];
	int numFilas,numCol;
//	int libre = 0;
	
	public TableroJuego(int tam){
		numFilas = tam;
		numCol = tam;
		tablero = new int[numFilas][numCol];
	}
	
	public int dameCasilla(int fila, int columna){
		 return tablero[fila][columna];
	}

	public int ponCasilla(int fila, int columna, int ficha){
		int ant = tablero[fila][columna];
		tablero[fila][columna] = ficha;
		return ant;
	}
	
	public void inicializar(){
		tablero = new int[numFilas][numCol];
		
		for (int i = 0; i < numCol; i++)
			for (int k = 0; k < 3; k++){
				if ((i % 2 == 0 && k % 2 == 0) || (i % 2 == 1 && k % 2 == 1))
					tablero[k][i] = 1;
				else
					tablero[numCol-1-k][i] = 3;
			}
		
	}
}
