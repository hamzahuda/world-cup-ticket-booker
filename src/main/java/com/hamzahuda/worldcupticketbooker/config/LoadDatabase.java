package com.hamzahuda.worldcupticketbooker.config;

import com.hamzahuda.worldcupticketbooker.model.Seat;
import com.hamzahuda.worldcupticketbooker.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private static final int TOTAL_SEATS = 100_000;
    private static final int BATCH_SIZE = 1000;

    @Bean
    CommandLineRunner initDatabase(SeatRepository repository) {

        return args -> {

            if (repository.count() > 0) {
                log.info("Seats already seeded ({} rows) — skipping.", repository.count());
                return;
            }

            log.info("Inserting {} seats...", TOTAL_SEATS);

            List<Seat> batch = new ArrayList<>(BATCH_SIZE);

            for (int i = 1; i <= TOTAL_SEATS; i++) {
                batch.add(Seat.builder()
                        .seatNumber(i)
                        .status(Seat.SeatStatus.AVAILABLE)
                        .build());

                if (batch.size() == BATCH_SIZE) {
                    repository.saveAll(batch);
                    batch.clear();
                }
            }

            if (!batch.isEmpty()) {
                repository.saveAll(batch);
            }

            log.info("Inserted {} seats.", repository.count());
        };
    }
}