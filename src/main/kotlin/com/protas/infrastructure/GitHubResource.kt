package com.protas.infrastructure

import com.protas.application.VersionControlSystemService
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.jboss.resteasy.client.exception.ResteasyNotFoundException

@Path("/github")
class GitHubResource(private val versionControlSystemService: VersionControlSystemService) {

    @GET
    @Path("/repositories/{userLogin}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserRepositories(@PathParam("userLogin") userLogin: String) : Uni<Response> {
        return versionControlSystemService.getUserRepositories(userLogin)
            .map { repositories -> Response.ok(repositories).build() }
            .onFailure().recoverWithItem { throwable ->
                determineErrorResponse(throwable, userLogin)
            }
    }

    private fun determineErrorResponse(throwable: Throwable?, userLogin: String): Response? =
        when (throwable) {
            is ResteasyNotFoundException -> Response.status(404)
                .entity(ErrorResponse(404, "No repositories found for: $userLogin"))
                .build()

            else -> Response.status(500)
                .entity(ErrorResponse(500, "Internal server error"))
                .build()
        }

}