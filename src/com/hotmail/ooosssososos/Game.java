package com.hotmail.ooosssososos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 9/22/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
/*
ID: ooossso1
LANG: JAVA
TASK: Game
 */
public class Game {
    public static final int WAITING = 0;
    public static final int IN_PROGRESS = 1;
    public static final int RESTARTING = 2;
    public static final int UNINITIALIZED = 3;
    public int min_Player;
    public int max_Player;
    public static final int TEAM_RED = 1;
    public static final int TEAM_BLUE = 2;
    public Location spawn_B;
    public Location spawn_R;
    public Location lobby;
    public int status;
    public Location statusSign;
    public String Name;
    public HashMap<ALNPlayer, Integer> players;
    public int blueMembers = 0;
    public int redMembers = 0;
    public int RScore = 0;
    public int BScore = 0;
    public int winCondition;
    public Game(){
        players = new HashMap<ALNPlayer, Integer>();
        status = 3;
    }
    private void checkWin(){
        if(BScore >= winCondition){
            Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "BLUE TEAM HAS WON THE MATCH, RESTARTING");
            restart();
        } else if(RScore >= winCondition){
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "RED TEAM HAS WON THE MATCH, RESTARTING");
            restart();
        }
    }
    public void killConfirmed(int team){
        switch(team){
            case Game.TEAM_BLUE:
                Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "BlueTeam Point");
                BScore++;
                break;
            case Game.TEAM_RED:
                Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "RedTeam Point");
                RScore++;
                break;
        }

        checkWin();
    }
    public Game(String nam){
        players = new HashMap<ALNPlayer, Integer>();
        status = 3;
        Name = nam;
    }
    public void restart(){
        RScore = 0;
        BScore = 0;
        for(Map.Entry<ALNPlayer,Integer> pair : players.entrySet()){
            pair.getKey().reset();
        }
        players = new HashMap<ALNPlayer, Integer>();
        updateTeams();
        status = 2;
    }
    public int getTeam(ALNPlayer p){
        return players.get(p);
    }
    public void addPlayer(ALNPlayer p){
        if(players.size() >= min_Player){
            this.startGame();
        }
        if(blueMembers > redMembers){
            players.put(p , TEAM_RED);
        } else{
            players.put(p , TEAM_BLUE);
        }
        if(players.size() >= min_Player && status == Game.WAITING){
            startGame();
        }
        updateTeams();
    }
    public void remPlayer(ALNPlayer p){

        p.Despawn();
        players.remove(p);
        updateTeams();
    }
    public void startGame(){
        for(ALNPlayer p : players.keySet()){
            p.p.sendMessage("Minimum Met Starting Game");
            p.spawn();
        }

        status = Game.IN_PROGRESS;
    }
    public void updateTeams(){
        for(Map.Entry<ALNPlayer,Integer> pair : players.entrySet()){
            if(pair.getValue() == TEAM_BLUE){
                blueMembers++;
            }   else{
                redMembers++;
            }
        }
    }
    public void spawn(ALNPlayer p){
       p.spawn();

    }

}
