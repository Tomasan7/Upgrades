package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.menus.menuElements.*;
import cz.tomasan7.upgrades.other.ConfigKeys;
import cz.tomasan7.upgrades.other.Constants;
import cz.tomasan7.upgrades.other.Defaults;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainMenu extends Menu
{
	public MainMenu (ConfigurationSection config, Player player)
	{
		super(config, player);
	}

	@Override
	@NotNull
	public MenuElement parseMenuElement (ConfigurationSection config)
	{
		MenuElement.MenuElementType type = MenuElement.MenuElementType.getByConfigValue(config.getString(ConfigKeys.Menu.MenuElement.TYPE, Defaults.MenuElement.getType().toString()).toUpperCase());

		return type == MenuElement.MenuElementType.BLANK ? new BlankElement(this, config, player) : new MainMenuElement(this, config, player);
	}
}
