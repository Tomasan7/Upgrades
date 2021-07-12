package cz.tomasan7.upgrades.other;

import cz.tomasan7.upgrades.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Messages {

    public static void Message (Player player, String message)
    {
        player.sendMessage(getPrefix() + message);
    }

    public static String getPrefix()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();

        return Utils.formatText(config.getString("Messages.plugin-prefix"));
    }

    public static String getSuccessfulBuy()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();

        return Utils.formatText(config.getString("Messages.successful-buy"));
    }

    public static String getAlreadyHave()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();

        return Utils.formatText(config.getString("Messages.already-have"));
    }

    public static String getNoMoney()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();

        return Utils.formatText(config.getString("Messages.no-money"));
    }

    public static String getDontHaveMustHavePerm()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();

        return Utils.formatText(config.getString("Messages.dont-have-must-have-perm"));
    }
}
