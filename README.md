# GitHub Repositories Service

This project is a Kotlin-based service that interacts with the GitHub API to fetch user repositories (which are not forks) and their branches. 
It uses the following technologies:

- Kotlin
- Quarkus
- Maven
- SmallRye Mutiny

## Project Structure

- `src/main/kotlin/com/protas/application/`
  - `GitHubService.kt`: Implements the `VersionControlSystemService` interface to fetch user repositories and their branches.
  - `VersionControlSystemService.kt`: Interface defining the contract for fetching user repositories.
- `src/main/kotlin/com/protas/infrastructure/`
  - `GitHubResource.kt`: REST resource to expose endpoints for fetching user repositories.

## Prerequisites

- JDK 17 or higher
- Maven 3.8 or higher

## API reference

#### Find all user's repositories that are not forks

```http
  GET /github/repositories/{userLogin}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userLogin` | `string` | **Required** - Name of the GitHub user |

Example response from the API:
```
[
    {
        "name": "repository",
        "ownerLogin": "owner",
        "branches": [
            {
                "name": "master",
                "lastCommitSha": "566e24f4901a72cb427627e159bba68e14bffca8"
            }
        ]
    },
]
```

## Error handling
The API returns standard http status codes:
* `200 OK` - when operations is performed successfully
* `403 NOT FOUND` - when user given by `userLogin` is not found in the GitHub service
* `500 INTERNAL` - when other exception occurs

Example error response:
```
{
    “status”: ${responseCode}
    “message”: ${whyHasItHappened}
}
```

## Building the Project

To build the project in **development mode**, run the following command:

```sh
./mvnw quarkus:dev
```

The application can be packaged using:

```sh
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using:
```sh
java -jar target/quarkus-app/quarkus-run.jar
```

If you want to build an _über-jar_, execute the following command:
```sh
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using: 
``` sh
java -jar target/*-runner.jar
```

