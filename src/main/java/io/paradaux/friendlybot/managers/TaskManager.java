package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import net.dv8tion.jda.api.entities.Guild;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TaskManager {

    private static TaskManager instance;

    private final ScheduledExecutorService executor;

    public TaskManager() {
        executor = Executors.newScheduledThreadPool(3);
        scheduleStatsJob();
        instance = this;
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }
        return instance;
    }

    public void scheduleStatsJob() {
        executor.scheduleAtFixedRate(() -> {
            Logger.getAnonymousLogger().info("Beginning task");
            int guildCount = DiscordBotManager.getInstance().getClient().getGuilds().size();
            int userCount = 0;

            for (Guild guild : DiscordBotManager.getInstance().getClient().getGuilds()) {
                userCount += guild.getMemberCount();
            }

            MongoManager.getInstance().updateStats(userCount, guildCount);

        }, 1, 15, TimeUnit.MILLISECONDS);


    }



}
