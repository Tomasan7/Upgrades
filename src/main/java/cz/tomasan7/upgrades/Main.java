package cz.tomasan7.upgrades;

import cz.tomasan7.upgrades.commands.SubCommandsManager;
import cz.tomasan7.upgrades.events.MenuHandler;
import cz.tomasan7.upgrades.menus.MainMenu;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Main extends JavaPlugin {

    private static Economy economy = null;
    private static LuckPerms luckPerms = null;

    @Override
    public void onEnable ()
    {
        registerEvents();

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        if (!setupEconomy())
        {
            getServer().getLogger().severe("&cVault not installed, please install, disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!setupLuckPerms())
        {
            getServer().getLogger().severe("&cLuckPerms not installed, please install, disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        MainMenu.Load();    // Load MainMenu from the config.
    }

    @Override
    public void onDisable ()
    {
        // Plugin shutdown logic
    }

    /**
     * Register all events.
     */
    private void registerEvents ()
    {
        getServer().getPluginManager().registerEvents(new MenuHandler(), this);
    }

    /**
     * Register all commands.
     */
    private void registerCommands ()
    {
        PluginCommand upgradesCmd = getCommand("upgrades");

        upgradesCmd.setExecutor(new SubCommandsManager());
        upgradesCmd.setTabCompleter(new SubCommandsManager());
    }

    /**
     * All Vault things.
     *
     * @return boolean If setup was successfully made.
     */
    private boolean setupEconomy ()
    {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp.getProvider();

        return economy != null;
    }

    private boolean setupLuckPerms ()
    {
        RegisteredServiceProvider<LuckPerms> provider = getServer().getServicesManager().getRegistration(LuckPerms.class);
        luckPerms = provider.getProvider();

        return luckPerms != null;
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
