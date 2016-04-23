package fr.oan.translate.dao;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.oan.translate.domain.Score;

public interface ScoreDao extends MongoRepository<Score, Serializable> {

	public long count();

}
