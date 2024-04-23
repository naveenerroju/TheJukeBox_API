# TheJukeBox
The Juke Box API

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


