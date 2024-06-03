# Jukebox Application

The Jukebox Application is a web-based music playlist management system. It allows users to create playlists, add songs to them, and manage their music collection. This project demonstrates a backend application built with Spring Boot and Spring Data JPA.

## Features

- **User Management**:
  - Create users with unique usernames.
  - Authenticate users using credentials.

- **Playlist Management**:
  - Create playlists and add songs to them.
  - Retrieve playlists by ID or for a specific user.
  - Delete playlists.

- **Song Management**:
  - Add songs to the database.
  - Retrieve all songs from the database.
  - Delete songs from the database.

## Technology Stack

- **Java 17**
- **Spring Boot** - For creating the RESTful API and managing dependencies.
- **Spring Data JPA** - For database access using Hibernate.
- **H2 Database** - In-memory database for development and testing.
- **Swagger** - For API documentation.

## Setup Instructions

To run the Jukebox Application locally, follow these steps:

1. **Clone the repository**:
   https://github.com/naveenerroju/TheJukeBox_API.git

2. **Build the application**:
mvn clean install
3. **Run the application**: Refer https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
4. **Open Postman and run the collection**
5. **Explore the application**

## Data Model

The following sections describe the entity classes and their relationships in the Jukebox system.

### Entities

#### UserEntity

- **Attributes**:
  - `id`: Long (Primary Key)
  - `username`: String (Unique, 4-10 characters)
  - `password`: String (4-10 characters)
  - `name`: String (3-20 characters)

- **Relationships**:
  - Has many `PlaylistsEntity`

#### PlaylistsEntity

- **Attributes**:
  - `id`: Long (Primary Key)
  - `title`: String
  - `userId`: Long (Foreign Key to UserEntity)

- **Relationships**:
  - Belongs to one `UserEntity`
  - Has many `SongsEntity` (Many-to-Many relationship via PLAYLIST_SONGS join table)

#### SongsEntity

- **Attributes**:
  - `id`: Long (Primary Key)
  - `title`: String (4-20 characters)
  - `owner`: String (4-20 characters)
  - `collaborators`: List of Strings (Optional)

- **Relationships**:
  - Belongs to many `PlaylistsEntity` (Many-to-Many relationship via PLAYLIST_SONGS join table)


### Description

- **UserEntity**: Represents users in the system. Each user can have multiple playlists.
- **PlaylistsEntity**: Represents a collection of songs grouped together by users. Each playlist is owned by one user and can contain multiple songs.
- **SongsEntity**: Represents individual songs. Songs can appear in multiple playlists and can have multiple collaborators.


4. **Access the application**:
   Open a web browser and go to `http://localhost:8080` to access the Swagger UI for API documentation.

## API Documentation

Swagger is integrated into the Jukebox Application to provide API documentation. You can view and interact with the API endpoints using Swagger UI. Here are the steps to access it:

1. Start the application as mentioned above.
2. Open a web browser and go to `http://localhost:8081/swagger-ui/index.html#/` to access the Swagger UI.

## Testing
1. This project includes a Postman collection. It is in test > resources. It is recommended to explore the postman collection.
2. Unit testing is implemented to test the portions of the code individually.

## Notes

- **Database**: The application uses an in-memory H2 database by default. You can configure it to use other databases like MySQL, PostgreSQL, etc., by modifying the `application.properties` file.
- **Testing**: The project includes unit tests that can be run using Maven.

## Usage

This project is provided under the following terms:

- You are free to use this code for personal use.
- You are not allowed to modify or distribute this code for commercial purposes without explicit permission.
- Any modifications to the code should be for personal use only and should not be distributed.

## License

This project is not licensed under an open-source license. It is provided as-is without any warranty.