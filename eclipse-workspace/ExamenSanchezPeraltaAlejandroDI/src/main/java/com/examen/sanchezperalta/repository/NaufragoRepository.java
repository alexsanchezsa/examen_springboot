package com.examen.sanchezperalta.repository;

import com.examen.sanchezperalta.entity.Naufrago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NaufragoRepository extends JpaRepository<Naufrago, Long> {

    @Query("SELECT n.nacionalidad, COUNT(n) FROM Naufrago n GROUP BY n.nacionalidad")
    List<Object[]> countByNacionalidad();

    @Query("SELECT n.sexo, COUNT(n) FROM Naufrago n GROUP BY n.sexo")
    List<Object[]> countBySexo();
}
