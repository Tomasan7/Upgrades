package cz.tomasan7.upgrades.other;

import cz.tomasan7.upgrades.Main;
import net.luckperms.api.context.ContextSet;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class Config
{
    public static ContextSet getGlobalContexts ()
    {
        return PermissionManager.contextsFromConfig(Main.getInstance().getConfig().getConfigurationSection("global-contexts").getValues(false));
    }

	public static Boolean getEnchantOnHave ()
	{
		return Main.getInstance().getConfig().getBoolean("enchant-on-have");
	}

	public static Boolean getColorOnHave ()
	{
		return Main.getInstance().getConfig().getBoolean("color-on-have");
	}

	public static String getHaveColor ()
	{
		return Utils.formatText(Main.getInstance().getConfig().getString("have-color"));
	}

	public static String getNotHaveColor ()
	{
		return Utils.formatText(Main.getInstance().getConfig().getString("not-have-color"));
	}
}
