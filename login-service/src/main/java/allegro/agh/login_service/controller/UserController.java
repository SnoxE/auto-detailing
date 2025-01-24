package allegro.agh.login_service.controller;

import allegro.agh.login_service.common.ResponseDto;
import allegro.agh.login_service.common.problem.DuplicateKeyErrorProblem;
import allegro.agh.login_service.common.problem.InternalServerErrorProblem;
import allegro.agh.login_service.database.user.dto.UserDto;
import allegro.agh.login_service.service.UserService;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  UserService userService;

  //    CarService carService;
  //    ReservationService reservationService;
  //    EmailService emailService;

  //    private final PasswordEncoder passwordEncoder;

  //    public UserController(PasswordEncoder passwordEncoder) {
  //        this.passwordEncoder = passwordEncoder;
  //    }

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDto> registerUser(@RequestBody UserDto userDto) {
    UserDto user =
        userService.registerUser(
            userDto.firstName(),
            userDto.lastName(),
            userDto.email(),
            userDto.phoneNumber(),
            userDto.password(),
            userDto.role());

    //        emailService.sendRegisterConfirmationEmail(userDto);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/problem")
  public void throwProblem() {
    throw new InternalServerErrorProblem();
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

  @GetMapping("/email")
  public int getUserCountByEmail(@RequestParam("email") String email) {
    return userService.getUserCountByEmail(email);
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
