package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.Constants;
import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public class SubMenu implements InventoryHolder
{
	private final String name;

	private final String title;
	private final int rows;
	private final Set<SubMenuElement> elements;

	private final ConfigurationSection config;

	public SubMenu (String name, ConfigurationSection config)
	{
		this.name = name;
		this.config = config;
		title = Utils.formatText(config.getString("title"));
		rows = config.getInt("rows");
		elements = new HashSet<>();

		ConfigurationSection itemsSection = config.getConfigurationSection("items");

		for (String itemKey : itemsSection.getKeys(false))
			elements.add(new SubMenuElement(itemsSection.getConfigurationSection(itemKey), player));
	}

	/*public List<SubMenuElement> getElements (Player player)
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
	}*/

	public FileConfiguration getConfig ()
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
				InputStream link = (getClass().getResourceAsStream("/defaultSubMenu.yml"));
				Files.copy(link, file.getAbsoluteFile().toPath());
				file.createNewFile();
			}
			catch (IOException e)
			{
			}
		}

		fileConfiguration = YamlConfiguration.loadConfiguration(file);
		return fileConfiguration;
	}

	public Inventory getInventory (Player player)
	{
		Inventory inventory = Bukkit.createInventory(this, rows * Constants.ROW_SIZE, title);

		ConfigurationSection itemsSection = config.getConfigurationSection("items");

		for (String itemKey : itemsSection.getKeys(false))
		{
			SubMenuElement element = new SubMenuElement(itemsSection.getConfigurationSection(itemKey), player);
			inventory.setItem(element.getSlot(), element.getItemStack());
		}

		return inventory;
	}

	@Nullable
	@Override
	public Inventory getInventory ()
	{
		return null;
	}
}
