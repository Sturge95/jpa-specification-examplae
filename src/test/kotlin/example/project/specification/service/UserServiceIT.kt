package example.project.specification.service

import example.project.specification.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["/sql/service/user-service-clean-up.sql", "/sql/service/user-service-tests.sql"])
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = ["/sql/service/user-service-clean-up.sql"])
class UserServiceIT : AbstractIntegrationTest() {

    @Test
    fun test() {
        Assertions.assertThat(true)
    }

}