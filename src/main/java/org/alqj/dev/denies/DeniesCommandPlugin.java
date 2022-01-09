package org.alqj.dev.denies;

import org.alqj.dev.denies.color.Msg;
import org.alqj.dev.denies.commands.MainCommand;
import org.alqj.dev.denies.commands.TabComplete;
import org.alqj.dev.denies.config.Config;
import org.alqj.dev.denies.controllers.Versions;
import org.alqj.dev.denies.listeners.CommandsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class DeniesCommandPlugin extends JavaPlugin {

    PluginDescriptionFile pdffile = getDescription();
    private final String DEVELOPER = "iAlqjDV";
    private final String VERSION = pdffile.getVersion();
    private final ConsoleCommandSender log = Bukkit.getConsoleSender();
    private Versions versions;

    public String getDeveloperName(){ return DEVELOPER; }

    public String getCurrentlyVersion(){ return VERSION; }

    @Override
    public void onEnable() {
        long START = System.currentTimeMillis();

        log.sendMessage(Msg.color(""));
        log.sendMessage(Msg.color("&c   DeniesCommands"));
        log.sendMessage(Msg.color(""));
        log.sendMessage(Msg.color("&f Developer: &a" + getDeveloperName()));
        log.sendMessage(Msg.color("&f  Version: &a" + getCurrentlyVersion()));
        log.sendMessage(Msg.color(""));

        try{
            Class.forName("org.spigotmc.SpigotConfig");
        } catch(ClassNotFoundException ex){
            log.sendMessage(Msg.color("&c Could not found a Spigot/Paper .jar, please install the required jar and restart the server."));
            log.sendMessage(Msg.color("&c The plugin will be deactivated..."));
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Config.createFile();
        Config.loadFile();
        setupCommands();
        getServer().getPluginManager().registerEvents(new CommandsManager(this), this);

        versions = new Versions(this);

        log.sendMessage(Msg.color("&a Enabled in &e" + (System.currentTimeMillis() - START) + "ms&a."));
        log.sendMessage(Msg.color(""));
    }

    @Override
    public void onDisable(){}

    public Versions getVersions(){ return versions; }

    public PluginDescriptionFile getPDFFIle(){ return pdffile; }

    private void setupCommands(){
        getCommand("deniescommands").setExecutor(new MainCommand(this));

        getCommand("deniescommands").setTabCompleter(new TabComplete());
    }
}
