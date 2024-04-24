# Usa una imagen base de Node.js para Angular
FROM node:14

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia los archivos necesarios para la instalación de Angular CLI
COPY package.json .
COPY package-lock.json .

# Instala las dependencias de Angular CLI y las dependencias de desarrollo
RUN npm install -g @angular/cli@10.1.4
RUN npm install

# Copia el resto de los archivos de tu aplicación
COPY . .

# Expone el puerto 4200 para el servidor de desarrollo de Angular
EXPOSE 4200

# Comando para iniciar el servidor de desarrollo de Angular
CMD ["npm", "start"]
