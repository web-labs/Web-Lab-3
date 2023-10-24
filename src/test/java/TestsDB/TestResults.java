package TestsDB;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestResults {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgre:15-0"
    );
}
