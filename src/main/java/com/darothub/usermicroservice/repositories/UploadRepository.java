package com.darothub.usermicroservice.repositories;


import com.darothub.usermicroservice.model.dto.UploadImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends JpaRepository<UploadImage, String> {
}
