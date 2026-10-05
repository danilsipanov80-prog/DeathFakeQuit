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

        // Убираем стандартное сообщение о смерти — будем показывать своё
        event.setDeathMessage(null);

        // 1. Сразу показываем всем, что игрок убит
        Bukkit.broadcastMessage("§c☠ " + player.getName() + " был убит!");

        // 2. Личное сообщение умершему
        player.sendMessage("§c§l☠ Ты умер!");
        player.sendMessage("§7Сейчас другие увидят, что ты покинул игру...");

        // 3. Звук пробуждения визера — всем игрокам
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.playSound(
                onlinePlayer.getLocation(),
                Sound.ENTITY_WITHER_SPAWN,
                1.0F,
                1.0F
            );
        }

        // 4. Через 3 секунды (60 тиков) — фейковое сообщение о выходе
        //    Только для тех, кто ещё онлайн и не сам умерший
        Bukkit.getScheduler().runTaskLater(this, () -> {
            String fakeQuitMessage = "§e" + player.getName() + " покинул игру";
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.equals(player)) {
                    onlinePlayer.sendMessage(fakeQuitMessage);
                }
            }
        }, 60L); // 60 тиков = 3 секунды. Хочешь быстрее — поставь 40 (2 сек) или 20 (1 сек)
    }
}
