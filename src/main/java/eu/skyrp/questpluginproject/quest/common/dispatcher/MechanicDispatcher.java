package eu.skyrp.questpluginproject.quest.common.dispatcher;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic.IABreakMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic.IACollectMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic.IAPlaceMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic.SFBreakMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic.SFCollectMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic.SFPlaceMechanic;
import eu.skyrp.questpluginproject.quest.vanilla.mechanic.*;

public class MechanicDispatcher implements Dispatchable<BaseMechanic<?>> {

    @Override
    public BaseMechanic<?> dispatch(String str) {
        return switch (MechanicType.valueOf(str.toUpperCase())) {
            case BREAK -> new BreakMechanic();
            case IA_BREAK -> new IABreakMechanic();
            case SF_BREAK -> new SFBreakMechanic();
            case COLLECT -> new CollectMechanic();
            case IA_COLLECT -> new IACollectMechanic();
            case SF_COLLECT -> new SFCollectMechanic();
            case CONNECT -> new ConnectMechanic();
            case KILL -> new KillMechanic();
            case PLACE -> new PlaceMechanic();
            case IA_PLACE -> new IAPlaceMechanic();
            case SF_PLACE -> new SFPlaceMechanic();
            case TRAVEL -> new TravelMechanic();
        };
    }

}
