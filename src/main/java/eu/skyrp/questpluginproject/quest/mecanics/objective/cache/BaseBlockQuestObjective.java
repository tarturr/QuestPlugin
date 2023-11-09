package eu.skyrp.questpluginproject.quest.mecanics.objective.cache;

import dev.lone.itemsadder.api.CustomBlock;
import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseCountableQuestObjective;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class BaseBlockQuestObjective<T extends BlockEvent> extends BaseCountableQuestObjective<T, ItemStack> implements Cachable<Block> {

    private final JavaPlugin plugin;

    /**
     * @param id         Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BaseBlockQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(id, playerUUID, getBlockById(targetId), amount);
        this.plugin = plugin;
    }

    @Override
    public void onEventTriggered(T event) {
        Player player = super.player();
        Block block = event.getBlock();

        if (CustomBlock.byAlreadyPlaced(block) == null)
            this.onBlockTriggered(block, player);
    }

    protected void onBlockTriggered(Block block, Player player) {
        player.sendMessage("Triggered");

        /*
         TODO: Modifier la classe pour prendre soit un Material, soit un CustomBlock de ItemsAdder, soit le CustomBlock
           de SlimeFun en U de BaseCountableQuestObjective<T, U>
         */
        if (!(isTarget(block) && !this.hasElemPlayer(player, block))) {
            return;
        }

        player.sendMessage("Tests passed");

        this.addPlayerToElem(player, block);
        super.incrementCount();

        if (super.getCount() >= super.getAmount()) {
            player.sendMessage("�a[Quests] Qu�te termin�e !");
        }
    }

    /**
     * Check si le block correspond a la bonne target
     * @param block block cibl�
     * @return true Si la target et bloc corresponde
     */
    private boolean isTarget(Block block) {
        SlimefunItem slimefunItem = BlockStorage.check(block);
        CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);

        if (slimefunItem != null) {
            return slimefunItem.getItem().isSimilar(super.target);
        } else if (customBlock != null) {
            return customBlock.getItemStack().isSimilar(super.target);
        }
        return block.getType() == super.target.getType();
    }

    protected abstract Player getEventPlayer(T event);

    /**
     * V�rifie si le block a d�j� le joueur en cache.
     * @param player Le joueur en question.
     * @param elem Le block en question.
     * @return true si le block a le joueur en cache, false sinon.
     */
    @Override
    public final boolean hasElemPlayer(Player player, Block elem) {
        return elem.hasMetadata(player.getUniqueId().toString());
    }

    /**
     * Ajoute le joueur dans le cache du block.
     * @param player Le joueur en question.
     * @param elem Le block en question.
     */
    @Override
    public final void addPlayerToElem(Player player, Block elem) {
        elem.setMetadata(player.getUniqueId().toString(), new FixedMetadataValue(this.plugin, true));
    }

    // TODO: Ajouter la possibilit� d'un CustomItemStack (SlimeFun)
    private static ItemStack getBlockById(String targetId) {
        if (!targetId.contains(":")) {
            return new ItemStack(Material.valueOf(targetId.toUpperCase()));
        }

        String[] parts = targetId.split(":");
        String prefix = parts[0];
        String item   = parts[1];

        if (prefix.equalsIgnoreCase("ia") || prefix.equalsIgnoreCase("itemsadder")) {
            return CustomBlock.isInRegistry(item) ? CustomBlock.getInstance(item).getItemStack() : null;
        } else if (prefix.equalsIgnoreCase("sf") || prefix.equalsIgnoreCase("slimefun")) {
            SlimefunItem slimefunItem = SlimefunItem.getById(item);
            return slimefunItem != null ? slimefunItem.getItem() : null;
        } else {
            throw new IllegalArgumentException("The \"" + prefix + "\" item is not recognized by the plugin. " +
                    "Please use \"ia:\"/\"itemsadder:\" prefix for ItemsAdder or \"sf:\"/\"slimefun:\" prefix for Slimefun.");
        }
    }
}
