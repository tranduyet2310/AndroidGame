package com.mygdx.game.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroy;
    protected Body body;

    public Item(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setBounds(getX(), getY(), 32 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        defineItem();
        toDestroy = false;
        destroy = false;
    }

    public abstract void defineItem();

    public abstract void use();
    public void update(float dt){
        if(toDestroy && !destroy){
            world.destroyBody(body);
            destroy = true;
        }
    }
    @Override
    public void draw(Batch batch) {
        if (!destroy)
            super.draw(batch);
    }
    public void destroy(){
        toDestroy = true;
    }
}
