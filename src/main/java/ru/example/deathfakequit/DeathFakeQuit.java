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

        // 1. Убираем стандартное сообщение о смерти
        event.setDeathMessage(null);

        // 2. Личное сообщение только умершему игроку
        player.sendMessage("§c§l☠ Ты умер!");
        player.sendMessage("§7Другие игроки видят, что ты покинул игру.");

        // 3. Фейковое сообщение о выходе — всем ОСТАЛЬНЫМ игрокам
        String fakeQuitMessage = "§e" + player.getName() + " покинул игру";
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.equals(player)) {
                onlinePlayer.sendMessage(fakeQuitMessage);
            }
        }

        // 4. Звук пробуждения визера — всем игрокам
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
