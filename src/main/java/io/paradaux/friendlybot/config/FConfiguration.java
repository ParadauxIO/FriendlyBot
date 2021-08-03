package io.paradaux.friendlybot.config;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.serialize.SerializationException;

@ConfigSerializable
public class FConfiguration {

    private transient CommentedConfigurationNode root;

    @Comment("Your Discord Bot Token")
    private String botToken;
    @Comment("What symbol to begin commands with.")
    private String commandPrefix;

    @Comment("Mailgun is used to send verification emails. Enter your API key and URL here.")
    private String mailgunApplicationKey;
    private String mailgunBaseUrl;

    @Comment("Imgflip is used for meme generation. Enter your login details here.")
    private String imgflipUsername;
    private String imgflipPassword;

    @Comment("Other random services.")
    private String wolframAlphaApiKey;
    private String imgurClientId;
    private String bytebinInstanceUrl;
    private String openWeatherMapApiKey;

    @Comment("Database connection")
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    public FConfiguration() {

    }

    public void save() throws SerializationException {
        root.set(FConfiguration.class, root);
    }

    public CommentedConfigurationNode getRoot() {
        return root;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public String getMailgunApplicationKey() {
        return mailgunApplicationKey;
    }

    public String getMailgunBaseUrl() {
        return mailgunBaseUrl;
    }

    public String getImgflipUsername() {
        return imgflipUsername;
    }

    public String getImgflipPassword() {
        return imgflipPassword;
    }

    public String getWolframAlphaApiKey() {
        return wolframAlphaApiKey;
    }

    public String getImgurClientId() {
        return imgurClientId;
    }

    public String getBytebinInstanceUrl() {
        return bytebinInstanceUrl;
    }

    public String getOpenWeatherMapApiKey() {
        return openWeatherMapApiKey;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setRoot(CommentedConfigurationNode root) {
        this.root = root;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }

    public void setMailgunApplicationKey(String mailgunApplicationKey) {
        this.mailgunApplicationKey = mailgunApplicationKey;
    }

    public void setMailgunBaseUrl(String mailgunBaseUrl) {
        this.mailgunBaseUrl = mailgunBaseUrl;
    }

    public void setImgflipUsername(String imgflipUsername) {
        this.imgflipUsername = imgflipUsername;
    }

    public void setImgflipPassword(String imgflipPassword) {
        this.imgflipPassword = imgflipPassword;
    }

    public void setWolframAlphaApiKey(String wolframAlphaApiKey) {
        this.wolframAlphaApiKey = wolframAlphaApiKey;
    }

    public void setImgurClientId(String imgurClientId) {
        this.imgurClientId = imgurClientId;
    }

    public void setBytebinInstanceUrl(String bytebinInstanceUrl) {
        this.bytebinInstanceUrl = bytebinInstanceUrl;
    }

    public void setOpenWeatherMapApiKey(String openWeatherMapApiKey) {
        this.openWeatherMapApiKey = openWeatherMapApiKey;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }
}
