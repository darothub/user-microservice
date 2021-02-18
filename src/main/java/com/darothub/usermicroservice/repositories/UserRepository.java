package com.darothub.usermicroservice.repositories;


import com.darothub.usermicroservice.model.Artisan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Artisan, Long> {
    Artisan findByEmailAddress(String email);
}
