# Proyecto 3 Modelado y Programación - Esquema de Secreto compartido de Shamir
Repositorio correspondiente al Proyecto 3 de Modelado y Programacion

---
### Contribuidores

-  Mario Letepichia Romero  (MarioLetepichia) 
-  Celic Aislinn Liahut Ley  (Aislinn-Liahut) 
-  Ivette González Mancera   (Ivette612)

---
(El pdf se encuentra en la carpeta resources)
## Instalaciones y prerequisitos:

1. **Instalar java (version 3+)**
Para verificarlo usa 
```bash
java -version
```

2. **Instalar maven**

Consulta el siguiente link : https://maven.apache.org/download.cgi

 

## Instrucciones para compilar, ejecutar  y obtener la salida:
1. Ya que realizaste los prerequisitos anteriores colocate en el directorio MP_Proyecto03:
```bash
cd MP_Proyecto03
```
2. teclea el siguiente comando que instalara las librerias usando maven:
```bash
mvn clean install
```

3. Ejecuta las siguiente linea de codigo para que se compilen todos los archivos java: 
```bash
mvn package
```
4.Ejecuta las siguiente linea de codigo para correr el programa: 
```bash
java -cp target/<.jar file> Main <options>
```

En el caso en el que quieras cifrar:
```bash
java -cp target/<.jar file> Main c nombreArchivoA n t nombreArchivoB
```
donde: 
nombreArchivoA , archivo donde se guardaran las n evaluaciones

n= numero de evaluaciones deseadas (n>=2)

t= El número mínimo de puntos necesarios para descifrar (1 < t ≤ n).

nombreArchivoB= Direccion del archivo a cifrar.

En el caso en el que quieras decifrar:
```bash
-cp target/<.jar file> Main d nombreArchivoA nombreArchivoB
```
nombreArchivoA= Direccion del archivo que contiene las t evaluaciones

nombreArchivoB= Direccion del archivo cifrado.
