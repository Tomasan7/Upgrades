package cz.tomasan7.upgrades.menus.menuElements;

import cz.tomasan7.upgrades.other.ConfigKeys;
import cz.tomasan7.upgrades.other.Constants;
import cz.tomasan7.upgrades.other.Defaults;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

public class MenuItem
{
	private final Material material;
	private final String displayName;
	private final List<String> lore;
	private final int amount;
	private final int slot;

	private final ItemStack itemStack;

	public MenuItem (Material material, String displayName, List<String> lore, int amount, int slot, Player player)
	{
		this.material = material != null ? material : Defaults.MenuItem.getMaterial();
		this.displayName = Utils.formatText(displayName != null ? displayName : Defaults.MenuItem.getDisplayName());
		this.lore = lore != null ? lore.stream().map(l -> Utils.formatText(l, player)).toList() : Defaults.MenuItem.getLore().stream().map(l -> Utils.formatText(l, player)).toList();
		this.amount = amount;
		this.slot = slot;
		this.itemStack = createItemStack();
	}

	public MenuItem (Material material, String displayName, List<String> lore, int amount, int slot)
	{
		this(material, displayName, lore, amount, slot, null);
	}

	public MenuItem (ConfigurationSection config, Player player)
	{
		this.material = Material.matchMaterial(config.getString(ConfigKeys.Menu.MenuElement.MenuItem.MATERIAL, Defaults.MenuItem.getMaterial().toString()));
		this.displayName = Utils.formatText(config.getString(ConfigKeys.Menu.MenuElement.MenuItem.DISPLAY_NAME, Defaults.MenuItem.getDisplayName()), player);
		this.lore = ((List<String>) config.getList(ConfigKeys.Menu.MenuElement.MenuItem.LORE, Defaults.MenuItem.getLore())).stream().map(l -> Utils.formatText(l, player)).toList();
		this.amount = config.getInt(ConfigKeys.Menu.MenuElement.MenuItem.AMOUNT, Defaults.MenuItem.getAmount());
		this.slot = config.getInt(ConfigKeys.Menu.MenuElement.MenuItem.SLOT, Defaults.MenuItem.getSlot());
		this.itemStack = createItemStack();
	}

	public MenuItem (ConfigurationSection config)
	{
		this(config, null);
	}

	public MenuItem (ItemStack itemStack, int slot)
	{
		this.material = itemStack.getType();
		this.displayName = itemStack.getItemMeta().hasDisplayName() ? itemStack.getItemMeta().getDisplayName() : "";
		this.lore = itemStack.getItemMeta().getLore();
		this.amount = itemStack.getAmount();
		this.slot = slot;
		this.itemStack = itemStack;
	}

	private ItemStack createItemStack ()
	{
		ItemStack itemStack = new ItemStack(material, amount);

		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemMeta.setLore(lore);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public ItemStack getItemStack ()
	{
		return itemStack.clone();
	}

	public Material getMaterial ()
	{
		return material;
	}

	public String getDisplayName ()
	{
		return displayName;
	}

	public List<String> getLore ()
	{
		return new ArrayList<>(lore);
	}

	public int getAmount ()
	{
		return amount;
	}

	public int getSlot ()
	{
		return slot;
	}
}
