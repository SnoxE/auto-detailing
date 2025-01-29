package allegro.agh.auto_detailing.controller;

import allegro.agh.auto_detailing.common.ResponseDto;
import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.database.reservations.dto.AddReservationDto;
import allegro.agh.auto_detailing.database.reservations.dto.ReservationDto;
import allegro.agh.auto_detailing.service.ReservationService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping("/daily-hours")
  public Map<LocalDate, List<LocalTime>> getAvailableDailyHours(
      @RequestParam("length_hours") int lengthHours,
      @RequestParam("length_minutes") int lengthMinutes) {
    return reservationService.getAvailableSlots(lengthHours, lengthMinutes);
  }

  @PostMapping("/{userId}/add-reservation")
  public ResponseEntity<ResponseDto> addReservation(
      @PathVariable("userId") String userId, @RequestBody AddReservationDto addReservationDto) {
    reservationService.addReservation(
        Integer.parseInt(userId),
        addReservationDto.serviceId(),
        addReservationDto.carId(),
        addReservationDto.startAtDate(),
        addReservationDto.startAtTime(),
        addReservationDto.length());

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{userId}/delete-reservation/{reservationId}")
  public ResponseEntity<ResponseDto> deleteReservation(
      @PathVariable("userId") String userId, @PathVariable("reservationId") String reservationId) {
    reservationService.deleteReservation(Integer.parseInt(userId), Integer.parseInt(reservationId));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/calendar")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ContentDto<ReservationDto> getCalendarReservations(
      @RequestParam(value = "after", required = false) String after,
      @RequestParam(value = "before", required = false) String before) {
    return reservationService.getCalendarReservations(after, before);
  }
}
