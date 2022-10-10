package com.dkharchenko_hillel.homework7.reposiroties;

import com.dkharchenko_hillel.homework7.models.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findPersonByUsername(String username);

    @Modifying
    @Query("update Person set firstName = ?2 where username = ?1")
    Integer updatePersonFirstNameByUsername(String username, String firstName);

    @Modifying
    @Query("update Person set lastName = ?2 where username = ?1")
    Integer updatePersonLastNameByUsername(String username, String lastName);

    @Modifying
    @Query("update Person set phoneNumber = ?2 where username = ?1")
    Integer updatePersonPhoneNumberByUsername(String username, String phoneNumber);
}
