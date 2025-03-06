package br.com.fiap.Controller;

import br.com.fiap.DTO.VeiculoDto;
import br.com.fiap.exceptions.veiculoException.VeiculoNotFoundException;
import br.com.fiap.exceptions.veiculoException.VeiculoNotSavedException;
import br.com.fiap.exceptions.veiculoException.VeiculoUnsupportedServiceOperationException;
import br.com.fiap.model.Veiculo;
import br.com.fiap.service.veiculoService.VeiculoService;
import br.com.fiap.service.veiculoService.VeiculoServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/veiculo")
public class VeiculoController {

    private final VeiculoService veiculoService = VeiculoServiceFactory.create();
    private static final Logger logger = Logger.getLogger(VeiculoController.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(VeiculoDto input) {
        if (input.getCodigo() == null) {
            try {
                Veiculo veiculo = new Veiculo();
                // Set properties from input...

                veiculo = this.veiculoService.create(veiculo);

                return Response
                        .status(Response.Status.CREATED)
                        .entity(veiculo)
                        .build();
            } catch (VeiculoUnsupportedServiceOperationException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("mensagem", e.getMessage())).build();
            } catch (SQLException | VeiculoNotSavedException e) {
                logger.severe("Erro ao criar veículo: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "Erro inesperado ao tentar inserir um veículo")).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", "Este método só permite a criação de novos veículos sem código")).build();
        }
    }


    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Veiculo> veiculos = this.veiculoService.findall();
        if (veiculos == null || veiculos.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(veiculos).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, VeiculoDto input) {
        try {
            Veiculo veiculo = new Veiculo();
            veiculo.setCodigo(id);
            veiculo.setTipoVeiculo(input.getTipoVeiculo());
            veiculo.setRenavam(input.getRenavam());
            veiculo.setPlaca(input.getPlaca());
            veiculo.setProprietario(input.getProprietario());
            veiculo.setModelo(input.getModelo());
            veiculo.setCor(input.getCor());
            veiculo.setMontadora(input.getMontadora());
            veiculo.setMotor(input.getMotor());
            veiculo.setAnofabricacao(input.getAnofabricacao());

            // Chamar o serviço para atualizar o veículo
            Veiculo updated = this.veiculoService.update(veiculo);

            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (VeiculoNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Veículo não encontrado para atualização")).build();
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar veículo: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar atualizar o veículo")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long codigo) {
        try {
            this.veiculoService.deleteById(codigo);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (VeiculoNotFoundException e) {
            logger.warning("Veículo não encontrado para exclusão: ID " + codigo);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Veículo não encontrado para exclusão")).build();
        } catch (SQLException e) {
            logger.severe("Erro ao deletar veículo: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar deletar o veículo")).build();
        }
    }
}
