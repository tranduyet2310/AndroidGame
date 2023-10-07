package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Sword.SwordAttack;
import com.mygdx.game.Tools.Utils;

public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, ATTACKING, AIRATTACK, DEAD, HIT}

    public State currentState;
    public State previousState;
    //
    public PlayScreen screen;
    public World world;
    public Body b2Body;
    private TextureRegion playerStand;
    private Animation<TextureRegion> playerRun, playerJump, playerIdle, playerFalling, playerAttacking, playerAirAttack, playerHit, playerDead;
    private float stateTimer;
    private int maxHealth, currentHealth, powerAttack, maxMana, currentMana;
    private float timeToRegenerateMana;
    private boolean runningRight;
    public boolean isAttacking, isDead, isAirAttack, takeDamage;
    private boolean hasBlueDiamond, hasSilverCoin, hasGoldCoin, hasGoldenSkull, hasRedDiamond;
    private Array<SwordAttack> swordAttacks;
    private Array<TextureRegion> frames;

    public Player(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        //
        isAttacking = false;
        isAirAttack = false;
        takeDamage = false;
        isDead = false;
        hasBlueDiamond = hasRedDiamond = hasGoldenSkull = hasGoldCoin = hasSilverCoin = false;
        //
        setPosition(x, y);
        // define Player animation
        frames = new Array<TextureRegion>();
        initAnimation(frames);
        // define player character in Box2d
        definePlayer();
        // set default animation
        playerStand = new TextureRegion(Utils.getRegion("player/Idle Sword/Idle Sword 01.png"));
        setRegion(playerStand);
        //
        setBounds(0, 0, 64 / Constants.PPM, 48 / Constants.PPM);
        // inital value of player
        currentHealth = maxHealth = Constants.PLAYER_MAXHEALTH;
        powerAttack = Constants.PLAYER_ATTACK;
        currentMana = maxMana = Constants.PLAYER_MAXMANA;
        timeToRegenerateMana = 0;
        //
        swordAttacks = new Array<SwordAttack>();
    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        // Automatically restores mana
        timeToRegenerateMana += dt;
        if (timeToRegenerateMana >= 2.0f) {
            if (currentMana < maxMana) {
                currentMana += 5;
            }
            timeToRegenerateMana = 0;
        }

        for (SwordAttack swordAttack : swordAttacks) {
            swordAttack.update(dt);
            if (swordAttack.isDestroyed()) {
                swordAttacks.removeValue(swordAttack, true);
                Gdx.app.log("Attack", "in update():" + swordAttacks.size);
            }
        }

        checkCurrentHealth();
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case FALLING:
                region = playerFalling.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case ATTACKING:
                region = playerAttacking.getKeyFrame(stateTimer);
                break;
            case AIRATTACK:
                region = playerAirAttack.getKeyFrame(stateTimer);
                break;
            case HIT:
                region = playerHit.getKeyFrame(stateTimer);
                if (playerHit.isAnimationFinished(stateTimer))
                    takeDamage = false;
                break;
            case DEAD:
                region = playerDead.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = playerIdle.getKeyFrame(stateTimer);
                break;
        }
        if ((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (b2Body.getLinearVelocity().y > 0 || (b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            if (isAirAttack) {
                return State.AIRATTACK;
            }
            return State.JUMPING;
        } else if (b2Body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2Body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if (isAttacking) {
            return State.ATTACKING;
        } else if (isDead) {
            return State.DEAD;
        } else if (takeDamage) {
            return State.HIT;
        } else return State.STANDING;
    }

    public void definePlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
        fixtureDef.filter.maskBits = Constants.GROUND_BIT | Constants.ENEMY_BIT |
                Constants.SPIKE_BIT | Constants.WATER_BIT | Constants.SPECIAL_ITEM_BIT |
                Constants.BLUE_DIAMOND | Constants.GOLD_COIN_BIT | Constants.SILVER_COIN_BIT | Constants.GOLDEN_SKULL |
                Constants.BIG_MAP | Constants.SMALL_MAP_1 | Constants.SMALL_MAP_2 | Constants.SMALL_MAP_3 | Constants.SMALL_MAP_4;

        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef).setUserData(this);

        FixtureDef swordDef = new FixtureDef();
        EdgeShape swordShape = new EdgeShape();
        swordShape.set(new Vector2((5f) / Constants.PPM, b2Body.getLocalCenter().y / Constants.PPM), new Vector2((30f) / Constants.PPM, b2Body.getLocalCenter().y / Constants.PPM));
        swordDef.filter.categoryBits = Constants.SWORD_BIT;
        swordDef.filter.maskBits = Constants.ENEMY_BIT;
        swordDef.shape = swordShape;
        swordDef.isSensor = true;
        b2Body.createFixture(swordDef).setUserData(this);
    }

    public void initAnimation(Array<TextureRegion> frames) {
        for (int i = 1; i <= 5; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Idle Sword/Idle Sword 0" + i + ".png")));
        }
        playerIdle = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Run Sword/Run Sword 0" + i + ".png")));
        }
        playerRun = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
        for (int i = 1; i <= 3; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Jump Sword/Jump Sword 0" + i + ".png")));
        }
        playerJump = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
        for (int i = 1; i <= 3; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Fall Sword/Fall 0" + i + ".png")));
        }
        playerFalling = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
        for (int i = 1; i <= 9; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Attack 1/Attack 1 0" + i + ".png")));
        }
        playerAttacking = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Air Attack 1/Air Attack 1 0" + i + ".png")));
        }
        playerAirAttack = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Dead Hit/Dead Hit 0" + i + ".png")));
        }
        playerDead = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Hit Sword/Hit Sword 0" + i + ".png")));
        }
        playerHit = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
    }

    public void checkCurrentHealth() {
        if (currentHealth == 0)
            isDead = true;
        if (Utils.isPlayerOnWater()) {
            currentHealth -= maxHealth;
            isDead = true;
            Utils.setPlayerOnWater(false);
        }
    }

    public void getsHurt(int damage) {
        takeDamage = true;
        if (currentHealth <= 0) currentHealth = 0;
        else currentHealth -= damage;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public boolean isDead() {
        return isDead;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public boolean isHasBlueDiamond() {
        return hasBlueDiamond;
    }

    public void setHasBlueDiamond(boolean hasBlueDiamond) {
        this.hasBlueDiamond = hasBlueDiamond;
    }

    public boolean isHasSilverCoin() {
        return hasSilverCoin;
    }

    public void setHasSilverCoin(boolean hasSilverCoin) {
        this.hasSilverCoin = hasSilverCoin;
    }

    public boolean isHasGoldCoin() {
        return hasGoldCoin;
    }

    public void setHasGoldCoin(boolean hasGoldCoin) {
        this.hasGoldCoin = hasGoldCoin;
    }

    public boolean isHasGoldenSkull() {
        return hasGoldenSkull;
    }

    public void setHasGoldenSkull(boolean hasGoldenSkull) {
        this.hasGoldenSkull = hasGoldenSkull;
    }

    public boolean isHasRedDiamond() {
        return hasRedDiamond;
    }

    public void setHasRedDiamond(boolean hasRedDiamond) {
        this.hasRedDiamond = hasRedDiamond;
    }

    public void addBlood(int blood) {
        currentHealth += blood;
        if (currentHealth > maxHealth) currentHealth = maxHealth;
    }

    public void addMana(int mana) {
        currentMana += mana;
        if (currentMana > maxMana) currentMana = maxMana;
    }

    public void attack() {
        Gdx.app.log("Attack", "in attack()");
        if (currentMana >= 10) {
            currentMana -= 10;
            swordAttacks.add(new SwordAttack(screen, b2Body.getPosition().x, b2Body.getPosition().y, runningRight));
        }
    }

    public void draw(Batch batch) {
        super.draw(batch);
        for (SwordAttack attack : swordAttacks) {
            Gdx.app.log("Attack", "in draw(): " + swordAttacks.size);
            attack.draw(batch);
        }
    }
}
