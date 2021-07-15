package cz.tomasan7.upgrades.other;

import cz.tomasan7.upgrades.Main;
import net.luckperms.api.context.ContextSet;
import org.bukkit.configuration.ConfigurationSection;

public class Config
{
    public static ContextSet getGlobalContexts ()
    {
        return PermissionManager.contextsFromConfig(Main.getInstance().getConfig().getConfigurationSection(ConfigKeys.GLOBAL_CONTEXTS).getValues(false));
    }

    public static ConfigurationSection getMenuItemDefaults ()
	{
		return Main.getInstance().getConfig().getConfigurationSection(ConfigKeys.DEFAULTS);
	}

	public static Boolean getEnchantOnHave ()
	{
		return Main.getInstance().getConfig().getBoolean(ConfigKeys.ENCHANT_ON_HAVE);
	}

	public static Boolean getColorOnHave ()
	{
		return Main.getInstance().getConfig().getBoolean(ConfigKeys.COLOR_ON_HAVE);
	}

	public static String getHaveColor ()
	{
		return Utils.formatText(Main.getInstance().getConfig().getString(ConfigKeys.HAVE_COLOR));
	}

	public static String getNotHaveColor ()
	{
		return Utils.formatText(Main.getInstance().getConfig().getString(ConfigKeys.NOT_HAVE_COLOR));
	}

	public static ConfigurationSection getMainMenu ()
	{
		return Main.getInstance().getConfig().getConfigurationSection(ConfigKeys.MAIN_MENU);
	}
}
