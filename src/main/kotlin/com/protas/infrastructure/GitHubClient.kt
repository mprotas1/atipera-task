package com.protas.infrastructure

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.*
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@RegisterRestClient(baseUri = "https://api.github.com")
interface GitHubClient {
    @GET
    @Path("/users/{user}/repos")
    fun getUserRepositories(@PathParam("user") userLogin: String): Uni<List<UserRepositoryResult>>

    @GET
    @Path("/repos/{user}/{repo}/branches")
    fun getBranches(@PathParam("user") userLogin: String, @PathParam("repo") repositoryName: String): Uni<List<BranchResult>>
}