package moe.kayla.decisive.client.model;

import moe.kayla.decisive.integration.FloatColor;
import net.theprism.ramiel.api.common.CachedPlayer;
import net.theprism.ramiel.api.common.Standing;

import java.util.HashMap;

/**
 * Manages Cached players loaded from Ramiel.
 */
public class PlayerManager {

    private HashMap<String, CachedPlayer> cachedPlayers = new HashMap<>();

    /* TODO when packets are available
    public synchronized CachedPlayer getCachedPlayerInfo(final String username) {

    }
     */

    //Temporary
    public synchronized CachedPlayer getPlayer(final String username) {
        if(cachedPlayers.containsKey(username)) {
            return cachedPlayers.get(username);
        } else {
            CachedPlayer addPlayer = new CachedPlayer(username, null, Standing.NEUTRAL, false);
            cachedPlayers.put(username, addPlayer);
            return addPlayer;
        }
    }

    public FloatColor getColorForStanding(Standing standing) {
        switch (standing) {
            case SECURITY_COUNCIL:
            case GOVERNMENT:
            case CITIZEN:
                return new FloatColor(0,255,0);
            case ALLY:
                return new FloatColor(25, 140, 255);
            case FRIENDLY:
                return new FloatColor(155,0,230);
            case NEUTRAL:
                return new FloatColor(255,255,255);
            case SUS:
                return new FloatColor(255,213,0);
            case ENEMY:
                return new FloatColor(255,0,0);
            default:
                return new FloatColor(0,0,0);
        }
    }
}
