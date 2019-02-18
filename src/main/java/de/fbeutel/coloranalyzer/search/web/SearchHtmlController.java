package de.fbeutel.coloranalyzer.search.web;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.fbeutel.coloranalyzer.color.service.ColorTileService;

@Controller
@RequestMapping(value = "/search", produces = TEXT_HTML_VALUE)
public class SearchHtmlController {

  private final ColorTileService colorTileService;

  public SearchHtmlController(final ColorTileService colorTileService) {
    this.colorTileService = colorTileService;
  }

  @GetMapping
  public ModelAndView getSearch() {
    final Map<String, Object> model = new ConcurrentHashMap<>();
    model.put("colorTiles", colorTileService.getColorTiles());
    model.put("hueTiles", colorTileService.getHueTiles());

    return new ModelAndView("search/index", model);
  }
}
