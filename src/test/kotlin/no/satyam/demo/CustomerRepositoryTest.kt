package no.satyam.demo

import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@SpringBootTest
@RunWith(SpringRunner::class)
@ContextConfiguration(initializers = [DockerPostgreDataSourceInitializer::class])
class CustomerRepositoryTest {
        @Autowired
        private lateinit var repository: CustomerRepository

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




