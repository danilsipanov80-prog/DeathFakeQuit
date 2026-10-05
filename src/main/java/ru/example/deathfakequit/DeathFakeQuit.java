package ru.example.deathfakequit;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
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

        // 1. Определяем причину смерти
        String deathReason = "умер";
        EntityDamageEvent lastDamage = player.getLastDamageCause();
        if (lastDamage != null) {
            EntityDamageEvent.DamageCause cause = lastDamage.getCause();
            switch (cause) {
                case ENTITY_ATTACK:
                case ENTITY_SWEEP_ATTACK:
                    if (player.getKiller() != null) {
                        deathReason = "был убит игроком " + player.getKiller().getName();
                    } else {
                        deathReason = "был убит мобом";
                    }
                    break;
                case PROJECTILE:
                    deathReason = "был застрелен";
                    break;
                case FALL:
                    deathReason = "разбился насмерть";
                    break;
                case BLOCK_EXPLOSION:
                case ENTITY_EXPLOSION:
                    deathReason = "взорвался";
                    break;
                case FIRE:
                case FIRE_TICK:
                    deathReason = "сгорел";
                    break;
                case LAVA:
                    deathReason = "сгорел в лаве";
                    break;
                case DROWNING:
                    deathReason = "утонул";
                    break;
                case VOID:
                    deathReason = "упал в пустоту";
                    break;
                case POISON:
                    deathReason = "отравился";
                    break;
                case WITHER:
                    deathReason = "умер от иссушения";
                    break;
                case STARVATION:
                    deathReason = "умер от голода";
                    break;
                case MAGIC:
                    deathReason = "умер от магии";
                    break;
                case LIGHTNING:
                    deathReason = "был убит молнией";
                    break;
                case SUFFOCATION:
                    deathReason = "задохнулся";
                    break;
                case CONTACT:
                    deathReason = "умер от кактуса";
                    break;
                case CRAMMING:
                    deathReason = "был раздавлен";
                    break;
                case FLY_INTO_WALL:
                    deathReason = "влетел в стену";
                    break;
                case HOT_FLOOR:
                    deathReason = "сгорел на магме";
                    break;
                case DRAGON_BREATH:
                    deathReason = "умер от дыхания дракона";
                    break;
                case FALLING_BLOCK:
                    deathReason = "был раздавлен блоком";
                    break;
                case THORNS:
                    deathReason = "умер от шипов";
                    break;
                default:
                    deathReason = "умер";
            }
        }

        // 2. Сразу показываем причину смерти всем
        Bukkit.broadcastMessage("§c" + player.getName() + " " + deathReason);

        // 3. Сразу же показываем фейковый выход из игры
        Bukkit.broadcastMessage("§e" + player.getName() + " покинул игру");

        // 4. Личное сообщение умершему
        player.sendMessage("§cТы " + deathReason);

        // 5. Звук пробуждения визера — всем
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
