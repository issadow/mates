package damas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;




import damas.Damas;

public class TableroFichas extends Tablero implements MouseListener
{
	boolean pintar;
	Damas dama;
	Image[] imgFichas;
	int cont;
	//1 ficha roja //2 dama roja //3 ficha negra//4 dama negra //0 vacio
	ComprobarMov tablero;
	boolean segundoClick;
	boolean primeraJugada;
	int x;
	int y;
	int i;
	int j;
	boolean turno;//equipo rojo false // equipo azul true
	int fil,col;
//	ComprobadorMov movimiento;
	public TableroFichas(Damas dama)
	{
		col=0;
		fil=0;
		tablero = new ComprobarMov(dama.getTamanio());
		primeraJugada = true;
		segundoClick=false;
		turno=false;
		pintar=false;
		this.dama=dama;
		imgFichas =new Image[5];	
		this.addMouseListener(this);
		inicializarImagenes();
	
	}
	
	public void setTamanio(int tamanio){

		tablero = new ComprobarMov(tamanio);
	}
	
	public void update(Graphics g)
	{
			
		paint(g);
		
	}
	
	public void paint(Graphics g)
	{
		
		paint(g,tablero.jugador);
		pintarTablero(dama.getTamanio(),g);
		pintarFicha(g);
		pintarFichas(pintar,g,dama.getTamanio());
		
	}
	public void pintarFicha(Graphics g)
	{
		if(!tablero.getPrimerClick())
		{
			
			 
			if(dama.getTamanio()==8)
			{
				g.setColor(Color.yellow);
					int x=240+col*70;
					int y=30+fil*70;
					g.fillRect(x, y, 70, 70);
					
					
					
				  
			}
			 
			if(dama.getTamanio()==10)
			{
				g.setColor(Color.yellow);
				 int x=230+col*61;
				  int y=6+fil*61;
				  g.fillRect(x, y, 61, 61);
			}
			 
			if(dama.getTamanio()==12)
			{
				g.setColor(Color.yellow);
				 int x=190+col*50;
				  int y=6+fil*50;
				  g.fillRect(x, y, 50, 50);
			}
		}
		
		
	}
	public void pintarFichas(boolean b,Graphics g,int i)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, 180, 27);
		g.setColor(Color.white);
		g.drawString(tablero.getSTR(), 5, 15);
		g.setColor(Color.blue);
		int iniX,x,y,sumar,tam;
		if (!pintar){
			if (i == 8){
				tam = 50;
				iniX=x=251;
				 y=38;
				 sumar = 70;
			}
			else if (i == 10){
				tam = 48;
				 iniX = x=236;
				 y=12;
				 sumar = 61;
			}
			else {
				tam = 40;
				 iniX = x=195;
				 y=10;
				 sumar = 50;
			}
				
				  for(int n=0;n<i;n++)
					{	for(int m=0;m<i;m++)
						{
							int fichaPintar = tablero.dameCasilla(n,m);
							if (fichaPintar != 0)		
								g.drawImage(imgFichas[fichaPintar], x, y, tam, tam, this);
						
							x+=sumar;
	
						}
					y+=sumar;
					x=iniX;
					}						
			}

	}

	public void inicializarImagenes()
	{
		imgFichas[1]=getToolkit().getImage("C:\\Users\\altiqa\\Documents\\NetBeansProjects\\Matematicas\\src\\damas\\f2.gif");
		imgFichas[2]=getToolkit().getImage("C:\\Users\\altiqa\\Documents\\NetBeansProjects\\Matematicas\\src\\damas\\ficha_roja.png");//dama
		imgFichas[3]=getToolkit().getImage("C:\\Users\\altiqa\\Documents\\NetBeansProjects\\Matematicas\\src\\damas\\f.gif");
		imgFichas[4]=getToolkit().getImage("C:\\Users\\altiqa\\Documents\\NetBeansProjects\\Matematicas\\src\\damas\\ficha_negra.png");//dama
	}
	
	public void pintarTablero(int i,Graphics g)
	{
		if(i==8)
		{ boolean negro=true;
		  int x=240;
		  int y=30;
			for(int n=0;n<8;n++)
			{	for(int m=0;m<8;m++)
				{
					if (negro)
				
					{g.setColor(Color.black);
					g.fillRect(x, y, 70, 70);
					negro=!negro;
					}
					else
					{
					g.setColor(Color.white);
					g.fillRect(x, y, 70, 70);
					negro=!negro;
					}
					x+=70;
						
				}
			y+=70;
			negro=!negro;
			x=240;
			}
		}
		
		if(i==10)
		{
			 boolean negro=true;
			  int x=230;
			  int y=6;
			  for(int n=0;n<10;n++)
				{	for(int m=0;m<10;m++)
					{
						if (negro)
					
						{g.setColor(Color.black);
						g.fillRect(x, y, 61, 61);
						negro=!negro;
						}
						else
						{
						g.setColor(Color.white);
						g.fillRect(x, y, 61, 61);
						negro=!negro;
						}
						x+=61;
							
					}
				y+=61;
				negro=!negro;
				x=230;
				}	
		}
		if (i==12)
		{
			
			 boolean negro=true;
			  int x=190;
			  int y=6;
			  for(int n=0;n<12;n++)
				{	for(int m=0;m<12;m++)
					{
						if (negro)
					
						{g.setColor(Color.black);
						g.fillRect(x, y, 50, 50);
						negro=!negro;
						}
						else
						{
						g.setColor(Color.white);
						g.fillRect(x, y, 50, 50);
						negro=!negro;
						}
						x+=50;
	
					}
				y+=50;
				negro=!negro;
				x=190;
				}	
			
			
		}
		
			
		
	}
	

	public void dibujarExtra(Graphics g) {	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}

	public void mouseEntered(MouseEvent e) {	}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		 x=e.getX();
		 y=e.getY();
		 int columna = buscarFichaX(x,dama.getTamanio());
		 int fila = buscarFichaY(y,dama.getTamanio());
		
		 fil=fila;
		 col=columna;
		 if (fila != -1 && columna != -1){
			 tablero.procesarMovimiento(fila, columna);
			 fil=tablero.getFilaOrigen();
			 col=tablero.getColOrigen();
			 
			 repaint();
		 }
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public int buscarFichaX(int l,int tam)
	{  
		
		
		
		if(tam==8){
			
			if(l-240<0||(l-240)/70>=8)
				return -1;
			else
			
				return (l-240)/70;
		}
		if(tam==10)
		{

			if(l-230<0||(l-230)/61>=10)
				return -1;
			else
				return (l-240)/61; 
		}
		if(tam==12)
		{

			if(l-190<0||(l-190)/50>=12)
				return -1;
			else
				return (l-190)/50;
		}
		return -1;
		
	}
	public int buscarFichaY(int m,int tam)
	{
		

		if(tam==8){
			
			if(m-30<0||(m-30)/70>=8)
				return -1;
			else
				return (m-30)/70;
		}
		if(tam==10)
		{

			if(m-6<0||(m-6)/61>=10)
				return -1;
			else
				return (m-6)/61; 
		}
		if(tam==12)
		{

			if(m-6<0||(m-6)/50>=12)
				return -1;
			else
				return (m-6)/50;
		}
		return -1;
		
		
	}

	public void setEstrategia(boolean d, boolean a, boolean m){
		tablero.setEstrategia(d,a,m);
	}
	
	public void setNivel(int n){
		tablero.setNivel(n);
	}
	 

}
