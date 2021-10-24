package moe.kayla.decisive.integration;

import com.mamiyaotaru.voxelmap.interfaces.AbstractVoxelMap;
import com.mamiyaotaru.voxelmap.interfaces.IWaypointManager;
import com.mamiyaotaru.voxelmap.util.Waypoint;
import net.fabricmc.loader.api.FabricLoader;
import net.theprism.ramiel.api.client.waynet.IWaypoint;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Some code & methods are taken from github.com/Gjum/Synapse
 *
 * Helps make fucking with VoxelMap Waypoints easier.
 */
public class VoxelMapHelper {
    @Nullable
    public static IWaypointManager getWaypointManager() {
        if (!isVoxelMapActive()) return null;
        final AbstractVoxelMap instance = AbstractVoxelMap.getInstance();
        return instance == null ? null : instance.getWaypointManager();
    }

    public static void createWaypoint(DecisiveWaypoint waypoint) {
        if (getWaypointManager() == null) return;
        waypoint.vmWaypoint = new Waypoint(waypoint.getName(),
                waypoint.getXI(), waypoint.getYI(), waypoint.getZI(),
                true,
                waypoint.getColor().r, waypoint.getColor().g, waypoint.getColor().b,
                VmImage.person.toString(), waypoint.getWorld(), new TreeSet<>());
        waypoint.vmWaypoint.dimensions.add(waypoint.getDimension());
        getWaypointManager().addWaypoint(waypoint.vmWaypoint);
    }

    public static void updateWaypoint(DecisiveWaypoint waypoint) {
        if (getWaypointManager() == null) return;
        if (waypoint.vmWaypoint == null) createWaypoint(waypoint);
        if (waypoint.vmWaypoint == null) return;
        waypoint.vmWaypoint.name = waypoint.getName();
        waypoint.vmWaypoint.x = waypoint.getXI();
        waypoint.vmWaypoint.y = waypoint.getYI();
        waypoint.vmWaypoint.z = waypoint.getZI();
        waypoint.vmWaypoint.red = waypoint.getColor().r;
        waypoint.vmWaypoint.green = waypoint.getColor().g;
        waypoint.vmWaypoint.blue = waypoint.getColor().b;
        waypoint.vmWaypoint.enabled = true;
        waypoint.vmWaypoint.imageSuffix = VmImage.person.toString();

        // add if it got deleted or it was somehow not already added
        // iterate reverse to avoid checking old waypoints
        final ArrayList<Waypoint> renderedWaypoints = getWaypointManager().getWaypoints();
        for (int i = renderedWaypoints.size() - 1; i >= 0; i--) {
            if (renderedWaypoints.get(i) == waypoint.vmWaypoint) {
                return;
            }
        }
        getWaypointManager().addWaypoint(waypoint.vmWaypoint);
    }

    public static void deleteWaypoint(DecisiveWaypoint waypoint) {
        if (getWaypointManager() == null) return;
        if (waypoint.vmWaypoint == null) return;
        getWaypointManager().deleteWaypoint(waypoint.vmWaypoint);
        waypoint.vmWaypoint = null;
    }

    public static void deleteWaypoint(Waypoint waypoint) {
        if (getWaypointManager() == null) return;
        getWaypointManager().deleteWaypoint(waypoint);
    }

    /**
     * Set any arg to null to match any corresponding value.
     */
    public static Collection<Waypoint> getWaypointsByNameImgColor(@Nullable Pattern namePattern, @Nullable VmImage image, @Nullable FloatColor color) {
        if (getWaypointManager() == null) return Collections.emptyList();
        return getWaypointManager().getWaypoints().stream().filter(p ->
                (image == null || image.toString().equals(p.imageSuffix)
                ) && (color == null || (color.r == p.red && color.g == p.green && color.b == p.blue)
                ) && (namePattern == null || namePattern.matcher(p.name).matches())
        ).collect(Collectors.toList());
    }

    public static boolean isVoxelMapActive() {
        try {
            Class.forName("com.mamiyaotaru.voxelmap.fabricmod.FabricModVoxelMap");
            return FabricLoader.getInstance().isModLoaded("voxelmap");
        } catch (NoClassDefFoundError | ClassNotFoundException ignored) {
        }
        return false;
    }
}
