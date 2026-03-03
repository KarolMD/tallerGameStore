package co.edu.uniquindio.poo.tallergamestore.repository;

import co.edu.uniquindio.poo.tallergamestore.entity.Genero;
import co.edu.uniquindio.poo.tallergamestore.entity.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideojuegoRepository extends JpaRepository<Videojuego,Long> {

    // Derived Query
    List<Videojuego> findByGenero(Genero genero);

    // Derived Query
    List<Videojuego> findByTituloContainingIgnoreCase(String titulo);

    // JPQL
    @Query("SELECT v FROM Videojuego v WHERE v.precio BETWEEN :min AND :max")
    List<Videojuego> buscarPorRangoPrecio(@Param("min") Double min,
                                          @Param("max") Double max);

    // Native Query
    @Query(value = "SELECT * FROM videojuego ORDER BY fecha_creacion DESC LIMIT 5",
            nativeQuery = true)
    List<Videojuego> ultimos5();
}
