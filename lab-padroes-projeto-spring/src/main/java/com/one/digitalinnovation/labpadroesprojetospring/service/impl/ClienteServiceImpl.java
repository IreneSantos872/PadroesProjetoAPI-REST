package com.one.digitalinnovation.labpadroesprojetospring.service.impl;

import com.one.digitalinnovation.labpadroesprojetospring.model.Cliente;
import com.one.digitalinnovation.labpadroesprojetospring.model.ClienteRepository;
import com.one.digitalinnovation.labpadroesprojetospring.model.Endereco;
import com.one.digitalinnovation.labpadroesprojetospring.model.EnderecoRepository;
import com.one.digitalinnovation.labpadroesprojetospring.service.ClienteService;
import com.one.digitalinnovation.labpadroesprojetospring.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton: Injetar os componentes do Spring com @Autowired
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    // Strategy: Implementar os métodos definidos na interface
    // Facade: Abstrair integrações com subsistemas, provendo uma interface simples


    @Override
    public Iterable<Cliente> buscarTodos() {
        // Buscar todos os Clientes.
        return clienteRepository.findAll();
    }


    @Override
    public Cliente buscarPorId(Long id){
        //Buscar Cliente por ID
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente){
      salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente){
        //Buscar Cliente por ID, caso exista
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent()) {
            salvarClienteComCep(cliente);
        }
   }

    @Override
    public void deletar(Long id){
        //Deletar cliente por id
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        // Verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        // Inserir Cliente, vinculando o Endereco (novo ou existente).
        clienteRepository.save(cliente);
    }
}
