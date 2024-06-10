package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
