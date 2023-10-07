package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Crabby;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.Shark;
import com.mygdx.game.Sprites.NPC.BigGuy;
import com.mygdx.game.Sprites.NPC.BombGuy;
import com.mygdx.game.Sprites.NPC.NPC;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.TileObjects.Ground;
import com.mygdx.game.Sprites.TileObjects.MerchantShip;
import com.mygdx.game.Sprites.TileObjects.SpecialItem;
import com.mygdx.game.Sprites.TileObjects.Spike;
import com.mygdx.game.Sprites.TileObjects.Water;

public class B2WorldCreator {
    private Array<Crabby> crabbies;
    private Array<Shark> sharks;
    private Array<NPC> npcs;
    private Array<Ground> grounds;
    private Array<Water> waters;
    private Array<Spike> spikes;
    private Array<SpecialItem> specialItems;
    private Array<MerchantShip> merchantShips;
    private Player player;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Create ground bodies fixtures
        grounds = new Array<Ground>();
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            grounds.add(new Ground(screen, object));
        }
        // Create water bodies fixtures
        waters = new Array<Water>();
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            waters.add(new Water(screen, object));
        }
        // Create spike boides fixture
        spikes = new Array<Spike>();
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            spikes.add(new Spike(screen, object));
        }
        // Create NPC boides fixture
        npcs = new Array<NPC>();
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            if (Utils.isIsMap1()) {
                npcs.add(new BombGuy(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
            } else if (Utils.isIsMap2()) {
                npcs.add(new BigGuy(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
            }
        }
        // Create special item boides fixture
        specialItems = new Array<SpecialItem>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            specialItems.add(new SpecialItem(screen, object));
        }
        // Create Crabby boides fixture
        crabbies = new Array<Crabby>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            crabbies.add(new Crabby(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
//        // Create box boides fixture
//        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
//            new SpecialItem(screen, object);
//        }
//        // Create barrel boides fixture
//        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
//            new SpecialItem(screen, object);
//        }
        // Create merchant ship boides fixture
        merchantShips = new Array<MerchantShip>();
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            merchantShips.add(new MerchantShip(screen, object));
        }
        // Create player boides fixture
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            player = new Player(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM);
        }
        // Create Shark boides fixture
        sharks = new Array<Shark>();
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            sharks.add(new Shark(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
    }

    public Array<Crabby> getCrabbies() {
        return crabbies;
    }

    public Array<Shark> getSharks() {
        return sharks;
    }

    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(sharks);
        enemies.addAll(crabbies);
        return enemies;
    }

    public Array<Ground> getGrounds() {
        return grounds;
    }

    public Array<Water> getWaters() {
        return waters;
    }

    public Array<Spike> getSpikes() {
        return spikes;
    }

    public Array<SpecialItem> getSpecialItems() {
        return specialItems;
    }

    public Array<MerchantShip> getMerchantShips() {
        return merchantShips;
    }

    public Array<NPC> getNpcs() {
        return npcs;
    }

    public Player getPlayer() {
        return player;
    }
}
