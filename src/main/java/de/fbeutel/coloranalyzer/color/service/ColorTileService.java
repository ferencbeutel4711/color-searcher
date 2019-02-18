package de.fbeutel.coloranalyzer.color.service;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import de.fbeutel.coloranalyzer.color.domain.ColorTile;
import de.fbeutel.coloranalyzer.color.domain.HslColor;

@Service
public class ColorTileService {

  private final ColorConversionService colorConversionService;
  private final Map<String, List<ColorTile>> colorTiles;
  private final List<ColorTile> hueTiles;

  public ColorTileService(final ColorConversionService colorConversionService) {
    this.colorConversionService = colorConversionService;

    final Map<String, Integer> hueMapping = new ConcurrentHashMap<>();
    hueMapping.put("RED", 360);
    hueMapping.put("ORANGE", 30);
    hueMapping.put("YELLOW", 55);
    hueMapping.put("GREEN", 120);
    hueMapping.put("TEAL", 180);
    hueMapping.put("BLUE", 230);
    hueMapping.put("VIOLET", 275);
    hueMapping.put("PINK", 310);

    final Map<String, List<ColorTile>> colorTiles = hueMapping.entrySet().stream()
      .collect(toMap(Map.Entry::getKey, entry -> generateColorTilesForHue(entry.getKey(), entry.getValue())));

    colorTiles.put("GRAY", generateUnsaturatedColorTiles("GRAY"));

    this.colorTiles = colorTiles;

    this.hueTiles = colorTiles.entrySet().stream()
      .map(entry -> entry.getValue().stream()
        .filter(colorTile -> colorTile.getName().contains("_100_50") || colorTile.getName().contains("GRAY_0_50"))
        .findFirst()
        .get())
      .collect(Collectors.toList());
  }

  public Map<String, List<ColorTile>> getColorTiles() {
    return colorTiles;
  }

  public List<ColorTile> getHueTiles() {
    return hueTiles;
  }

  private List<ColorTile> generateUnsaturatedColorTiles(final String name) {
    final List<ColorTile> colorTileList = new ArrayList<>();
    IntStream.range(0, 11).forEach(lightnessIndex -> {
      final int lightness = lightnessIndex * 10;

      colorTileList.add(ColorTile.builder()
        .name(name + '_' + 0 + '_' + lightness)
        .color(colorConversionService.toRgb(HslColor.builder()
          .h(0)
          .s(0)
          .l(lightness)
          .build()))
        .build());
    });

    return colorTileList;
  }

  private List<ColorTile> generateColorTilesForHue(final String name, final int hue) {
    final List<ColorTile> colorTileList = new ArrayList<>();
    IntStream.range(1, 10)
      .forEach(lightnessIndex -> IntStream.range(1, 11)
        .forEach(saturationIndex -> {
          final int saturation = saturationIndex * 10;
          final int lightness = lightnessIndex * 10;

          colorTileList.add(ColorTile.builder()
            .name(name + '_' + saturation + '_' + lightness)
            .color(colorConversionService.toRgb(HslColor.builder()
              .h(hue)
              .s(saturation)
              .l(lightness)
              .build()))
            .build());
        }));

    return colorTileList;
  }
}
