package de.fbeutel.coloranalyzer.color.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class HslColor {

  private final long h;
  private final long s;
  private final long l;

  @Override
  public String toString() {
    final String delimiter = ",";
    final String[] className = this.getClass().getName().split("\\.");
    return className[className.length - 1] + ":" + h + delimiter + s + delimiter + l;
  }
}
