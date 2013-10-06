package com.hotmail.ooosssososos;

import org.bukkit.entity.Player;

import com.hotmail.ooosssososos.ALNPlayer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/23/13
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: ALNPlayerManager
 */
public class ALNPlayerManager {

    private static HashMap<Player, ALNPlayer> data;

    public ALNPlayerManager(){
        data = new HashMap<Player, ALNPlayer>();
    }
    public static Player getPlayer(ALNPlayer ap){
        for(Map.Entry<Player, ALNPlayer> pair : data.entrySet()){
            if(pair.getValue().equals(pair.getValue())){
                return pair.getKey();
            }
        }
        return null;
    }
    public static ALNPlayer getALNPlayer(Player p){
        return data.get(p);
    }
    public static void add(Player p){
        data.put(p, new ALNPlayer(p));
    }

    public static void rem(Player p){
        data.remove(p);
    }

}
