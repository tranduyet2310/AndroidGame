package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Utils;

public class MapScreen implements Screen, InputProcessor {
    private MyGdxGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Rectangle rectLustel, rectRodaphite, rectTarish, rectTuran, rectAstarte, rectBackButton;
    private Stage stage, backStage, levelStage;
    private Dialog dialog, levelDialog;
    InputMultiplexer inputMultiplexer;
    private boolean hasClicked, hasTouched;
    private Music music;
    private AudioManager audioManager;
    private Utils utils;

    public MapScreen(final MyGdxGame game) {
        this.game = game;
        utils = Utils.getInstance();
        hasClicked = false;
        hasTouched = false;

        // Create cam used to follow player through cam world
        gameCam = new OrthographicCamera();
        // Create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM, gameCam);
        // Load our map and setup our map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("world_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);
        // initially set our gameCam to be centered correctly at the start of
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        initBody();

        inputMultiplexer = new InputMultiplexer();
        stage = new Stage();
        backStage = new Stage();
        levelStage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        dialog = new Dialog("Notify", skin);
        Label label = new Label("The land has not been unlocked yet!", skin, "title");
        Label back = new Label("Back", skin, "title");
        TextButton button = new TextButton("Close", skin, "round");

        dialog.text(label);
        dialog.button(button, true);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hasClicked = false;
                dialog.hide();
            }
        });
        dialog.show(stage);
        table.add(back).pad(15f, 0, 0, 55f);

        levelDialog = new Dialog("", skin);
        Texture background = new Texture(Gdx.files.internal("resource/bg_level.png"));
        Image backgroundImage = new Image(background);
        levelDialog.setBackground(backgroundImage.getDrawable());

        Label levels = new Label("LEVELS", skin, "title");
        Label lvMap1 = new Label("LEVEL 1", skin, "title");
        Label lvMap2 = new Label("LEVEL 2", skin, "title");
        Label lvMap3 = new Label("LEVEL 3", skin, "title");
        Label lvMap4 = new Label("LEVEL 4", skin, "title");
        Label lvMap5 = new Label("LEVEL 5", skin, "title");

        TextButton btnMap1 = new TextButton("PLAY", skin);
        TextButton btnMap2 = new TextButton("PLAY", skin);
        TextButton btnMap3 = new TextButton("PLAY", skin);
        TextButton btnMap4 = new TextButton("PLAY", skin);
        TextButton btnMap5 = new TextButton("PLAY", skin);

        TextButton btnCancle = new TextButton("CANCEL", skin, "round");

        Table contentTable = levelDialog.getContentTable();
//        contentTable.setDebug(true);

        contentTable.add(levels).colspan(2).padBottom(5f);
        contentTable.row();
        contentTable.add(lvMap1).padRight(10f);
        contentTable.add(btnMap1).padLeft(10f);
        contentTable.row();
        contentTable.add(lvMap2).padRight(10f);
        contentTable.add(btnMap2).padLeft(10f);
        contentTable.row();
        contentTable.add(lvMap3).padRight(10f);
        contentTable.add(btnMap3).padLeft(10f);
        contentTable.row();
        contentTable.add(lvMap4).padRight(10f);
        contentTable.add(btnMap4).padLeft(10f);
        contentTable.row();
        contentTable.add(lvMap5).padRight(10f);
        contentTable.add(btnMap5).padLeft(10f);

        levelDialog.getButtonTable().add(btnCancle);

        levelDialog.setPosition((stage.getWidth() - levelDialog.getWidth()) / 2, (stage.getHeight() - levelDialog.getHeight()) / 2);

        btnMap1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                utils.setLevel(1);
//                game.setScreen(new PlayScreen(game));
                game.setScreen(new StorylineScreen(game));
            }
        });
        btnMap2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (utils.getLevelPassed() >= 1) {
                    utils.setLevel(2);
                    game.setScreen(new StorylineScreen(game));
                }
            }
        });
        btnMap3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (utils.getLevelPassed() >= 2) {
                    utils.setLevel(3);
                    game.setScreen(new StorylineScreen(game));
                }
            }
        });
        btnMap4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(utils.getLevelPassed() >=3){
                    utils.setLevel(4);
                    game.setScreen(new StorylineScreen(game));
                }
            }
        });
        btnMap5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(utils.getLevelPassed()>= 4){
                    utils.setLevel(5);
                    game.setScreen(new StorylineScreen(game));
                }
            }
        });
        btnCancle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hasTouched = false;
                levelDialog.hide();
            }
        });

        stage.addActor(dialog);
        backStage.addActor(table);
        stage.addActor(levelDialog);

        audioManager = AudioManager.getInstance();
        music = audioManager.getMusic(Constants.MUSIC_LEVEL1);
        music.setLooping(true);
        music.setVolume(MyGdxGame.MUSIC_VOLUME);
    }

    @Override
    public void show() {
        if (MyGdxGame.IS_MUSIC_ENABLED)
            music.play();

        inputMultiplexer.addProcessor(MapScreen.this);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render(float delta) {
        // Renders all the layers of the map
        renderer.setView(gameCam);
        renderer.render();
        // Renderer our Box2DDebugLines
        b2dr.render(world, gameCam.combined);
        // Render dialog
        if (hasTouched) {
            dialog.hide();
            stage.act();
            stage.draw();
        }
        if (hasClicked) {
            levelDialog.hide();
            stage.act();
            stage.draw();
        }
        backStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        music.stop();
    }

    @Override
    public void dispose() {
        System.out.println("MapScreen dispose()");
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        stage.dispose();
        backStage.dispose();
//        music.stop();
        music.dispose();
    }

    private void initBody() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        // Create Lustel bodies fixtures
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            rectLustel = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectLustel.getX() + rectLustel.getWidth() / 2) / Constants.PPM, (rectLustel.getY() + rectLustel.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rectLustel.getWidth() / 2 / Constants.PPM, rectLustel.getHeight() / 2 / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        // Create Rodaphite bodies fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            rectRodaphite = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectRodaphite.getX() + rectRodaphite.getWidth() / 2) / Constants.PPM, (rectRodaphite.getY() + rectRodaphite.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rectRodaphite.getWidth() / 2 / Constants.PPM, rectRodaphite.getHeight() / 2 / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        // Create Tarish bodies fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            rectTarish = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectTarish.getX() + rectTarish.getWidth() / 2) / Constants.PPM, (rectTarish.getY() + rectTarish.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rectTarish.getWidth() / 2 / Constants.PPM, rectTarish.getHeight() / 2 / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);

        }
        // Create Turan bodies fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            rectTuran = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectTuran.getX() + rectTuran.getWidth() / 2) / Constants.PPM, (rectTuran.getY() + rectTuran.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rectTuran.getWidth() / 2 / Constants.PPM, rectTuran.getHeight() / 2 / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        // Create Astarte bodies fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            rectAstarte = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectAstarte.getX() + rectAstarte.getWidth() / 2) / Constants.PPM, (rectAstarte.getY() + rectAstarte.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rectAstarte.getWidth() / 2 / Constants.PPM, rectAstarte.getHeight() / 2 / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        // Create BackButton bodies fixtures
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            rectBackButton = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectBackButton.getX() + rectBackButton.getWidth() / 2) / Constants.PPM, (rectBackButton.getY() + rectBackButton.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rectBackButton.getWidth() / 2 / Constants.PPM, rectBackButton.getHeight() / 2 / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    public void comparePosition(Rectangle rect, Vector3 touchPoint) {
        float x = rect.getX() / Constants.PPM;
        float y = rect.getY() / Constants.PPM;
        float w = rect.getWidth() / Constants.PPM;
        float h = rect.getHeight() / Constants.PPM;
        if (rect == rectBackButton) {
            if ((touchPoint.x >= x) && (touchPoint.x <= x + w) && ((touchPoint.y >= y) && (touchPoint.y) <= y + h)) {
                game.changeScreen(Constants.MENU);
            }
        } else {
            if ((touchPoint.x >= x) && (touchPoint.x <= x + w) && ((touchPoint.y >= y) && (touchPoint.y) <= y + h)) {
                hasClicked = true;
                dialog.show(stage);
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPoint = new Vector3(screenX, screenY, 0);
        gameCam.unproject(touchPoint);
        float x = rectLustel.getX() / Constants.PPM;
        float y = rectLustel.getY() / Constants.PPM;
        float w = rectLustel.getWidth() / Constants.PPM;
        float h = rectLustel.getHeight() / Constants.PPM;

        if ((touchPoint.x >= x) && (touchPoint.x <= x + w) && ((touchPoint.y >= y) && (touchPoint.y) <= y + h)) {
//            if (Utils.getLevel() == 5) game.setScreen(new PlayScreen(game));
//            else game.changeScreen(Constants.PLAY);
            hasTouched = true;
            levelDialog.show(stage);
        } else {
            comparePosition(rectRodaphite, touchPoint);
            comparePosition(rectTarish, touchPoint);
            comparePosition(rectTuran, touchPoint);
            comparePosition(rectAstarte, touchPoint);
            comparePosition(rectBackButton, touchPoint);
        }
        System.out.println("Clicked! x=" + touchPoint.x + " y=" + touchPoint.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
