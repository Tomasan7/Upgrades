package cz.tomasan7.upgrades.commands;

import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.commands.subCommands.Open;
import cz.tomasan7.upgrades.commands.subCommands.Reload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubCommandsManager implements TabExecutor {

    public ArrayList<SubCommand> subCommands = new ArrayList<>();

    public SubCommandsManager ()
    {
        subCommands.add(new Reload());
        subCommands.add(new Open());
    }

    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 1)
        {
            if (args[0].equals("help"))
                showHelp(sender);
            else
            {
                for (SubCommand subCommand : getSubCommands())
                {
                    if (subCommand.getName().equals(args[0]))
                    {
                        subCommand.perform(sender, args);
                    }
                }
            }
        }
        else if (args.length == 0)
        {
            for (SubCommand subCommand : getSubCommands())
            {
                if (subCommand.getName().equals("open"))
                {
                    subCommand.perform(sender, args);
                }
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands ()
    {
        return subCommands;
    }

    private void showHelp (CommandSender sender)
    {
        Plugin plugin = Main.getPlugin(Main.class);
        String version = plugin.getDescription().getVersion();

        sender.sendMessage("§7§m               §b§l Upgrades §f§l" + version + " §7§m               ");

        for (SubCommand subCommand : getSubCommands())
        {
            if (sender.hasPermission(subCommand.getPermission()))
                sender.sendMessage("§b/upgrades " + subCommand.getName() + " §f| §3" + subCommand.getDescription());
        }

        sender.sendMessage("§7§m                                               ");
    }

    @Override
    public List<String> onTabComplete (CommandSender sender, Command command, String alias, String[] args)
    {
        if (sender instanceof ConsoleCommandSender)
            return null;

        ArrayList<String> subCommands = new ArrayList<>();
        ArrayList<String> tabCompletions = new ArrayList<>();

        if (args.length > 1)
            return null;

        for (SubCommand subCommand : getSubCommands())
        {
            if (sender.hasPermission(subCommand.getPermission()))
                subCommands.add(subCommand.getName());
        }
        subCommands.add("help");

        StringUtil.copyPartialMatches(args[0], subCommands, tabCompletions);

        Collections.sort(tabCompletions);

        return tabCompletions;
    }
}
