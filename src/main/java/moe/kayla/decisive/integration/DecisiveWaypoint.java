package moe.kayla.decisive.integration;

import com.mamiyaotaru.voxelmap.util.Waypoint;
import moe.kayla.decisive.Decisive;
import moe.kayla.decisive.client.model.PlayerManager;
import net.theprism.ramiel.api.client.waynet.IWaypoint;
import net.theprism.ramiel.api.common.CachedPlayer;

public class DecisiveWaypoint implements IWaypoint {
    private int x;
    private int y;
    private int z;
    private String world;

    private CachedPlayer assocPlayer;
    public Waypoint vmWaypoint;

    public DecisiveWaypoint(int x, int y, int z, String world,String username) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.assocPlayer = Decisive.playerManager.getPlayer(username);
        this.vmWaypoint = null;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    public int getXI() {
        return x;
    }

    public int getYI() {
        return y;
    }

    public int getZI() {
        return z;
    }

    public String getWorld() {
        return world;
    }

    public int getDimension() {
        if ("world".equals(world)) return 0; // hot path
        if ("the_end".equals(world.toLowerCase())) return 1;
        if ("nether".equals(world.toLowerCase())) return -1;
        return 0; // custom named worlds (shards) show as overworld
    }

    @Override
    public String getName() {
        return assocPlayer.getUsername();
    }

    @Override
    public boolean isLocal() {
        return true;
    }

    public FloatColor getColor() {
        return Decisive.playerManager.getColorForStanding(assocPlayer.getStanding());
    }

    public Waypoint getVmWaypoint() {
        return vmWaypoint;
    }

    @Override
    public String toString() {
        return "DecisiveWaypoint{"
                + "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", remote=false" +
                ", username='" + assocPlayer.getUsername() + "'" +
                "}";
    }
}
