package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.InteractiveTileObject;

import com.mygdx.game.MyGdxGame;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        //
        if (fixA.getUserData() == "Player" || fixB.getUserData() == "Player") {
            Fixture Player = fixA.getUserData() == "Player" ? fixA : fixB;
            Fixture object = Player == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        if (fixA.getUserData() == "Sword" || fixB.getUserData() == "Sword") {
            Fixture Player = fixA.getUserData() == "Sword" ? fixA : fixB;
            Fixture object = Player == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onSwordHit();
            }
        }
        //
        switch (cDef) {
            case MyGdxGame.PLAYER_BIT | MyGdxGame.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).hitEnemy();
                else {
                    ((Enemy) fixB.getUserData()).hitEnemy();
                }
                break;
            case MyGdxGame.ENEMY_BIT | MyGdxGame.SPIKE_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
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
