package io.paradaux.friendlybot.bot.commands.test;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.data.database.models.FUser;

import java.util.Date;

@Command(name="createuser", permission = "commands.createuser", description = "wefwe")
public class CreateUser extends DiscordCommand {

    @Override
    public void execute(FGuild guild, CommandBody body) {
        FUser user = new FUser();

        user.setId(0);
        user.setUserId("wefwef");
        user.setCustomColorRole("wefwef");
        user.setGuild(guild);
        user.setLastChangedColor(new Date());


        guild.getUsers().add(user);
        guild.save();
    }
}
