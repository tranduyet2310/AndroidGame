package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.StateManager;

public class Water extends InteractiveTileObject{
    public Water(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Water", "Collision");
        StateManager.playerOnWater = true;
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("Water", "Sword Collision");
    }
}
