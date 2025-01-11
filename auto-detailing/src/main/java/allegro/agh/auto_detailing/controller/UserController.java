package allegro.agh.auto_detailing.controller;

import allegro.agh.auto_detailing.common.ResponseDto;
import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.database.car.CarDto;
import allegro.agh.auto_detailing.database.reservations.dto.ReservationDto;
import allegro.agh.auto_detailing.database.user.dto.PasswordDto;
import allegro.agh.auto_detailing.database.user.dto.UserDto;
import allegro.agh.auto_detailing.service.CarService;
import allegro.agh.auto_detailing.service.EmailService;
import allegro.agh.auto_detailing.service.ReservationService;
import allegro.agh.auto_detailing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired UserService userService;
    @Autowired CarService carService;
    @Autowired ReservationService reservationService;
    @Autowired EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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

        emailService.sendRegisterConfirmationEmail(userDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/add-car")
    public ResponseEntity<ResponseDto> addCar(
            @PathVariable("userId") String userId, @RequestBody CarDto carDto) {
        carService.addCar(
                Integer.parseInt(userId),
                carDto.make(),
                carDto.model(),
                carDto.productionYear(),
                carDto.size(),
                carDto.colour());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<ResponseDto> changePassword(
            @PathVariable("userId") String userId, @RequestBody PasswordDto passwordDto) {
        String oldPassword = userService.getPasswordByUserId(Integer.parseInt(userId));
        if (!Objects.equals(oldPassword, passwordEncoder.encode(passwordDto.oldPassword()))) {
            return ResponseEntity.badRequest().build();
        } else if (Objects.equals(oldPassword, passwordEncoder.encode(passwordDto.newPassword()))) {
            return ResponseEntity.badRequest().build();
        } else {
            userService.changePassword(
                    Integer.parseInt(userId), passwordEncoder.encode(passwordDto.newPassword()));
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public UserDto getLoggedUser(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    @GetMapping("/email")
    public int getUserCountByEmail(@RequestParam("email") String email) {
        return userService.getUserCountByEmail(email);
    }

    @GetMapping("/{userId}/cars")
    public ContentDto<CarDto> getUserCars(@PathVariable("userId") String userId) {
        return carService.getUserCars(userId);
    }

    @DeleteMapping("/{userId}/delete-car/{carId}")
    public ResponseEntity<ResponseDto> deleteCar(
            @PathVariable("userId") String userId, @PathVariable("carId") String carId) {
        carService.deleteCar(Integer.parseInt(userId), Integer.parseInt(carId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/reservations")
    public ContentDto<ReservationDto> getUserReservations(@PathVariable("userId") String userId) {
        return reservationService.getUserReservations(userId);
    }
}

