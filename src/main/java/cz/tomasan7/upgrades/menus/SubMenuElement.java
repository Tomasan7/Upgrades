package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.other.Config;
import cz.tomasan7.upgrades.other.PermissionManager;
import cz.tomasan7.upgrades.other.Utils;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubMenuElement {

    public ItemStack itemStack;
    public PermissionNode[] permissions;
    public HashMap<String, String> mustHavePerms = new HashMap<>();
    public int slot;
    public double price;

    private FileConfiguration config;
    private String path;
    private Player player;

    public SubMenuElement (FileConfiguration config, String path, Player player)
    {
        this.config = config;
        this.path = path;
        this.player = player;

        this.permissions = getPermissions();
        this.price = getPrice();
        this.itemStack = getItemStack();
        this.mustHavePerms = getMustHavePerms();
        this.slot = getSlot();
    }

    private ItemStack getItemStack ()
    {
        ItemStack itemStack = new ItemStack(getMaterial(), getAmount());
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = getDisplayName();

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
            {
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
            }

            if (Config.getColorOnHave())
            {
                displayName = Utils.removeColors("§", displayName);
                displayName = Config.getHaveColor() + displayName;
            }
        }
        else
        {
            if (Config.getColorOnHave())
            {
                displayName = Utils.removeColors("§", displayName);
                displayName = Config.getNotHaveColor() + displayName;
            }
        }

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(getLore());

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private Material getMaterial ()
    {
        return Material.getMaterial(config.getString(path + ".material"));
    }

    private String getDisplayName ()
    {
        return Utils.formatText(config.getString(path + ".display-name"));
    }

    private List<String> getLore ()
    {
        List<String> lore = new ArrayList<>();

        for (String eachLore : config.getStringList(path + ".lore"))
        {
            lore.add(Utils.formatText(eachLore, "%price%", this.price));
        }

        return lore;
    }

    private int getAmount ()
    {
        return config.getInt(path + ".amount");
    }

    private PermissionNode[] getPermissions ()
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
    }

    private HashMap<String, String> getMustHavePerms ()
    {
        HashMap<String, String> mustHavePerms = new HashMap<>();

        if (config.getConfigurationSection(path).contains("must-have-perms"))
        {
            for (String mustHavePerm : config.getConfigurationSection(path + ".must-have-perms").getKeys(false))
            {
                mustHavePerms.put(mustHavePerm, config.getString(path + ".must-have-perms." + mustHavePerm));
            }
        }

        return mustHavePerms;
    }

    private double getPrice ()
    {
        return config.getDouble(path + ".price");
    }

    private int getSlot ()
    {
        return config.getInt(path + ".slot");
    }
}