package eu.skyrp.questpluginproject.quest.mecanics.objective;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class BaseItemQuestObjective<T extends Event> extends BaseCountableQuestObjective<T, ItemStack> {

    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     * @param id Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     * @param amount Nombre requis pour atteindre le bout de la qu�te.
     */
    public BaseItemQuestObjective(String id, UUID playerUUID, ItemStack target, int amount) {
        super(id, playerUUID, target, amount);
    }

}
