package com.uri.anatomizer.repository;

import org.springframework.data.repository.CrudRepository;

import com.uri.anatomizer.model.URIModel;

public interface URIRepository extends CrudRepository<URIModel, String> {

}
