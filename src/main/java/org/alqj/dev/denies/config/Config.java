package org.alqj.dev.denies.config;

import org.alqj.dev.denies.DeniesCommandPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private static final DeniesCommandPlugin dcp = DeniesCommandPlugin.getPlugin(DeniesCommandPlugin.class);

    private static File file;
    private static FileConfiguration config;

    public static void createFile(){
        file = new File("plugins/DeniesCommands", "config.yml");
        if(!file.exists()) dcp.saveResource("config.yml", false);
    }

    public static void loadFile(){ config = getFileInstance(); }

    public static void saveFile(){
        try{
            config.save(file);
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private static FileConfiguration getFileInstance(){ return YamlConfiguration.loadConfiguration(file); }

    public static void reload(){ config = YamlConfiguration.loadConfiguration(file); }

    public static FileConfiguration getFile(){ return config; }
}
