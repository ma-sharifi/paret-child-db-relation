FROM node:20-alpine
RUN npm install -g @usebruno/cli && apk add --no-cache curl
WORKDIR /collection
COPY . .
RUN chmod +x entrypoint.sh
ENV BASE_URL=http://app:8080
ENTRYPOINT ["sh", "entrypoint.sh"]
