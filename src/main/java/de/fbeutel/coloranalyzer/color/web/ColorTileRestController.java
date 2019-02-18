package de.fbeutel.coloranalyzer.color.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fbeutel.coloranalyzer.color.domain.ColorTile;
import de.fbeutel.coloranalyzer.color.service.ColorTileService;

@RestController
@RequestMapping(value = "/tiles", produces = APPLICATION_JSON_VALUE)
public class ColorTileRestController {

  private final ColorTileService colorTileService;

  public ColorTileRestController(final ColorTileService colorTileService) {
    this.colorTileService = colorTileService;
  }

  @GetMapping
  public ResponseEntity<Map<String, List<ColorTile>>> getAllTiles() {
    return ResponseEntity.ok(colorTileService.getColorTiles());
  }
}
