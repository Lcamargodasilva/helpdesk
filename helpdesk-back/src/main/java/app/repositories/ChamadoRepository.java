package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
