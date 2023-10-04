package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AudioManager;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scences.Hud;
import com.mygdx.game.Scences.LifeBar;
import com.mygdx.game.Scences.StatusBar;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Items.GoldCoin;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.SilverCoin;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.Utils;
import com.mygdx.game.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingDeque;

public class PlayScreen implements Screen {
    private MyGdxGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private StatusBar statusBar;
    //    Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //    Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    //    Character
    private Player player;
    private boolean hasJupmed = false;
    private LifeBar lifeBar;
    //    Calculate map
    private TiledMapTileLayer mapTileLayer;
    private float evalHeight, evalWidth, totalMapWidth;
//    public static final Float minZoom = 0.1f;
//    public static final Float maxZoom = 1f;

    //    Music
    private Sound sound;
    private Music music;
    private AudioManager audioManager;
    // Item
    private Array<Item> items;
    private LinkedBlockingDeque<ItemDef> itemsToSpawn;

    public PlayScreen(MyGdxGame game) {
//        atlas = new TextureAtlas("player.atlas");
        this.game = game;
        // Create cam used to follow player through cam world
        gameCam = new OrthographicCamera();
        // Create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM, gameCam);
        // Create our game HUD for timer/level info
        hud = new Hud(game.batch);
        statusBar = new StatusBar(game.batch, game);
        // Load our map and setup our map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1.tmx");
        mapTileLayer = (TiledMapTileLayer) map.getLayers().get(1);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);

        // initially set our gameCam to be centered correctly at the start of
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

//        player = new Player(this);
        player = creator.getPlayer();

        world.setContactListener(new WorldContactListener());

        audioManager = AudioManager.getInstance();
        music = audioManager.getMusic(Utils.MUSIC_LEVEL2);
        music.setLooping(true);
        music.setVolume(MyGdxGame.MUSIC_VOLUME);


        // get the width and height of map
        int mapWidthInTiles = mapTileLayer.getWidth();
//        int mapHeightInTiles = mapTileLayer.getHeight();
        // get the width and height of tile
        float tileWidth = mapTileLayer.getTileWidth();
//        float tileHeight = mapTileLayer.getTileHeight();
        // Caculate the width and height of the map in pixel (Fix sẵn độ dài và rộng mã khi thiết kế rồi nền để nguyên luôn)
        totalMapWidth = mapWidthInTiles * tileWidth / Constants.PPM;
//        totalMapHeight = mapHeightInTiles * tileHeight;

//        evalWidth = gamePort.getWorldWidth() * gameCam.zoom;
//        evalHeight = gamePort.getWorldHeight() * gameCam.zoom;
//
//        gameCam.position.x = MathUtils.clamp(gameCam.position.x, evalWidth / 2f, totalMapWidth - evalWidth / 2f);
//        gameCam.position.y = MathUtils.clamp(gameCam.position.y, evalHeight / 2f, MyGdxGame.V_HEIGHT - evalHeight / 2f);
//        gameCam.zoom = MathUtils.clamp(gameCam.zoom, minZoom, maxZoom);
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingDeque<ItemDef>();
        //
        lifeBar = new LifeBar(game.batch, player);
    }

    public void spwanItem(ItemDef idef) {
        itemsToSpawn.add(idef);
    }

    public void handleSpwaningItems() {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == GoldCoin.class) {
                items.add(new GoldCoin(this, idef.position.x, idef.position.y));
            } else if (idef.type == SilverCoin.class) {
                items.add(new SilverCoin(this, idef.position.x, idef.position.y));
            }

        }
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && !hasJupmed) {
            player.b2Body.applyLinearImpulse(new Vector2(0, 4f), player.b2Body.getWorldCenter(), true);
            hasJupmed = true;
            sound = audioManager.getSound(Utils.SOUND_JUMP);
            long idSound = sound.play(0.5f);
            sound.setPitch(idSound, 1);
            sound.setLooping(idSound, false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.isAirAttack = true;
            sound = audioManager.getSound(Utils.SOUND_ATTACK2);
            long idSound = sound.play(0.5f);
            sound.setLooping(idSound, false);
        } else player.isAirAttack = false;

        if (player.b2Body.getLinearVelocity().y >= 4)
            player.b2Body.getLinearVelocity().y = 0;

        if (player.b2Body.getLinearVelocity().y == 0) {
            hasJupmed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2Body.getLinearVelocity().x <= 2) {
            player.b2Body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2Body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2Body.getLinearVelocity().x >= -2) {
            player.b2Body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2Body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.isAttacking = true;
            sound = audioManager.getSound(Utils.SOUND_ATTACK1);
            long idSound = sound.play(0.5f);
            sound.setLooping(idSound, false);
        } else player.isAttacking = false;

    }

    public void update(float dt) {
        // handle user input first
        handleInput(dt);
        handleSpwaningItems();
        // take 1 step in the physics simulation (60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);

        for (Enemy enemy : creator.getCrabbies()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 320 / Constants.PPM)
                enemy.b2body.setActive(true);
        }

        for (Item item : items) {
            item.update(dt);
        }

        hud.update(dt);
        statusBar.update(dt);
        lifeBar.update(dt);

        // attach our gamecam to our player.x and player.y coordinate
//        gameCam.position.x = player.b2Body.getPosition().x;
//        gameCam.position.y = player.b2Body.getPosition().y;
        if (player.b2Body.getPosition().x >= gameCam.viewportWidth / 2 && player.b2Body.getPosition().x <= totalMapWidth - gameCam.viewportWidth / 2)
            gameCam.position.x = player.b2Body.getPosition().x;
        else if (player.b2Body.getPosition().x >= totalMapWidth - gameCam.viewportWidth / 2 && player.b2Body.getPosition().x <= totalMapWidth) {
            // Out of the boundary
        }
        // update our gameCam with correct coordinates after changes
        gameCam.update();
        // tell our renderer to draw only what our camera can see in our game world
        renderer.setView(gameCam);
    }

    @Override
    public void show() {
        if (MyGdxGame.IS_MUSIC_ENABLED)
            music.play();
    }

    @Override
    public void render(float delta) {
        // separate our update logic from render
        update(delta);

        // Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Renders all the layers of the map
        renderer.render();

        // Renderer our Box2DDebugLines
        b2dr.render(world, gameCam.combined);

        //
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);

        for (Enemy enemy : creator.getCrabbies()) {
            enemy.draw(game.batch);
        }

        for (Item item : items) {
            item.draw(game.batch);
        }

        game.batch.end();

        // Set our batch to now draw what the Hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        statusBar.stage.act();
        statusBar.stage.draw();
        lifeBar.stage.act();;
        lifeBar.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {
        music.stop();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        Gdx.app.log("PlayScreen", "inDispose()");
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        music.stop();
        statusBar.dispose();
        lifeBar.dispose();
//        music.dispose();
//        sound.dispose();
    }
}
