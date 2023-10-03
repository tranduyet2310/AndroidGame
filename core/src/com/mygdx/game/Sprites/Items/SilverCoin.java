package com.mygdx.game.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.TileObjects.InteractiveTileObject;

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
        screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / MyGdxGame.PPM), GoldCoin.class));
        getCell().setTile(null);
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("SilverCoin", "Sword Collision");
    }
}
