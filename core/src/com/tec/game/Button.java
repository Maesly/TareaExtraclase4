package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Button {

    private float SCREEN_WIDTH = Gdx.graphics.getWidth(), SCREEN_HEIGHT = Gdx.graphics.getHeight();

    private TextureAtlas button_textures;
    private TextureRegion button_release, button_pressed, button_sprite;
    private Sprite sprite;
    private float radio_effect,x,y,width,height;
    private boolean pressed = false, released = false, next_screen = false;

    public Button(String button_src, float x, float y, float width, float height){
        button_textures =new TextureAtlas("button_textures.atlas");
        this.button_release = button_textures.findRegion(button_src+"_button");
        this.button_pressed = button_textures.findRegion(button_src+"_pressed_button");
        this.button_sprite = button_release;
        this.x=x-width/2;
        this.y=y-height/2;
        this.width=width;
        this.height=height;
        this.radio_effect = 1;
    }

    public void update (SpriteBatch spriteBatch){
        sprite = new Sprite(button_sprite);
        sprite.setSize(width, height);
        sprite.setPosition(x, y);
        sprite.draw(spriteBatch);
        checkIfClicked();
    }

    public boolean checkIfClicked (){
        boolean touched=false;
        released =false;
        pressed=false;
        button_sprite = button_release;

        for (int i = 0; i < 10; i++){
            int ix = Gdx.input.getX(i);
            int iy = -Gdx.input.getY(i) + Gdx.graphics.getHeight();
            if (Gdx.input.isTouched(i) && x < ix && y < iy && x + width > ix && y + height > iy){
                button_sprite = button_pressed;
                pressed=true;
            }
            if (Gdx.input.justTouched() && Gdx.input.isTouched(i) && x < ix && y < iy && x + width > ix && y + height > iy){
                released = true;
                next_screen =true;
                touched=true;
                sprite.setRegion(button_pressed);
                System.out.println("Button clicked!");
            }
        }

        return touched;
    }

    public void nextScreen(Game game, Screen screen, ShapeRenderer shapeRenderer, float[] color){
        if(next_screen) {
            radio_effect += (radio_effect/Math.hypot(SCREEN_WIDTH, SCREEN_HEIGHT))*SCREEN_WIDTH/4;
            shapeRenderer.setColor(color[0], color[1], color[2], 1);
            shapeRenderer.circle(x+width/2,y+height/2, radio_effect);
            if(radio_effect >= Math.hypot(SCREEN_WIDTH, SCREEN_HEIGHT)){
                next_screen = false;
                radio_effect = 1;
                game.setScreen(screen);
            }
        }
    }

    public void exitGame(){
        if(checkIfClicked()) {
            Gdx.app.exit();
        }
    }

    public boolean pressed() { return pressed; }
    public boolean released() { return released; }

    public void dipose(){
        button_textures.dispose();
    }
}
