# --- Stage 1: Build the React application ---
# We use a Node.js image to build our frontend assets.
# We name this stage "build".
FROM node:18-alpine as build

WORKDIR /app

# Copy package.json and package-lock.json to leverage Docker's layer caching.
COPY package*.json ./

# Install all the npm dependencies.
RUN npm install

# Copy the rest of the application source code.
COPY . .

# Run the build script to create a production-ready build in the /app/build directory.
RUN npm run build

# --- Stage 2: Serve the application from Nginx ---
# Nginx is a very lightweight and high-performance web server.
FROM nginx:1.23-alpine

# Copy the static files from the "build" stage into the Nginx public directory.
COPY --from=build /app/build /usr/share/nginx/html

# Expose port 80, which is the default port for Nginx.
EXPOSE 80

# The command to start the Nginx server in the foreground.
CMD ["nginx", "-g", "daemon off;"]