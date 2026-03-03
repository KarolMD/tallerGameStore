package co.edu.uniquindio.poo.tallergamestore.service;

import co.edu.uniquindio.poo.tallergamestore.entity.Desarrolladora;
import co.edu.uniquindio.poo.tallergamestore.repository.DesarrolladoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesarrolladoraService {
    private final DesarrolladoraRepository repo;
    public DesarrolladoraService(DesarrolladoraRepository repo) {
        this.repo = repo;
    }

    public List<Desarrolladora> listar(){
        return repo.findAll();
    }
    public Desarrolladora crear(Desarrolladora d){
        return repo.save(d);
    }
}
