package org.alqj.dev.denies.commands;

import org.alqj.dev.denies.DeniesCommandPlugin;
import org.alqj.dev.denies.color.Msg;
import org.alqj.dev.denies.config.Config;
import org.alqj.dev.denies.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MainCommand implements CommandExecutor {

    private final DeniesCommandPlugin dcp;
    private final ConsoleCommandSender log;
    private Sound sound;
    private Sound sound1;
    private int volume;
    private int pitch;

    public MainCommand(DeniesCommandPlugin dcp){
        Optional<XSound> xs = XSound.matchXSound(Config.getFile().getString("sounds_settings.permission"));
        Optional<XSound> xs1 = XSound.matchXSound(Config.getFile().getString("sounds_settings.reload"));
        if(xs.isPresent() || xs1.isPresent()){
            this.sound = xs.get().parseSound();
            this.sound1 = xs1.get().parseSound();
        } else {
            this.sound = XSound.ENTITY_ITEM_BREAK.parseSound();
            this.sound1 = XSound.UI_BUTTON_CLICK.parseSound();
        }
        this.volume = Config.getFile().getInt("sounds_settings.volume");
        this.pitch = Config.getFile().getInt("sounds_settings.pitch");
        this.log = Bukkit.getConsoleSender();
        this.dcp = dcp;
    }

    private Sound getSound(){ return sound; }

    private Sound getSound1(){ return sound1; }

    private int getVolume(){ return volume; }

    private int getPitch(){ return pitch; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String la, String[] args) {
        String prefix = Config.getFile().getString("messages.prefix");
        if(sender instanceof Player){
            final Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage(Msg.color(prefix + "&7 Running on version &e" + dcp.getCurrentlyVersion() + "&7 by &a" + dcp.getDeveloperName()));
                return true;
            }

            if(args[0].equalsIgnoreCase("commands")){
                if(player.hasPermission("deniescommands.cmd.commands")){
                    String text = Config.getFile().getString("messages.list_cmds");
                    String[] textSplit = text.split("\n");
                    for(int i = 0 ; i < textSplit.length ; i++){
                        String commands = textSplit[i];
                        commands = Msg.color(commands);
                        player.sendMessage(commands);
                    }
                } else {
                    String message = Config.getFile().getString("messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    if(Config.getFile().getBoolean("sounds_settings.reproduce")) execute(player);
                    player.sendMessage(Msg.color(message));
                }
                return true;
            }

            if(args[0].equalsIgnoreCase("reload")){
                if(player.hasPermission("deniescommands.cmd.reload")){
                    long RELOAD = System.currentTimeMillis();
                    Config.reload();
                    String message = Config.getFile().getString("messages.reload");
                    message = message.replace("<prefix>", prefix);
                    message = message.replace("<ms>", (System.currentTimeMillis() - RELOAD) + "");
                    if(Config.getFile().getBoolean("sounds_settings.reproduce")) execute1(player);
                    player.sendMessage(Msg.color(message));
                } else {
                    String message = Config.getFile().getString("messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    if(Config.getFile().getBoolean("sounds_settings.reproduce")) execute(player);
                    player.sendMessage(Msg.color(message));
                }
                return true;
            }

            player.sendMessage(Msg.color(Config.getFile().getString("messages.not_command"))
                .replace("<prefix>", Msg.color(prefix)));
            return false;

        } else {
            if(args.length == 0){
                log.sendMessage(Msg.color(prefix + "&7 Running on version &e" + dcp.getCurrentlyVersion() + "&7 by &a" + dcp.getDeveloperName()));
                return true;
            }

            if(args[0].equalsIgnoreCase("commands")){
                if(log.hasPermission("deniescommands.cmd.commands")){
                    String text = Config.getFile().getString("messages.list_cmds");
                    String[] textSplit = text.split("\n");
                    for(int i = 0 ; i < textSplit.length ; i++){
                        String commands = textSplit[i];
                        commands = Msg.color(commands);
                        log.sendMessage(commands);
                    }
                } else {
                    String message = Config.getFile().getString("messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    log.sendMessage(Msg.color(message));
                }
                return true;
            }

            if(args[0].equalsIgnoreCase("reload")){
                if(log.hasPermission("deniescommands.cmd.reload")){
                    long RELOAD = System.currentTimeMillis();
                    Config.reload();
                    String message = Config.getFile().getString("messages.reload");
                    message = message.replace("<prefix>", prefix);
                    message = message.replace("<ms>", (System.currentTimeMillis() - RELOAD) + "");
                    log.sendMessage(Msg.color(message));
                } else {
                    String message = Config.getFile().getString("messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    log.sendMessage(Msg.color(message));
                }
                return true;
            }

            log.sendMessage(Msg.color(Config.getFile().getString("messages.not_command"))
                    .replace("<prefix>", Msg.color(prefix)));
            return false;
        }
    }

    private void execute(Player player){ player.playSound(player.getLocation(), getSound(), getVolume(), getPitch()); }

    private void execute1(Player player){ player.playSound(player.getLocation(), getSound1(), getVolume(), getPitch()); }
}
