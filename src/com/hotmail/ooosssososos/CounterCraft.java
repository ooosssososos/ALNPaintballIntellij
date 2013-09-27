package com.hotmail.ooosssososos;

import com.hotmail.ooosssososos.Listeners.Commands;
import com.hotmail.ooosssososos.Listeners.IconMenu;
import com.hotmail.ooosssososos.Listeners.PlayerInteractListener;
import com.hotmail.ooosssososos.Listeners.PlayerShotEvent;
import com.hotmail.ooosssososos.Thread.PlayerTimeCounter;
import com.hotmail.ooosssososos.Thread.SignUpdateThread;
import com.hotmail.ooosssososos.util.SerializableLocation;
import com.shampaggon.crackshot.CSDirector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/22/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: CounterCraft
 */
public class CounterCraft extends JavaPlugin{
    public FileConfiguration conf;
    public CSDirector csapi;
    public GameManager gm;
    public ALNPlayerManager pm;
    public IconMenu GShop;
    public HashMap<String, Integer> GunPrices;
    public void onEnable(){
        getCommand("cc").setExecutor(new Commands(this));
        csapi = (CSDirector) Bukkit.getServer().getPluginManager().getPlugin("CrackShot");
        gm = new GameManager(this);
        conf = this.getConfig();
        pm = new ALNPlayerManager();
        initialize();
        loadConfig();
    }
    public void initialize(){
        for(Player p : this.getServer().getOnlinePlayers()){
            pm.add(p);
        }
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerShotEvent(this), this);
      GShop = new IconMenu("WeaponsMenu", 36, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                if(event.getName().equalsIgnoreCase("Exit")){
                    event.setWillClose(true);
                }else{
                    ALNPlayer req = pm.getALNPlayer(event.getPlayer());
                    if(GunPrices.get(event.getName())==null){

                        event.getPlayer().sendMessage("This Item isnt properly configured... no price go bug Mr_Creepersss");
                        return;
                    }
                    else if(GunPrices.get(event.getName()) <= req.Money){
                    csapi.giveWeapon(event.getPlayer(),event.getName(), 1);
                        req.Money -= GunPrices.get(event.getName());
                        req.updateMoney();
                    event.getPlayer().sendMessage("You have Bought " + event.getName() + ". " + GunPrices.get(event.getName()) + " has been deducted from your account");
                    }
                    event.getPlayer().sendMessage("You Dont have enough money");
                    event.setWillClose(false);
                }
            }
        }, this)
                .setOption(35, new ItemStack(Material.NETHER_STAR, 1), "Exit", "")
                .setOption(0, new ItemStack(Material.ARROW, 1), "DEAGLE", "Standard Issue Desert Eagle")
                .setOption(9, new ItemStack(Material.ARROW, 1), "BAZOOKA", "Bazooka nuff said")
              .setOption(10, new ItemStack(Material.ARROW, 1), "PUTTY", "REMOTE EXPLOSIVE")
              .setOption(11, new ItemStack(Material.ARROW, 1), "TYPE95", "A RIFLE I THINK")
              .setOption(12, new ItemStack(Material.ARROW, 1), "TOASTER", "a toaster that explodes when punched")
              .setOption(13, new ItemStack(Material.ARROW, 1), "COCOPOPS", "clusterbomb")
              .setOption(14, new ItemStack(Material.ARROW, 1), "AIRSTRIKE", "airstrike nuff said.")
              .setOption(15, new ItemStack(Material.ARROW, 1), "GAUSS", "GAUSS shotgun penetrates walls 40 block range")
              .setOption(16, new ItemStack(Material.ARROW, 1), "CARBINE", "Rifle with grenade launcher underbarrel")
              .setOption(17, new ItemStack(Material.ARROW, 1), "OLYMPIA", "Dragonsbreath shotgun")
              .setOption(18, new ItemStack(Material.ARROW, 1), "FLASHBANG", "BOOM aaaaand your blind")
              .setOption(19, new ItemStack(Material.ARROW, 1), "C4", "Remote detonated explosive")
              .setOption(20, new ItemStack(Material.ARROW, 1), "AK-47", "Standard issue assault rifle")
              .setOption(21, new ItemStack(Material.ARROW, 1), "HUNTING", "Scoped High power hunting rifle");



        new PlayerTimeCounter(this).runTaskTimer(this,100,20);
        new SignUpdateThread(this).runTaskTimer(this,100,5);
    }
    public void loadConfig(){
        GunPrices = new HashMap<String, Integer>();
        conf.addDefault("games",null);
        for(String rpath : conf.getConfigurationSection("games").getKeys(false)){
            ConfigurationSection sec = conf.getConfigurationSection("games." + rpath);
            Game g = new Game();
            g.Name = rpath;
            g.max_Player = sec.getInt("max_Player");
            g.min_Player = sec.getInt("min_Player");
            g.statusSign = SerializableLocation.fromString(sec.getString("statusSign")).toLocation();
            g.spawn_B = SerializableLocation.fromString(sec.getString("spawn_B")).toLocation();
            g.spawn_R = SerializableLocation.fromString(sec.getString("spawn_R")).toLocation();
            g.lobby =  SerializableLocation.fromString(sec.getString("lobby")).toLocation();
            g.status = Game.WAITING;
            g.winCondition = sec.getInt("winCondition");
            gm.games.put(g.Name, g);
        }
        GunPrices = (HashMap)conf.getConfigurationSection("gunPrices").getValues(false);
    }
    public void saveConf(){
        GunPrices.put("Test", 3);

        for(Map.Entry<String, Game> pair : gm.games.entrySet()){
            if(!conf.isConfigurationSection("games." + pair.getKey())){
                conf.createSection("games." + pair.getKey());
            }
            ConfigurationSection sec = conf.getConfigurationSection("games." + pair.getKey());

            System.out.println(pair.getValue().max_Player);
            sec.set("max_Player", pair.getValue().max_Player);
            sec.set("min_Player", pair.getValue().min_Player);
            sec.set("spawn_B", (new SerializableLocation(pair.getValue().spawn_B)).toString());
            sec.set("spawn_R", (new SerializableLocation(pair.getValue().spawn_R)).toString());
            sec.set("lobby", (new SerializableLocation(pair.getValue().lobby)).toString());
            sec.set("statusSign", (new SerializableLocation(pair.getValue().statusSign)).toString());
            sec.set("winCondition", pair.getValue().winCondition);
        }
        conf.createSection("gunPrices", GunPrices);
        this.saveConfig();
    }
    public void onDisable(){
        saveConf();
        this.setEnabled(false);
        this.getServer().getPluginManager().disablePlugin(this);

    }

}
