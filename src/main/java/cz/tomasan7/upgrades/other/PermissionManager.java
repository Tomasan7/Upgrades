package cz.tomasan7.upgrades.other;

import cz.tomasan7.upgrades.Upgrades;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class PermissionManager
{

	public static boolean checkPermission (Player player, PermissionNode permissionNode, boolean contextual)
	{
		LuckPerms luckPerms = Upgrades.getLuckPerms();
		User user = luckPerms.getUserManager().getUser(player.getName());

		ImmutableContextSet contexts = permissionNode.getContexts();
		CachedPermissionData permissionData = user.getCachedData().getPermissionData(contextual ? QueryOptions.contextual(contexts) : QueryOptions.nonContextual());

		return permissionData.checkPermission(permissionNode.getPermission()).asBoolean();
	}

	public static void addPermission (Player player, PermissionNode permissionNode)
	{
		LuckPerms luckPerms = Upgrades.getLuckPerms();
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
		user.data().add(permissionNode);
		luckPerms.getUserManager().saveUser(user);
	}

	public static PermissionNode createPermissionNode (String permission, ContextSet contexts, boolean addDefaultContexts)
	{
		PermissionNode.Builder builder = PermissionNode.builder(permission);

		if (contexts != null)
			builder.withContext(contexts);

		if (addDefaultContexts)
			builder.withContext(Config.getGlobalContexts());

		return builder.build();
	}

	public static ContextSet contextsFromConfig (Map<String, ?> map)
	{
		ImmutableContextSet.Builder builder = ImmutableContextSet.builder();

		for (Map.Entry<String, ?> entry : map.entrySet())
		{
			if (entry.getValue() instanceof String value)
				builder.add(entry.getKey(), value);

			if (entry.getValue() instanceof List<?> valueList)
				valueList.stream()
						.filter(value -> value instanceof String).forEach(value -> builder.add(entry.getKey(), (String) value));
		}

		return builder.build();
	}
}
