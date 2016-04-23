package fr.oan.translate.dao;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.oan.translate.domain.Lang;

public interface LangDao extends MongoRepository<Lang, Serializable> {

}
