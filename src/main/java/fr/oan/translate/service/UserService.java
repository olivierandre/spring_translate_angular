package fr.oan.translate.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import fr.oan.translate.dao.UserDao;
import fr.oan.translate.domain.User;
import fr.oan.translate.domain.UserTransfer;
import fr.oan.translate.exception.TranslateException;
import fr.oan.translate.request.LoginRequest;
import fr.oan.translate.security.TokenUtils;

@Service
public class UserService {

	private final String prefixSalt = new String("a787efa1d100448b9fbc31f4ac8625bb");

	@Autowired
	UserDao userDao;

	@Autowired
	MailService mailService;

	public List<User> findAllUsers() {
		return userDao.findAll();
	}

	public long countUsers() {
		return userDao.count();
	}

	public User findUserByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	public User createUser(User user) {
		return userDao.insert(user);
	}

	public void deleteAllUsers() {
		userDao.deleteAll();
	}

	public UserTransfer getUserTransferById(String id) {
		User user = userDao.findById(id);
		return getUserTransfer(user);
	}

	public User createDefaultUser() throws NoSuchAlgorithmException {
		User user = new User();
		user.setFirstName("Adminstrator");
		user.setLastName("Adminstrator");
		user.setUserName("admin");
		user.setEnabled(true);
		user.setSalt(BCrypt.gensalt());
		user.setToken(BCrypt.gensalt());
		user.setPassword(BCrypt.hashpw("password", user.getSalt()));
		user.setFirstConnexion(true);

		return user;
	}

	public String getSecurePassword(String passwordToHash, String salt) {
		// Le mot de passe est construit => prefixSalt + password + salt User
		String hashPassword = new String();
		StringBuilder sb = new StringBuilder(prefixSalt);

		sb.append(passwordToHash);

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes());

			byte[] bytes = md.digest(sb.toString().getBytes());
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				result.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			hashPassword = result.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return hashPassword;
	}

	public UserTransfer authenticate(String username, String password) throws BadCredentialsException {
		User user = userDao.findByUserName(username);

		if (user == null) {
			throw new BadCredentialsException("Bad Credentials");
		}

		String cryptPassword = BCrypt.hashpw(password, user.getSalt());

		if (!cryptPassword.contains(user.getPassword())) {
			throw new BadCredentialsException("Bad Credentials");
		}

		return this.authenticateUser(user);

	}

	public UserTransfer getUserTransfer(User user) {
		UserTransfer userTransfer = new UserTransfer();
		userTransfer.setId(user.getId());
		userTransfer.setUsername(user.getUserName());
		userTransfer.setFirstName(user.getFirstName());
		userTransfer.setLastName(user.getLastName());
		userTransfer.setTokenAttribut(user.getPassword());
		userTransfer.setToken(TokenUtils.createToken(userTransfer));
		userTransfer.setFirstConnection(user.isFirstConnexion());
		userTransfer.setEnabled(user.isEnabled());
		userTransfer.setEmail(user.getEmail());

		return userTransfer;
	}

	public UserTransfer getUserByUserName(String username) {
		User user = userDao.findByUserName(username);
		if (user != null) {
			UserTransfer userTransfer = getUserTransfer(user);
			return userTransfer;
		} else {
			return null;
		}
	}

	public UserTransfer setNewPassword(LoginRequest loginRequest) throws TranslateException {
		User user = userDao.findById(loginRequest.getId());

		String newSalt = BCrypt.gensalt();
		String newPassword = BCrypt.hashpw(loginRequest.getPassword(), newSalt);

		user.setSalt(newSalt);
		user.setPassword(newPassword);
		user.setFirstConnexion(false);

		// mailService.send("olivier.andre@outlook.com", "Changement de poste");
		user = userDao.save(user);

		UserTransfer ut = this.authenticateUser(user);

		return ut;

	}

	public UserTransfer updateUser(UserTransfer ut) {
		User user = userDao.findById(ut.getId());

		user.updateUserByUserTransfer(ut);

		return getUserTransfer(userDao.save(user));

	}

	private UserTransfer authenticateUser(User user) {
		UserTransfer userTransfer = getUserTransfer(user);

		if (userTransfer != null) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					user.getUserName(), null, AuthorityUtils.createAuthorityList("user"));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return userTransfer;

		} else {
			return null;
		}

	}
}
