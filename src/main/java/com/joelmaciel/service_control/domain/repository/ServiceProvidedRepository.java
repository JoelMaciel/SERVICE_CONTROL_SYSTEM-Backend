package com.joelmaciel.service_control.domain.repository;

import com.joelmaciel.service_control.domain.model.ServiceProvided;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProvidedRepository extends JpaRepository<ServiceProvided, Long> {

    @Query("select s from ServiceProvided s join s.client c " +
    " where upper( c.name ) like upper( :name ) and MONTH(s.creationDate) =:month ")
    List<ServiceProvided> findByNameClientAndMonth(@Param("name") String name, @Param("month") Integer month);
}
