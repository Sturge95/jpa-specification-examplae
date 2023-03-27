package example.project.specification.service

import example.project.specification.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["/sql/service/user-service-clean-up.sql", "/sql/service/user-service-tests.sql"])
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = ["/sql/service/user-service-clean-up.sql"])
class UserServiceIT : AbstractIntegrationTest() {

    @Autowired
    lateinit var userService: UserService

    @Test
    fun `countUsersWithAgeGreaterThanOrEqualTo old users`() {
        val oldUsers = userService.countUsersWithAgeGreaterThanOrEqualTo(89)
        Assertions.assertThat(oldUsers).isEqualTo(2L)
    }

    @Test
    fun `countUsersWithAgeGreaterThanOrEqualTo testing on bounday`() {
        val oldUsers = userService.countUsersWithAgeGreaterThanOrEqualTo(99)
        Assertions.assertThat(oldUsers).isEqualTo(2L)
    }

    @Test
    fun `countUsersWithAgeGreaterThanOrEqualTo testing over`() {
        val oldUsers = userService.countUsersWithAgeGreaterThanOrEqualTo(100)
        Assertions.assertThat(oldUsers).isEqualTo(1L)
    }

    @Test
    fun getAllByAgeGreaterThan() {
        val users = userService.getAllUsersOnStreet("terrington street")

        Assertions.assertThat(users).hasSize(2)
            .extracting("id").containsExactlyInAnyOrder(-1L, -2L)
    }

}