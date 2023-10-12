package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Utils;

public class StorylineScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private MyGdxGame game;
    private Skin skin;
    private Utils utils;
    private int count, level;
    private Label contentLabel0, contentLabel1, contentLabel2, contentLabel3, contentLabel4, contentLabel5;
    private static final String CONTENT0 = "To get the hidden treasure in this land, you must collect all 4 map pieces.";
    private static final String CONTENT1 = "After collecting all 4 pieces, we will have a complete map leading to the treasure.";
    private static final String CONTENT2 = "Complete the quest assigned by the NPC in each level and you will collect the small map piece!";
    private static final String BOMBGUY1 = "Bomb Guy who detonated the bomb was a member of a pirate group. \nHe lost a precious souvenir, a silver coin, left by his late father.";
    private static final String BOMBGUY2 = "You must help him find that coin to get the small map 1 which he is holding.";
    private static final String BOMBGUY3 = "BOMB GUY: \"The last time I saw it was near a tree growing on the wall. \n\t\t\tMy leg was injured so I couldn't climb up there to get it. \n\t\t\tSo please get it back to me\"";
    private static final String BIGGUY1 = "Big guy is a naval officer, he came to investigate the theft of gold coins";
    private static final String BIGGUY2 = "You must help him find that coin to get the small map 2 which he is holding.";
    private static final String BIGGUY3 = "BIG GUY: \"According to reports the coin was hidden somewhere around \n\t\tthe location of the pirate flag. Help me get the gold coin, I'll give you the map\"";
    private static final String BALDPIRATE1 = "The bald pirate is a member of a notorious pirate crew. He is wanted for\n\t a large amount of money. He's looking for something!";
    private static final String BALDPIRATE2 = "You must help him find that golden skull to get the small map 3 which he is holding.";
    private static final String BALDPIRATE3 = "BALD PIRATE: \"The golden skull I'm looking for is in a box. But it was stolen from me by a stranger, \n\t\t\tbring it back to me and I will give you what you want \"";
    private static final String CAPTAIN1 = "The captain was once the captain of a pirate crew. He traveled to many lands to \n\tsearch for the blue diamond, one of two important things kept at the temple";
    private static final String CAPTAIN2 = "You must help him find that blue diamond to get the small map 4 which he is holding.";
    private static final String CAPTAIN3 = "CAPTAIN: \"The blue diamond was kept in a chest. My pirate crew got them and \n\tare on their way back. But now I have lost contact with them for a few days. \n\t\t\tFind and bring the diamond back here. \"";
    private static final String UNKNOWN1 = "Humans have broken into the temple and taken two important things here. \n\tThese are blue diamonds and red diamonds. It's unacceptable!!!";
    private static final String UNKNOWN2 = "You must help him find that red diamond to get the key to achieve treasures.";
    private static final String UNKNOWN3 = "UNKNOWN: \"The entrance has been opened. The red diamond has been stolen, \n\t\tit's mine! It's mine, give it back!!!\"";

    public StorylineScreen(MyGdxGame game) {
        this.game = game;
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        utils = Utils.getInstance();
        count = 0;
        level = utils.getLevel();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label titleLabel = new Label("STORYLINE!!!", skin, "title");
        Label blankLabel1 = new Label("", skin);
        Label blankLabel2 = new Label("", skin);
        Label blankLabel3 = new Label("", skin);
        contentLabel0 = new Label("", skin);
        contentLabel1 = new Label("", skin);
        contentLabel2 = new Label("", skin);
        contentLabel3 = new Label("", skin);
        contentLabel4 = new Label("", skin);
        contentLabel5 = new Label("", skin);

        titleLabel.setFontScale(2.0f);
        contentLabel0.setFontScale(2.0f);
        contentLabel1.setFontScale(2.0f);
        contentLabel2.setFontScale(2.0f);
        contentLabel3.setFontScale(2.0f);
        contentLabel4.setFontScale(2.0f);
        contentLabel5.setFontScale(2.0f);

        table.add(titleLabel).expandX();
        table.row();
        table.add(contentLabel0).expandX().padTop(10f);
        table.row();
        table.add(contentLabel1).expandX().padTop(5f);
        table.row();
        table.add(contentLabel2).expandX().padTop(5f);
        table.row();
        table.add(blankLabel1).expandX().padTop(5f);
        table.row();
        table.add(blankLabel2).expandX().padTop(5f);
        table.row();
        table.add(blankLabel3).expandX().padTop(5f);
        table.row();
        table.add(contentLabel3).expandX().padTop(5f);
        table.row();
        table.add(contentLabel4).expandX().padTop(5f);
        table.row();
        table.add(contentLabel5).expandX().padTop(5f);

        stage.addActor(table);

        if (level == 1) {
            contentLabel0.setText(CONTENT0);
            contentLabel1.setText(CONTENT1);
            contentLabel2.setText(CONTENT2);
        } else {
            contentLabel0.setText("");
            contentLabel1.setText("");
            contentLabel2.setText("");
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            if (count > 3) {
                game.setScreen(new PlayScreen(game));
                dispose();
            } else {
                count++;
                showInfoOfNPC();
            }

        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    public void showInfoOfNPC() {
        switch (level) {
            case 1:
                if (count == 1) {
                    contentLabel3.setText(BOMBGUY1);
                } else if (count == 2) {
                    contentLabel4.setText(BOMBGUY2);
                } else if (count == 3) {
                    contentLabel5.setText(BOMBGUY3);
                }
                break;
            case 2:
                if (count == 1) {
                    contentLabel0.setText(BIGGUY1);
                } else if (count == 2) {
                    contentLabel1.setText(BIGGUY2);
                } else if (count == 3) {
                    contentLabel2.setText(BIGGUY3);
                }
                break;
            case 3:
                if (count == 1) {
                    contentLabel0.setText(BALDPIRATE1);
                } else if (count == 2) {
                    contentLabel1.setText(BALDPIRATE2);
                } else if (count == 3) {
                    contentLabel2.setText(BALDPIRATE3);
                }
                break;
            case 4:
                if (count == 1) {
                    contentLabel0.setText(CAPTAIN1);
                } else if (count == 2) {
                    contentLabel1.setText(CAPTAIN2);
                } else if (count == 3) {
                    contentLabel2.setText(CAPTAIN3);
                }
                break;
            case 5:
                if (count == 1) {
                    contentLabel0.setText(UNKNOWN1);
                } else if (count == 2) {
                    contentLabel1.setText(UNKNOWN2);
                } else if (count == 3) {
                    contentLabel2.setText(UNKNOWN3);
                }
                break;

        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
