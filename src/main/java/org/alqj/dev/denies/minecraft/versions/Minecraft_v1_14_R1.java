package org.alqj.dev.denies.minecraft.versions;

import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_14_R1.PlayerConnection;
import org.alqj.dev.denies.minecraft.NMS;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Minecraft_v1_14_R1 implements NMS {

    @Override
    public void sendTitle(Player player, String title, String subtitle, int in, int show, int out) {
        PlayerConnection connection = (((CraftPlayer)player).getHandle()).playerConnection;
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, in, show, out);
        connection.sendPacket(titlePacket);
        if(subtitle != null){
            IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle subtitleInfo = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, component);
            connection.sendPacket((Packet) subtitleInfo);
        }
        if(title != null){
            IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle titleInfo = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, component);
            connection.sendPacket((Packet) titleInfo);
        }
    }
}
