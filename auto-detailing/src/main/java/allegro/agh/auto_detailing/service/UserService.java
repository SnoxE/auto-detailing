package allegro.agh.auto_detailing.service;

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

  public int getUserCountByEmail(String email) {
    return userSqlService.getCountByEmail(email);
  }
}
