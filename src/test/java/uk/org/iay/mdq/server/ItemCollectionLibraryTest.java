
package uk.org.iay.mdq.server;

import java.util.ArrayList;
import java.util.List;

import net.shibboleth.metadata.Item;
import net.shibboleth.metadata.ItemId;
import net.shibboleth.metadata.ItemTag;
import net.shibboleth.metadata.MockItem;
import net.shibboleth.metadata.pipeline.SimplePipeline;
import net.shibboleth.metadata.pipeline.Stage;
import net.shibboleth.metadata.pipeline.StaticItemSourceStage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ItemCollectionLibraryTest {

    @Test
    public void testTaggedCollections() throws Exception {
        
        final Item<String> item1 = new MockItem("item1");
        item1.getItemMetadata().put(new ItemId("item1"));
        item1.getItemMetadata().put(new ItemId("item1alias"));
        item1.getItemMetadata().put(new ItemTag("odds"));
        item1.getItemMetadata().put(new ItemTag("three"));
        
        final Item<String> item2 = new MockItem("item2");
        item2.getItemMetadata().put(new ItemId("item2"));
        item2.getItemMetadata().put(new ItemId("item2alias"));
        item2.getItemMetadata().put(new ItemTag("evens"));
        item2.getItemMetadata().put(new ItemTag("three"));
        
        final Item<String> item3 = new MockItem("item3");
        item3.getItemMetadata().put(new ItemId("item3"));
        item3.getItemMetadata().put(new ItemId("item3alias"));
        item3.getItemMetadata().put(new ItemTag("odds"));
        item3.getItemMetadata().put(new ItemTag("three"));
        
        final Item<String> item4 = new MockItem("item4");
        item4.getItemMetadata().put(new ItemId("item4"));
        
        final List<Item<String>> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        
        final StaticItemSourceStage<String> sos = new StaticItemSourceStage<>();
        sos.setId("staticSource");
        sos.setSourceItems(items);
        sos.initialize();
        
        final List<Stage<String>> stages = new ArrayList<>();
        stages.add(sos);
        
        final SimplePipeline<String> pipeline = new SimplePipeline<>();
        pipeline.setId("pipeline");
        pipeline.setStages(stages);
        pipeline.initialize();
        
        final ItemCollectionLibrary<String> library = new ItemCollectionLibrary<>();
        library.setId("library");
        library.setSourcePipeline(pipeline);
        library.initialize();
        
        // look at the ALL output
        Assert.assertEquals(4, library.getAll().getItems().size());
        
        // Something that doesn't exist
        Assert.assertNull(library.get("noSuchItem"));
        
        // Individual items and their aliases
        Assert.assertEquals(1, library.get("item3").getItems().size());
        Assert.assertEquals(1, library.get("item3alias").getItems().size());
        Assert.assertEquals(2, library.get("item3").getIdentifiers().size());
        
        // check size of tagged collections
        Assert.assertEquals(2, library.get("odds").getItems().size());
        Assert.assertEquals(1, library.get("odds").getIdentifiers().size());
        Assert.assertEquals(1, library.get("evens").getItems().size());
        Assert.assertEquals(3, library.get("three").getItems().size());
    }

}
