package cz.tomasan7.upgrades.menus.menuElements;

import cz.tomasan7.upgrades.Upgrades;
import cz.tomasan7.upgrades.menus.Menu;
import cz.tomasan7.upgrades.menus.SubMenu;
import cz.tomasan7.upgrades.menus.menuElements.MenuElement;
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
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SubMenuElement implements MenuElement
{
	private final MenuElementType TYPE = MenuElementType.NORMAL;

	private final SubMenu owningMenu;
	private final MenuItem menuItem;
	private final Player player;

	private final Set<PermissionNode> permissions;
	private final Map<String, String> mustHavePerms;
	private final double price;

	private final ConfigurationSection config;

	public SubMenuElement (SubMenu owningMenu, ConfigurationSection config, Player player)
	{
		this.owningMenu = owningMenu;
		this.config = config;
		this.player = player;

		permissions = new HashSet<>();
		ConfigurationSection permsSection = config.getConfigurationSection(ConfigKeys.SubMenu.SubMenuElement.PERMISSIONS);
		for (String permKey : permsSection.getKeys(false))
			permissions.add(createPermissionNode(permsSection.getConfigurationSection(permKey)));

		mustHavePerms = setupMustHavePerms();
		price = config.getDouble(ConfigKeys.SubMenu.SubMenuElement.PRICE, Defaults.SubMenuElement.getPrice());
		this.menuItem = parseMenuItem();
	}

	private MenuItem parseMenuItem ()
	{
		ItemStack itemStack = new MenuItem(config, player).getItemStack();
		ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.setDisplayName(itemMeta.getDisplayName().replaceAll("%price%", String.valueOf(price)));
		itemMeta.setLore(itemMeta.getLore().stream().map(l -> l.replaceAll("%price%", String.valueOf(price))).toList());

		boolean hasAllPerms = true;

		for (PermissionNode permissionNode : permissions)
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
				itemMeta.setDisplayName(Config.getHaveColor() + ChatColor.stripColor(itemMeta.getDisplayName()));
		}
		else
		{
			if (Config.getColorOnHave())
				itemMeta.setDisplayName(Config.getNotHaveColor() + ChatColor.stripColor(itemMeta.getDisplayName()));
		}

		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemStack.setItemMeta(itemMeta);

		return new MenuItem(itemStack, config.getInt(ConfigKeys.Menu.MenuElement.MenuItem.SLOT, Defaults.MenuItem.getSlot()));
	}

	private static PermissionNode createPermissionNode (ConfigurationSection permSection)
	{
		String permission = permSection.getString("perm");

		Map<String, ?> contexts = permSection.getValues(false);
		contexts.remove("perm");

		return PermissionManager.createPermissionNode(permission, PermissionManager.contextsFromConfig(contexts), true);
	}

	private Map<String, String> setupMustHavePerms ()
	{
		Map<String, String> mustHavePerms = new HashMap<>();

		if (!config.contains(ConfigKeys.SubMenu.SubMenuElement.MUST_HAVE_PERMS))
			return mustHavePerms;

		ConfigurationSection mustHavePermsSection = config.getConfigurationSection(ConfigKeys.SubMenu.SubMenuElement.MUST_HAVE_PERMS);

		for (String mustHavePerm : mustHavePermsSection.getKeys(false))
			mustHavePerms.put(Utils.formatText(mustHavePerm), mustHavePermsSection.getString(mustHavePerm));

		if (mustHavePerms.isEmpty())
			mustHavePerms = Defaults.SubMenuElement.getMustHavePerms();

		return mustHavePerms;
	}

	@Override
	public void onClick (InventoryClickEvent event)
	{
		Economy economy = Upgrades.getEconomy();
		Player player = (Player) event.getWhoClicked();

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
			player.openInventory(owningMenu.getOwningMainMenu().newSubMenu(owningMenu.getName()).getInventory());
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
	@NotNull
	public MenuElementType getType ()
	{
		return TYPE;
	}

	@Override
	@NotNull
	public Menu getOwningMenu ()
	{
		return owningMenu;
	}

	@Override
	@NotNull
	public MenuItem getMenuItem ()
	{
		return menuItem;
	}

	@Nullable
	public Player getPlayer ()
	{
		return player;
	}

	@NotNull
	public Set<PermissionNode> getPermissions ()
	{
		return new HashSet<>(permissions);
	}

	@NotNull
	public Map<String, String> getMustHavePerms ()
	{
		return new HashMap<>(mustHavePerms);
	}

	public double getPrice ()
	{
		return price;
	}
}