package com.hotmail.ooosssososos.Listeners;

import com.hotmail.ooosssososos.CounterCraft;
import com.hotmail.ooosssososos.Game;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import java.util.EventListener;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/23/13
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: PlayerShotEvent
 */
public class PlayerShotEvent implements Listener{
    CounterCraft plugin;
    public PlayerShotEvent(CounterCraft plu){
        plugin = plu;
    }
    @EventHandler
    public void onWasShot(WeaponDamageEntityEvent event){
        Game g = plugin.gm.getGame(event.getPlayer());
        if(g == null) return;
        if(event.getVictim() instanceof Player){

            if(g.getTeam(plugin.pm.getALNPlayer(event.getPlayer())) == g.getTeam(plugin.pm.getALNPlayer((Player)event.getVictim()))){event.setCancelled(true);return;}
            plugin.pm.getALNPlayer((Player)event.getVictim()).Money += 30;

        }


    }
}
