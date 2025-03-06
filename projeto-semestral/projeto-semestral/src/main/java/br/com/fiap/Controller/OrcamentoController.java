package br.com.fiap.Controller;

import br.com.fiap.DTO.OrcamentoDto;
import br.com.fiap.exceptions.orcamentoException.OrcamentoNotFoundException;
import br.com.fiap.exceptions.orcamentoException.OrcamentoNotSavedException;
import br.com.fiap.exceptions.orcamentoException.OrcamentoUnsupportedServiceOperationException;
import br.com.fiap.model.Orcamento;
import br.com.fiap.service.orcamentoService.OrcamentoService;
import br.com.fiap.service.orcamentoService.OrcamentoServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/orcamento")
public class OrcamentoController {

    private final OrcamentoService orcamentoService = OrcamentoServiceFactory.create();
    private static final Logger logger = Logger.getLogger(OrcamentoController.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(OrcamentoDto input) {
        if (input.getCodigo() == null) {
            try {
                Orcamento orcamento = new Orcamento();
                orcamento.setDataOrcamento(input.getDataOrcamento());
                orcamento.setMaoDeObra(input.getMaoDeObra());
                orcamento.setValorHora(input.getValorHora());
                orcamento.setQuantidadeHoras(input.getQuantidadeHoras());
                orcamento.setValorTotal(input.getValorTotal());

                // Chamar o serviço para criar o orçamento
                orcamento = this.orcamentoService.create(orcamento);

                return Response
                        .status(Response.Status.CREATED)
                        .entity(orcamento)
                        .build();
            } catch (OrcamentoUnsupportedServiceOperationException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("mensagem", e.getMessage())).build();
            } catch (SQLException | OrcamentoNotSavedException e) {
                logger.severe("Erro ao criar orçamento: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "Erro inesperado ao tentar inserir um orçamento")).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", "Este método só permite a criação de novos orçamentos sem código")).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Orcamento> orcamentos = this.orcamentoService.findAll();
        if (orcamentos == null || orcamentos.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(orcamentos).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, OrcamentoDto input) {
        try {
            Orcamento orcamento = new Orcamento();
            orcamento.setCodigo(id);
            orcamento.setDataOrcamento(input.getDataOrcamento());
            orcamento.setMaoDeObra(input.getMaoDeObra());
            orcamento.setValorHora(input.getValorHora());
            orcamento.setQuantidadeHoras(input.getQuantidadeHoras());
            orcamento.setValorTotal(input.getValorTotal());

            // Chamar o serviço para atualizar o orçamento
            Orcamento updated = this.orcamentoService.update(orcamento);

            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (OrcamentoNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Orçamento não encontrado para atualização")).build();
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar orçamento: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar atualizar o orçamento")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long codigo) {
        try {
            this.orcamentoService.deleteById(codigo);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (OrcamentoNotFoundException e) {
            logger.warning("Orçamento não encontrado para exclusão: ID " + codigo);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Orçamento não encontrado para exclusão")).build();
        } catch (SQLException s) {
            logger.severe("Erro ao deletar orçamento: " + s.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar deletar o orçamento")).build();
        }
    }
}
