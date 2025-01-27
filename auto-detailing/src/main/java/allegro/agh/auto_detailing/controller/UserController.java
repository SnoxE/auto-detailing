package allegro.agh.auto_detailing.controller;

import allegro.agh.auto_detailing.common.ResponseDto;
import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.database.car.CarDto;
import allegro.agh.auto_detailing.database.reservations.dto.ReservationDto;
import allegro.agh.auto_detailing.database.user.dto.UserDto;
import allegro.agh.auto_detailing.service.CarService;
import allegro.agh.auto_detailing.service.ReservationService;
import allegro.agh.auto_detailing.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  UserService userService;
  CarService carService;
  ReservationService reservationService;

  public UserController(
      UserService userService, CarService carService, ReservationService reservationService) {
    this.userService = userService;
    this.carService = carService;
    this.reservationService = reservationService;
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
