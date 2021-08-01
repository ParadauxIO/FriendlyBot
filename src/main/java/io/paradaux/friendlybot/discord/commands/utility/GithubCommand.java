/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.GithubCommand :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.discord.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
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
