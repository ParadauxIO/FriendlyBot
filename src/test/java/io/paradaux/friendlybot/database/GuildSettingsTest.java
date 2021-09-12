package io.paradaux.friendlybot.database;

import io.ebean.DB;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.data.database.models.GuildSettings;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

    @Test
    public void fGuildTest() {
        FGuild guild = new FGuild();
        guild.setGuildId("778219443737329684");
        guild.setCommandPrefix(";");

        guild.setVerificationRoleId("781365795237527602");
        guild.setVerificationInputId("886628377564430387");

        guild.setAuditLogId("852334545750851624");
        guild.setModAuditLogId("852334546627723294");

        guild.setModMailInId("852334544316923905");
        guild.setModMailOutId("852334545104928828");

        guild.setMessageLogId("852334547105218591");

        guild.setModerators(new ArrayList<>());
        guild.setAdministrators(new ArrayList<>());


        guild.setLastIncidentId(0);
        guild.setLastTicketId(0);

        DB.save(guild);


    }
}
