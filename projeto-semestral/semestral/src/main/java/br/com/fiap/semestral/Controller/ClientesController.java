package br.com.fiap.semestral.Controller;

import br.com.fiap.DTO.ClientesDto;
import br.com.fiap.exceptions.clientesException.ClientesNotFoundException;
import br.com.fiap.exceptions.clientesException.ClientesNotSavedException;
import br.com.fiap.exceptions.clientesException.ClientesUnsupportedServiceOperationExcept;
import br.com.fiap.model.Clientes;
import br.com.fiap.model.Contato;
import br.com.fiap.model.Endereco;
import br.com.fiap.service.clientesService.ClientesService;
import br.com.fiap.service.clientesService.ClientesServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/clientes")
public class ClientesController {

    private final ClientesService clientesService = ClientesServiceFactory.create();
    private static final Logger logger = Logger.getLogger(ClientesController.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(ClientesDto input) {
        if (input.getCodigo() == null) {
            try {
                Clientes clientes = new Clientes();
                clientes.setNome(input.getNome());
                clientes.setTipoCliente(input.getTipoCliente());
                clientes.setSobrenome(input.getSobrenome());
                clientes.setSexo(input.getSexo());
                clientes.setTipoDocumento(input.getTipoDocumento());
                clientes.setNumeroDocumento(input.getNumeroDocumento());
                clientes.setDataNascimento(input.getDataNascimento());
                clientes.setAtividadeProfissional(input.getAtividadeProfissional());

                // Mapear Endereço
                if (input.getEndereco() != null) {
                    Endereco endereco = new Endereco();
                    endereco.setCep(input.getEndereco().getCep());
                    endereco.setLogradouro(input.getEndereco().getLogradouro());
                    endereco.setNumero(input.getEndereco().getNumero());
                    endereco.setComplemento(input.getEndereco().getComplemento());
                    endereco.setBairro(input.getEndereco().getBairro());
                    endereco.setCidade(input.getEndereco().getCidade());
                    endereco.setEstado(input.getEndereco().getEstado());
                    clientes.setEndereco(endereco);
                }

                // Mapear Contato
                if (input.getContato() != null) {
                    Contato contato = new Contato();
                    contato.setCelular(input.getContato().getCelular());
                    contato.setEmail(input.getContato().getEmail());
                    contato.setContato(input.getContato().getContato());
                    clientes.setContato(contato);
                }

                // Chamar o serviço para criar o cliente
                clientes = this.clientesService.create(clientes);

                return Response
                        .status(Response.Status.CREATED)
                        .entity(clientes)
                        .build();
            } catch (ClientesUnsupportedServiceOperationExcept e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("mensagem", e.getMessage())).build();
            } catch (SQLException | ClientesNotSavedException e) {
                logger.severe("Erro ao criar cliente: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "Erro inesperado ao tentar inserir um cliente")).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", "Este método só permite a criação de novos clientes sem código")).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        List<Clientes> clientes = this.clientesService.findAll();
        if (clientes == null || clientes.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(clientes).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, ClientesDto input){
        try {
            Clientes clientes = new Clientes();
            clientes.setCodigo(id);
            clientes.setNome(input.getNome());
            clientes.setTipoCliente(input.getTipoCliente());
            clientes.setSobrenome(input.getSobrenome());
            clientes.setSexo(input.getSexo());
            clientes.setTipoDocumento(input.getTipoDocumento());
            clientes.setNumeroDocumento(input.getNumeroDocumento());
            clientes.setDataNascimento(input.getDataNascimento());
            clientes.setAtividadeProfissional(input.getAtividadeProfissional());

            // Mapear Endereço
            if (input.getEndereco() != null) {
                Endereco endereco = new Endereco();
                endereco.setCep(input.getEndereco().getCep());
                endereco.setLogradouro(input.getEndereco().getLogradouro());
                endereco.setNumero(input.getEndereco().getNumero());
                endereco.setComplemento(input.getEndereco().getComplemento());
                endereco.setBairro(input.getEndereco().getBairro());
                endereco.setCidade(input.getEndereco().getCidade());
                endereco.setEstado(input.getEndereco().getEstado());
                clientes.setEndereco(endereco);
            }

            // Mapear Contato
            if (input.getContato() != null) {
                Contato contato = new Contato();
                contato.setCelular(input.getContato().getCelular());
                contato.setEmail(input.getContato().getEmail());
                contato.setContato(input.getContato().getContato());
                clientes.setContato(contato);
            }

            // Chamar o serviço para atualizar o cliente
            Clientes updated = this.clientesService.update(clientes);

            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (ClientesNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Cliente não encontrado para atualização")).build();
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar cliente: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar atualizar o cliente")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long codigo){
        try {
            this.clientesService.deleteById(codigo);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ClientesNotFoundException e) {
            logger.warning("Cliente não encontrado para exclusão: ID " + codigo);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Cliente não encontrado para exclusão")).build();
        } catch (SQLException s) {
            logger.severe("Erro ao deletar cliente: " + s.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar deletar o cliente")).build();
        }
    }
}
