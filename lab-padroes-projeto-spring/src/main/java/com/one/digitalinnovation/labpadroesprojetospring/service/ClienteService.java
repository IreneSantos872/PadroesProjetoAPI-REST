package com.one.digitalinnovation.labpadroesprojetospring.service;

import com.one.digitalinnovation.labpadroesprojetospring.model.Cliente;

/*
* Interface que define o padrão <b>Strategy</b> no dominio de cliente.
* Com isso, se necessário, podemos ter multiplas implementações dessa mesma
* interface.
*
* @author Irene Santos
 */
public interface ClienteService {

    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}
