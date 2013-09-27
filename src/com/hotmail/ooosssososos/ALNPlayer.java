package com.hotmail.ooosssososos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/23/13
 * Time: 9:10 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: ALNPlayer
 */
public class ALNPlayer implements Comparable{
    public Player p;
    public int Money = 1000;
    public int TimeAlive = 0;
    public ALNPlayer(Player d){
        p = d;
    }
    public void reset(){
        Money = 1000;
        TimeAlive  = 0;
    }

    @Override
    public int compareTo(Object o) {
        if(((ALNPlayer)o).p.equals(this.p)){
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }
        else{return -1;}
    }
    public void spawn(){
        System.out.println(GameManager.getGame(p).status);
        if(GameManager.getGame(p).status == Game.UNINITIALIZED || GameManager.getGame(p).status == Game.RESTARTING){
            p.teleport(GameManager.getGame(p).lobby);
        }
        if(GameManager.getGame(p).getTeam(this) == Game.TEAM_BLUE){
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET,1);
            LeatherArmorMeta meta = ((LeatherArmorMeta)helmet.getItemMeta());
            meta.setColor(Color.fromBGR(255, 0, 0));
            p.getInventory().clear();
            helmet.setItemMeta(meta);
            p.getInventory().setHelmet(helmet);
            p.getInventory().setItem(8, new ItemStack(Material.NETHER_STAR));
            updateMoney();
            p.teleport(GameManager.getGame(p).spawn_B);
        }   else{
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET,1);
            LeatherArmorMeta meta = ((LeatherArmorMeta)helmet.getItemMeta());
            meta.setColor(Color.fromBGR(0,0,255));
            p.getInventory().clear();
            helmet.setItemMeta(meta);
            p.getInventory().setHelmet(helmet);
            p.getInventory().setItem(8, new ItemStack(Material.NETHER_STAR));
            updateMoney();
            p.teleport(GameManager.getGame(p).spawn_R);
        }


        TimeAlive = 0;
    }
    public void updateMoney(){

        ItemStack Money = new ItemStack(Material.GOLD_INGOT,1);
        ItemMeta temp = Money.getItemMeta();
        temp.setDisplayName(ChatColor.GOLD + "Money");
        List<String> lore = temp.getLore();
        if(lore == null){
            lore = new ArrayList<String>();
        }
        lore.add(ChatColor.GREEN + "You have : " + this.Money);
        temp.setLore(lore);
        Money.setItemMeta(temp);
        p.getInventory().setItem(9, Money);
        p.updateInventory();

    }

    public void Despawn(){
        p.getInventory().clear();
        p.updateInventory();
        p.teleport(GameManager.getGame(p).lobby);
    }
    public void incTime(){
        if(TimeAlive >= 60){

            p.getInventory().remove(Material.NETHER_STAR);
            p.updateInventory();
        }
        TimeAlive++;
    }
}
