package allegro.agh.auto_detailing.service;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.database.reservations.dto.AddReservationDto.LengthDto;
import allegro.agh.auto_detailing.database.reservations.dto.ReservationDto;
import allegro.agh.auto_detailing.database.reservations.dto.ReservationStartEndTimes;
import allegro.agh.auto_detailing.database.reservations.sql.AddReservationSqlService;
import allegro.agh.auto_detailing.database.reservations.sql.ReservationSqlRow;
import allegro.agh.auto_detailing.database.reservations.sql.ReservationSqlService;
import allegro.agh.auto_detailing.database.reservations.sql.ReservationStartEndTimesSqlRow;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {

  @Autowired private final AddReservationSqlService addReservationSqlService;
  @Autowired private final ReservationSqlService reservationSqlService;

  public ReservationService(
      AddReservationSqlService addReservationSqlService,
      ReservationSqlService reservationSqlService) {
    this.addReservationSqlService = addReservationSqlService;
    this.reservationSqlService = reservationSqlService;
  }

  public Integer addReservation(
      int userId,
      int serviceId,
      int carId,
      String startAtDate,
      String startAtTime,
      LengthDto lengthDto) {
    String startDateTime = startAtDate + " " + startAtTime;
    LocalTime endTime =
        LocalTime.parse(startAtTime).plusHours(lengthDto.hours()).plusMinutes(lengthDto.minutes());

    String endDateTime = startAtDate + " " + endTime.toString() + ":00";

    return addReservationSqlService.createReservation(
        userId, serviceId, carId, startDateTime, endDateTime);
  }

  public void deleteReservation(int userId, int reservationId) {
    reservationSqlService.deleteReservation(userId, reservationId);
  }

  public ContentDto<ReservationDto> getUserReservations(String userId) {
    List<ReservationDto> reservationDtoList =
        reservationSqlService.getReservations(userId).stream()
            .map(ReservationService::reservationDtoMapper)
            .toList();

    return new ContentDto<>(reservationDtoList);
  }

  public ContentDto<ReservationDto> getCalendarReservations(String after, String before) {

    LocalDate afterDate;
    LocalDate beforeDate;

    if (after == null) afterDate = LocalDate.now();
    else afterDate = LocalDate.parse(after);

    if (after == null) beforeDate = LocalDate.now().plusWeeks(1);
    else beforeDate = LocalDate.parse(before);

    List<ReservationDto> reservationDtoList =
        reservationSqlService.getCalendarReservations(afterDate, beforeDate).stream()
            .map(ReservationService::reservationDtoMapper)
            .toList();

    return new ContentDto<>(reservationDtoList);
  }

  private static ReservationDto reservationDtoMapper(ReservationSqlRow reservationSqlRow) {
    return new ReservationDto(
        reservationSqlRow.id(),
        reservationSqlRow.servicesName(),
        reservationSqlRow.servicesPrice(),
        reservationSqlRow.carsMake(),
        reservationSqlRow.carsModel(),
        reservationSqlRow.carsYear(),
        reservationSqlRow.carsColour(),
        reservationSqlRow.resStartAt(),
        reservationSqlRow.resEndAt());
  }

  public static List<LocalTime> generateDailyTimeSlots() {
    LocalTime startTime = LocalTime.of(9, 0);
    LocalTime endTime = LocalTime.of(19, 0);
    List<LocalTime> timeSlots = new ArrayList<>();

    while (startTime.isBefore(endTime)) {
      timeSlots.add(startTime);
      startTime = startTime.plusMinutes(30);
    }

    return timeSlots;
  }

  public static Map<LocalDate, List<LocalTime>> generateTimeSlotsForRange(
      LocalDate startDate, int days) {
    Map<LocalDate, List<LocalTime>> timeSlots = new HashMap<>();
    for (int i = 0; i < days; i++) {
      LocalDate date = startDate.plusDays(i);
      timeSlots.put(date, generateDailyTimeSlots());
    }
    return timeSlots;
  }

  public Map<LocalDate, List<LocalTime>> filterSlots(
      Map<LocalDate, List<LocalTime>> slots,
      List<ReservationStartEndTimes> reservations,
      int lengthHours,
      int lengthMinutes) {
    Map<LocalDate, List<LocalTime>> availableSlots = new HashMap<>();
    LocalTime closingTime = LocalTime.of(19, 0);

    for (Map.Entry<LocalDate, List<LocalTime>> entry : slots.entrySet()) {
      LocalDate date = entry.getKey();
      List<LocalTime> dailySlots = new ArrayList<>(entry.getValue());

      dailySlots.removeIf(
          slot ->
              date.atTime(slot)
                  .plusHours(lengthHours)
                  .plusMinutes(lengthMinutes)
                  .isAfter(date.atTime(closingTime)));

      for (ReservationStartEndTimes reservation : reservations) {

        if (reservation.startAt().toLocalDate().equals(date)
            || reservation.endAt().toLocalDate().equals(date)) {
          dailySlots.removeIf(
              slot ->
                  date.atTime(slot)
                          .plusHours(lengthHours)
                          .plusMinutes(lengthMinutes)
                          .isAfter(reservation.startAt())
                      && date.atTime(slot).isBefore(reservation.endAt()));
        }
      }

      availableSlots.put(date, dailySlots);
    }

    return availableSlots;
  }

  public Map<LocalDate, List<LocalTime>> getAvailableSlots(int lengthHours, int lengthMinutes) {
    Map<LocalDate, List<LocalTime>> slots = generateTimeSlotsForRange(LocalDate.now(), 14);
    List<ReservationStartEndTimes> reservations =
        reservationSqlService.getReservationStartEndTimes().stream()
            .map(this::toReservationStartEndTimes)
            .toList();

    return filterSlots(slots, reservations, lengthHours, lengthMinutes);
  }

  private ReservationStartEndTimes toReservationStartEndTimes(
      ReservationStartEndTimesSqlRow reservationStartEndTimesSqlRow) {
    return new ReservationStartEndTimes(
        reservationStartEndTimesSqlRow.startAt(), reservationStartEndTimesSqlRow.endAt());
  }
}
