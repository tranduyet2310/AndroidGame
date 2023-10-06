package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {
    private static boolean playerOnWater;
    public static TextureRegion getRegion(String path) {
        return new TextureRegion(new Texture(Gdx.files.internal(path)));
    }

    public static boolean isPlayerOnWater() {
        return playerOnWater;
    }

    public static void setPlayerOnWater(boolean playerOnWater) {
        Utils.playerOnWater = playerOnWater;
    }
}
