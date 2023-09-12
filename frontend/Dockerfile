# Stage 1: Build the Angular application
FROM node:18 AS build

# Set working directory.
WORKDIR /app

# Copy package.json and package-lock.json before other files
# Utilize Docker cache to save re-installing dependencies if unchanged
COPY package*.json ./

# Install Angular CLI and node modules.
RUN npm install -g @angular/cli && npm install

# Copy all files from current directory to default docker image directory.
COPY . .

# Build the application
RUN ng build --configuration production

# Stage 2: Serve the Angular app with nginx
FROM nginx:1.19.0-alpine

# Copy the build output to replace the default nginx contents.
COPY --from=build /app/dist/frontend /usr/share/nginx/html

# Expose port 80.
EXPOSE 80

# Run nginx.
CMD ["nginx", "-g", "daemon off;"]
