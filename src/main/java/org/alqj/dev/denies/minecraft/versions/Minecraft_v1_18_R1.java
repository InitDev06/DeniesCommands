package org.alqj.dev.denies.minecraft.versions;

import org.alqj.dev.denies.minecraft.NMS;
import org.bukkit.entity.Player;

public class Minecraft_v1_18_R1 implements NMS {

    @Override
    public void sendTitle(Player player, String title, String subtitle, int in, int show, int out) {
        player.sendTitle(title, subtitle, in, show, out);
    }
}
