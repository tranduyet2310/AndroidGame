package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Spike extends InteractiveTileObject{
    public Spike(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        setCategoryFilter(MyGdxGame.SPIKE_BIT);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Spike", "Collision");
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("Spike", "Sword Collision");
    }
}
