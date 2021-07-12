package cz.tomasan7.upgrades.commands.subCommands;

import cz.tomasan7.upgrades.commands.SubCommand;
import cz.tomasan7.upgrades.menus.MainMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Open extends SubCommand {

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
    public void perform (CommandSender sender, String[] args)
    {
        if (sender instanceof Player)
            ((Player)sender).openInventory(MainMenu.getInventory((Player)sender));
        else
            sender.sendMessage("§cOnly players can do that.");
    }
}
