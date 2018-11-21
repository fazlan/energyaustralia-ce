package au.com.energyaustralia.digital.eacodingtest.car;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
class CarShowsController {

    private final CarShowsService service;

    @Autowired
    CarShowsController(CarShowsService service) {
        this.service = service;
    }

    @GetMapping(path = "/cars")
    List<CarMakeResource> cars() {
        return service.list();
    }
}
