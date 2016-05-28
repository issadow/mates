package damas;




import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Damas extends JPanel implements ActionListener
{

	int tamanio;
	int k;
	JTextArea nombreVer;
	JTextArea nombreVer1;
	JFrame frame;
	JLabel estado;
	String j1;
	String j2;
	String state;
	TableroFichas tablero;
	boolean defensa;
	boolean ataque;
	boolean  mi;
	public static void main(String[] args)
	{
		Damas dama= new Damas();	
	}

	public Damas()
	{
		j1="j1";
		j2="j2";
		tamanio=0;
		k=0;
		defensa=false;
		ataque = false;
		mi = false;
		tablero =new TableroFichas(this);
		JFrame app=new JFrame("DAMAS");
		app.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e)
		{
				app.setVisible(false);
			}
		});
		JPanel panelTablero=new JPanel();	
		app.setJMenuBar(getMenuPrincipal());
		panelTablero.setLayout(new BorderLayout());
		
	
		panelTablero.add(getTableroArriba(),BorderLayout.NORTH);
		panelTablero.add(tablero,BorderLayout.CENTER);
		panelTablero.add(getTableroAbajo(),BorderLayout.SOUTH);

		app.setContentPane(panelTablero);		
		app.setVisible(true);
		app.setEnabled(true);
		app.setSize(1000, 1000);
		}
	
	public JPanel getTableroAbajo()
	{
		JPanel estado=new JPanel();
		estado.setLayout(new BorderLayout());
		JButton boton1=new JButton(j1);
		JButton boton2=new JButton(j2);
		boton1.setBackground(java.awt.Color.red); 
		boton2.setBackground(java.awt.Color.blue); 
		estado.add(boton1,BorderLayout.WEST);
		estado.add(boton2,BorderLayout.EAST);
		return estado;	
	}
	
	public int getTamanio()
	{
		return tamanio;
	}
	public int getK()
	{
		return k;
	}
	public JPanel getTableroArriba()
	{
		JPanel panelAbajo =new JPanel();
		panelAbajo.setLayout(new BorderLayout());
		panelAbajo.add(getBoton(),BorderLayout.NORTH);
		panelAbajo.add(new JLabel());
		
		panelAbajo.add(getTexto());
		
		return panelAbajo;
	}
	
	public JLabel getTexto()
	{
		estado=new JLabel("estate");//poner el estado
		return estado;
		
	}
	
	public JButton getBoton()
	{
		JButton boton=new JButton("TERMINAR");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//reiniciar
				System.exit(1);
					}
		});
		
		return boton;
		
	}
	
	public JMenuBar getMenuPrincipal() {
		JMenuBar barra = new JMenuBar();
		barra.add(getMenu());
		return barra;
	}
	
	public JMenu getMenu()
	{
		JMenu menu =new JMenu("Opciones");
		menu.add(getOpcion1());
		menu.add(getOpcion2());
		menu.add(getOpcion3());
		menu.setEnabled(true);
		return menu;
		
	}
	
	public JMenuItem getOpcion1()
	{
		JMenuItem item1=new JMenuItem();
		item1.setText("Defensivo");
		item1.setEnabled(true);
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame = new JFrame();
				frame.setVisible(true);
				frame.setEnabled(true);
				frame.setTitle("Tama�o");
				frame.setSize(300, 200);
				frame.setContentPane(getPanel());		
			
				defensa=true;
				ataque = false;
				mi = false;
				tablero.setEstrategia(defensa,ataque,mi);
				}
		});
		return item1;
		}
	
	public JPanel getPanel()
	{
		
		JPanel panelP = new JPanel();
		FlowLayout fl = new FlowLayout();
		panelP.setLayout(fl);
		JLabel j1 = new JLabel("Introduce Tama�o /8/10/12 :");
		JLabel j2 = new JLabel("Introduce profundidad del �rbol:");
		nombreVer = new JTextArea("           ");
		nombreVer1 = new JTextArea("           ");
		panelP.add(j1);
		panelP.add(nombreVer);
		panelP.add(j2);
		panelP.add(nombreVer1);
		
		JButton boton = new JButton("LEER");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tamanio=Integer.parseInt(nombreVer.getText().replace(" ", ""));
				tablero.setTamanio(tamanio);
				k=Integer.parseInt(nombreVer1.getText().replace(" ", ""));
				tablero.setNivel(k);
				tablero.setEstrategia(defensa,ataque,mi);
				/////////// k PROFUNDIDADDDDD!!!!!!!!!!!!
				frame.setVisible(false);
				tablero.repaint();
			
					}
		});
		panelP.add(boton);
		
		return panelP;
		
	}
	public JMenuItem getOpcion2()
	{
		JMenuItem item2=new JMenuItem();
		item2.setText("Ofensivo");
		item2.setEnabled(true);
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame();
				frame.setVisible(true);
				frame.setEnabled(true);
				frame.setTitle("Tama�o");
				frame.setSize(300, 200);
				frame.setContentPane(getPanel());
				defensa= false;
				ataque = true;
				mi = false;
				tablero.setEstrategia(defensa,ataque,mi);
				
			}
		});
		return item2;
	}
	public JMenuItem getOpcion3()
	{
		JMenuItem item3=new JMenuItem();
		item3.setText("*Mi estrategia*");
		item3.setEnabled(true);
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame();
				frame.setVisible(true);
				frame.setEnabled(true);
				frame.setTitle("Tama�o");
				frame.setSize(300, 200);
				frame.setContentPane(getPanel());
				defensa=false;
				ataque = false;
				mi = true;
				tablero.setEstrategia(defensa,ataque,mi);
			}
		});
		return item3;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
