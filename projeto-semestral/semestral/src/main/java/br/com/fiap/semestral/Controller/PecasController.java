package com.br.fiap.semestral.Controller;

import br.com.fiap.DTO.PecasDto;
import br.com.fiap.exceptions.pecasException.PecasNotFoundException;
import br.com.fiap.exceptions.pecasException.PecasNotSavedException;
import br.com.fiap.exceptions.pecasException.PecasUnsupportedServiceOperationException;
import br.com.fiap.model.Pecas;
import br.com.fiap.service.pecasService.PecasService;
import br.com.fiap.service.pecasService.PecasServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/pecas")
public class PecasController {

    private final PecasService pecasService = PecasServiceFactory.create();
    private static final Logger logger = Logger.getLogger(PecasController.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(PecasDto input) {
        if (input.getCodigo() == null) {
            try {
                Pecas pecas = new Pecas();
                pecas.setTipoVeiculo(input.getTipoVeiculo());
                pecas.setFabricante(input.getFabricante());
                pecas.setDescricao(input.getDescricao());
                pecas.setDataCompra(input.getDataCompra());
                pecas.setPreco(input.getPreco());
                pecas.setDesconto(input.getDesconto());
                pecas.setTotalDesconto(input.getTotalDesconto());

                // Chamar o serviço para criar a peça
                pecas = this.pecasService.create(pecas);

                return Response
                        .status(Response.Status.CREATED)
                        .entity(pecas)
                        .build();
            } catch (PecasUnsupportedServiceOperationException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("mensagem", e.getMessage())).build();
            } catch (SQLException | PecasNotSavedException e) {
                logger.severe("Erro ao criar peça: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "Erro inesperado ao tentar inserir uma peça")).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", "Este método só permite a criação de novas peças sem código")).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Pecas> pecasList = this.pecasService.findaall();
        if (pecasList == null || pecasList.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(pecasList).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, PecasDto input) {
        try {
            Pecas pecas = new Pecas();
            pecas.setCodigo(id);
            pecas.setTipoVeiculo(input.getTipoVeiculo());
            pecas.setFabricante(input.getFabricante());
            pecas.setDescricao(input.getDescricao());
            pecas.setDataCompra(input.getDataCompra());
            pecas.setPreco(input.getPreco());
            pecas.setDesconto(input.getDesconto());
            pecas.setTotalDesconto(input.getTotalDesconto());

            // Chamar o serviço para atualizar a peça
            Pecas updated = this.pecasService.update(pecas);

            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (PecasNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Peça não encontrada para atualização")).build();
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar peça: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar atualizar a peça")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long codigo) {
        try {
            this.pecasService.deleteById(codigo);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (PecasNotFoundException e) {
            logger.warning("Peça não encontrada para exclusão: ID " + codigo);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Peça não encontrada para exclusão")).build();
        } catch (SQLException s) {
            logger.severe("Erro ao deletar peça: " + s.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar deletar a peça")).build();
        }
    }
}
