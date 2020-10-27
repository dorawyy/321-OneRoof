FROM node:12

WORKDIR /usr/src/app

COPY package*.json ./
RUN npm install
COPY . .
RUN \
        node node_modules/knex/bin/cli.js migrate:latest && \
        node node_modules/knex/bin/cli.js seed:run
COPY secrets .
ENV GOOGLE_APPLICATION_CREDENTIALS=secrets/oneroof-20f41-firebase-adminsdk-up001-48645a9bc3.json
EXPOSE 3000
CMD [ "node", "bin/www" ]
