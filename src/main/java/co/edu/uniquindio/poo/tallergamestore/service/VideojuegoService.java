package co.edu.uniquindio.poo.tallergamestore.service;

import co.edu.uniquindio.poo.tallergamestore.entity.Desarrolladora;
import co.edu.uniquindio.poo.tallergamestore.entity.Videojuego;
import co.edu.uniquindio.poo.tallergamestore.exception.ResourceNotFoundException;
import co.edu.uniquindio.poo.tallergamestore.repository.DesarrolladoraRepository;
import co.edu.uniquindio.poo.tallergamestore.repository.VideojuegoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideojuegoService {

    private final VideojuegoRepository repo;
    private final DesarrolladoraRepository desarrolladoraRepo;

    public VideojuegoService(VideojuegoRepository repo,
                             DesarrolladoraRepository desarrolladoraRepo) {
        this.repo = repo;
        this.desarrolladoraRepo = desarrolladoraRepo;
    }

    public Videojuego crear(Videojuego v) {

        if (v.getPrecio() < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");

        if (v.getTitulo() == null || v.getTitulo().isBlank())
            throw new IllegalArgumentException("El título no puede estar vacío");

        Desarrolladora dev = desarrolladoraRepo.findById(
                        v.getDesarrolladora().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Desarrolladora no existe"));

        v.setDesarrolladora(dev);

        return calcularIva(repo.save(v));
    }

    public List<Videojuego> listar() {
        return repo.findAll().stream().map(this::calcularIva).toList();
    }

    public Videojuego obtener(Long id) {
        Videojuego v = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego no encontrado"));

        return calcularIva(v);
    }

    public Videojuego aplicarDescuento(Long id, Double porcentaje) {
        Videojuego v = obtener(id);
        double nuevoPrecio = v.getPrecio() - (v.getPrecio() * porcentaje / 100);
        v.setPrecio(nuevoPrecio);
        return calcularIva(repo.save(v));
    }

    private Videojuego calcularIva(Videojuego v) {
        v.setPrecioConIva(v.getPrecio() * 1.19);
        return v;
    }
}
