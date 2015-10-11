package ro.mve.rest.resource;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ro.mve.rest.exception.ApiException;
import ro.mve.rest.model.ApiResponse;

@Provider
public class SampleExceptionMapper implements ExceptionMapper<Exception> {
  public Response toResponse(Exception exception) {
    if (exception instanceof javax.ws.rs.WebApplicationException) {
      javax.ws.rs.WebApplicationException e = (javax.ws.rs.WebApplicationException) exception;
      return Response
          .status(e.getResponse().getStatus())
          .entity(new ApiResponse(e.getResponse().getStatus(),
              exception.getMessage())).type("application/json").build();
    } else if (exception instanceof com.fasterxml.jackson.core.JsonParseException) {
      return Response.status(400)
          .entity(new ApiResponse(400, "bad input")).build();
    } else if (exception instanceof NotFoundException) {
      return Response
          .status(Status.NOT_FOUND)
          .entity(new ApiResponse(ApiResponse.ERROR, exception
              .getMessage())).type("application/json").build();
    } else if (exception instanceof BadRequestException) {
      return Response
          .status(Status.BAD_REQUEST)
          .entity(new ApiResponse(ApiResponse.ERROR, exception
              .getMessage())).type("application/json").build();
    } else if (exception instanceof ApiException) {
      return Response
          .status(Status.BAD_REQUEST)
          .entity(new ApiResponse(ApiResponse.ERROR, exception
              .getMessage())).type("application/json").build();
    } else {
      return Response.status(500)
          .entity(new ApiResponse(500, "something bad happened"))
          .type("application/json")
          .build();
    }
  }
}