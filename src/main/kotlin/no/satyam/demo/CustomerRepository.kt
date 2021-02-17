package no.satyam.demo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<Customer, Long> {
    fun findByFirstName(firstName:String) : List<Customer>?
    fun findByLastName(lastName:String) : List<Customer>?
}