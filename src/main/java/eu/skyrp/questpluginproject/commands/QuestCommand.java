package eu.skyrp.questpluginproject.commands;

import eu.skyrp.questpluginproject.quest.manager.QuestManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class QuestCommand implements CommandExecutor {

    private final QuestManager manager;

    public QuestCommand(QuestManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                System.err.println("[QuestPlugin] La console ne peut pas exécuter cette commande.");
                return false;
            }

            player.sendMessage("§a[Quests] Voici la liste de vos quêtes :");

            this.manager.getPlayerQuests(player.getUniqueId()).quests().forEach(quest -> {
                player.sendMessage("- §b" + quest.name());
            });

            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                this.manager.reload();
                sender.sendMessage("§a[Quests] Toutes les quêtes ont été rechargées !");
                return true;
            }
        }

        return false;
    }

}
