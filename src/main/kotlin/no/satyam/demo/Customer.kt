package no.satyam.demo

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("Customer")
data class Customer(
        @Id
        override var id: Long? = null,

        @Column("firstName")
        val firstName: String,

        @Column("lastName")
        val lastName: String
) : BasicEntity