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
    public void eliminar(Long id) {
        Videojuego videojuego = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego no encontrado"));

        repo.delete(videojuego);
    }

    public List<Videojuego> buscarPorRango(Double min, Double max) {
        return repo.buscarPorRangoDePrecio(min, max);
    }

    public List<Videojuego> buscarPorTitulo(String titulo) {
        return repo
                .findByTituloContainingIgnoreCase(titulo);
    }

    private void validar(Videojuego videojuego) {

        if (videojuego.getTitulo() == null || videojuego.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        if (videojuego.getPrecio() == null || videojuego.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if (videojuego.getCodigoRegistro() == null || videojuego.getCodigoRegistro().isBlank()) {
            throw new IllegalArgumentException("El código de registro es obligatorio");
        }

        if (videojuego.getDesarrolladora() == null ||
                videojuego.getDesarrolladora().getId() == null) {
            throw new IllegalArgumentException("Debe asignar una desarrolladora válida");
        }
    }

    public Videojuego actualizar(Long id, Videojuego nuevo) {

        Videojuego existente = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Videojuego no encontrado"));

        validar(nuevo);

        existente.setTitulo(nuevo.getTitulo());
        existente.setPrecio(nuevo.getPrecio());
        existente.setGenero(nuevo.getGenero());
        existente.setCodigoRegistro(nuevo.getCodigoRegistro());
        Desarrolladora desarrolladora = desarrolladoraRepo
                .findById(nuevo.getDesarrolladora().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("La desarrolladora no existe"));

        existente.setDesarrolladora(desarrolladora);

        Videojuego actualizado = repo.save(existente);

        calcularIva(actualizado);

        return actualizado;
    }
}
