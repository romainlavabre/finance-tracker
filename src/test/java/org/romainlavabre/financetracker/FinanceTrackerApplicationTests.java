package org.romainlavabre.financetracker;

import ch.vorburger.exec.ManagedProcessException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.romainlavabre.financetracker.database.DatabaseProvider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FinanceTrackerApplicationTests {

	@BeforeAll
	public static void initDB() throws ManagedProcessException {
		DatabaseProvider.initDB();
	}

	@Test
	void contextLoads() {
	}

}
