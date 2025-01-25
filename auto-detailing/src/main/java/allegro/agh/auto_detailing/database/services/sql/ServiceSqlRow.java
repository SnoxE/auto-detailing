package allegro.agh.auto_detailing.database.services.sql;

import java.sql.Time;

public record ServiceSqlRow(int id, String name, int price, Time length, String size) {

  public record ServiceNamesSqlRow(String name) {
    public static final String NAME = "name";
  }

  public static final String ID = "id";
  public static final String NAME = "name";
  public static final String PRICE = "price";
  public static final String LENGTH = "length";
  public static final String SIZE = "size";
}
