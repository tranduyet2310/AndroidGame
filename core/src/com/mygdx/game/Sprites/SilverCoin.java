package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class SilverCoin extends InteractiveTileObject {
    public SilverCoin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.SILVER_COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("SilverCoin", "Collision");
        setCategoryFilter(MyGdxGame.COLLECTED_BIT);
        getCell().setTile(null);
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("SilverCoin", "Sword Collision");
    }
}
