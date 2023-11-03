package eu.skyrp.questpluginproject.quest.mecanics.objective;

import org.bukkit.event.Event;

import java.util.UUID;

public abstract class BaseCountableQuestObjective<T extends Event, U> extends BaseQuestObjective<T, U> implements Countable {

    protected final int amount;
    protected int count;

    public BaseCountableQuestObjective(String id, UUID player, U targetType, int amount) {
        super(id, player, targetType);
        this.amount = amount;
    }

    protected void incrementCount() {
        ++this.count;
    }

    @Override
    public final int getCount() {
        return this.count;
    }

    @Override
    public final int getAmount() {
        return this.amount;
    }
}
