package allegro.agh.login_service.controller;

import allegro.agh.login_service.database.user.dto.NewUserDto;
import allegro.agh.login_service.database.user.dto.UserDto;
import allegro.agh.login_service.service.EmailService;
import allegro.agh.login_service.service.UserService;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  UserService userService;
  EmailService emailService;

  //    CarService carService;
  //    ReservationService reservationService;

  public UserController(UserService userService, EmailService emailService) {
    this.userService = userService;
    this.emailService = emailService;
  }

  @PostMapping("/register")
  public UserDto registerUser(@RequestBody NewUserDto newUserDto) {
    UserDto user =
        userService.registerUser(
            newUserDto.firstName(),
            newUserDto.lastName(),
            newUserDto.email(),
            newUserDto.password(),
            newUserDto.role());

    long beforeDbUpdate = System.currentTimeMillis();
    emailService.sendRegisterConfirmationEmail(user);
    log.info("send email: {} ms", System.currentTimeMillis() - beforeDbUpdate);

    return user;
  }

  @GetMapping("/email")
  public int getUserCountByEmail(@RequestParam("email") String email) {
    return userService.getUserCountByEmail(email);
  }

  //    @PostMapping("/{userId}/add-car")
  //    public ResponseEntity<ResponseDto> addCar(
  //            @PathVariable("userId") String userId, @RequestBody CarDto carDto) {
  //        carService.addCar(
  //                Integer.parseInt(userId),
  //                carDto.make(),
  //                carDto.model(),
  //                carDto.productionYear(),
  //                carDto.size(),
  //                carDto.colour());
  //
  //        return ResponseEntity.ok().build();
  //    }

  //    @PutMapping("/{userId}/change-password")
  //    public ResponseEntity<ResponseDto> changePassword(
  //            @PathVariable("userId") String userId, @RequestBody PasswordDto passwordDto) {
  //        String oldPassword = userService.getPasswordByUserId(Integer.parseInt(userId));
  //        if (!Objects.equals(oldPassword, passwordEncoder.encode(passwordDto.oldPassword()))) {
  //            return ResponseEntity.badRequest().build();
  //        } else if (Objects.equals(oldPassword,
  // passwordEncoder.encode(passwordDto.newPassword()))) {
  //            return ResponseEntity.badRequest().build();
  //        } else {
  //            userService.changePassword(
  //                    Integer.parseInt(userId),
  // passwordEncoder.encode(passwordDto.newPassword()));
  //        }
  //
  //        return ResponseEntity.ok().build();
  //    }

  @GetMapping("/user")
  public UserDto getLoggedUser(Principal principal) {
    return userService.getUserByEmail(principal.getName());
  }

  //    @GetMapping("/{userId}/cars")
  //    public ContentDto<CarDto>
  // getUserCars(@org.springframework.web.bind.annotation.PathVariable("userId") String userId) {
  //        return carService.getUserCars(userId);
  //    }
  //
  //    @DeleteMapping("/{userId}/delete-car/{carId}")
  //    ResponseEntity<allegro.agh.login_service.common.ResponseDto> deleteCar(
  //            @org.springframework.web.bind.annotation.PathVariable("userId") String userId,
  // @org.springframework.web.bind.annotation.PathVariable("carId") String carId) {
  //        carService.deleteCar(Integer.parseInt(userId), Integer.parseInt(carId));
  //        return org.springframework.http.ResponseEntity.ok().build();
  //    }
  //
  //    @GetMapping("/{userId}/reservations")
  //    public ContentDto<ReservationDto>
  // getUserReservations(@org.springframework.web.bind.annotation.PathVariable("userId") String
  // userId) {
  //        return reservationService.getUserReservations(userId);
  //    }
}
