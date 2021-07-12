package cz.tomasan7.upgrades.other;

import cz.tomasan7.upgrades.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static Boolean getEnchantOnHave()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();

        return config.getBoolean("enchant-on-have");
    }

    public static Boolean getColorOnHave()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();

        return config.getBoolean("color-on-have");
    }

    public static String getHaveColor()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();

        return Utils.formatText(config.getString("have-color"));
    }

    public static String getNotHaveColor()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();

        return Utils.formatText(config.getString("not-have-color"));
    }
}
