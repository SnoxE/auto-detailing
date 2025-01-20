package allegro.agh.login_service.service;

import allegro.agh.login_service.common.exceptions.AuthException;
import allegro.agh.login_service.common.resource.ResourceException;
import allegro.agh.login_service.database.user.dto.UserDto;
import allegro.agh.login_service.database.user.sql.UserSqlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    private final UserSqlService userSqlService;

    public UserService(UserSqlService userSqlService) {
        this.userSqlService = userSqlService;
    }

    public UserDto registerUser(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String password,
            String role) throws ResourceException {

        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (email != null) {
            email = email.toLowerCase();
            if (!pattern.matcher(email).matches())
                throw new AuthException("Wrong email address format");
        }

        Integer count = userSqlService.getCountByEmail(email);
        if (count > 0) throw new AuthException("Email address already in use");

        userSqlService.createUser(firstName, lastName, email, phoneNumber, password, role);

        return userSqlService.getUserByEmail(email);
    }

    public UserDto getUserByEmail(String email) {
        return userSqlService.getUserByEmail(email);
    }

//    public void changePassword(int userId, String password) {
//        userSqlService.changePassword(userId, password);
//    }

    public String getPasswordByUserId(int userId) {
        return userSqlService.getPasswordByUserId(userId);
    }

    public int getUserCountByEmail(String email)
    {
        return userSqlService.getCountByEmail(email);
    }
}