# BUILD
FROM node:19 AS stage1
COPY . /front-app/
WORKDIR /front-app
RUN npm install
RUN npm run build
# RUN
FROM nginx:alpine AS stage2
COPY --from=stage1 /front-app/dist/ /usr/share/nginx/html/
