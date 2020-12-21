package io.seanapse.clients.jms.services.resources.query.api.port;

import io.seanapse.clients.jms.services.resources.core.port.models.Location;
import io.seanapse.clients.jms.services.resources.core.port.models.Port;
import io.seanapse.clients.jms.services.resources.query.api.port.repositories.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializeSamplePortData implements ApplicationRunner {
    @Autowired
    PortRepository portRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Port izmir = Port.builder()
                .id("0abd959c-b802-4324-9461-9d91d86c7851")
                .portName("Izmir")
                .location(new Location("Turkey", "TRIZM", 38.45704, 27.15267))
                .build();
        Port porto = Port.builder()
                .id("8ac83d63-97db-4c07-9854-b5da1217aa5b")
                .portName("Porto")
                .location(new Location("Portugal", "PTOPO", 41.14407, -8.6414315))
                .build();
        Port tallinn = Port.builder()
                .id("96901e45-ea02-451d-acfe-3f6dc77d42be")
                .portName("Tallinn")
                .location(new Location("Estonia", "EEVEB", 59.46138, 24.655385))
                .build();
        Port glasgow = Port.builder()
                .id("ba20ac16-21e8-4346-aeab-37c6430fbb59")
                .portName("Glasgow")
                .location(new Location("United Kingdom", "GBGLW", 55.869905, -4.336425))
                .build();
        Port valencia = Port.builder()
                .id("effe0f4b-d686-442f-ab49-46e3f1546db8")
                .portName("Valencia")
                .location(new Location("Spain", "ESVLC",  39.44231, -0.31646595))
                .build();
        portRepository.deleteAll();
        portRepository.save(izmir);
        portRepository.save(porto);
        portRepository.save(tallinn);
        portRepository.save(glasgow);
        portRepository.save(valencia);

    }
}
