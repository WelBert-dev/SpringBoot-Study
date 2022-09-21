package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("anime")
public class AnimeController {
    // equivalente a: localhost:8080/anime/list
    // obs: sem a anotação a nivel de classe @RequestMapping() não iria conter um contexto então o resultado seria
        // localhos:8080/list

    // @RequestMapping(method = RequestMethod.GET, path = "list") -> obsoleto
    @GetMapping(path = "list")
    public List<Anime> list(){
        return List.of(new Anime("DBZ"), new Anime("Hellsing"));
    }
}
