package com.dkharchenko_hillel.homework7.reposiroties;

import com.dkharchenko_hillel.homework7.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findRoleByName(String name);

}
