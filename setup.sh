PORT="8080"
kill -9 $(lsof -ti ":$PORT") 2>/dev/null
git pull
./gradlew build
./gradlew run