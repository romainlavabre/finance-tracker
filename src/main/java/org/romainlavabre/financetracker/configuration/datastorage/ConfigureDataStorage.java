package org.romainlavabre.financetracker.configuration.datastorage;

import org.romainlavabre.database.DataStorageConfigurer;
import org.romainlavabre.financetracker.configuration.event.Event;
import org.springframework.stereotype.Service;

@Service
public class ConfigureDataStorage {

    public ConfigureDataStorage() {
        configure();
    }


    private void configure() {
        DataStorageConfigurer
                .init()
                .setTransactionSuccessEvent( Event.TRANSACTION_SUCCESS )
                .build();
    }
}
