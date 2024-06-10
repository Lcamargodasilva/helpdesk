package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

}
