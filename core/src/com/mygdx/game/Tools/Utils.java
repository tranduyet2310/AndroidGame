package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {
    private static Utils instance;

    private Utils() {
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    private boolean playerOnWater;
    private boolean playerHasKey;
    private boolean completeRequest;
    private boolean isMap1, isMap2, isMap3, isMap4, isMap5;
    private int level, levelPassed;

    public static TextureRegion getRegion(String path) {
        return new TextureRegion(new Texture(Gdx.files.internal(path)));
    }

    public boolean isPlayerOnWater() {
        return playerOnWater;
    }

    public void setPlayerOnWater(boolean playerOnWater) {
        this.playerOnWater = playerOnWater;
    }

    public boolean isPlayerHasKey() {
        return playerHasKey;
    }

    public void setPlayerHasKey(boolean playerHasKey) {
        this.playerHasKey = playerHasKey;
    }

    public boolean isCompleteRequest() {
        return completeRequest;
    }

    public void setCompleteRequest(boolean completeRequest) {
        this.completeRequest = completeRequest;
    }

    public boolean isIsMap1() {
        return isMap1;
    }

    public void setIsMap1(boolean isMap1) {
        this.isMap1 = isMap1;
    }

    public boolean isIsMap2() {
        return isMap2;
    }

    public void setIsMap2(boolean isMap2) {
        this.isMap2 = isMap2;
    }

    public boolean isIsMap3() {
        return isMap3;
    }

    public void setIsMap3(boolean isMap3) {
        this.isMap3 = isMap3;
    }

    public boolean isIsMap4() {
        return isMap4;
    }

    public void setIsMap4(boolean isMap4) {
        this.isMap4 = isMap4;
    }

    public boolean isIsMap5() {
        return isMap5;
    }

    public void setIsMap5(boolean isMap5) {
        this.isMap5 = isMap5;
    }

    public void resetFlag(int level) {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevelPassed() {
        return levelPassed;
    }

    public void setLevelPassed(int levelPassed) {
        this.levelPassed = levelPassed;
    }
}
