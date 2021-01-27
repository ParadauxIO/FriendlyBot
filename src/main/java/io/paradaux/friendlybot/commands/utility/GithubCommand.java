/*
 * Copyright (c) 2021 |  Rían Errity. GPLv3
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.friendlybot.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.objects.BaseCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;

public class GithubCommand extends BaseCommand {

    private static final String REPO_INFO_ENDPOINT = "https://api.github.com/repos/%s";
    private static final String REPO_ISSUES_ENDPOINT = "https://api.github.com/repos/%s/issues";
    private static final String CLOSED_ISSUES_ENDPOINT = "https://api.github.com/search/issues?q=repo:%s/+type:issue+state:closed";
    private static final String OPEN_ISSUES_ENDPOINT = "https://api.github.com/search/issues?q=repo:%s/+type:issue+state:open";

    public GithubCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "github";
        this.aliases = new String[]{"gh"};
        this.help = "Looks up the specified github repository.";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = getArgs(event.getArgs());
        TextChannel channel = event.getTextChannel();
        Message message = event.getMessage();

        MessageEmbed embed;

        switch (args.length) {

            case 1: {
//                embed = makeInfoEmbed(args[0]);
                break;
            }

            // TODO implement issue searching
//            case 2: {
//                embed = makeIssueEmbed(args[0], Long.parseLong(args[1]));
//                break;
//            }

            default: {
                respondSyntaxError(message, ";github <repository> [issue #]");
                return;
            }

        }

//        channel.sendMessage(embed).queue();
    }

//    public MessageEmbed makeInfoEmbed(String repository) {
//        EmbedBuilder embed = new EmbedBuilder();
//        try {
//            String repoContent = WebApiUtil.getStringWithThrows(new URL(String.format(REPO_INFO_ENDPOINT, repository)), "GET",
//                    WebSettings.WEB_TIMEOUT, WebSettings.WEB_USER_AGENT, WebSettings.getJsonWebHeaders());
//
//            Gson gson = new GsonBuilder()
//                    .registerTypeAdapter(Data.class, new CustomDeserilizer())
//                    .create();
//
//
//            JSONDeserializer<GithubRepoModel> repoModelDeserializer = new JSONDeserializer<>();
//            repoModelDeserializer.use(Instant.class, new InstantTransformer());
//            GithubRepoModel repoModel = repoModelDeserializer.deserialize(repoContent, GithubRepoModel.class);
//            List<GithubIssueModel> issueModels = getIssues(repository);
//
//            embed.setAuthor(repoModel.getName(), repoModel.getHtmlURL(), repoModel.getOwner().getAvatarURL());
//            embed.setColor(Color.YELLOW);
//            embed.setDescription(repoModel.getDescription());
//            embed.setThumbnail(repoModel.getOwner().getAvatarURL());
//
//            embed.addInlineField("\uD83C\uDF1F Stars", String.format("```%s```", repoModel.getStargazers()));
//            embed.addInlineField("\u203C Issues", String.format("```%s```", repoModel.getOpenIssues()));
//
//            StringBuilder issuenames = new StringBuilder();
//            if (!issueModels.isEmpty()) {
//                for (int i = 0; i < Math.min(issueModels.size(), 3); i++) {
//                    issuenames.append("[#"+issueModels.get(i).getNumber()+"]").append("("+issueModels.get(i).getHtmlUrl()+") ")
//                            .append("```" + issueModels.get(i).getTitle() + "```");
//                }
//            }
//
//            embed.addField("Current issues", issuenames.toString().isEmpty() ? "None!" : issuenames.toString());
//
//            embed.setTimestampToNow();
//
//            return embed.build();
//        } catch (IOException ex) {
//            getLogger().error(ex.getMessage(), ex);
//            embed.setTitle("Unknown repo!").setColor(Color.RED);
//        }
//        return embed;
//    }
}
