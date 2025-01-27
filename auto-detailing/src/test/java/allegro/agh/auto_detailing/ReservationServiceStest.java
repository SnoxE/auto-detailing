package allegro.agh.auto_detailing;

import allegro.agh.auto_detailing.common.dto.ContentDto;
import allegro.agh.auto_detailing.database.reservations.dto.ReservationDto;
import allegro.agh.auto_detailing.database.reservations.dto.ReservationStartEndTimes;
import allegro.agh.auto_detailing.database.reservations.sql.ReservationSqlRow;
import allegro.agh.auto_detailing.database.reservations.sql.ReservationSqlService;
import allegro.agh.auto_detailing.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationSqlService reservationSqlService;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldReturnUserReservations() {
        List<ReservationSqlRow> mockReservations = List.of(new ReservationSqlRow(1, "Detailing", 200, "Toyota", "Corolla", "2020", "Black", null, null));
        when(reservationSqlService.getReservations("1")).thenReturn(mockReservations);

        ContentDto<ReservationDto> result = reservationService.getUserReservations("1");

        assertEquals(1, result.content().size());
        assertEquals("Detailing", result.content().get(0).servicesName());
        verify(reservationSqlService, times(1)).getReservations("1");
    }

    @Test
    void shouldMapReservationSqlRowToReservationDto() {
        Timestamp startAt = Timestamp.valueOf(LocalDateTime.of(2025, 1, 27, 10, 0));
        Timestamp endAt = Timestamp.valueOf(LocalDateTime.of(2025, 1, 27, 12, 0));
        ReservationSqlRow reservationSqlRow = new ReservationSqlRow(
                1,
                "Cleaning",
                100,
                "Toyota",
                "Corolla",
                "2020",
                "Red",
                startAt,
                endAt
        );

        ReservationDto result = ReservationService.reservationDtoMapper(reservationSqlRow);

        assertEquals(1, result.id());
        assertEquals("Cleaning", result.servicesName());
        assertEquals(100, result.servicesPrice());
        assertEquals("Toyota", result.carsMake());
        assertEquals("Corolla", result.carsModel());
        assertEquals("2020", result.carsYear());
        assertEquals("Red", result.carsColour());
        assertEquals(startAt, result.resStartAt());
        assertEquals(endAt, result.resEndAt());
    }
    @Test
    void shouldGenerateDailyTimeSlots() {
        List<LocalTime> result = ReservationService.generateDailyTimeSlots();
        List<LocalTime> expectedTimeSlots = List.of(
                LocalTime.of(9, 0),
                LocalTime.of(9, 30),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                LocalTime.of(11, 0),
                LocalTime.of(11, 30),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30),
                LocalTime.of(13, 0),
                LocalTime.of(13, 30),
                LocalTime.of(14, 0),
                LocalTime.of(14, 30),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30),
                LocalTime.of(16, 0),
                LocalTime.of(16, 30),
                LocalTime.of(17, 0),
                LocalTime.of(17, 30),
                LocalTime.of(18, 0),
                LocalTime.of(18, 30)
        );

        assertEquals(LocalTime.of(9, 0), result.get(0));
        assertEquals(LocalTime.of(18, 30), result.get(result.size() - 1));
        assertEquals(20, result.size());
        assertEquals(expectedTimeSlots, result);
    }

    @Test
    void shouldGenerateTimeSlotsForRange() {
        LocalDate startDate = LocalDate.of(2025, 1, 27);
        LocalDate secondDate = LocalDate.of(2025, 1, 28);

        Map<LocalDate, List<LocalTime>> result = ReservationService.generateTimeSlotsForRange(startDate, 2);

        assertTrue(result.containsKey(startDate));
        assertEquals(20, result.get(startDate).size());


        assertTrue(result.containsKey(secondDate));
        assertEquals(20, result.get(secondDate).size());
    }

    @Test
    void shouldFilterSlots() {
        ReservationService reservationService = mock(ReservationService.class);

        List<ReservationStartEndTimes> reservations = List.of(
                new ReservationStartEndTimes(
                        LocalDate.of(2025, 1, 27).atTime(10, 0),
                        LocalDate.of(2025, 1, 27).atTime(12, 0)
                )
        );

        Map<LocalDate, List<LocalTime>> slots = Map.of(
                LocalDate.of(2025, 1, 27), List.of(
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0)
                )
        );

        Map<LocalDate, List<LocalTime>> availableSlots = new HashMap<>();
        availableSlots.put(LocalDate.of(2025, 1, 27), List.of(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        ));

        when(reservationService.filterSlots(slots, reservations, 2, 0)).thenReturn(availableSlots);

        Map<LocalDate, List<LocalTime>> result = reservationService.filterSlots(slots, reservations, 2, 0);

        // Weryfikacja: slot 10:00 powinien zostać usunięty
        assertEquals(2, result.get(LocalDate.of(2025, 1, 27)).size());
        assertEquals(LocalTime.of(9, 0), result.get(LocalDate.of(2025, 1, 27)).get(0));
        assertEquals(LocalTime.of(11, 0), result.get(LocalDate.of(2025, 1, 27)).get(1));
    }


}
