package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.Upgrades;
import cz.tomasan7.upgrades.other.*;
import net.luckperms.api.node.types.PermissionNode;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SubMenuElement implements MenuElement
{
	private final ItemStack itemStack;
	private final Set<PermissionNode> permissions;
	private final Map<String, String> mustHavePerms;
	private final int slot;
	private final double price;
	private final ConfigurationSection config;
	private final SubMenu owningMenu;

	private final Player player;

	public SubMenuElement (SubMenu owningMenu, ConfigurationSection config, Player player)
	{
		this.config = config;
		this.owningMenu = owningMenu;
		this.player = player;
		permissions = new HashSet<>();

		ConfigurationSection permsSection = config.getConfigurationSection("permissions");

		for (String permKey : permsSection.getKeys(false))
			permissions.add(createPermissionNode(permsSection.getConfigurationSection(permKey)));

		price = config.getDouble("price");
		mustHavePerms = setupMustHavePerms();
		slot = config.getInt("slot");

		itemStack = createItemStack();
	}

	private ItemStack createItemStack ()
	{
		Material material = Material.matchMaterial(config.getString("material"));
		int amount = config.getInt("amount");

		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		String displayName = Utils.formatText(config.getString("display-name"));

		boolean hasAllPerms = true;

		for (PermissionNode permissionNode : this.permissions)
		{
			if (!PermissionManager.checkPermission(player, permissionNode, true))
			{
				hasAllPerms = false;
				break;
			}
		}

		if (hasAllPerms)
		{
			if (Config.getEnchantOnHave())
				itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);

			if (Config.getColorOnHave())
			{
				displayName = ChatColor.stripColor(displayName);
				displayName = Config.getHaveColor() + displayName;
			}
		}
		else
		{
			if (Config.getColorOnHave())
			{
				displayName = ChatColor.stripColor(displayName);
				displayName = Config.getNotHaveColor() + displayName;
			}
		}

		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName(displayName);
		List<String> lore = config.getStringList("lore").stream()
				.map(s -> Utils.formatText(s, "%price%", price))
				.toList();
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	private static PermissionNode createPermissionNode (ConfigurationSection permSection)
	{
		String permission = permSection.getString("perm");

		Map<String, ?> contexts = permSection.getValues(false);
		contexts.remove("perm");

		return PermissionManager.createPermissionNode(permission, PermissionManager.contextsFromConfig(contexts), true);
	}

	private HashMap<String, String> setupMustHavePerms ()
	{
		HashMap<String, String> mustHavePerms = new HashMap<>();

		if (!config.contains("must-have-perms"))
			return mustHavePerms;

		ConfigurationSection mustHavePermsSection = config.getConfigurationSection("must-have-perms");

		for (String mustHavePerm : mustHavePermsSection.getKeys(false))
			mustHavePerms.put(Utils.formatText(mustHavePerm), mustHavePermsSection.getString(mustHavePerm));

		return mustHavePerms;
	}

	@Override
	public void onClick (InventoryClickEvent event)
	{
		Economy economy = Upgrades.getEconomy();

		Constants.CLICK_SOUND.play(player);

		if (permissions.stream().allMatch(perm -> PermissionManager.checkPermission(player, perm, true)))
		{
			Messages.Message(player, Messages.getAlreadyHave());
			player.closeInventory();
			return;
		}

		/* If player misses at least one mustHavePermission, then interrupt. */
		for (String key : mustHavePerms.keySet())
		{
			if (!PermissionManager.checkPermission(player, PermissionManager.createPermissionNode(mustHavePerms.get(key), Config.getGlobalContexts(), false), false))
			{
				Messages.Message(player, Messages.getDontHaveMustHavePerm() + Utils.formatText(key));
				player.closeInventory();
				return;
			}
		}

		EconomyResponse response = economy.withdrawPlayer(player, price);

		if (response.transactionSuccess())
		{
			permissions.forEach(perm -> PermissionManager.addPermission(player, perm));
			Constants.BUY_SUCCESS_SOUND.play(player);
			Messages.Message(player, Messages.getSuccessfulBuy());
			player.openInventory(SubMenu.newSubMenu(owningMenu.getName(), player).getInventory());
		}
		else
		{
			if (response.errorMessage.equals("Loan was not permitted"))
				Messages.Message(player, Messages.getNoMoney());
			else
				Messages.Message(player, response.errorMessage);
			player.closeInventory();
		}
	}

	@Override
	public @NotNull ItemStack getItemStack ()
	{
		return itemStack;
	}

	@Override
	@NotNull
	public Menu getOwningMenu ()
	{
		return null;
	}

	public Set<PermissionNode> getPermissions ()
	{
		return permissions;
	}

	public Map<String, String> getMustHavePerms ()
	{
		return mustHavePerms;
	}

	@Override
	public int getSlot ()
	{
		return slot;
	}

	public double getPrice ()
	{
		return price;
	}

	public ConfigurationSection getConfig ()
	{
		return config;
	}

	public Player getPlayer ()
	{
		return player;
	}
}