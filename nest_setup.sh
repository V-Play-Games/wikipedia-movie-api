# Get port number
PORT=$(nest get_port | tr -d -c 0-9)

# Stop current running gradle processes
./gradlew --stop

# Reset Caddy Proxy
nest caddy rm movie.vaibhavgt0.hackclub.app
nest caddy add movie.vaibhavgt0.hackclub.app --proxy localhost:$PORT

# Create a new server
bash setup.sh $PORT
