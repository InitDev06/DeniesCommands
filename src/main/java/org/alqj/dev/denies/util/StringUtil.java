package org.alqj.dev.denies.util;

import org.alqj.dev.denies.DeniesCommandPlugin;
import org.alqj.dev.denies.config.Config;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    private static final DeniesCommandPlugin dcp = DeniesCommandPlugin.getPlugin(DeniesCommandPlugin.class);

    public static void sendTitle(Player player, String title, String subtitle, int in, int show, int out){
        dcp.getVersions().getMinecraft().sendTitle(player, title, subtitle, in, show, out);
    }

    public static String setPlaceholders(String text, Player player){
        if(player == null) return null;

        if(text.contains("<player>")) text = text.replace("<player>", player.getName());
        if(text.contains("<uuid>")) text = text.replace("<uuid>", player.getUniqueId() + "");
        if(text.contains("<address>")) text = text.replace("<address>", player.getAddress() + "");
        if(text.contains("<date_format>")){
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat(Config.getFile().getString("date_format"));
            text = text.replace("<date_format>", format.format(date));
        }

        return text;
    }
}
