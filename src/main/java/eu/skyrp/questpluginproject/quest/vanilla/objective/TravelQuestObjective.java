package eu.skyrp.questpluginproject.quest.vanilla.objective;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TravelQuestObjective extends BaseQuestObjective<PlayerMoveEvent, Object> {

    @Setter
    @Accessors(fluent = true)
    private List<String> regions;

    /**
     * Constructeur de la classe BaseQuestObjective.
     *
     * @param regions Les r�gions qui devront �tre franchies par le joueur.
     */
    public TravelQuestObjective(List<String> regions) {
        super(PlayerMoveEvent.class, MechanicType.TRAVEL, null, 0);
        this.regions = regions;
    }

    public TravelQuestObjective() {
        this(new ArrayList<>());
    }

    /**
     * R�agit lorsque l'�v�nement de type T est d�clench�.
     *
     * @param event L'event d�clench�.
     * @return true si l'event s'est correctement ex�cut�, false sinon.
     */
    @Override
    public boolean onEventTriggered(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLoc = BukkitAdapter.adapt(player.getLocation());
        ApplicableRegionSet areaRegions = WorldGuard.getInstance()
                .getPlatform()
                .getRegionContainer()
                .createQuery()
                .getApplicableRegions(playerLoc);

        for (ProtectedRegion areaRegion : areaRegions) {
            Optional<String> foundRegion = this.regions.stream()
                    .filter(region -> region.equals(areaRegion.getId()))
                    .findAny();

            if (foundRegion.isPresent()) {
                this.regions.remove(foundRegion.get());
                player.sendMessage("�a[Quests] Vous avez franchi une r�gion concern�e par votre qu�te !");

                return this.regions.size() == 0;
            }
        }

        return false;
    }
}
