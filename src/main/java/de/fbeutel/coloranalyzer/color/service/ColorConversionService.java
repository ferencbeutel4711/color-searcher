package de.fbeutel.coloranalyzer.color.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import de.fbeutel.coloranalyzer.color.domain.HslColor;
import de.fbeutel.coloranalyzer.color.domain.RgbColor;

@Slf4j
@Service
public class ColorConversionService {

  public RgbColor toRgb(final HslColor hslColor) {
    final double scaledLightness = hslColor.getL() / 100.0;
    final double scaledSaturation = hslColor.getS() / 100.0;
    final double chroma = (1 - Math.abs(2 * scaledLightness - 1)) * scaledSaturation;
    final double hueQuadrant = hslColor.getH() / 60.0;
    final double x = chroma * (1 - Math.abs(hueQuadrant % 2 - 1));
    double tempR = 0;
    double tempG = 0;
    double tempB = 0;
    if (hueQuadrant <= 1) {
      tempR = chroma;
      tempG = x;
      tempB = 0;
    } else if (hueQuadrant <= 2) {
      tempR = x;
      tempG = chroma;
      tempB = 0;
    } else if (hueQuadrant <= 3) {
      tempR = 0;
      tempG = chroma;
      tempB = x;
    } else if (hueQuadrant <= 4) {
      tempR = 0;
      tempG = x;
      tempB = chroma;
    } else if (hueQuadrant <= 5) {
      tempR = x;
      tempG = 0;
      tempB = chroma;
    } else if (hueQuadrant <= 6) {
      tempR = chroma;
      tempG = 0;
      tempB = x;
    }

    final double matcher = scaledLightness - 0.5 * chroma;
    return RgbColor.builder()
      .r(Math.round((tempR + matcher) * 255))
      .g(Math.round((tempG + matcher) * 255))
      .b(Math.round((tempB + matcher) * 255))
      .build();
  }
}
