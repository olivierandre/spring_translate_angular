package fr.oan.translate.dao;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.oan.translate.domain.Setting;

public interface SettingDao extends MongoRepository<Setting, Serializable> {

	public long count();
}
