package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SubMenu {

    public String name;
    private FileConfiguration config;

    public SubMenu (String name)
    {
        this.name = name;
        config = getConfig();
    }

    public Inventory getInventory(Player player)
    {
        String title = Utils.formatText(config.getString("title"));
        int size = config.getInt("size");

        Inventory inventory = Bukkit.createInventory(player, size, title);

        for (SubMenuElement element : getElements(player))
        {
            inventory.setItem(element.slot, element.itemStack);
        }

        return inventory;
    }

    public List<SubMenuElement> getElements(Player player)
    {
        FileConfiguration config = getConfig();
        ArrayList<SubMenuElement> elements = new ArrayList<>();

        for (String key : config.getConfigurationSection("Items").getKeys(false))
        {
            String path = "Items." + key;
            SubMenuElement element = new SubMenuElement(config, path, player);

            elements.add(element);
        }

        return elements;
    }

    public FileConfiguration getConfig()
    {
        Plugin plugin = Main.getPlugin(Main.class);
        File file;
        FileConfiguration fileConfiguration;

        File subFolder = new File(plugin.getDataFolder().getPath() + System.getProperty("file.separator") + "SubMenus");
        if (!subFolder.exists())
            subFolder.mkdir();

        file = new File(plugin.getDataFolder().getPath() + "/SubMenus", name + ".yml");

        if (!file.exists())
        {
            try
            {
                file = new File(plugin.getDataFolder().getPath() + "/SubMenus", name + ".yml");
                InputStream link = (getClass().getResourceAsStream("/DefaultSubMenu.yml"));
                Files.copy(link, file.getAbsoluteFile().toPath());
                file.createNewFile();
            } catch (IOException e) {}
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        return fileConfiguration;
    }
}
