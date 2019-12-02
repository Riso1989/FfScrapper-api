package com.ffscrapper.demo.rest.repository;

import com.ffscrapper.demo.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "events", path = "events", itemResourceRel = "event")
public interface ComparerRestRepository extends CrudRepository<Event, Long> {

    List<Event> findAll();

    List<Event> findByCountry(@Param("country") String country);

    List<Event> findByTitle(@Param("title") String title);

}
