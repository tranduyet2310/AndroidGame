package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;

public class Spike extends InteractiveTileObject{
    public Spike(PlayScreen screen, MapObject object){
        super(screen, object);
        setCategoryFilter(Constants.SPIKE_BIT);
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
