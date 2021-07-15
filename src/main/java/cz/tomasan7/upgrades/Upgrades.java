package cz.tomasan7.upgrades;

import cz.tomasan7.upgrades.commands.UpgradesCmd;
import cz.tomasan7.upgrades.events.MenuHandler;
import cz.tomasan7.upgrades.menus.MainMenu;
import cz.tomasan7.upgrades.other.Config;
import cz.tomasan7.upgrades.other.ConfigKeys;
import cz.tomasan7.upgrades.other.Defaults;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Upgrades
{
	private static Main main;

	private static Economy economy;
	private static LuckPerms luckPerms;

	/**
	 * Setups everything, when the plugin enables.
	 */
	public static void onEnable ()
	{
		main = Main.getInstance();

		registerEvents();
		registerCommands();

		setupEconomy();
		setupLuckPerms();

		main.saveDefaultConfig();
		main.getConfig().options().copyDefaults(true);
		Defaults.load(Config.getMenuItemDefaults());
	}

	public static void reload ()
	{
		main.saveDefaultConfig();
		main.reloadConfig();
		Defaults.load(Config.getMenuItemDefaults());
	}

	/**
	 * Register all events.
	 */
	private static void registerEvents ()
	{
		main.getServer().getPluginManager().registerEvents(new MenuHandler(), main);
	}

	/**
	 * Register all commands.
	 */
	private static void registerCommands ()
	{
		new UpgradesCmd().registerMainCommand(Main.getInstance(), "upgrades");
	}

	/**
	 * Initialize Vault economy.
	 *
	 * @return boolean Whether the setup was successfully made.
	 */
	private static boolean setupEconomy ()
	{
		RegisteredServiceProvider<Economy> rsp = main.getServer().getServicesManager().getRegistration(Economy.class);

		if (rsp == null)
			return false;

		economy = rsp.getProvider();

		return true;
	}

	/**
	 * Initialize LuckPerms.
	 * @return boolean Whether the setup was successfully made.
	 */
	private static boolean setupLuckPerms ()
	{
		try
		{
			luckPerms = LuckPermsProvider.get();
		}
		catch (IllegalStateException exception)
		{
			return false;
		}

		return true;
	}

	public static Economy getEconomy ()
	{
		return economy;
	}

	public static LuckPerms getLuckPerms ()
	{
		return luckPerms;
	}
}
