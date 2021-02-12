/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.managers.DiscordBotManager :  31/01/2021, 01:26
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.paradaux.friendlybot.managers;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.paradaux.friendlybot.commands.fun.*;
import io.paradaux.friendlybot.commands.staff.moderation.*;
import io.paradaux.friendlybot.commands.staff.technician.*;
import io.paradaux.friendlybot.commands.utility.*;
import io.paradaux.friendlybot.listeners.ReadyListener;
import io.paradaux.friendlybot.listeners.logging.MessageDeleteLog;
import io.paradaux.friendlybot.listeners.logging.MessageLog;
import io.paradaux.friendlybot.listeners.logging.UpdatedMessageLog;
import io.paradaux.friendlybot.listeners.logging.audit.GuildJoinLog;
import io.paradaux.friendlybot.listeners.logging.audit.GuildLeaveLog;
import io.paradaux.friendlybot.listeners.modmail.ModMailChannelListener;
import io.paradaux.friendlybot.listeners.modmail.ModMailPrivateMessageListener;
import io.paradaux.friendlybot.listeners.utility.LongMessageListener;
import io.paradaux.friendlybot.listeners.verification.VerificationCodeReceivedListener;
import io.paradaux.friendlybot.listeners.verification.VerificationEmailReceivedListener;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.slf4j.Logger;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DiscordBotManager {

    // Singleton Instance
    private static DiscordBotManager instance = null;
    private final ConfigurationEntry config;
    private final Logger logger;
    private final PermissionManager permissionManager;
    private final MongoManager mongo;
    private final JDA client;
    private final EventWaiter eventWaiter;
    private final RoleManager roles;

    public DiscordBotManager(ConfigurationEntry config, Logger logger, PermissionManager permissionManager, MongoManager mongo, RoleManager roles) {
        this.config = config;
        this.logger = logger;
        this.permissionManager = permissionManager;
        this.mongo = mongo;
        this.eventWaiter = new EventWaiter();
        this.roles = roles;

        logger.info("Initialising: BotController");
        logger.info("Attempting to login");

        try {
            client = login(config.getBotToken());
        } catch (LoginException e) {
            throw new RuntimeException("Failed to login");
        }

        logger.info("Login successful.");

        instance = this;
    }

    public static DiscordBotManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    private CommandClient createCommandClient() {
        logger.info("Initialising: CommandController");

        CommandClientBuilder builder = new CommandClientBuilder()
                .setPrefix(config.getCommandPrefix())
                .setOwnerId("150993042558418944")
                .setActivity(Activity.listening("to modmail queries.."))
                .addCommands(
                        // Fun Commands
                        new CatCommand(config, logger),
                        new DogCommand(config, logger),
                        new EightBallCommand(config, logger),
                        new InspireCommand(config, logger),
                        new LmgtfyCommand(config, logger),
                        new MemeCommand(config, logger),
                        new MonkeCommand(config, logger),
                        new MonkeyCommand(config, logger),
                        new XKCDCommand(config, logger),
                        new YodaifyCommand(config, logger),

                        // Moderation Commands
                        new AnnouncementCommand(config, logger, permissionManager, eventWaiter),
                        new BanCommand(config, logger, permissionManager),
                        new CiteCommand(config, logger, permissionManager),
                        new KickCommand(config, logger, permissionManager),
                        new LookupCommand(config, logger, permissionManager),
                        new PruneCommand(config, logger, permissionManager),
                        new PunishmentCommand(config, logger, permissionManager, mongo),
                        new RespondCommand(config, logger, permissionManager, mongo),
                        new TempBanCommand(config, logger, permissionManager, mongo),
                        new TicketCommand(config, logger, permissionManager),
                        new TimeOutCommand(config, logger, permissionManager),
                        new WarnCommand(config, logger, permissionManager),

                        // Technician Commands.
                        new DmCommand(config, logger, permissionManager),
                        new PermissionsCommand(config, logger, permissionManager),
                        new SayCommand(config, logger, permissionManager),
                        new SendEmbedCommand(config, logger, permissionManager),
                        new VerificationCommand(config, logger, permissionManager),

                        // Utility Commands
                        new CommandsCommand(config, logger),
                        new GithubCommand(config, logger),
                        new GoogleCommand(config, logger),
                        new InviteCommand(logger),
                        new MemeImagesCommand(config, logger),
                        new PingCommand(logger),
                        new ServerInfoCommand(config, logger),
                        new SetColorCommand(config, logger, roles, mongo),
                        new TagCommand(config, logger, mongo),
                        new UserInfoCommand(config, logger, permissionManager),
                        new WolframAlphaCommand(config, logger)
                );

        return builder.build();
    }

    /**
     * Creates an Instance of JDA from the provided token.
     * @param token The Discord Token taken from the configuration file.
     * @return JDA An Instance of JDA
     * @throws LoginException When logging in proved unsuccessful.
     * */
    public JDA login(String token) throws LoginException {

        CommandClient commandClient = createCommandClient();

        JDABuilder builder = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setBulkDeleteSplittingEnabled(false)
                .addEventListeners(eventWaiter, commandClient,
                        new GuildJoinLog(config, logger),
                        new GuildLeaveLog(config, logger),
                        new ModMailChannelListener(config, logger),
                        new ModMailPrivateMessageListener(logger),
                        new VerificationCodeReceivedListener(config, logger),
                        new VerificationEmailReceivedListener(config, logger),
                        new ReadyListener(logger),
                        new MessageDeleteLog(config, logger, mongo),
                        new MessageLog(config, logger, mongo),
                        new UpdatedMessageLog(config, logger, mongo),
                        new LongMessageListener(config, logger)
                );

        if (token == null) {
            throw new LoginException("The Configuration File does not contain a token.");
        }

        return builder.build();
    }

    @Nonnull
    @CheckReturnValue
    public JDA getClient() {
        return client;
    }

    @Nonnull
    @CheckReturnValue
    public Guild getGuild(String guildId) {
        Guild guild = client.getGuildById(guildId);

        if (guild == null) {
            throw new RuntimeException("Guild came back null.");
        }

        return guild;
    }

    @Nonnull
    @CheckReturnValue
    public TextChannel getChannel(String channelId) {
        TextChannel channel = client.getTextChannelById(channelId);

        if (channel == null) {
            throw new RuntimeException("Channel came back null.");
        }

        return channel;
    }

    @Nonnull
    @CheckReturnValue
    public Role getRole(String guildId, String roleId) {
        Guild guild = getGuild(guildId);
        Role role = guild.getRoleById(roleId);

        if (role == null) {
            throw new RuntimeException("Role came back null");
        }

        return role;
    }

    @Nonnull
    @CheckReturnValue
    public List<Role> getRolesByName(String guildId, String roleName) {
        return getGuild(guildId).getRolesByName(roleName, true);
    }



    @Nonnull
    @CheckReturnValue
    public User getUser(String userId) {
        User user;
        try {
             user = client.retrieveUserById(userId).submit().get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info("Concurrency issue occurred while trying to get the user object.");
            throw new RuntimeException("Concurrency issue occurred while trying to get the user object.");
        }

        return user;
    }

}
