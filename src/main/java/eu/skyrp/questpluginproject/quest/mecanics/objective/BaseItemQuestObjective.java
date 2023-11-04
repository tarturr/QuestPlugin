package eu.skyrp.questpluginproject.quest.mecanics.objective;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class BaseItemQuestObjective<T extends Event> extends BaseCountableQuestObjective<T, ItemStack> {

    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     * @param id Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param target Objet inclus dans l'objectif de quête.
     * @param amount Nombre requis pour atteindre le bout de la quête.
     */
    public BaseItemQuestObjective(String id, UUID playerUUID, ItemStack target, int amount) {
        super(id, playerUUID, target, amount);
    }

}
