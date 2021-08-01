package io.paradaux.friendlybot.discord.commands.staff.technician;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.paradaux.friendlybot.managers.GuildSettingsManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.database.GuildSettingsEntry;
import io.paradaux.friendlybot.core.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;

public class SetupCommand extends PrivilegedCommand {

    private final EventWaiter waiter;
    private final GuildSettingsManager guilds;

    public SetupCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager, EventWaiter waiter) {
        super(config, logger, permissionManager);
        this.waiter = waiter;
        this.name = "setup";
        this.help = "Configures guild settings";
        this.guilds = GuildSettingsManager.getInstance();
    }

    @Override
    protected void execute(CommandEvent event) {

        Guild guild = event.getGuild();

        if (!isStaff(event.getGuild(), event.getAuthor().getId())) {
            return;
        }

        if (guilds.guildExists(guild.getId())) {
            event.reply("This guild has already been setup.");
            return;
        }

        event.reply("Beginning to configure this guild.");
        event.reply("This will create several channels which you can move around or rename, but they must remain the same channels");

        guild.createCategory("FriendlyBot Administration").queue((category) -> {
            // Make it so it isn't visible to the public
            category.createPermissionOverride(guild.getPublicRole()).setDeny(Permission.MESSAGE_READ).queue((action) -> {
                // Create the feature channels
                category.createTextChannel("modmail").queue((modmail) -> {
                    category.createTextChannel("modmail-output").queue((modmailOutput) -> {
                        category.createTextChannel("auditlog").queue((auditlog) -> {
                            category.createTextChannel("auditlog-private").queue((auditlogPrivate) -> {
                                category.createTextChannel("message-logs").queue((messageLogs) -> {
                                    GuildSettingsEntry guildEntry = guilds.createNewProfile(guild.getId())
                                            .setModmailInputChannel(modmail.getId())
                                            .setModmailOutputChannel(modmailOutput.getId())
                                            .setPublicAuditLogChannel(auditlog.getId())
                                            .setPrivateAuditLogChannel(auditlogPrivate.getId())
                                            .setMessageLogChannel(messageLogs.getId());

                                    guilds.updateProfile(guildEntry);
                                    event.reply("Setup complete. Do not delete these channels. All channels are under FriendlyBot Administration and cannot be seen by anyone except Server Administrators.");
                                });
                            });
                        });
                    });
                });
            });
        });
    }
}
