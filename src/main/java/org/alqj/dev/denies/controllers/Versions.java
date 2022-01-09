package org.alqj.dev.denies.controllers;

import org.alqj.dev.denies.DeniesCommandPlugin;
import org.alqj.dev.denies.color.Msg;
import org.alqj.dev.denies.minecraft.NMS;
import org.alqj.dev.denies.minecraft.versions.*;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Versions {

    private final DeniesCommandPlugin dcp;
    private final ConsoleCommandSender log;
    private NMS minecraft;

    public Versions(DeniesCommandPlugin dcp){
        this.dcp = dcp;
        this.log = Bukkit.getConsoleSender();
        setupVersion();
    }

    private void setupVersion(){
        try{
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            switch(version){
                case "v1_8_R3":
                    minecraft = new Minecraft_v1_8_R3();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.8_R3"));
                    return;
                case "v1_9_R2":
                    minecraft = new Minecraft_v1_9_R2();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.9_R2"));
                    return;
                case "v1_10_R1":
                    minecraft = new Minecraft_v1_10_R1();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.10_R1"));
                    return;
                case "v1_11_R1":
                    minecraft = new Minecraft_v1_11_R1();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.11_R1"));
                    return;
                case "v1_12_R1":
                    minecraft = new Minecraft_v1_12_R1();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.12_R1"));
                    return;
                case "v1_13_R2":
                    minecraft = new Minecraft_v1_13_R2();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.13_R2"));
                    return;
                case "v1_14_R1":
                    minecraft = new Minecraft_v1_14_R1();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.14_R1"));
                    return;
                case "v1_15_R1":
                    minecraft = new Minecraft_v1_15_R1();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.15_R1"));
                    return;
                case "v1_16_R3":
                    minecraft = new Minecraft_v1_16_R3();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.16_R3"));
                    return;
                case "v1_17_R1":
                    minecraft = new Minecraft_v1_17_R1();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.17_R1"));
                    return;
                case "v1_18_R1":
                    minecraft = new Minecraft_v1_18_R1();
                    log.sendMessage(Msg.color("&f Minecraft: &e1.18_R1"));
                    return;
            }
            log.sendMessage(Msg.color("&c Your server version is not supported by the plugin."));
            log.sendMessage(Msg.color("&c The plugin will be deactivated..."));
            Bukkit.getPluginManager().disablePlugin(dcp);
        } catch(ArrayIndexOutOfBoundsException ex){
            log.sendMessage(Msg.color("&c An occurred a error to check your server version..."));
            Bukkit.getPluginManager().disablePlugin(dcp);
        }
    }

    public NMS getMinecraft(){ return minecraft; }
}
