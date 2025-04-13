package br.com.fiap.Controller;

import br.com.fiap.DTO.OficinaDto;
import br.com.fiap.exceptions.oficinaException.OficinaNotFoundException;
import br.com.fiap.exceptions.oficinaException.OficinaNotSavedException;
import br.com.fiap.exceptions.oficinaException.OficinaUnsupportedServiceOperationExcept;
import br.com.fiap.model.Oficina;
import br.com.fiap.service.oficinaService.OficinaService;
import br.com.fiap.service.oficinaService.OficinaServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/oficina")
public class OficinaController {

    private final OficinaService oficinaService = OficinaServiceFactory.create();
    private static final Logger logger = Logger.getLogger(OficinaController.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(OficinaDto input) {
        if (input.getCodigo() == null) {
            try {
                Oficina oficina = new Oficina();
                oficina.setDataOficina(input.getDataOficina());
                oficina.setDescricaoProblema(input.getDescricaoProblema());
                oficina.setDiagnostico(input.getDiagnostico());
                oficina.setPartesAfetadas(input.getPartesAfetadas());
                oficina.setHorasTrabalhadas(input.getHorasTrabalhadas());

                // Chamar o serviço para criar a oficina
                oficina = this.oficinaService.create(oficina);

                return Response
                        .status(Response.Status.CREATED)
                        .entity(oficina)
                        .build();
            } catch (OficinaUnsupportedServiceOperationExcept e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("mensagem", e.getMessage())).build();
            } catch (SQLException | OficinaNotSavedException e) {
                logger.severe("Erro ao criar oficina: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "Erro inesperado ao tentar inserir uma oficina")).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", "Este método só permite a criação de novas oficinas sem código")).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Oficina> oficinas = this.oficinaService.findall();
        if (oficinas == null || oficinas.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(oficinas).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, OficinaDto input) {
        try {
            Oficina oficina = new Oficina();
            oficina.setCodigo(id);
            oficina.setDataOficina(input.getDataOficina());
            oficina.setDescricaoProblema(input.getDescricaoProblema());
            oficina.setDiagnostico(input.getDiagnostico());
            oficina.setPartesAfetadas(input.getPartesAfetadas());
            oficina.setHorasTrabalhadas(input.getHorasTrabalhadas());

            // Chamar o serviço para atualizar a oficina
            Oficina updated = this.oficinaService.update(oficina);

            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (OficinaNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Oficina não encontrada para atualização")).build();
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar oficina: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar atualizar a oficina")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long codigo) {
        try {
            this.oficinaService.deleteById(codigo);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (OficinaNotFoundException e) {
            logger.warning("Oficina não encontrada para exclusão: ID " + codigo);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Oficina não encontrada para exclusão")).build();
        } catch (SQLException s) {
            logger.severe("Erro ao deletar oficina: " + s.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar deletar a oficina")).build();
        }
    }
}
