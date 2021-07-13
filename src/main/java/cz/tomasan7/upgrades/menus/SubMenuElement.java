package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.other.Config;
import cz.tomasan7.upgrades.other.PermissionManager;
import cz.tomasan7.upgrades.other.Utils;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SubMenuElement
{
	private final ItemStack itemStack;
	private final Set<PermissionNode> permissions;
	private final Map<String, String> mustHavePerms;
	private final int slot;
	private final double price;
	private final ConfigurationSection config;

	private final Player player;

	public SubMenuElement (ConfigurationSection config, Player player)
	{
		this.config = config;
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

	/*private Set<PermissionNode> getPermissions ()
	{
		PermissionNode[] permissions;
		String[] permissionElements = Utils.fromSetToArray(config.getConfigurationSection(path + ".permissions").getKeys(false));

		permissions = new PermissionNode[permissionElements.length];

		for (int i = 0; i < permissionElements.length; i++)
		{
			String permission = config.getString(path + ".permissions." + permissionElements[i] + ".perm");
			String[] worlds = new String[0];

			if (config.getConfigurationSection(path + ".permissions." + permissionElements[i]).contains("world"))
			{
				worlds = new String[config.getStringList(path + ".permissions." + permissionElements[i] + ".world").size()];
				config.getStringList(path + ".permissions." + permissionElements[i] + ".world").toArray(worlds);
			}

			String[] servers = new String[0];

			if (config.getConfigurationSection(path + ".permissions." + permissionElements[i]).contains("server"))
			{
				servers = new String[config.getStringList(path + ".permissions." + permissionElements[i] + ".server").size()];
				config.getStringList(path + ".permissions." + permissionElements[i] + ".server").toArray(servers);
			}

			permissions[i] = PermissionManager.createPermissionNode(permission, worlds, servers);
		}

		return permissions;
	}*/

	private static PermissionNode createPermissionNode (ConfigurationSection permissionSection)
	{
		String permission = permissionSection.getString("perm");
		//List<String> servers = permissionSection.getStringList("servers");
		Set<String> worlds = new HashSet<>(permissionSection.getStringList("worlds"));

		return PermissionManager.createPermissionNode(permission, worlds, null);
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

	public ItemStack getItemStack ()
	{
		return itemStack;
	}

	public Set<PermissionNode> getPermissions ()
	{
		return permissions;
	}

	public Map<String, String> getMustHavePerms ()
	{
		return mustHavePerms;
	}

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