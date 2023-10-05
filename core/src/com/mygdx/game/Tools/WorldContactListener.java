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

//        if (fixA.getUserData() == "Sword" || fixB.getUserData() == "Sword") {
//            Fixture Player = fixA.getUserData() == "Sword" ? fixA : fixB;
//            Fixture object = Player == fixA ? fixB : fixA;
//
//            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
//                ((InteractiveTileObject) object.getUserData()).onSwordHit();
//            }
//        }
        //
        switch (cDef) {
            case Constants.SWORD_BIT | Constants.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).getsHurt();
                else {
                    ((Enemy) fixB.getUserData()).getsHurt();
                }
                break;
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
            case Constants.ENEMY_BIT | Constants.SPIKE_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case Constants.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
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
            case Constants.PLAYER_BIT | Constants.SPECIAL_ITEM_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                } else {
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                }
                break;
            case Constants.SWORD_ATTACK_BIT | Constants.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Constants.SWORD_ATTACK_BIT) {
                    ((SwordAttack) fixA.getUserData()).setToDestroy();
//                    ((Enemy) fixB.getUserData()).getsHurt();
                    Gdx.app.log("Attack", "Hit");
                } else {
                    ((SwordAttack) fixB.getUserData()).setToDestroy();
//                    ((Enemy) fixA.getUserData()).getsHurt();
                    Gdx.app.log("Attack", "Hit");
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
