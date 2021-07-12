package cz.tomasan7.upgrades.other;

import cz.tomasan7.upgrades.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.context.DefaultContextKeys;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

public class PermissionManager {

    public static boolean checkPermission (Player player, PermissionNode permissionNode, boolean contextual)
    {
        LuckPerms luckPerms = Main.getLuckPerms();
        User user = luckPerms.getUserManager().getUser(player.getName());

        ImmutableContextSet contexts = permissionNode.getContexts();
        CachedPermissionData permissionData = user.getCachedData().getPermissionData(contextual ? QueryOptions.contextual(contexts) : QueryOptions.nonContextual());

        return permissionData.checkPermission(permissionNode.getPermission()).asBoolean();
    }

    public static void addPermission (Player player, PermissionNode permissionNode)
    {
        LuckPerms luckPerms = Main.getLuckPerms();
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        user.data().add(permissionNode);
        luckPerms.getUserManager().saveUser(user);
    }

    public static PermissionNode createPermissionNode (String permission, String[] worlds, String[] servers)
    {
        PermissionNode.Builder builder = PermissionNode.builder(permission);

        if (worlds != null)
        {
            for (String world : worlds)
                builder.withContext(DefaultContextKeys.WORLD_KEY, world);
        }

        if (servers != null)
        {
            for (String server : servers)
                builder.withContext(DefaultContextKeys.SERVER_KEY, server);
        }

        return builder.build();
    }
}
