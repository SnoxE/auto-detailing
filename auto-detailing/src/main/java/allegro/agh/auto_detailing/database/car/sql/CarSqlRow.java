package allegro.agh.auto_detailing.database.car.sql;

public record CarSqlRow(
    int id, String make, String model, String productionYear, String size, String colour) {
  public static final String ID = "id";
  public static final String MAKE = "make";
  public static final String MODEL = "model";
  public static final String PRODUCTION_YEAR = "production_year";
  public static final String SIZE = "size";
  public static final String COLOUR = "colour";
}
