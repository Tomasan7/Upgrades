package cz.tomasan7.upgrades.commands;

import cz.tomasan7.CommandManager.MainCommand;
import cz.tomasan7.CommandManager.SubCommand;
import cz.tomasan7.CommandManager.argumentMatchers.ContainingStringArgumentMatcher;
import cz.tomasan7.upgrades.commands.subCommands.Open;
import cz.tomasan7.upgrades.commands.subCommands.Reload;
import cz.tomasan7.upgrades.other.Messages;

public class UpgradesCmd extends MainCommand
{
    public UpgradesCmd ()
    {
        super(Messages.getNoPermission(), new ContainingStringArgumentMatcher());
    }

    @Override
    protected void registerSubCommands ()
    {
        subCommands.add(new Open());
        subCommands.add(new Reload());
    }

    @Override
    public SubCommand getHelpSubCommand ()
    {
        return subCommands.stream().filter(sc -> sc.getName().equals("open")).findAny().orElse(null);
    }
}
