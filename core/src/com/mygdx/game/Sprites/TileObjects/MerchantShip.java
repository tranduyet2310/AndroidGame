package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;

public class MerchantShip extends InteractiveTileObject{
    public MerchantShip(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("MerchantShip", "Collision");
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("MerchantShip", "Sword Collision");
    }
}
