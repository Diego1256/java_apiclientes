package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.components.EmailComponent;
import br.com.cotiinformatica.components.EmailComponentModel;
import br.com.cotiinformatica.dtos.ClienteGetDto;
import br.com.cotiinformatica.dtos.ClientePostDto;
import br.com.cotiinformatica.dtos.ClientePutDto;
import br.com.cotiinformatica.dtos.ClienteResponseDto;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.repositories.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Clientes")
@RestController
public class ClienteController {
	
	//inicialização automática (injeção de dependência)
	@Autowired
	EmailComponent emailComponent;

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para cadastro de clientes.")
	@PostMapping("/api/clientes")
	public ClienteResponseDto post(@RequestBody ClientePostDto dto) {

		ClienteResponseDto response = new ClienteResponseDto();
		
		try {
			
			Cliente cliente = new Cliente();
			
			cliente.setNome(dto.getNome());
			cliente.setEmail(dto.getEmail());
			cliente.setCpf(dto.getCpf());
			
			ClienteRepository clienteRepository = new ClienteRepository();
			clienteRepository.create(cliente);
			
			//enviarMensagemDeBoasVindas(cliente);
			
			response.setMensagem("Cliente cadastrado com sucesso.");
		}
		catch(Exception e) {
			response.setMensagem("Erro: " + e.getMessage());
		}		
		
		return response;
	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para atualização de clientes.")
	@PutMapping("/api/clientes")
	public ClienteResponseDto put(@RequestBody ClientePutDto dto) {

		ClienteResponseDto response = new ClienteResponseDto();
		
		try {
			
			Cliente cliente = new Cliente();
			
			cliente.setIdCliente(dto.getIdCliente());
			cliente.setNome(dto.getNome());
			cliente.setCpf(dto.getCpf());
			cliente.setEmail(dto.getEmail());
			
			ClienteRepository clienteRepository = new ClienteRepository();
			clienteRepository.update(cliente);
			
			response.setMensagem("Cliente atualizado com sucesso.");
		}
		catch(Exception e) {
			response.setMensagem("Erro: " + e.getMessage());
		}
		
		return response;
	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para exclusão de clientes.")
	@DeleteMapping("/api/clientes/{id}")
	public ClienteResponseDto delete(@PathVariable("id") Integer idCliente) {

		ClienteResponseDto response = new ClienteResponseDto();
		
		try {
			
			ClienteRepository clienteRepository = new ClienteRepository();
			clienteRepository.delete(idCliente);
			
			response.setMensagem("Cliente excluído com sucesso.");
		}
		catch(Exception e) {
			response.setMensagem("Erro: " + e.getMessage());
		}
		
		return response;
	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para consultar todos os clientes.")
	@GetMapping("/api/clientes")
	public List<ClienteGetDto> getAll() {

		List<ClienteGetDto> lista = new ArrayList<ClienteGetDto>();
		
		try {
			
			ClienteRepository clienteRepository = new ClienteRepository();
			for(Cliente cliente : clienteRepository.findAll()) {
				
				ClienteGetDto dto = new ClienteGetDto();
				
				dto.setIdCliente(cliente.getIdCliente());
				dto.setNome(cliente.getNome());
				dto.setCpf(cliente.getCpf());
				dto.setEmail(cliente.getEmail());
				
				lista.add(dto);
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para consultar 1 clientes através do ID.")
	@GetMapping("/api/clientes/{id}")
	public ClienteGetDto getById(@PathVariable("id") Integer idCliente) {

		ClienteGetDto clienteGetDto = null;
		
		try {
			
			ClienteRepository clienteRepository = new ClienteRepository();
			Cliente cliente = clienteRepository.findById(idCliente);
			
			if(cliente != null) {
				
				clienteGetDto = new ClienteGetDto();
				
				clienteGetDto.setIdCliente(cliente.getIdCliente());
				clienteGetDto.setNome(cliente.getNome());
				clienteGetDto.setEmail(cliente.getEmail());
				clienteGetDto.setCpf(cliente.getCpf());
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return clienteGetDto;
	}
	
	//método privado para fazer o envio do email
	private void enviarMensagemDeBoasVindas(Cliente cliente) throws Exception {
		
		EmailComponentModel model = new EmailComponentModel();
		model.setEmailDest(cliente.getEmail());
	
		//assunto e corpo da mensagem
		model.setAssunto("Parabéns, sua conta de cliente foi criada com sucesso! COTI Informática");
		String texto = "<div style='border: 2px solid #ccc; padding: 40px; margin: 40px;'>"
					 + "<center>"
					 + "<img src='https://www.cotiinformatica.com.br/imagens/logo-coti-informatica.png'/>"
					 + "<h2>Parabéns, <strong>" + cliente.getNome() + "</strong></h2>"
					 + "<p>Sua conta de cliente foi cadastrada com sucesso em nosso sistema, seguem os dados:</p>"					
					 + "<p>Nome: " + cliente.getNome() + "</p>"
					 + "<p>Email: " + cliente.getEmail() + "</p>"
					 + "<p>CPF:" + cliente.getCpf() + "</p>"
					 + "<p>Att,</p>"
					 + "<p>Equipe COTI Informática</p>"
					 + "</center>"
					 + "</div>";
		
		model.setTexto(texto);
		model.setHtml(true);
		
		//enviando a mensagem		
		emailComponent.enviarMensagem(model);
	}

}











