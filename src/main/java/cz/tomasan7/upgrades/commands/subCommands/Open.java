package cz.tomasan7.upgrades.commands.subCommands;

import cz.tomasan7.CommandManager.SubCommand;
import cz.tomasan7.upgrades.Upgrades;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Open implements SubCommand
{
    @Override
    public String getName ()
    {
        return "open";
    }

    @Override
    public String getDescription ()
    {
        return "Opens upgrades menu.";
    }

    @Override
    public String getSyntax ()
    {
        return "/upgrades open";
    }

    @Override
    public String getPermission ()
    {
        return "upgrades.open";
    }

    @Override
    public List<String> getTabCompletions (int i, String[] strings)
    {
        return null;
    }

    @Override
    public void perform (CommandSender sender, String[] args)
    {
        if (sender instanceof Player)
            ((Player)sender).openInventory(Upgrades.getMainMenu().getInventory());
        else
            sender.sendMessage("§cOnly players can do that.");
    }
}
