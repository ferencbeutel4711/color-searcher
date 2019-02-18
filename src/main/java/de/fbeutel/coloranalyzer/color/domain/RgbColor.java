package de.fbeutel.coloranalyzer.color.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RgbColor {

  private final long r;
  private final long g;
  private final long b;

  @Override
  public String toString() {
    final String delimiter = ",";
    final String[] className = this.getClass().getName().split("\\.");
    return className[className.length - 1] + ":" + r + delimiter + g + delimiter + b;
  }

  public String rgbString() {
    return "rgb(" + r + "," + g + "," + b + ")";
  }
}
