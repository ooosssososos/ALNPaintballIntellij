package com.hotmail.ooosssososos.Thread;

import com.hotmail.ooosssososos.ALNPlayer;
import com.hotmail.ooosssososos.CounterCraft;
import com.hotmail.ooosssososos.GameType.Game;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/23/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: PlayerTimeCounter
 */
public class PlayerTimeCounter extends BukkitRunnable{
    CounterCraft plugin;
    public PlayerTimeCounter(CounterCraft plu){
        plugin = plu;
    }
    @Override
    public void run() {
        for(Map.Entry<String, Game> pair : plugin.gm.games.entrySet()){

            if(pair.getValue().status == Game.IN_PROGRESS){

                for(ALNPlayer p : pair.getValue().players.keySet()){
                    p.incTime();
                }
            }
        }
    }
}
