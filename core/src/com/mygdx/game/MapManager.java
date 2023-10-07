package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.Tools.Utils;

public class MapManager {
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private MyGdxGame game;

    public MapManager(MyGdxGame game) {
        this.game = game;
        mapLoader = new TmxMapLoader();
    }

    public TiledMap getMap() {
        int level = Utils.getLevel();
        switch (level) {
            case 1:
                map = mapLoader.load("map1.tmx");
                Utils.setIsMap1(true);
                break;
            case 2:
                map = mapLoader.load("map2.tmx");
                Utils.setIsMap2(true);
                break;
            case 3:
                map = mapLoader.load("map3.tmx");
                Utils.setIsMap3(true);
                break;
            case 4:
                map = mapLoader.load("map4.tmx");
                Utils.setIsMap4(true);
                break;
            case 5:
                map = mapLoader.load("map5.tmx");
                Utils.setIsMap5(true);
                break;
            default:
                break;
        }
        return map;
    }
}
