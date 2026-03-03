package co.edu.uniquindio.poo.tallergamestore.controller;

import co.edu.uniquindio.poo.tallergamestore.entity.Desarrolladora;
import co.edu.uniquindio.poo.tallergamestore.service.DesarrolladoraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desarrolladoras")
public class DesarrolladoraController {

    private final DesarrolladoraService service;
    public DesarrolladoraController(DesarrolladoraService service) {
        this.service = service;
    }

    @GetMapping
    public List<Desarrolladora> listar(){
        return service.listar();
    }

    @PostMapping
    public Desarrolladora crear(@RequestBody Desarrolladora d){
        return service.crear(d);
    }
}
