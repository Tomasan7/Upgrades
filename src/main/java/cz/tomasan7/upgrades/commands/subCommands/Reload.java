package cz.tomasan7.upgrades.commands.subCommands;

import cz.tomasan7.CommandManager.SubCommand;
import cz.tomasan7.upgrades.Upgrades;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Reload implements SubCommand
{
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
		return "/upgrades reload";
	}

	@Override
	public String getPermission ()
	{
		return "upgrades.reload";
	}

	@Override
	public List<String> getTabCompletions (int i, String[] strings)
	{
		return null;
	}

	@Override
	public void perform (CommandSender sender, String[] args)
	{
		Upgrades.reload();

		sender.sendMessage("§2Configuration reloaded.");
	}
}
