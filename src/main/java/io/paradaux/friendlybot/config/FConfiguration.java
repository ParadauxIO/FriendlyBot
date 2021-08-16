package io.paradaux.friendlybot.config;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class FConfiguration {

    @Comment("Your Discord Bot Token")
    private @Nullable String botToken = "BOT-TOKEN";
    @Comment("What symbol to begin commands with.")
    private @Nullable String commandPrefix = ";";

    @Comment("Mailgun is used to send verification emails. Enter your API key and URL here.")
    private @Nullable String mailgunApplicationKey = "MAILGUN-TOKEN";
    private @Nullable String mailgunBaseUrl = "MAILGUN-URL";

    @Comment("Imgflip is used for meme generation. Enter your login details here.")
    private @Nullable String imgflipUsername = "IMGFLIP-USERNAME";
    private @Nullable String imgflipPassword = "IMGFLIP-PASSWORD";

    @Comment("Other random services.")
    private @Nullable String wolframAlphaApiKey = "WOLFRAM-API";
    private @Nullable String imgurClientId = "IMGUR-CLIENT";
    private @Nullable String bytebinInstanceUrl = "BYTEBIN-URL";
    private @Nullable String openWeatherMapApiKey = "OPENWEATHER-API";

    @Comment("Database connection")
    private @Nullable String jdbcUrl = "DATABASE-URL";
    private @Nullable String jdbcUsername = "DATABASE-USER";
    private @Nullable String jdbcPassword = "DATABASE-PASS";

    @Nullable
    public String getBotToken() {
        return botToken;
    }

    @Nullable
    public String getCommandPrefix() {
        return commandPrefix;
    }

    @Nullable
    public String getMailgunApplicationKey() {
        return mailgunApplicationKey;
    }

    @Nullable
    public String getMailgunBaseUrl() {
        return mailgunBaseUrl;
    }

    @Nullable
    public String getImgflipUsername() {
        return imgflipUsername;
    }

    @Nullable
    public String getImgflipPassword() {
        return imgflipPassword;
    }

    @Nullable
    public String getWolframAlphaApiKey() {
        return wolframAlphaApiKey;
    }

    @Nullable
    public String getImgurClientId() {
        return imgurClientId;
    }

    @Nullable
    public String getBytebinInstanceUrl() {
        return bytebinInstanceUrl;
    }

    @Nullable
    public String getOpenWeatherMapApiKey() {
        return openWeatherMapApiKey;
    }

    @Nullable
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    @Nullable
    public String getJdbcUsername() {
        return jdbcUsername;
    }

    @Nullable
    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setBotToken(@Nullable String botToken) {
        this.botToken = botToken;
    }

    public void setCommandPrefix(@Nullable String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }

    public void setMailgunApplicationKey(@Nullable String mailgunApplicationKey) {
        this.mailgunApplicationKey = mailgunApplicationKey;
    }

    public void setMailgunBaseUrl(@Nullable String mailgunBaseUrl) {
        this.mailgunBaseUrl = mailgunBaseUrl;
    }

    public void setImgflipUsername(@Nullable String imgflipUsername) {
        this.imgflipUsername = imgflipUsername;
    }

    public void setImgflipPassword(@Nullable String imgflipPassword) {
        this.imgflipPassword = imgflipPassword;
    }

    public void setWolframAlphaApiKey(@Nullable String wolframAlphaApiKey) {
        this.wolframAlphaApiKey = wolframAlphaApiKey;
    }

    public void setImgurClientId(@Nullable String imgurClientId) {
        this.imgurClientId = imgurClientId;
    }

    public void setBytebinInstanceUrl(@Nullable String bytebinInstanceUrl) {
        this.bytebinInstanceUrl = bytebinInstanceUrl;
    }

    public void setOpenWeatherMapApiKey(@Nullable String openWeatherMapApiKey) {
        this.openWeatherMapApiKey = openWeatherMapApiKey;
    }

    public void setJdbcUrl(@Nullable String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setJdbcUsername(@Nullable String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public void setJdbcPassword(@Nullable String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }
}
