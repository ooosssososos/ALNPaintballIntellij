package com.hotmail.ooosssososos.Thread;

import com.hotmail.ooosssososos.CounterCraft;
import com.hotmail.ooosssososos.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/22/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: SignUpdateThread
 */
public class SignUpdateThread extends BukkitRunnable {
    private CounterCraft plugin;
    public SignUpdateThread(CounterCraft inp){ plugin = inp; }

    @Override
    public void run() {
        try{
        for(Map.Entry<String, Game> cursor : plugin.gm.games.entrySet()){

            Game g = cursor.getValue();
            if(g.status != Game.UNINITIALIZED){
            Sign s = (Sign)g.statusSign.getBlock().getState();
            s.setLine(0,ChatColor.GREEN + "ALNCounterCraft");
            switch(g.status){
                case Game.WAITING: s.setLine(1, ChatColor.GREEN + "WAITING_ROOM");
                    s.setLine(2, g.players.size() + " / " + g.max_Player);
                    s.setLine(3, ChatColor.GREEN + "[Click to join]");
                    break;
                case Game.IN_PROGRESS: s.setLine(1, ChatColor.BLUE + "IN_PROGRESS");
                    s.setLine(2, g.players.size() + " / " + g.max_Player);
                    s.setLine(3, ChatColor.GREEN + "[Click to join]");
                    break;
                case Game.RESTARTING: s.setLine(1, ChatColor.RED + "RESTARTING");

                    break;

            }
                s.update();
            }

        }
        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }
}
