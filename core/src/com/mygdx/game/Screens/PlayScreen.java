package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
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
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Constants;
import com.mygdx.game.Tools.MapManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scences.ConfirmDialog;
import com.mygdx.game.Scences.Hud;
import com.mygdx.game.Scences.HealthPowerBar;
import com.mygdx.game.Scences.Keyboard;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Items.Prize.ChestKey;
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
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.RequestedItems.GoldenSkull;
import com.mygdx.game.Sprites.Items.RequestedItems.RedDiamond;
import com.mygdx.game.Sprites.Items.RequestedItems.SilverCoin;
import com.mygdx.game.Sprites.Items.Prize.Treasure;
import com.mygdx.game.Sprites.NPC.NPC;
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
    private ConfirmDialog confirmDialog;
    private Keyboard keyboard;
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
    private HealthPowerBar healthPowerBar;
    //    Calculate map
    private TiledMapTileLayer mapTileLayer;
    private float evalHeight, evalWidth, totalMapWidth;

    //    Music
    private Sound sound;
    private Music music;
    private AudioManager audioManager;
    // Item
    private Array<Item> items;
    private LinkedBlockingDeque<ItemDef> itemsToSpawn;
    private Array<Enemy> enemies;
    private int currentLevel;
    private Utils utils;
    private InputMultiplexer inputMultiplexer;

    public PlayScreen(MyGdxGame game) {
//        atlas = new TextureAtlas("player.atlas");
        this.game = game;
        // Create cam used to follow player through cam world
        gameCam = new OrthographicCamera();
        // Create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM, gameCam);
        // Create our game HUD for timer/level info
        hud = new Hud(game.batch);
        confirmDialog = new ConfirmDialog(game.batch, game, PlayScreen.this);
        // Load our map and setup our map renderer
//        mapLoader = new TmxMapLoader();
//        map = mapLoader.load("map1.tmx");

        utils = Utils.getInstance();

        currentLevel = utils.getLevel();
        MapManager mapManager = new MapManager(game);
        map = mapManager.getMap();
        //
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
        playMusic();

        // get the width and height of map
        int mapWidthInTiles = mapTileLayer.getWidth();
//        int mapHeightInTiles = mapTileLayer.getHeight();
        // get the width and height of tile
        float tileWidth = mapTileLayer.getTileWidth();
//        float tileHeight = mapTileLayer.getTileHeight();
        // Caculate the width and height of the map in pixel
        totalMapWidth = mapWidthInTiles * tileWidth / Constants.PPM;
//        totalMapHeight = mapHeightInTiles * tileHeight;

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingDeque<ItemDef>();
        //
        healthPowerBar = new HealthPowerBar(game.batch, player);
        enemies = creator.getEnemies();
        // inital default value for flag
        utils.setPlayerOnWater(false);
        utils.setCompleteRequest(false);
        //
        keyboard = new Keyboard(game.batch, player);
        inputMultiplexer = new InputMultiplexer();
    }

    private void playMusic() {
        switch (currentLevel) {
            case 1:
            case 3:
                music = audioManager.getMusic(Constants.MUSIC_LEVEL2);
                music.setLooping(true);
                music.setVolume(MyGdxGame.MUSIC_VOLUME);
                break;
            case 2:
            case 4:
                music = audioManager.getMusic(Constants.MUSIC_LEVEL1);
                music.setLooping(true);
                music.setVolume(MyGdxGame.MUSIC_VOLUME);
                break;
            case 5:
                music = audioManager.getMusic(Constants.MUSIC_MENU);
                music.setLooping(true);
                music.setVolume(MyGdxGame.MUSIC_VOLUME);
                break;
        }

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
            } else if (idef.type == BigMap.class) {
                items.add(new BigMap(this, idef.position.x, idef.position.y));
            } else if (idef.type == GoldenSkull.class) {
                items.add(new GoldenSkull(this, idef.position.x, idef.position.y));
            } else if (idef.type == BlueDiamond.class) {
                items.add(new BlueDiamond(this, idef.position.x, idef.position.y));
            } else if (idef.type == BluePoition.class) {
                items.add(new BluePoition(this, idef.position.x, idef.position.y));
            } else if (idef.type == GreenPoition.class) {
                items.add(new GreenPoition(this, idef.position.x, idef.position.y));
            } else if (idef.type == RedPoition.class) {
                items.add(new RedPoition(this, idef.position.x, idef.position.y));
            } else if (idef.type == SmallMap1.class) {
                items.add(new SmallMap1(this, idef.position.x, idef.position.y));
            } else if (idef.type == SmallMap2.class) {
                items.add(new SmallMap2(this, idef.position.x, idef.position.y));
            } else if (idef.type == SmallMap3.class) {
                items.add(new SmallMap3(this, idef.position.x, idef.position.y));
            } else if (idef.type == SmallMap4.class) {
                items.add(new SmallMap4(this, idef.position.x, idef.position.y));
            } else if (idef.type == RedDiamond.class) {
                items.add(new RedDiamond(this, idef.position.x, idef.position.y));
            } else if (idef.type == ChestKey.class) {
                items.add(new ChestKey(this, idef.position.x, idef.position.y));
            }
        }
    }

    public void handleInput(float dt) {
        if (!player.isDead()) {
            // jump
            if (Gdx.input.isKeyPressed(Input.Keys.UP) && !hasJupmed) {
                player.b2Body.applyLinearImpulse(new Vector2(0, 4f), player.b2Body.getWorldCenter(), true);
                hasJupmed = true;
                sound = audioManager.getSound(Constants.SOUND_JUMP);
                long idSound = sound.play(0.5f);
                sound.setPitch(idSound, 1);
                sound.setLooping(idSound, false);
            }
            // jump + attack
            if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                player.isAirAttack = true;
                sound = audioManager.getSound(Constants.SOUND_ATTACK2);
                long idSound = sound.play(0.5f);
                sound.setLooping(idSound, false);
            } else player.isAirAttack = false;
            // no cumulative force
            if (player.b2Body.getLinearVelocity().y >= 4)
                player.b2Body.getLinearVelocity().y = 0;
            // fall to ground
            if (player.b2Body.getLinearVelocity().y == 0) {
                hasJupmed = false;
            }
            // moveright
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2Body.getLinearVelocity().x <= 2) {
                player.b2Body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2Body.getWorldCenter(), true);
            }
            // move left
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2Body.getLinearVelocity().x >= -2) {
                player.b2Body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2Body.getWorldCenter(), true);
            }
            // Attack
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                player.isAttacking = true;
                sound = audioManager.getSound(Constants.SOUND_ATTACK1);
                long idSound = sound.play(0.2f);
                sound.setLooping(idSound, false);
            } else player.isAttacking = false;
            // player dead
            if (player.getState() == Player.State.DEAD) {
                sound = audioManager.getSound(Constants.SOUND_DIE);
                long idSound = sound.play(0.5f);
                sound.setLooping(idSound, false);
            }
            //
            if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                player.attack();
                sound = audioManager.getSound(Constants.SOUND_ATTACK3);
                long idSound = sound.play(0.5f);
                sound.setLooping(idSound, false);
            }
        }
    }

    public void update(float dt) {
        // handle user input first
        handleInput(dt);
        handleSpwaningItems();
        // take 1 step in the physics simulation (60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);

        for (Enemy enemy : enemies) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 320 / Constants.PPM)
                enemy.b2body.setActive(true);
//            Gdx.app.log("Enemy", "Size: "+enemies.size);
            if (enemy.isDestroyed()) {
                enemies.removeValue(enemy, true);
//                Gdx.app.log("Enemy", "Size: "+enemies.size);
            }
        }

        for (Item item : items) {
            item.update(dt);
        }

        for (NPC npc : creator.getNpcs()) {
            npc.update(dt);
        }

        for (Treasure treasure : creator.getTreasures()) {
            treasure.update(dt);
        }

        hud.update(dt);
        confirmDialog.update(dt);
        healthPowerBar.update(dt);
        keyboard.update(dt);

        // attach our gamecam to our player.x and player.y coordinate
//        gameCam.position.x = player.b2Body.getPosition().x;
//        gameCam.position.y = player.b2Body.getPosition().y;
        if (player.b2Body.getPosition().x >= gameCam.viewportWidth / 2 && player.b2Body.getPosition().x <= totalMapWidth - gameCam.viewportWidth / 2)
            gameCam.position.x = player.b2Body.getPosition().x;
        else if (player.b2Body.getPosition().x >= totalMapWidth - gameCam.viewportWidth / 2 && player.b2Body.getPosition().x <= totalMapWidth) {
            // Out of the boundary
            Gdx.app.log("PlayScreen", "Hold camera in tiledMap");
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
        inputMultiplexer.addProcessor(keyboard.stage);
        inputMultiplexer.addProcessor(confirmDialog.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
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
        for (Enemy enemy : enemies) {
            enemy.draw(game.batch);
        }

        for (Item item : items) {
            item.draw(game.batch);
        }

        for (NPC npc : creator.getNpcs()) {
            npc.draw(game.batch);
        }

        for (Treasure treasure : creator.getTreasures()) {
            treasure.draw(game.batch);
        }

        game.batch.end();

        // Set our batch to now draw what the Hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        healthPowerBar.stage.act();
        healthPowerBar.stage.draw();
        keyboard.stage.act();
        keyboard.stage.draw();
        confirmDialog.stage.act();
        confirmDialog.stage.draw();
        //
        if (gameOver() || (hud.getWorldTimer() == 0)) {
            this.dispose();
            game.setScreen(new GameOverScreen(game));
            return;
        }
        if (isLevelCompleted()) {
            Preferences prefs = Gdx.app.getPreferences("mygdxgame");

            String scoreLabel = "score" + currentLevel;
            int currentScore = 300 - hud.getWorldTimer();
            int highScore = prefs.getInteger(scoreLabel, 300);

            prefs.putInteger("levelPassed", currentLevel);
            prefs.flush();

            if (currentScore < highScore) {
                prefs.putInteger(scoreLabel, currentScore);
                prefs.flush();
            }
            Gdx.app.log("Hud", "in Preferences -label:" + scoreLabel + "-value:" + currentScore);

            utils.resetFlag(currentLevel);
            utils.setLevel(++currentLevel);
            this.dispose();
            game.setScreen(new LevelCompletedScreen(game));
        }
    }

    public boolean gameOver() {
        return player.currentState == Player.State.DEAD && player.getStateTimer() > 3;
    }

    public boolean isLevelCompleted() {
        return utils.isCompleteRequest();
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
        confirmDialog.dispose();
        healthPowerBar.dispose();
        keyboard.dispose();
    }
}
