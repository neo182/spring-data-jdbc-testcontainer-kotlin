package no.satyam.demo

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.*

class TestPostgreSQLContainer(imageName: String) : PostgreSQLContainer<TestPostgreSQLContainer>(imageName)

@SpringBootTest
@RunWith(SpringRunner::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [(CustomerRepositoryTest.DockerPostgreDataSourceInitializer::class)])
@Testcontainers
class CustomerRepositoryTest {
        @Autowired
        private lateinit var repository: CustomerRepository

        companion object class DockerPostgreDataSourceInitializer : ApplicationContextInitializer<ConfigurableApplicationContext?> {
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

        @Test
         fun testRepository() {
                val customer = Customer(firstName = "Ole", lastName = "Gunnar")
                val customerReloaded = repository.save(customer)
                Assertions.assertThat(customerReloaded).isNotNull


                val customerSaved: Optional<Customer> = repository.findById(1)

                Assertions.assertThat(customerSaved).isPresent
                Assertions.assertThat(customerSaved.get().firstName).isEqualTo("Ole")
                Assertions.assertThat(customerSaved.get().lastName).isEqualTo("Gunnar")
        }

}




