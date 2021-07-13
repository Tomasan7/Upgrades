package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.other.Constants;
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
import org.jetbrains.annotations.NotNull;

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
	private final Player player;

	private final ConfigurationSection config;
	private final Inventory inventory;

	public SubMenu (String name, ConfigurationSection config, Player player)
	{
		this.name = name;
		this.player = player;
		this.config = config;
		title = Utils.formatText(config.getString("title"));
		rows = config.getInt("rows");
		elements = new HashSet<>();
		inventory = Bukkit.createInventory(this, rows * Constants.ROW_SIZE, title);

		ConfigurationSection itemsSection = config.getConfigurationSection("items");

		for (String itemKey : itemsSection.getKeys(false))
		{
			SubMenuElement element = new SubMenuElement(itemsSection.getConfigurationSection(itemKey), player);
			elements.add(element);
			inventory.setItem(element.getSlot(), element.getItemStack());
		}
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

	public Set<SubMenuElement> getElements ()
	{
		return new HashSet<>(elements);
	}

	public static SubMenu newSubMenu (String name, Player player)
	{
		Plugin plugin = Main.getInstance();
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
				InputStream link = (Main.getInstance().getClass().getResourceAsStream("/defaultSubMenu.yml"));
				Files.copy(link, file.getAbsoluteFile().toPath());
				file.createNewFile();
			}
			catch (IOException ignored) {}
		}

		fileConfiguration = YamlConfiguration.loadConfiguration(file);

		return new SubMenu(name, fileConfiguration, player);
	}

	public String getName ()
	{
		return name;
	}

	@NotNull
	@Override
	public Inventory getInventory ()
	{
		return inventory;
	}
}
