package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseCountableQuestObjective;
import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class ConnectQuestObjective extends BaseCountableQuestObjective<PlayerJoinEvent, Player> {

    private final JavaPlugin main;
    private BukkitTask playerTimer;

    @Builder
    public ConnectQuestObjective(JavaPlugin main, String id, UUID player, int toReach) {
        super(id, player, Bukkit.getPlayer(player), toReach);
        this.main = main;
        this.playerTimer = null;
    }

    @EventHandler
    @Override
    public void onEventTriggered(PlayerJoinEvent event) {
        if (event.getPlayer() != super.target) {
            return;
        }

        if (this.playerTimer == null) {
            this.playerTimer = Bukkit.getScheduler().runTaskTimer(main, () -> {
                if (super.getCount() < super.getAmount()) {
                    return;
                }

                super.incrementCount();

                if (super.getCount() < super.getAmount()) {
                    return;
                }

                super.target.sendMessage("§a[Quests] Quête terminée !");
                this.playerTimer.cancel();
            }, 0, 20);
        } else {
            throw new IllegalStateException("The player connect timer cannot exist when player joins. " +
                    "Please avoir server reloads, restart it instead.");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (event.getPlayer() != super.target) {
            return;
        }

        if (this.playerTimer != null) {
            this.playerTimer.cancel();
            this.playerTimer = null;
        } else {
            throw new IllegalStateException("The player connect timer cannot be null. " +
                    "Please avoir server reloads, restart it instead.");
        }
    }

}
