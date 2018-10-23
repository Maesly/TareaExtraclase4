package com.tec.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Casilla {

    int color;
    private float x,y,ancho,largo;

    public Casilla(float x,float y, float ancho,float largo){
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.largo = largo;

    }

    public void draw(ShapeRenderer shapeRenderer){
        if(color == 0){ //Una casilla normal
            shapeRenderer.setColor(Color.WHITE);
        }else if(color == 1){ //Casilla de llegada
            shapeRenderer.setColor(Color.RED);
        }else if(color == 2){ //Casilla bloqueada
            shapeRenderer.setColor(Color.BLACK);
        }else if(color == 3){ //Casillas de la ruta
            shapeRenderer.setColor(Color.MAGENTA);
        }
        else{ //Casilla de salida
            shapeRenderer.setColor(Color.BLUE);
        }
        shapeRenderer.rect(x,y,ancho,largo);
    }

    /**
     * Cambia el color de las casillas
     * @param color
     */
    public void setColor(int color){this.color = color;}
}
