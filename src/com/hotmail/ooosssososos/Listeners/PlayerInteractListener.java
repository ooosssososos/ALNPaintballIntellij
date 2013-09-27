package com.hotmail.ooosssososos.Listeners;

import com.hotmail.ooosssososos.CounterCraft;
import com.hotmail.ooosssososos.Game;
import com.hotmail.ooosssososos.GameManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/22/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: PlayerInteractListener
 */
public class PlayerInteractListener implements Listener{
    CounterCraft plugin;
    public PlayerInteractListener(CounterCraft plu){
        plugin = plu;
    }
    @EventHandler
    public void onClick(PlayerInteractEvent event){

        //Check If trying to join game
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){

            Block b = event.getClickedBlock();

            for(Map.Entry<String, Game> pair : plugin.gm.games.entrySet()){
                if(pair.getValue().statusSign.getBlock().equals(b)){
                    System.out.println(pair.getValue().players.size());
                    if(pair.getValue().players.size() <= pair.getValue().max_Player){
                        pair.getValue().addPlayer(plugin.pm.getALNPlayer(event.getPlayer()));
                        pair.getValue().spawn(plugin.pm.getALNPlayer(event.getPlayer()));
                    }else{
                        event.getPlayer().sendMessage("that game is full try another one");
                    }
                }
            }

        }

        //Check if trying to access shop
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            if(event.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR)){
                plugin.GShop.open(event.getPlayer());
            }
        }

    }
    @EventHandler
    public void Drop(PlayerDropItemEvent event){
        event.setCancelled(true);
        event.getPlayer().sendMessage("No Dropping Items!");
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(GameManager.getGame(event.getEntity()) != null){
            event.getDrops().clear();

            plugin.pm.getALNPlayer(event.getEntity()).Money += 200;
            plugin.pm.getALNPlayer(event.getEntity().getKiller()).Money += 400;

            plugin.pm.getALNPlayer(event.getEntity()).updateMoney();
            plugin.pm.getALNPlayer(event.getEntity().getKiller()).updateMoney();
            GameManager.getGame(event.getEntity().getKiller()).killConfirmed(GameManager.getGame(event.getEntity().getKiller()).getTeam(plugin.pm.getALNPlayer(event.getEntity().getKiller())));
        }
    }



    @EventHandler(priority =  EventPriority.NORMAL)
    public void invevent(InventoryDragEvent event){

        if(event.getInventory().equals(event.getWhoClicked().getInventory())){
            event.setCancelled(true);
        }

    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onres(PlayerRespawnEvent event){
        if(GameManager.getGame(event.getPlayer()) != null){
        if(GameManager.getGame(event.getPlayer()).status == Game.UNINITIALIZED || GameManager.getGame(event.getPlayer()).status == Game.RESTARTING){
            event.setRespawnLocation(GameManager.getGame(event.getPlayer()).lobby);
        }
        if(GameManager.getGame(event.getPlayer()).getTeam(plugin.pm.getALNPlayer(event.getPlayer())) == Game.TEAM_BLUE){
           event.setRespawnLocation(GameManager.getGame(event.getPlayer()).spawn_B);
        }   else{
            event.setRespawnLocation(GameManager.getGame(event.getPlayer()).spawn_R);
        }
        if(GameManager.getGame(event.getPlayer()) != null){
            plugin.pm.getALNPlayer(event.getPlayer()).spawn();
        }
        }

    }
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        plugin.pm.add(event.getPlayer());

    }
    @EventHandler
    public void invEvent(InventoryClickEvent event){
        if(GameManager.getGame((Player)event.getWhoClicked()) == null)return;
        if(event.getInventory().equals(event.getWhoClicked().getInventory())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        if(GameManager.getGame(event.getPlayer()) != null){
            GameManager.getGame(event.getPlayer()).remPlayer(plugin.pm.getALNPlayer(event.getPlayer()));
            plugin.pm.rem(event.getPlayer());
        }
    }
}
