package com.protas.application

data class GitHubRepositoryDTO(
    val name : String,
    val ownerLogin : String,
    val branches : List<GitHubBranchDTO>
)