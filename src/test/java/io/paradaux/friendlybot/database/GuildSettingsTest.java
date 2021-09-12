package io.paradaux.friendlybot.database;

import io.ebean.DB;
import io.paradaux.friendlybot.core.data.database.models.GuildSettings;
import org.junit.jupiter.api.Test;

public class GuildSettingsTest {

    @Test
    public void insertFindDelete() {
        GuildSettings settings = new GuildSettings();
        settings.setGuildId("1");
        settings.setGuildName("This is a test");

        // insert the customer in the DB
        DB.save(settings);

        // Find by Id
        GuildSettings foundHello = DB.find(GuildSettings.class, "1");
        System.out.println(foundHello);

        // delete the customer
        DB.delete(settings);
    }
}
