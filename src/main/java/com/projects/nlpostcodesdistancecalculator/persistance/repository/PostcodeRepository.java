package com.projects.nlpostcodesdistancecalculator.persistance.repository;

import com.projects.nlpostcodesdistancecalculator.persistance.entity.Postcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostcodeRepository extends JpaRepository<Postcode, Long> {

    Optional<Postcode> findByPostCode(String postCode);
}
