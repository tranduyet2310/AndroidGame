package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AudioManager;
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
    private Rectangle rectLustel, rectRodaphite, rectTarish, rectTuran, rectAstarte;
    private Stage stage;
    private Dialog dialog;
    InputMultiplexer inputMultiplexer;
    private static boolean hasClicked = false;
    private Music music;
    private AudioManager audioManager;

    public MapScreen(MyGdxGame game) {
        this.game = game;
        // Create cam used to follow player through cam world
        gameCam = new OrthographicCamera();
        // Create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FillViewport(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM, gameCam);
        // Load our map and setup our map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("world_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);
        // initially set our gameCam to be centered correctly at the start of
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

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

        inputMultiplexer = new InputMultiplexer();
        stage = new Stage();

        audioManager = AudioManager.getInstance();
        music = audioManager.getMusic(Utils.MUSIC_LEVEL1);
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

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        dialog = new Dialog("Notify", skin);
        Label label = new Label("The land has not been unlocked yet!", skin, "title");
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
        stage.addActor(dialog);
    }

    @Override
    public void render(float delta) {
        // Renders all the layers of the map
        renderer.setView(gameCam);
        renderer.render();
        // Renderer our Box2DDebugLines
        b2dr.render(world, gameCam.combined);
        // Render dialog
        if (hasClicked) {
            System.out.println("In render()");
            stage.act();
            stage.draw();
        }
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
//        music.stop();
        music.dispose();
    }

    public void comparePosition(Rectangle rect, Vector3 touchPoint) {
        float x = rect.getX() / Constants.PPM;
        float y = rect.getY() / Constants.PPM;
        float w = rect.getWidth() / Constants.PPM;
        float h = rect.getHeight() / Constants.PPM;

        if ((touchPoint.x >= x) && (touchPoint.x <= x + w) && ((touchPoint.y >= y) && (touchPoint.y) <= y + h)) {
            hasClicked = true;
            dialog.show(stage);
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
            game.changeScreen(Constants.PLAY);
        } else {
            comparePosition(rectRodaphite, touchPoint);
            comparePosition(rectTarish, touchPoint);
            comparePosition(rectTuran, touchPoint);
            comparePosition(rectAstarte, touchPoint);
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
