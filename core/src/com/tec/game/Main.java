package com.tec.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Button;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main extends ApplicationAdapter {
	int count;
	SpriteBatch batch;
	ShapeRenderer shape;
	com.mygdx.game.Button siguiente;
	Casilla[][] tablero;
	public float SCREEN_WIDTH , SCREEN_HEIGHT;
	public int tamano_casilla;
	ResolverLaberinto maze ;
	int laberinto[][] =
			{{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1}};


	@Override
	public void create () {
		// Ancho y largo de la pantalla
		SCREEN_WIDTH = 720;//Gdx.graphics.getWidth();
		SCREEN_HEIGHT = 1280;//Gdx.graphics.getHeight();
		//Creamos el objeto boton
		siguiente = new Button("play",SCREEN_WIDTH+400,100,200,125);
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		//Matriz que contiene las casillas
		tablero = new Casilla[8][8];
		tamano_casilla = 75;
		count = 2;
		maze = new ResolverLaberinto();
		initializeTablero(SCREEN_WIDTH/2-200,SCREEN_HEIGHT/2-550,tamano_casilla);

	}

	/**
	 * @brief Inicializa el tablero del laberinto con todas las casillas en blanco junto con las casillas de llegada y salida
	 * @param x Posicion x en la pantalla
	 * @param y Posicion y en la pantalla
	 * @param tamano_casilla Tamaño de cada casilla
	 */
	public void initializeTablero(float x, float y, float tamano_casilla){
		float dirx = x;
		float diry = y;
		Casilla casilla_temp;
		for (int fila = 0; fila < 8; fila++) {
			for (int columna = 0; columna < 8 ; columna++) {
				casilla_temp = new Casilla(dirx+ columna*tamano_casilla,diry+(7-fila)*tamano_casilla, tamano_casilla,tamano_casilla);
				if(fila == 0 && columna == 0){
					casilla_temp.setColor(4);
				}else if(fila == 7 && columna == 7){
					casilla_temp.setColor(1);
				}else{
					casilla_temp.setColor(0);
				}
				dirx = dirx + 10;

				tablero[fila][columna] = casilla_temp;
			}
			dirx = x;
			diry = diry - 10;
		}
	}

	/**
	 * Bloquea dos casillas del laberinto cada vez que es llamado
	 */
	public void bloquearLaberinto(){

		Random random = new Random();
		int rand1 = random.nextInt(8);
		int rand2 = random.nextInt(8);

		while (count != 0){
			if((rand1 != 0 && rand2 != 0) || (rand1 != 7 && rand2 != 7)){

				if(laberinto[rand1][rand2] == 1){
					laberinto[rand1][rand2] = 0;
					tablero[rand1][rand2].setColor(2);
					count--;
				}

			}
			rand1 = random.nextInt(8);
			rand2 = random.nextInt(8);

		}
	}

	public void pintarSolucion(int tableroSol[][], int color){
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(tableroSol[i][j] == 1){
					tablero[i][j].setColor(color);
				}
			}
		}
		tablero[0][0].setColor(4);
		tablero[7][7].setColor(1);
	}
	public void setLaberinto(){

	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(83f/255f, 84f/255f, 71f/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(siguiente.checkIfClicked()){
			pintarSolucion(laberinto, 0);
			bloquearLaberinto();
			System.out.println("Entré al botón");
			try {

				if(maze.resolverLaberinto(laberinto)){
					pintarSolucion(maze.getSolucionFinal(),3);
					//TimeUnit.SECONDS.sleep(1);
					count += 2;
				}
			}catch (Exception e){}
		}

		shape.begin(ShapeRenderer.ShapeType.Filled);

		for (int f = 0; f < 8; f++) {
			for (int c = 0; c < 8; c++) {
				tablero[f][c].draw(shape);

			}
		}
		shape.end();

		batch.begin();
		//Actualiza el boton cada vez que se presiona
		siguiente.update(batch);
		batch.end();

	}
	
	@Override
	public void dispose () { batch.dispose(); }
}
