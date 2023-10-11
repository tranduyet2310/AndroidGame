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
import com.mygdx.game.Sprites.Enemies.BlueTotem;
import com.mygdx.game.Sprites.Enemies.Crabby;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.GreenTotem;
import com.mygdx.game.Sprites.Enemies.PinkStar;
import com.mygdx.game.Sprites.Enemies.RedTotem;
import com.mygdx.game.Sprites.Enemies.Seashell;
import com.mygdx.game.Sprites.Enemies.Shark;
import com.mygdx.game.Sprites.Items.Treasure;
import com.mygdx.game.Sprites.NPC.BaldPirate;
import com.mygdx.game.Sprites.NPC.BigGuy;
import com.mygdx.game.Sprites.NPC.BombGuy;
import com.mygdx.game.Sprites.NPC.Captain;
import com.mygdx.game.Sprites.NPC.NPC;
import com.mygdx.game.Sprites.NPC.Unknown;
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
    private Array<PinkStar> pinkStars;
    private Array<RedTotem> redTotems;
    private Array<GreenTotem> greenTotems;
    private Array<BlueTotem> blueTotems;
    private Array<Seashell> seashells;
    private Array<Treasure>  treasures;
    private Player player;
    private Utils utils;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        utils = Utils.getInstance();
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
            if (utils.isIsMap1()) {
                npcs.add(new BombGuy(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
            } else if (utils.isIsMap2()) {
                npcs.add(new BigGuy(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
            }else if (utils.isIsMap3()) {
                npcs.add(new BaldPirate(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
            }else if (utils.isIsMap4()) {
                npcs.add(new Captain(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
            }else if (utils.isIsMap5()) {
                npcs.add(new Unknown(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
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
        // Create PinkStar boides fixture
        pinkStars = new Array<PinkStar>();
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            pinkStars.add(new PinkStar(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
        // Create RedTotem boides fixture
        redTotems = new Array<RedTotem>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            redTotems.add(new RedTotem(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
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
        // Create GreenTotem boides fixture
        greenTotems = new Array<GreenTotem>();
        for (MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            greenTotems.add(new GreenTotem(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
        // Create BlueTotem boides fixture
        blueTotems = new Array<BlueTotem>();
        for (MapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            blueTotems.add(new BlueTotem(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
        // Create Seashell boides fixture
        seashells = new Array<Seashell>();
        for (MapObject object : map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            seashells.add(new Seashell(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
        // Create Treasure boides fixture
        treasures = new Array<Treasure>();
        for (MapObject object : map.getLayers().get(16).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            treasures.add(new Treasure(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
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
        enemies.addAll(pinkStars);
        enemies.addAll(blueTotems);
        enemies.addAll(greenTotems);
        enemies.addAll(redTotems);
        enemies.addAll(seashells);
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

    public Array<Treasure> getTreasures() {
        return treasures;
    }
}
