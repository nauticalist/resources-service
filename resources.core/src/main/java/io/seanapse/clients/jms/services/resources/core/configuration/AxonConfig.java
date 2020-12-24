package io.seanapse.clients.jms.services.resources.core.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

@Configuration
public class AxonConfig {
    @Value("${axon.data.mongodb.host:127.0.0.1}")
    private String mongoHost;

    @Value("${axon.data.mongodb.port:27017}")
    private int mongoPort;

    @Value("${axon.data.mongodb.database:resources}")
    private String mongoDatabase;

    @Value("${axon.data.mongodb.username:root}")
    private String username;

    @Value("${axon.data.mongodb.password:rootpassword}")
    private String password;

    @Bean
    public MongoClient mongo() {
        return MongoClients.create("mongodb://"+username+":"+password+"@"+mongoHost+":"+mongoPort+"/?authSource=admin&authMechanism=SCRAM-SHA-1");
    }

    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder()
                .mongoDatabase(mongo(), mongoDatabase)
                .build();
    }

    @Bean
    public TokenStore tokenStore(Serializer serializer) {
        return MongoTokenStore.builder()
                .mongoTemplate(axonMongoTemplate())
                .serializer(serializer)
                .build();
    }

    @Bean
    public EventStorageEngine storageEngine(MongoClient mongoClient) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(mongoClient).build())
                .build();
    }

    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration){
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }

//    @Bean
//    XStream xstream(){
//        XStream xstream = new XStream();
//        xstream.addPermission(NoTypePermission.NONE);
//        xstream.addPermission(NullPermission.NULL);
//        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
//        xstream.allowTypeHierarchy(Collection.class);
//        xstream.allowTypesByWildcard(new String[] {
//                "io.seanapse.clients.**",
//                "org.axonframework.**",
//                "com.thoughtworks.xstream.**"
//        });
//
//        return xstream;
//    }
//
//    @Bean
//    @Primary
//    public Serializer serializer(XStream xStream) {
//        return XStreamSerializer.builder().xStream(xStream).build();
//    }
}
