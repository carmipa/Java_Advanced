package br.com.fiap.semestral.Controller;

import br.com.fiap.DTO.PagamentoDto;
import br.com.fiap.exceptions.pagamentoException.PagamentoNotFoundException;
import br.com.fiap.exceptions.pagamentoException.PagamentoNotSavedException;
import br.com.fiap.exceptions.pagamentoException.PagamentoUnsupportedServiceOperationException;
import br.com.fiap.model.Pagamento;
import br.com.fiap.service.pagamentoService.PagamentoService;
import br.com.fiap.service.pagamentoService.PagamentoServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService = PagamentoServiceFactory.create();
    private static final Logger logger = Logger.getLogger(PagamentoController.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(PagamentoDto input) {
        if (input.getCodigo() == null) {
            try {
                Pagamento pagamento = new Pagamento();
                pagamento.setDataPagamento(input.getDataPagamento());
                pagamento.setTipoPagamento(input.getTipoPagamento());
                pagamento.setDesconto(input.getDesconto());
                pagamento.setParcelamento(input.getParcelamento());
                pagamento.setValorParcelas(input.getValorParcelas());
                pagamento.setTotalComDesconto(input.getTotalComDesconto());

                // Chamar o serviço para criar o pagamento
                pagamento = this.pagamentoService.create(pagamento);

                return Response
                        .status(Response.Status.CREATED)
                        .entity(pagamento)
                        .build();
            } catch (PagamentoUnsupportedServiceOperationException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("mensagem", e.getMessage())).build();
            } catch (SQLException | PagamentoNotSavedException e) {
                logger.severe("Erro ao criar pagamento: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "Erro inesperado ao tentar inserir um pagamento")).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", "Este método só permite a criação de novos pagamentos sem código")).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Pagamento> pagamentos = this.pagamentoService.findall();
        if (pagamentos == null || pagamentos.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(pagamentos).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, PagamentoDto input) {
        try {
            Pagamento pagamento = new Pagamento();
            pagamento.setCodigo(id);
            pagamento.setDataPagamento(input.getDataPagamento());
            pagamento.setTipoPagamento(input.getTipoPagamento());
            pagamento.setDesconto(input.getDesconto());
            pagamento.setParcelamento(input.getParcelamento());
            pagamento.setValorParcelas(input.getValorParcelas());
            pagamento.setTotalComDesconto(input.getTotalComDesconto());

            // Chamar o serviço para atualizar o pagamento
            Pagamento updated = this.pagamentoService.update(pagamento);

            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (PagamentoNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Pagamento não encontrado para atualização")).build();
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar pagamento: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar atualizar o pagamento")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long codigo) {
        try {
            this.pagamentoService.deleteById(codigo);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (PagamentoNotFoundException e) {
            logger.warning("Pagamento não encontrado para exclusão: ID " + codigo);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Pagamento não encontrado para exclusão")).build();
        } catch (SQLException s) {
            logger.severe("Erro ao deletar pagamento: " + s.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar deletar o pagamento")).build();
        }
    }
}
