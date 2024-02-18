FROM node:18.18.0
WORKDIR /usr/programmed-household-ui
ENV PATH /programmed-household-ui/node_modules/.bin:$PATH
COPY ./package.json ./
RUN npm install
COPY ./ ./
CMD ["npm", "run","dev"]

# Image name as programmed-household-ui
# Build command: docker build -t programmed-household-ui .
# Run command: docker run -p 80:80 programmed-household-ui