package example.project.specification.service.config

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ApplicationEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent
import org.testcontainers.containers.MySQLContainer

class MySqlTestContainerConfig : ApplicationContextInitializer<ConfigurableApplicationContext> {

    object MySqlTestContainer : MySQLContainer<MySqlTestContainer>("mysql:latest")

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val mysqlInstance = MySqlTestContainer.withDatabaseName("test")
            .withUsername("admin")
            .withPassword("password")

        mysqlInstance.start()
        TestPropertyValues.of(
            "spring.datasource.url=" + mysqlInstance.jdbcUrl,
            "spring.datasource.username=" + mysqlInstance.username,
            "spring.datasource.password=" + mysqlInstance.password
        ).applyTo(applicationContext.environment)


        applicationContext.addApplicationListener { event: ApplicationEvent ->
            if (event is ContextClosedEvent) {
                mysqlInstance.stop()
            }
        }
    }
}