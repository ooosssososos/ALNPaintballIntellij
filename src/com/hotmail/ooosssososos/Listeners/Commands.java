package com.hotmail.ooosssososos.Listeners;

import com.hotmail.ooosssososos.CounterCraft;
import com.hotmail.ooosssososos.Game;
import com.hotmail.ooosssososos.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/22/13
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: Commands
 */
public class Commands implements CommandExecutor {
    private CounterCraft plugin; // pointer to your main class, unrequired if you don't need methods from the main class

    public Commands(CounterCraft plu) {
        this.plugin = plu;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        if(args.length < 1) return false;
        if(args[0].equals("setup")){

            if(args.length >= 3){

            if(args[1].equalsIgnoreCase("setStatusSign")){
                plugin.gm.games.get(args[2]).statusSign = p.getTargetBlock(null,10).getLocation();
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }

            else if(args[1].equalsIgnoreCase("list")){
                p.sendMessage(ChatColor.GOLD + "All Arenas Managed with status");
                for(Map.Entry<String,Game> ent : plugin.gm.games.entrySet()){
                    p.sendMessage(ent.getKey() + " : " + ent.getValue().status);
                }
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }

            else if(args[1].equalsIgnoreCase("new")){
                plugin.gm.games.put(args[2], new Game(args[2]));
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }

            else if(args[1].equalsIgnoreCase("start")){
                plugin.gm.games.get(args[2]).status = Game.IN_PROGRESS;
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }

            else if(args[1].equalsIgnoreCase("setBSpawn")){
                plugin.gm.games.get(args[2]).spawn_B = p.getLocation();
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }

            else if(args[1].equalsIgnoreCase("setLobby")){
                plugin.gm.games.get(args[2]).lobby = p.getLocation();
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }

            else if(args[1].equalsIgnoreCase("setRSpawn")){
                plugin.gm.games.get(args[2]).spawn_R = p.getLocation();
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }
            }
            if(args.length >= 4){

            if(args[1].equalsIgnoreCase("min_Player")){
                plugin.gm.games.get(args[2]).min_Player = Integer.parseInt(args[3]);
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }

            else if(args[1].equalsIgnoreCase("max_Player")){
                plugin.gm.games.get(args[2]).max_Player = Integer.parseInt(args[3]);
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }

            else if(args[1].equalsIgnoreCase("winCondition")){
                plugin.gm.games.get(args[2]).winCondition = Integer.parseInt(args[3]);
                p.sendMessage(ChatColor.DARK_GREEN + "Success");
                return true;
            }

            }

        }
        else if(args[0].equals("help")){
            p.sendMessage(ChatColor.DARK_GREEN + "ALNCounterCraft Help \n /cc setup setStatusSign <GameName> \n /cc setup setBSpawn <GameName> \n /cc setup setRSpawn <GameName> \n /cc setup min_Players <GameName> <number> \n /cc setup max_Players <GameName> <number> ");
            return true;
        }else if(args[0].equalsIgnoreCase("leave")){
            GameManager.getGame(p).remPlayer(plugin.pm.getALNPlayer(p));
            return true;
        }
        else if(args[0].equalsIgnoreCase("clear")){
            GameManager.getGame(p).restart();
        }
        else if(args[0].equalsIgnoreCase("GunPrice")){
            if(args[1].equalsIgnoreCase( "set")){
            plugin.GunPrices.put(args[2], Integer.parseInt(args[3]));
            }else if(args[1].equalsIgnoreCase("remove")){
                plugin.GunPrices.remove(args[2]);
            }
        }

        return false;
    }
}
