package allegro.agh.auto_detailing.controller;

import allegro.agh.auto_detailing.common.ResponseDto;
import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.database.car.CarDto;
import allegro.agh.auto_detailing.database.reservations.dto.ReservationDto;
import allegro.agh.auto_detailing.service.CarService;
import allegro.agh.auto_detailing.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  CarService carService;
  ReservationService reservationService;

  public UserController(CarService carService, ReservationService reservationService) {
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
