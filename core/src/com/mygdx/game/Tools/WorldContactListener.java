package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Constants;
import com.mygdx.game.Sprites.Enemies.Crabby;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.NPC.NPC;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.Sword.SwordAttack;
import com.mygdx.game.Sprites.TileObjects.InteractiveTileObject;
import com.mygdx.game.Sprites.TileObjects.Water;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            // Collision sword with enemy
            case Constants.SWORD_BIT | Constants.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).getsHurt();
                else {
                    ((Enemy) fixB.getUserData()).getsHurt();
                }
                break;
            // Collision player take damage
            case Constants.PLAYER_BIT | Constants.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    if (fixB.getUserData() instanceof Crabby) {
                        ((Player) fixA.getUserData()).getsHurt(Constants.CRABBY_ATTACK);
                    }
                } else {
                    if (fixA.getUserData() instanceof Crabby) {
                        ((Player) fixB.getUserData()).getsHurt(Constants.CRABBY_ATTACK);
                    }
                }
                break;
            // Collision between two enemies
            case Constants.ENEMY_BIT:
                Gdx.app.log("check", "in here");
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            // Collision with Items
            case Constants.PLAYER_BIT | Constants.GOLD_COIN_BIT:
                if (fixA.getFilterData().categoryBits == Constants.GOLD_COIN_BIT)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.SILVER_COIN_BIT:
                if (fixA.getFilterData().categoryBits == Constants.SILVER_COIN_BIT)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.BLUE_DIAMOND:
                if (fixA.getFilterData().categoryBits == Constants.BLUE_DIAMOND)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.GOLDEN_SKULL:
                if (fixA.getFilterData().categoryBits == Constants.GOLDEN_SKULL)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.RED_DIAMOND:
                if (fixA.getFilterData().categoryBits == Constants.RED_DIAMOND)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.CHEST_KEY:
                if (fixA.getFilterData().categoryBits == Constants.CHEST_KEY)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.TREASURE_BIT:
                if (fixA.getFilterData().categoryBits == Constants.TREASURE_BIT)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.BIG_MAP:
                if (fixA.getFilterData().categoryBits == Constants.BIG_MAP)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.SMALL_MAP_1:
                if (fixA.getFilterData().categoryBits == Constants.SMALL_MAP_1)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.SMALL_MAP_2:
                if (fixA.getFilterData().categoryBits == Constants.SMALL_MAP_2)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.SMALL_MAP_3:
                if (fixA.getFilterData().categoryBits == Constants.SMALL_MAP_3)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.SMALL_MAP_4:
                if (fixA.getFilterData().categoryBits == Constants.SMALL_MAP_4)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.BLUE_POITION_BIT:
                if (fixA.getFilterData().categoryBits == Constants.BLUE_POITION_BIT)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.RED_POITION_BIT:
                if (fixA.getFilterData().categoryBits == Constants.RED_POITION_BIT)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.GREEN_POITION_BIT:
                if (fixA.getFilterData().categoryBits == Constants.GREEN_POITION_BIT)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else {
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            // Collision with Object
            case Constants.PLAYER_BIT | Constants.SPIKE_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).getsHurt(50);
                } else {
                    ((Player) fixB.getUserData()).getsHurt(50);
                }
                break;
            case Constants.PLAYER_BIT | Constants.WATER_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).getsHurt(Constants.PLAYER_MAXHEALTH);
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                } else {
                    ((Player) fixB.getUserData()).getsHurt(Constants.PLAYER_MAXHEALTH);
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                }
                break;
            case Constants.ENEMY_BIT | Constants.SPIKE_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            // Collision to get special item
            case Constants.PLAYER_BIT | Constants.SPECIAL_ITEM_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                } else {
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                }
                break;
            // Collision to deploy skill
            case Constants.SWORD_ATTACK_BIT | Constants.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).getSworkAttack();
                    ((SwordAttack) fixB.getUserData()).setToDestroy();
                } else {
                    ((Enemy) fixB.getUserData()).getSworkAttack();
                    ((SwordAttack) fixA.getUserData()).setToDestroy();
                }
                break;
            // Collision between player and NPC
            case Constants.PLAYER_BIT | Constants.NPC_BIT:
                if (fixA.getFilterData().categoryBits == Constants.NPC_BIT)
                    ((NPC) fixA.getUserData()).checkRequest((Player) fixB.getUserData());
                else {
                    ((NPC) fixB.getUserData()).checkRequest((Player) fixA.getUserData());
                }
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
