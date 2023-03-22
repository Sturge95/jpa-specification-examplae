package example.project.specification

import example.project.specification.service.config.MySqlTestContainerConfig
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

@ActiveProfiles(profiles = ["it"])
@ContextConfiguration(initializers = [MySqlTestContainerConfig::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(listeners = [SqlScriptsTestExecutionListener::class, DependencyInjectionTestExecutionListener::class])
abstract class AbstractIntegrationTest {

    @LocalServerPort
    protected var serverPort: Int = 0

}