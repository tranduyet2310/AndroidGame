package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Items.ChestKey;
import com.mygdx.game.Sprites.Items.Maps.BigMap;
import com.mygdx.game.Sprites.Items.Maps.SmallMap1;
import com.mygdx.game.Sprites.Items.Maps.SmallMap2;
import com.mygdx.game.Sprites.Items.Maps.SmallMap3;
import com.mygdx.game.Sprites.Items.Maps.SmallMap4;
import com.mygdx.game.Sprites.Items.Poitions.BluePoition;
import com.mygdx.game.Sprites.Items.Poitions.GreenPoition;
import com.mygdx.game.Sprites.Items.Poitions.RedPoition;
import com.mygdx.game.Sprites.Items.RequestedItems.BlueDiamond;
import com.mygdx.game.Sprites.Items.RequestedItems.GoldCoin;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.RequestedItems.GoldenSkull;
import com.mygdx.game.Sprites.Items.RequestedItems.RedDiamond;
import com.mygdx.game.Sprites.Items.RequestedItems.SilverCoin;

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
        getCell().setTile(null);

        if (object.getProperties().containsKey("gold_coin")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), GoldCoin.class));
        } else if (object.getProperties().containsKey("silver_coin")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), SilverCoin.class));
        } else if (object.getProperties().containsKey("red_potion")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), RedPoition.class));
        } else if (object.getProperties().containsKey("blue_poition")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), BluePoition.class));
        } else if (object.getProperties().containsKey("green_poition")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), GreenPoition.class));
        } else if (object.getProperties().containsKey("small_map1")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), SmallMap1.class));
        } else if (object.getProperties().containsKey("small_map2")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), SmallMap2.class));
        } else if (object.getProperties().containsKey("small_map3")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), SmallMap3.class));
        } else if (object.getProperties().containsKey("small_map4")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), SmallMap4.class));
        } else if (object.getProperties().containsKey("big_map")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), BigMap.class));
        } else if (object.getProperties().containsKey("blue_diamond")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), BlueDiamond.class));
        } else if (object.getProperties().containsKey("golden_skull")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), GoldenSkull.class));
        } else if (object.getProperties().containsKey("red_diamond")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), RedDiamond.class));
        }else if (object.getProperties().containsKey("chest_key")) {
            screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), ChestKey.class));
        }
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("SpecialItem", "Sword Collision");
    }
}
