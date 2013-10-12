package com.hotmail.ooosssososos.GameType;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.hotmail.ooosssososos.ALNPlayer;
import com.hotmail.ooosssososos.ALNPlayerManager;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftMinecart;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 10/2/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: CTFGame
 */
public class CTFGame extends Game{
    public Location R_Flag;
    public Location B_Flag;
    public Location R_Flag_Def;
    public Location B_Flag_Def;
    public static final int B_FLAG_NUM = 2;
    public static final int R_FLAG_NUM = 1;
    public ALNPlayer B_Possession = null;
    public ALNPlayer R_Possession = null;
    public ItemStack B_Flag_Item = new ItemStack(Material.WOOL,1,(short)11);
    public ItemStack R_Flag_Item = new ItemStack(Material.WOOL,1,(short)14);
    public CTFGame(){
        super();
        GameType = "CTF";
    }
    public CTFGame(String n){
        super();
        GameType = "CTF";
    }

    @Override
    public void PlayerDeath(PlayerDeathEvent event) {
        ALNPlayer p = ALNPlayerManager.getALNPlayer(event.getEntity());

        if(R_Possession != null && R_Possession.equals(p)){
            R_Flag = p.p.getLocation();
            R_Possession = null;
            R_Flag.getBlock().setType(Material.WOOL);
            R_Flag.getBlock().setData(DyeColor.RED.getWoolData());
            Bukkit.getServer().broadcastMessage("Red flag has been dropped at " + R_Flag.toString());
        }else if(B_Possession != null && B_Possession.equals(p)){
            B_Flag = p.p.getLocation();
            B_Possession = null;
            R_Flag.getBlock().setType(Material.WOOL);
            R_Flag.getBlock().setData(DyeColor.BLUE.getWoolData());
            Bukkit.getServer().broadcastMessage("Blue flag has been dropped at " + R_Flag.toString());
        }
    }
    //Does not increment point
    public void resetFlag(int flag){
        if(flag == CTFGame.R_FLAG_NUM){
            R_Flag = R_Flag_Def;
            R_Possession = null;
            R_Flag.getBlock().setType(Material.WOOL);
            R_Flag.getBlock().setData(DyeColor.RED.getWoolData());
            Bukkit.getServer().broadcastMessage("RED FLAG RESET");

        }else{
            B_Flag = B_Flag_Def;
            B_Possession = null;
            R_Flag.getBlock().setType(Material.WOOL);
            R_Flag.getBlock().setData(DyeColor.BLUE.getWoolData());
            R_Flag.getBlock().setType(Material.AIR);
            Bukkit.getServer().broadcastMessage("BLUE FLAG RESET");
        }
    }
    @Override
    public void PlayerMove(PlayerMoveEvent event) {






        Location to = event.getTo();
        ALNPlayer p = ALNPlayerManager.getALNPlayer(event.getPlayer());
        if(p.p.isDead() == true)return;
        if( R_Flag.distance(to) <= 2){
            if(getTeam(p) != Game.TEAM_RED){
                if(R_Possession == null){
                    R_Possession = p;
                    R_Flag.getBlock().setType(Material.AIR);
                //Effect For FlagPickup Red
                    Bukkit.getServer().broadcastMessage("Red team flag has been taken by : " + p.p.getName());
                    p.p.getInventory().setHelmet(R_Flag_Item);
                }
            }else if(B_Possession == p){
                    point(Game.TEAM_RED);
                    resetFlag(CTFGame.TEAM_BLUE);
                    ItemStack helmet = new ItemStack(Material.LEATHER_HELMET,1);
                    LeatherArmorMeta meta = ((LeatherArmorMeta)helmet.getItemMeta());
                    meta.setColor(Color.fromBGR(0, 0, 255));
                    helmet.setItemMeta(meta);
                    p.p.getInventory().setHelmet(helmet);
            }else if(getTeam(p) == Game.TEAM_RED && R_Possession == null && R_Flag != R_Flag_Def){
                R_Flag.getBlock().setType(Material.AIR);
                resetFlag(Game.TEAM_RED);

                Bukkit.getServer().broadcastMessage("Red Flag Retured");
            }
        }else if( B_Flag.distance(to) <= 2){
            if(getTeam(p) != Game.TEAM_BLUE){
                if(B_Possession == null){
                    B_Possession = p;
                    B_Flag.getBlock().setType(Material.AIR);
                    //Effect For FlagPickup Red
                    Bukkit.getServer().broadcastMessage("BLUE team flag has been taken by : " + p.p.getName());
                    p.p.getInventory().setHelmet(B_Flag_Item);
                }
            }else if(R_Possession == p){
                point(Game.TEAM_BLUE);
                resetFlag(CTFGame.TEAM_RED);
                ItemStack helmet = new ItemStack(Material.LEATHER_HELMET,1);
                LeatherArmorMeta meta = ((LeatherArmorMeta)helmet.getItemMeta());
                meta.setColor(Color.fromBGR(255, 0, 0));
                helmet.setItemMeta(meta);
                p.p.getInventory().setHelmet(helmet);
            }else if(getTeam(p) == Game.TEAM_BLUE && B_Possession == null && B_Flag != B_Flag_Def){
                B_Flag.getBlock().setType(Material.AIR);
                resetFlag(Game.TEAM_BLUE);
                Bukkit.getServer().broadcastMessage("Blue Flag Retured");
            }
        }

    }
}
