package fr.oan.translate.dao;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.oan.translate.domain.Level;

public interface LevelDao extends MongoRepository<Level, Serializable> {

	public long count();
}
