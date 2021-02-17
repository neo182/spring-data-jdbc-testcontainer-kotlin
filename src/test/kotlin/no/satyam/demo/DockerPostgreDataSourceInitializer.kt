package no.satyam.demo

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.PostgreSQLContainer

class DockerPostgreDataSourceInitializer : ApplicationContextInitializer<ConfigurableApplicationContext?> {
    private val postgreSQLContainer: TestPostgreSQLContainer = TestPostgreSQLContainer("postgres:13")

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        postgreSQLContainer.start()

        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            configurableApplicationContext,
            "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
            "spring.datasource.password=" + postgreSQLContainer.getPassword()
        )
    }

}

class TestPostgreSQLContainer(imageName: String) : PostgreSQLContainer<TestPostgreSQLContainer>(imageName)
