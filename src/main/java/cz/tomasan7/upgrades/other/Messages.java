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
        return Utils.formatText(Main.getInstance().getConfig().getString("messages.plugin-prefix"));
    }

    public static String getNoPermission ()
    {
        return Utils.formatText(Main.getInstance().getConfig().getString("messages.no-permission"));
    }

    public static String getSuccessfulBuy()
    {
        return Utils.formatText(Main.getInstance().getConfig().getString("messages.successful-buy"));
    }

    public static String getAlreadyHave()
    {
        return Utils.formatText(Main.getInstance().getConfig().getString("messages.already-have"));
    }

    public static String getNoMoney()
    {
        return Utils.formatText(Main.getInstance().getConfig().getString("messages.no-money"));
    }

    public static String getDontHaveMustHavePerm()
    {
        return Utils.formatText(Main.getInstance().getConfig().getString("messages.dont-have-must-have-perm"));
    }
}
