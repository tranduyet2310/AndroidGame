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
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.TileObjects.MerchantShip;
import com.mygdx.game.Sprites.TileObjects.SpecialItem;
import com.mygdx.game.Sprites.TileObjects.Spike;
import com.mygdx.game.Sprites.TileObjects.Water;

public class B2WorldCreator {
    private Array<Crabby> crabbies;
    private Array<Shark> sharks;
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
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / Constants.PPM, rect.getHeight() / 2 / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        // Create water bodies fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            new Water(screen, object);
        }
        // Create spike boides fixture
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            new Spike(screen, object);
        }
//        // Create NPC boides fixture
//        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
//            new SpecialItem(screen, object);
//        }
        // Create special item boides fixture
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            new SpecialItem(screen, object);
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
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            new MerchantShip(screen, object);
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

    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(sharks);
        enemies.addAll(crabbies);
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }
}
