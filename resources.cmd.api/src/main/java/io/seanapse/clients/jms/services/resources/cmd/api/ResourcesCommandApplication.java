package io.seanapse.clients.jms.services.resources.cmd.api;

import io.seanapse.clients.jms.services.resources.core.configuration.AxonConfig;
import io.seanapse.clients.jms.services.resources.core.configuration.MongoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class, MongoConfig.class})
public class ResourcesCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourcesCommandApplication.class, args);
	}

}
