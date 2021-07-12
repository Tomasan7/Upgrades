package cz.tomasan7.upgrades.commands.subCommands;

import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.commands.SubCommand;
import cz.tomasan7.upgrades.menus.MainMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Reload extends SubCommand {

    @Override
    public String getName ()
    {
        return "reload";
    }

    @Override
    public String getDescription ()
    {
        return "Reloads plugin's configuration file.";
    }

    @Override
    public String getSyntax ()
    {
        return "/voidtp reload";
    }

    @Override
    public String getPermission ()
    {
        return "upgrades.reload";
    }

    @Override
    public void perform (CommandSender sender, String[] args)
    {
        Plugin plugin = Main.getPlugin(Main.class);

        if (new File(plugin.getDataFolder(), "config.yml").exists())
            plugin.reloadConfig();
        else
            plugin.saveDefaultConfig();

        plugin.reloadConfig();
        MainMenu.load();

        sender.sendMessage("§2Configuration reloaded.");
    }
}
