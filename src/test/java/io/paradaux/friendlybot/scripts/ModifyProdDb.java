package io.paradaux.friendlybot.scripts;

import com.mongodb.client.FindIterable;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.TagManager;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.database.TagEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;


public class ModifyProdDb {

    private static final String connectionUri = "";

    private static final Logger logger = LoggerFactory.getLogger(ModifyProdDb.class);
    private static MongoManager mongo;
    private static TagManager tags;

    @BeforeAll
    public static void main() {
        ConfigurationEntry config = new ConfigurationEntry()
                .setMongoDbConnectionUri(connectionUri);

        mongo = new MongoManager(config, logger);
        tags = new TagManager(config, logger, mongo);
    }


    @Test
    public void lowercaseTags() {
        FindIterable<TagEntry> allTags = mongo.getTags().find();

        for (TagEntry tag : allTags) {
            String before = tag.getId();
            tag.setId(tag.getId().toLowerCase(Locale.ROOT));
            tags.updateTag(tag);
            System.out.printf("Replaced %s with %s", before, tag.getId());
        }

    }

}
