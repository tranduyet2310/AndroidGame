package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Tools.Utils;

public class Water extends InteractiveTileObject{
    public Water(PlayScreen screen, MapObject object) {
        super(screen, object);
        setCategoryFilter(Constants.WATER_BIT);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Water", "Collision");
        Utils.setPlayerOnWater(true);
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("Water", "Sword Collision");
    }

}
