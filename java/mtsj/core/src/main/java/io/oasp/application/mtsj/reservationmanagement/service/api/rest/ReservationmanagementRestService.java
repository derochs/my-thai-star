package io.oasp.application.mtsj.reservationmanagement.service.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.oasp.application.mtsj.reservationmanagement.logic.api.Reservationmanagement;
import io.oasp.application.mtsj.reservationmanagement.logic.api.to.ReservationEto;
import io.oasp.application.mtsj.reservationmanagement.logic.api.to.ReservationSearchCriteriaTo;
import io.oasp.application.mtsj.reservationmanagement.logic.api.to.ReservationTypeEto;
import io.oasp.application.mtsj.reservationmanagement.logic.api.to.ReservationTypeSearchCriteriaTo;
import io.oasp.application.mtsj.reservationmanagement.logic.api.to.TableEto;
import io.oasp.application.mtsj.reservationmanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * The service interface for REST calls in order to execute the logic of component {@link Reservationmanagement}.
 */
@Path("/reservationmanagement/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ReservationmanagementRestService {

  /**
   * Delegates to {@link Reservationmanagement#findTable}.
   *
   * @param id the ID of the {@link TableEto}
   * @return the {@link TableEto}
   */
  @GET
  @Path("/table/{id}/")
  public TableEto getTable(@PathParam("id") long id);

  /**
   * Delegates to {@link Reservationmanagement#saveTable}.
   *
   * @param table the {@link TableEto} to be saved
   * @return the recently created {@link TableEto}
   */
  @POST
  @Path("/table/")
  public TableEto saveTable(TableEto table);

  /**
   * Delegates to {@link Reservationmanagement#deleteTable}.
   *
   * @param id ID of the {@link TableEto} to be deleted
   */
  @DELETE
  @Path("/table/{id}/")
  public void deleteTable(@PathParam("id") long id);

  /**
   * Delegates to {@link Reservationmanagement#findTableEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding tables.
   * @return the {@link PaginatedListTo list} of matching {@link TableEto}s.
   */
  @Path("/table/search")
  @POST
  public PaginatedListTo<TableEto> findTablesByPost(TableSearchCriteriaTo searchCriteriaTo);

  /**
   * Delegates to {@link Reservationmanagement#findReservation}.
   *
   * @param id the ID of the {@link ReservationEto}
   * @return the {@link ReservationEto}
   */
  @GET
  @Path("/reservation/{id}/")
  public ReservationEto getReservation(@PathParam("id") long id);

  /**
   * Delegates to {@link Reservationmanagement#saveReservation}.
   *
   * @param reservation the {@link ReservationEto} to be saved
   * @return the recently created {@link ReservationEto}
   */
  @POST
  @Path("/reservation/")
  public ReservationEto saveReservation(ReservationEto reservation);

  /**
   * Delegates to {@link Reservationmanagement#deleteReservation}.
   *
   * @param id ID of the {@link ReservationEto} to be deleted
   */
  @DELETE
  @Path("/reservation/{id}/")
  public void deleteReservation(@PathParam("id") long id);

  /**
   * Delegates to {@link Reservationmanagement#findReservationEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding reservations.
   * @return the {@link PaginatedListTo list} of matching {@link ReservationEto}s.
   */
  @Path("/reservation/search")
  @POST
  public PaginatedListTo<ReservationEto> findReservationsByPost(ReservationSearchCriteriaTo searchCriteriaTo);

  /**
   * Delegates to {@link Reservationmanagement#findReservationType}.
   *
   * @param id the ID of the {@link ReservationTypeEto}
   * @return the {@link ReservationTypeEto}
   */
  @GET
  @Path("/reservationtype/{id}/")
  public ReservationTypeEto getReservationType(@PathParam("id") long id);

  /**
   * Delegates to {@link Reservationmanagement#saveReservationType}.
   *
   * @param reservationtype the {@link ReservationTypeEto} to be saved
   * @return the recently created {@link ReservationTypeEto}
   */
  @POST
  @Path("/reservationtype/")
  public ReservationTypeEto saveReservationType(ReservationTypeEto reservationtype);

  /**
   * Delegates to {@link Reservationmanagement#deleteReservationType}.
   *
   * @param id ID of the {@link ReservationTypeEto} to be deleted
   */
  @DELETE
  @Path("/reservationtype/{id}/")
  public void deleteReservationType(@PathParam("id") long id);

  /**
   * Delegates to {@link Reservationmanagement#findReservationTypeEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding reservationtypes.
   * @return the {@link PaginatedListTo list} of matching {@link ReservationTypeEto}s.
   */
  @Path("/reservationtype/search")
  @POST
  public PaginatedListTo<ReservationTypeEto> findReservationTypesByPost(
      ReservationTypeSearchCriteriaTo searchCriteriaTo);

}
