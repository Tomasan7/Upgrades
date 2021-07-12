package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class MainMenu {

    public static ArrayList<MainMenuElement> elements;
    public static ArrayList<SubMenu> subMenus;

    public static Inventory getInventory(Player player)
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();
        String MenuTitle = Utils.formatText(config.getString("MainMenu.title"));
        int MenuSize = config.getInt("MainMenu.size");

        Inventory inventory = Bukkit.createInventory(player, MenuSize, MenuTitle);

        for (MainMenuElement element : elements)
        {
            inventory.setItem(element.slot, element.item);
        }

        return inventory;
    }

    public static void load ()
    {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();
        ArrayList<MainMenuElement> elements = new ArrayList<>();
        subMenus = new ArrayList<>();

        for (String key : config.getConfigurationSection("MainMenu.Items").getKeys(false))
        {
            String path = "MainMenu.Items." + key + ".";
            MainMenuElement element = new MainMenuElement(config, path);
            subMenus.add(new SubMenu(config.getString(path + "sub-menu")));

            elements.add(element);
        }
        MainMenu.elements = elements;
    }
}
