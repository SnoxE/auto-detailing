package allegro.agh.auto_detailing.service;

import allegro.agh.auto_detailing.database.user.dto.UserDto;
import allegro.agh.auto_detailing.database.user.sql.UserSqlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  private final UserSqlService userSqlService;

  public UserService(UserSqlService userSqlService) {
    this.userSqlService = userSqlService;
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

  public int getUserCountByEmail(String email) {
    return userSqlService.getCountByEmail(email);
  }
}
