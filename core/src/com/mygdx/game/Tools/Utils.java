package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {
    private static boolean playerOnWater;
    private static boolean completeRequest;
    private static boolean isMap1, isMap2, isMap3, isMap4, isMap5;
    private static int level;

    public static TextureRegion getRegion(String path) {
        return new TextureRegion(new Texture(Gdx.files.internal(path)));
    }

    public static boolean isPlayerOnWater() {
        return playerOnWater;
    }

    public static void setPlayerOnWater(boolean playerOnWater) {
        Utils.playerOnWater = playerOnWater;
    }

    public static boolean isCompleteRequest() {
        return completeRequest;
    }

    public static void setCompleteRequest(boolean completeRequest) {
        Utils.completeRequest = completeRequest;
    }

    public static boolean isIsMap1() {
        return isMap1;
    }

    public static void setIsMap1(boolean isMap1) {
        Utils.isMap1 = isMap1;
    }

    public static boolean isIsMap2() {
        return isMap2;
    }

    public static void setIsMap2(boolean isMap2) {
        Utils.isMap2 = isMap2;
    }

    public static boolean isIsMap3() {
        return isMap3;
    }

    public static void setIsMap3(boolean isMap3) {
        Utils.isMap3 = isMap3;
    }

    public static boolean isIsMap4() {
        return isMap4;
    }

    public static void setIsMap4(boolean isMap4) {
        Utils.isMap4 = isMap4;
    }

    public static boolean isIsMap5() {
        return isMap5;
    }

    public static void setIsMap5(boolean isMap5) {
        Utils.isMap5 = isMap5;
    }

    public static void resetFlag(int level) {
        switch (level) {
            case 1:
                setIsMap1(false);
                break;
            case 2:
                setIsMap2(false);
                break;
            case 3:
                setIsMap3(false);
                break;
            case 4:
                setIsMap4(false);
                break;
            case 5:
                setIsMap5(false);
                break;
        }
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        Utils.level = level;
        if (level > 5) Utils.level = 5;
    }
}
