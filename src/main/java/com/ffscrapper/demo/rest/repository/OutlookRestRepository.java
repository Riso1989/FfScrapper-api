package com.ffscrapper.demo.rest.repository;

import com.ffscrapper.demo.entity.Outlook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "outlooks", path = "outlook", itemResourceRel = "outlook")
public interface OutlookRestRepository extends CrudRepository<Outlook, Long> {

    List<Outlook> findAll();

    List<Outlook> findBySymbol(@Param("symbol") String symbol);
}
