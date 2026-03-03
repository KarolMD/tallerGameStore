package co.edu.uniquindio.poo.tallergamestore.controller;

import co.edu.uniquindio.poo.tallergamestore.entity.Videojuego;
import co.edu.uniquindio.poo.tallergamestore.service.VideojuegoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private VideojuegoService service;

    public VideojuegoController(VideojuegoService service) {
        this.service = service;
    }
    @GetMapping
    public List<Videojuego> listar(){
        return service.listar();
    }
    @GetMapping("/{id}")
    public Videojuego obtener(@PathVariable Long id){
        return service.obtener(id);
    }
    @PostMapping
    public Videojuego crear(@RequestBody Videojuego videojuego){
        return service.crear(videojuego);
    }
    @PatchMapping("/{id}/descuento")
    public Videojuego descuento(@PathVariable Long id, @RequestParam Double porcentaje){
        return service.aplicarDescuento(id, porcentaje);
    }
}
