package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected FixtureDef fixtureDef;
    protected BodyDef bodyDef;
    protected PolygonShape shape;
    protected PlayScreen screen;
    protected MapObject object;

    public InteractiveTileObject(PlayScreen screen, MapObject object) {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.object = object;
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.screen = screen;

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / Constants.PPM, (bounds.getY() + bounds.getHeight() / 2) / Constants.PPM);
        body = world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth() / 2 / Constants.PPM, bounds.getHeight() / 2 / Constants.PPM);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);

    }

    public abstract void onHeadHit();

    public abstract void onSwordHit();

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (body.getPosition().x * Constants.PPM / 32), (int) (body.getPosition().y * Constants.PPM / 32));
    }
}

