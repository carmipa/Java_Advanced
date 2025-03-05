package br.com.fiap.semestral.Controller;

import br.com.fiap.DTO.AgendaDto;
import br.com.fiap.exceptions.agendaException.AgendaNotFoundException;
import br.com.fiap.exceptions.agendaException.AgendaNotSavedException;
import br.com.fiap.exceptions.agendaException.AgendaUnsupportedServiceOperationExcept;
import br.com.fiap.model.Agenda;
import br.com.fiap.service.agendaService.AgendaService;
import br.com.fiap.service.agendaService.AgendaServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/agenda")
public class AgendaController {

    private final AgendaService agendaService = AgendaServiceFactory.create();
    private static final Logger logger = Logger.getLogger(AgendaController.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(AgendaDto input) {
        if (input.getCodigo() == null) {
            try {
                Agenda agenda = new Agenda();
                agenda.setDataAgendamento(input.getDataAgendamento());
                agenda.setObsAgenda(input.getObsAgenda());

                // Chamar o serviço para criar a agenda
                agenda = this.agendaService.create(agenda);

                return Response
                        .status(Response.Status.CREATED)
                        .entity(agenda)
                        .build();
            } catch (AgendaUnsupportedServiceOperationExcept e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("mensagem", e.getMessage())).build();
            } catch (SQLException | AgendaNotSavedException e) {
                logger.severe("Erro ao criar agenda: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "Erro inesperado ao tentar inserir uma agenda")).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", "Este método só permite a criação de novas agendas sem código")).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        List<Agenda> agendas = this.agendaService.findAll();
        if (agendas == null || agendas.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(agendas).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, AgendaDto input){
        try {
            Agenda agenda = new Agenda();
            agenda.setCodigo(id);
            agenda.setDataAgendamento(input.getDataAgendamento());
            agenda.setObsAgenda(input.getObsAgenda());

            // Chamar o serviço para atualizar a agenda
            Agenda updated = this.agendaService.update(agenda);

            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (AgendaNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Agenda não encontrada para atualização")).build();
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar agenda: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar atualizar a agenda")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long codigo){
        try {
            this.agendaService.deleteById(codigo);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (AgendaNotFoundException e) {
            logger.warning("Agenda não encontrada para exclusão: ID " + codigo);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Agenda não encontrada para exclusão")).build();
        } catch (SQLException s) {
            logger.severe("Erro ao deletar agenda: " + s.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar deletar a agenda")).build();
        }
    }
}
