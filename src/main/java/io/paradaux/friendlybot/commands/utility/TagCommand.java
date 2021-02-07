/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.TagCommand :  06/02/2021, 17:26
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

package io.paradaux.friendlybot.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.TagEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

public class TagCommand extends BaseCommand {

    private final MongoManager mongo;

    public TagCommand(ConfigurationEntry config, Logger logger, MongoManager mongo) {
        super(config, logger);
        this.mongo = mongo;
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String[] args = getArgs(event.getArgs());

        if (args.length < 3) {
            respondSyntaxError(message, ";tag <create/delete/view> <id> [content]>");
            return;
        }

        EmbedBuilder entryNotFound = new EmbedBuilder()
                .setColor(0xeb5132);
//                .setTitle("Tag not found")


        switch (args[0]) {
            case "create": {
                TagEntry entry = mongo.getTag(args[1]);

//                if (entry == n)


                break;
            }

            case "delete": {

                break;
            }

            case "view": {

                break;
            }
        }

    }
}
