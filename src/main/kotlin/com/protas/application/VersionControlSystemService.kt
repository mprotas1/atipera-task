package com.protas.application

import io.smallrye.mutiny.Uni

interface VersionControlSystemService {
    fun getUserRepositories(userLogin : String) : Uni<List<GitHubRepositoryDTO>>
}