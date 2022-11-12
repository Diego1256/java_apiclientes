package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ClienteRepository {
	
	public void create(Cliente cliente) throws Exception{
		
		Connection connection = ConnectionFactory.createConnection();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE(NOME, CPF, EMAIL) VALUES (?, ?, ?)");
		statement.setString(1, cliente.getNome());
		statement.setString(2, cliente.getCpf());
		statement.setString(3, cliente.getEmail());
		
		statement.execute();
		connection.close();
		
	}
	
	
public void update(Cliente cliente) throws Exception{
		
		Connection connection = ConnectionFactory.createConnection();
		PreparedStatement statement = connection.prepareStatement("UPDATE CLIENTE SET NOME=?, CPF=?, EMAIL=? WHERE IDCLIENTE=? ");
		statement.setString(1, cliente.getNome());
		statement.setString(2, cliente.getCpf());
		statement.setString(3, cliente.getEmail());
		statement.setInt(4, cliente.getIdCliente());
		
		statement.execute();
		connection.close();
		
	}

public void delete(Integer idCliente) throws Exception{
	
	Connection connection = ConnectionFactory.createConnection();
	PreparedStatement statement = connection.prepareStatement("DELETE FROM CLIENTE WHERE IDCLIENTE=? ");
	
	statement.setInt(1, idCliente);
	
	statement.execute();
	connection.close();
	
}

public List<Cliente> findAll() throws Exception{
	
	Connection connection = ConnectionFactory.createConnection();
	PreparedStatement statement = connection.prepareStatement("SELECT * FROM CLIENTE");
	
	ResultSet resultSet = statement.executeQuery();
	
	List<Cliente> lista = new ArrayList<Cliente>();
	
	while(resultSet.next()) {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(resultSet.getInt("idcliente"));
		cliente.setNome(resultSet.getString("nome"));
		cliente.setEmail(resultSet.getString("email"));
		cliente.setCpf(resultSet.getString("cpf"));
		
		lista.add(cliente);
	}
	
	connection.close();
	return lista;
}

public Cliente findById(Integer id) throws Exception{
	
	Connection connection = ConnectionFactory.createConnection();
	PreparedStatement statement = connection.prepareStatement("SELECT * FROM CLIENTE WHERE IDCLIENTE=?");
	
	statement.setInt(1, id);
	
	ResultSet resultSet = statement.executeQuery();
	
	Cliente cliente = null;
	
	if(resultSet.next()) {
		cliente = new Cliente();
		cliente.setIdCliente(resultSet.getInt("IDCLIENTE"));
		cliente.setNome(resultSet.getString("NOME"));
		cliente.setEmail(resultSet.getString("EMAIL"));
		cliente.setCpf(resultSet.getString("cpf"));
		
		
	}
	
	connection.close();
	return cliente;
}

}
