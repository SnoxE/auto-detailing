package allegro.agh.auto_detailing.service;

import allegro.agh.auto_detailing.database.user.dto.UserDto;
import allegro.agh.auto_detailing.database.user.sql.UserSqlService;
import allegro.agh.auto_detailing.common.exceptions.DgAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    @Autowired
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
            String role) throws DgAuthException {

        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (email != null) {
            email = email.toLowerCase();
            if (!pattern.matcher(email).matches())
                throw new DgAuthException("Niepoprawny format adresu email");
        }

        Integer count = userSqlService.getCountByEmail(email);
        if (count > 0) throw new DgAuthException("Adres email jest już w użyciu");

        userSqlService.createUser(firstName, lastName, email, phoneNumber, password, role);

        return userSqlService.getUserByEmail(email);
    }

    public UserDto getUserByEmail(String email) {
        return userSqlService.getUserByEmail(email);
    }

    public void changePassword(int userId, String password) {
        userSqlService.changePassword(userId, password);
    }

    public String getPasswordByUserId(int userId) {
        return userSqlService.getPasswordByUserId(userId);
    }

    public int getUserCountByEmail(String email)
    {
        return userSqlService.getCountByEmail(email);
    }
}
