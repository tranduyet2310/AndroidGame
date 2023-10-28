package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Utils;

public class MapManager {
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private MyGdxGame game;
    private Utils utils;

    public MapManager(MyGdxGame game) {
        this.game = game;
        mapLoader = new TmxMapLoader();
        utils = Utils.getInstance();
    }

    public TiledMap getMap() {
        int level = utils.getLevel();
        switch (level) {
            case 1:
                map = mapLoader.load("map1.tmx");
                utils.setIsMap1(true);
                utils.setIsMap2(false);
                utils.setIsMap3(false);
                utils.setIsMap4(false);
                utils.setIsMap5(false);
                break;
            case 2:
                map = mapLoader.load("map2.tmx");
                utils.setIsMap1(false);
                utils.setIsMap2(true);
                utils.setIsMap3(false);
                utils.setIsMap4(false);
                utils.setIsMap5(false);
                break;
            case 3:
                map = mapLoader.load("map3.tmx");
                utils.setIsMap1(false);
                utils.setIsMap2(false);
                utils.setIsMap3(true);
                utils.setIsMap4(false);
                utils.setIsMap5(false);
                break;
            case 4:
                map = mapLoader.load("map4.tmx");
                utils.setIsMap1(false);
                utils.setIsMap2(false);
                utils.setIsMap3(false);
                utils.setIsMap4(true);
                utils.setIsMap5(false);
                break;
            case 5:
                map = mapLoader.load("map5.tmx");
                utils.setIsMap1(false);
                utils.setIsMap2(false);
                utils.setIsMap3(false);
                utils.setIsMap4(false);
                utils.setIsMap5(true);
                break;
        }
        return map;
    }
}
