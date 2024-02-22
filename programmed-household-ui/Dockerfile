# Stage 1: Build the React app
FROM node:18.18.0 AS build
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Serve the React app using Nginx
FROM nginx:latest AS ph-nginx-img
COPY --from=build /usr/src/app/dist /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]