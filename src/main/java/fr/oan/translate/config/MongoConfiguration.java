package fr.oan.translate.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import fr.oan.translate.config.settings.MongoSettings;

@Configuration
@EnableConfigurationProperties(MongoSettings.class)
public class MongoConfiguration {

	@Autowired
	private MongoSettings mongoSettings;

	@Bean
	public SimpleMongoDbFactory mongo() throws Exception {

		boolean resetDatabase = mongoSettings.isReset();
		String dbName = mongoSettings.getDbName();
		String host = mongoSettings.getHost();
		Integer port = mongoSettings.getPort();
		String user = mongoSettings.getUser();
		String pwd = System.getenv(mongoSettings.getPassword());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(MongoCredential.createScramSha1Credential(user, dbName, pwd.toCharArray()));
		MongoClient mongo = new MongoClient(new ServerAddress(host, port), credentials);

		if (resetDatabase) {
			mongo.dropDatabase(dbName);
		}

		return new SimpleMongoDbFactory(mongo, dbName);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo());
	}
}
