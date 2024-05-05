package mumeinosato.minecraftapiexample;

import com.palmergames.bukkit.towny.TownyAPI;
import io.javalin.Javalin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.palmergames.bukkit.towny.object.Town;


import java.util.UUID;

public final class Minecraft_api_example extends JavaPlugin {

    private Javalin app;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("プラグインが開始しました");

        app = Javalin.create().start(6008);

        app.get("/", ctx -> {
            ctx.result("Hello, Minecraft API Example!");
        });

        app.get("/town/{uuid}", ctx -> {
            String uuidString = ctx.pathParam("uuid");
            if (!uuidString.isEmpty()) {
                UUID playerUUID = UUID.fromString(uuidString);
                Player player = Bukkit.getPlayer(playerUUID);
                if (player != null && player.isOnline()) {
                    Town town = TownyAPI.getInstance().getTown(player);
                    if (town != null) {
                        ctx.result(town.getName());
                    } else {
                        ctx.result("No town");
                    }
                } else {
                    ctx.result("Player not online or not found");
                }
            } else {
                ctx.result("Invalid parameter");
            }
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("プラグインが停止しました");
        app.stop();
    }
}
