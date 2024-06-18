package app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dtos.ChamadoDTO;
import app.entities.Chamado;
import app.entities.Cliente;
import app.entities.Tecnico;
import app.enums.Prioridade;
import app.enums.Status;
import app.exceptions.ObjectnotFoundException;
import app.repositories.ChamadoRepository;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado: " + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO objDTO) {
		return repository.save(newChamado(objDTO));
	}

	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = updateChamado(oldObj, objDTO);
		return repository.save(oldObj);
	}

	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());

		Chamado chamado = new Chamado();
		chamado.setId(obj.getId());
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());

		if (obj.getStatus().equals(2)) { // ENCERRADO
			chamado.setDataFechamento(LocalDate.now());
		} else {
			chamado.setDataFechamento(null);
		}

		return chamado;
	}

	private Chamado updateChamado(Chamado existingChamado, ChamadoDTO obj) {
		existingChamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		existingChamado.setStatus(Status.toEnum(obj.getStatus()));
		existingChamado.setTitulo(obj.getTitulo());
		existingChamado.setObservacoes(obj.getObservacoes());
		existingChamado.setTecnico(tecnicoService.findById(obj.getTecnico()));
		existingChamado.setCliente(clienteService.findById(obj.getCliente()));

		if (obj.getStatus().equals(2)) { // ENCERRADO
			existingChamado.setDataFechamento(LocalDate.now());
		} else {
			existingChamado.setDataFechamento(null);
		}

		return existingChamado;
	}
}
