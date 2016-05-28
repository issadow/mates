package damas;

public class MovOrd {

	int profundidad;
	int valor;
	int filaOrig;
	int colOrig;
	int colDest;
	int filaDest;

	public void setValues(int k,int v, int filaO, int colO, int filaD, int colD) {
		profundidad = k;
		valor = v;
		filaOrig = filaO;
		colOrig = colO;
		filaDest = filaD;
		colDest = colD;
		profundidad = 1000;
		
	}

	public boolean hayMovimiento() {
		return filaDest != -1 && colOrig != -1;
	}
	
	public int setProfundidad(){
		return profundidad;
	}
	
	public int getProfundidad(){
		return profundidad;
	}

	public void setValor(int v){
		valor = v;
	}
	public int dameValor() {
		return valor;
	}

	public void setMov(int filaO, int colO, int filaD, int colD) {
		filaOrig = filaO;
		colOrig = colO;
		filaDest = filaD;
		colDest = colD;
		
	}

}
