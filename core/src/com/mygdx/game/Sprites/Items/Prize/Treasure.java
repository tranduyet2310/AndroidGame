package com.mygdx.game.Sprites.Items.Prize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Crabby;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.Utils;

public class Treasure extends Item {
    public enum TreasureState {CLOSE, OPEN}

    public TreasureState currentState;
    public TreasureState previousState;
    private float stateTimer;
    private Animation<TextureRegion> treasureCloseAnimation, treasureOpenAnimation;
    private Array<TextureRegion> frames;
    private Utils utils;

    public Treasure(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        currentState = previousState = TreasureState.CLOSE;
        setBounds(getX(), getY(), 64 / Constants.PPM, 35 / Constants.PPM);
        frames = new Array<TextureRegion>();
        for (int i = 1; i <= 10; i++) {
            frames.add(new TextureRegion(Utils.getRegion("item/Treasure/Chest Open/Chest Open 0" + i + ".png")));
        }
        treasureOpenAnimation = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 5; i++) {
            frames.add(new TextureRegion(Utils.getRegion("item/Treasure/Chest Close/Chest Close 0" + i + ".png")));
        }
        treasureCloseAnimation = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();

        stateTimer = 0;
        velocity = new Vector2(0, 0);
        utils = Utils.getInstance();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setRegion(getFrame(dt));
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 10 / Constants.PPM);
        body.setLinearVelocity(velocity);
    }

    public TreasureState getState() {
        if (utils.isPlayerHasKey()) {
            return TreasureState.OPEN;
        } else return TreasureState.CLOSE;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case OPEN:
                region = treasureOpenAnimation.getKeyFrame(stateTimer, true);
                break;
            case CLOSE:
            default:
                region = treasureCloseAnimation.getKeyFrame(stateTimer, true);
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    @Override
    public void defineItem() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.TREASURE_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.GROUND_BIT
                | Constants.SPIKE_BIT;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void use(Player player) {
        if (player.isHasChestKey()) {
            player.setHasChestKey(false);
            utils.setPlayerHasKey(false);
            utils.setCompleteRequest(true);
        }
    }
}
