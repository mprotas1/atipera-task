package com.protas.application

import com.protas.infrastructure.GitHubClient
import com.protas.infrastructure.UserRepositoryResult
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.time.Duration

@ApplicationScoped
class GitHubService(@RestClient val gitHubClient: GitHubClient) : VersionControlSystemService {

    override fun getUserRepositories(userLogin: String): Uni<List<GitHubRepositoryDTO>> {
        return gitHubClient.getUserRepositories(userLogin)
            .onItem().transform { repositories ->
                repositories.map { repository ->
                    buildRepositoryDto(userLogin, repository)
                }
            }
    }

    private fun buildRepositoryDto(userLogin: String, repository: UserRepositoryResult): GitHubRepositoryDTO =
        GitHubRepositoryDTO(
            repository.name,
            userLogin,
            gitHubClient.getBranches(userLogin, repository.name).onItem().transform { branchResults ->
                branchResults.map { branchResult ->
                    GitHubBranchDTO(branchResult.name, branchResult.commit.sha)
                }
            }.await().atMost(Duration.ofSeconds(5))
        )

}