package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;

public class Ground extends InteractiveTileObject{
    public Ground(PlayScreen screen, MapObject object) {
        super(screen, object);
        setCategoryFilter(Constants.GROUND_BIT);
    }

    @Override
    public void onHeadHit() {

    }

    @Override
    public void onSwordHit() {

    }
}
