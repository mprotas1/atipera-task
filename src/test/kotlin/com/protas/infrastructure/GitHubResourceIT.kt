package com.protas.infrastructure

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@QuarkusTest
class GitHubResourceIT {
    private val username = "mprotas1"

    @Test
    @DisplayName("Should return given user repositories")
    fun `should return user repositories`() {
        RestAssured.given()
            .`when`().get("/github/repositories/$username")
            .then()
            .statusCode(200)
            .headers("Content-Type", "application/json")
            .body("size()", greaterThan(0))
            .body("[0].name", not(emptyOrNullString()))
            .body("[0].ownerLogin", not(emptyOrNullString()))
            .body("[0].branches.size()", greaterThanOrEqualTo(0))
            .body("[0].branches.size()", greaterThan(0))
            .body("[0].branches[0].name", not(emptyOrNullString()))
            .body("[0].branches[0].lastCommitSha", not(emptyOrNullString()))
    }

}