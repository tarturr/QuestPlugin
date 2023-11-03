package eu.skyrp.questpluginproject.quest.mecanics.objective;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.UUID;

@Getter
@Accessors(fluent = true)
public abstract class BaseQuestObjective<T extends Event, U> implements Listener {

    protected final String id;
    protected final UUID playerUUID;
    protected final U target;

    public BaseQuestObjective(String id, UUID playerUUID, U target) {
        this.id = id;
        this.playerUUID = playerUUID;
        this.target = target;
    }

    public abstract void onEventTriggered(T event);

    public Player player() {
        return Bukkit.getPlayer(this.playerUUID);
    }

}
