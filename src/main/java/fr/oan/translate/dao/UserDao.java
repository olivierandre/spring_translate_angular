package fr.oan.translate.dao;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.oan.translate.domain.User;

public interface UserDao extends MongoRepository<User, Serializable> {
	public User findByUserName(String userName);

	public User findById(String id);
}
