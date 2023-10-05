package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Items.GoldCoin;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.SilverCoin;

public class SpecialItem extends InteractiveTileObject {
    public SpecialItem(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Constants.SPECIAL_ITEM_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("SpecialItem", "Collision");
        setCategoryFilter(Constants.COLLECTED_BIT);
        if(object.getProperties().containsKey("gold_coin")){
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), GoldCoin.class));
        } else if (object.getProperties().containsKey("silver_coin")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), SilverCoin.class));
        }

        getCell().setTile(null);
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("SpecialItem", "Sword Collision");
    }
}
