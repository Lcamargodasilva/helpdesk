package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
