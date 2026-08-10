# Wikipedia Movie API

A high-performance Ktor REST API providing structured movie data scraped from Wikipedia.

## Features

- **Random Movie Endpoint**: Fetch a random movie filtered by year and/or category.
- **Metadata Endpoints**: Query available years, categories, and genres.
- **Configurable Data Source**: Load data from bundled classpath resources or an external file path (`MOVIES_DATA_PATH`).
- **Standardized Content Negotiation**: Clean JSON responses using `kotlinx.serialization`.
- **Docker Support**: Containerized build with JDK 21 LTS and lightweight Alpine runtime.

## API Endpoints

### 1. Get Random Movie
- **URL**: `/api/v1/movies/random`
- **Method**: `GET`
- **Query Parameters**:
  - `year` (optional): Comma-separated list of release years (e.g. `?year=1990,2000`)
  - `category` (optional): Comma-separated list of movie categories (e.g. `?category=american,british`)
- **Responses**:
  - `200 OK`: Returns a single random movie object.
    ```json
    {
      "title": "The Enchanted Drawing",
      "year": 1900,
      "category": "american",
      "director": "J. Stuart Blackton",
      "genre": ["short"],
      "cast": "J. Stuart Blackton"
    }
    ```
  - `404 Not Found`: Returned when no movies match the provided filters.
    ```json
    {
      "error": "No movies found matching criteria"
    }
    ```

### 2. Get Available Years
- **URL**: `/api/v1/years`
- **Method**: `GET`
- **Response**: `200 OK` (JSON array of sorted distinct years)

### 3. Get Available Categories
- **URL**: `/api/v1/categories`
- **Method**: `GET`
- **Response**: `200 OK` (JSON array of sorted distinct category names)

### 4. Get Available Genres
- **URL**: `/api/v1/genres`
- **Method**: `GET`
- **Response**: `200 OK` (JSON array of sorted distinct genre names)

*(Note: Legacy `/api/alpha/...` routes remain supported but return a deprecation header.)*

## Configuration

| Environment Variable | Default | Description |
|---|---|---|
| `PORT` | `8080` | Port for Netty server to listen on. |
| `MOVIES_DATA_PATH` | Null (Classpath `/api.json`) | Optional path to an external JSON movie dataset file. |

## Building & Running

### Local Development
```bash
# Run tests
./gradlew test

# Run application locally
./gradlew run
```

### Docker
```bash
# Build fat JAR
./gradlew buildFatJar

# Build Docker image
docker build -t wikipedia-movie-api .

# Run container
docker run -p 8080:8080 wikipedia-movie-api
```
