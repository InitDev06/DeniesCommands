package org.alqj.dev.denies.listeners;

import org.alqj.dev.denies.DeniesCommandPlugin;
import org.alqj.dev.denies.color.Msg;
import org.alqj.dev.denies.config.Config;
import org.alqj.dev.denies.util.StringUtil;
import org.alqj.dev.denies.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.Optional;

public class CommandsManager implements Listener {

    private final DeniesCommandPlugin dcp;
    private final ConsoleCommandSender log;

    private Sound sound;
    private int volume;
    private int pitch;

    public CommandsManager(DeniesCommandPlugin dcp){
        Optional<XSound> xs = XSound.matchXSound(Config.getFile().getString("sounds_settings.blocked_cmd"));
        if(xs.isPresent()) this.sound = xs.get().parseSound();
        else this.sound = XSound.ENTITY_VILLAGER_DEATH.parseSound();

        this.volume = Config.getFile().getInt("sounds_settings.volume");
        this.pitch = Config.getFile().getInt("sounds_settings.pitch");
        this.log = Bukkit.getConsoleSender();
        this.dcp = dcp;
    }

    private Sound getSound(){ return sound; }

    private int getVolume(){ return volume; }

    private int getPitch(){ return pitch; }

    @EventHandler
    public void onBlockedCommands(PlayerCommandPreprocessEvent ev){
        final FileConfiguration config = Config.getFile();
        String prefix = config.getString("messages.prefix");
        final Player player = ev.getPlayer();
        if(config.getBoolean("messages.commands.blocked_cmds_toggle")){
            List<String> list = config.getStringList("messages.commands.blocked_cmds");
            for(int i = 0 ; i < list.size() ; i++){
                String commands = list.get(i);

                if(ev.getMessage().equalsIgnoreCase(commands)){
                    if(player.hasPermission("deniescommands.bypass.commands")){
                        player.sendMessage(Msg.color(config.getString("messages.commands.player_bypass_block")));
                        return;
                    }

                    ev.setCancelled(true);
                    String blocked = config.getString("messages.commands.blocked_command");
                    blocked = StringUtil.setPlaceholders(blocked, player);
                    blocked = blocked.replace("<prefix>", prefix);
                    blocked = blocked.replace("<command>", ev.getMessage());
                    player.sendMessage(Msg.color(blocked));
                    if(config.getBoolean("messages.commands.punishments_toggle")){
                        if(player.hasPermission("deniescommands.bypass.punishments")) return;

                        List<String> list1 = config.getStringList("messages.commands.punishments");
                        for(int x = 0 ; x < list1.size() ; x++){
                            String punishments = list1.get(x);
                            punishments = StringUtil.setPlaceholders(punishments, player);
                            punishments = Msg.color(punishments);

                            String type_executor = config.getString("messages.commands.punishments_executor");
                            if(type_executor.equals("CONSOLE")){
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), punishments);
                            } else if(type_executor.equals("PLAYER")){
                                if(player.hasPermission("deniescommands.dispatch.punishments")){
                                    Bukkit.dispatchCommand(player, punishments);
                                }
                            } else {
                                if(player.hasPermission("deniescommands.log.error")){
                                    player.sendMessage(Msg.color(config.getString("messages.commands.unknown_type"))
                                            .replace("<prefix>", Msg.color(prefix)));
                                }
                            }
                        }
                    }

                    if(config.getBoolean("sounds_settings.reproduce")) execute(player);
                    if(config.getBoolean("titles_settings.send")){
                        String title = config.getString("messages.commands.blocked_cmd_title");
                        String subtitle = config.getString("messages.commands.blocked_cmd_subtitle");

                        title = title.replace("<command>", ev.getMessage());
                        subtitle = subtitle.replace("<command>", ev.getMessage());
                        title = Msg.color(title);
                        subtitle = Msg.color(subtitle);

                        int in = config.getInt("titles_settings.in");
                        int show = config.getInt("titles_settings.show");
                        int out = config.getInt("titles_settings.out");

                        StringUtil.sendTitle(player, title, subtitle, in, show, out);
                    }

                    String message = config.getString("messages.commands.console_logger");
                    message = StringUtil.setPlaceholders(message, player);
                    Bukkit.getConsoleSender().sendMessage(Msg.color(message));
                    if(player.hasPermission("deniescommands.log.command")){
                        if(player.hasPermission("deniescommands.bypass.logs")) return;

                        String message1 = config.getString("messages.commands.blocked_cmd_logger");
                        message1 = StringUtil.setPlaceholders(message1, player);
                        player.sendMessage(Msg.color(message1));
                    }
                }
            }
        }
    }

    private void execute(Player player){ player.playSound(player.getLocation(), getSound(), getVolume(), getPitch()); }
}
