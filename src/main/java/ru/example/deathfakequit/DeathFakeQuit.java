package ru.example.deathfakequit;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathFakeQuit extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("DeathFakeQuit включен!");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);
        Bukkit.broadcastMessage("§e" + player.getName() + " покинул игру");

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.playSound(
                onlinePlayer.getLocation(),
                Sound.ENTITY_WITHER_SPAWN,
                1.0F,
                1.0F
            );
        }
    }
}
