package com.hotmail.ooosssososos;

import com.hotmail.ooosssososos.GameType.Game;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/22/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: GameManager
 */
public class GameManager {

    public static HashMap<String, Game> games;
    static CounterCraft plugin;
    public GameManager(CounterCraft plu){
        games = new HashMap<String, Game>();
        plugin = plu;
    }
    public void restartGame(int ID){
        games.get(ID).restart();
    }
    public static Game getGame(Player p){
        for(Map.Entry<String, Game> pair : games.entrySet()){
            if(pair.getValue().players.containsKey(plugin.pm.getALNPlayer(p)))return pair.getValue();
        }
        return null;
    }

}
