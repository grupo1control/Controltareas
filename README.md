# ControlTareas

UPDATE: API publicada en AWS: http://3.128.29.238:8000/swagger-ui.html

Prerrequisitos

-Base de datos Oracle 11g o superior

-IDE (Neatbeans, IntelliJ o similares)

-Maven

Procedimiento para arrancar la aplicación

1.- Ejecutar Script que se encuentran dentro de la carpeta BD en orden secuencial (en caso de ya tener creada la base de datos, Ejecutar solo script 2,3,4)

2.- Una vez descargado el proyecto en local, compilar y correr aplicación utilizando el comando:

mvnw clean install spring-boot:run

3.- Ingresar a Swagger a través de la ruta local http://localhost:8083/swagger-ui.html (Aplicación configurada para correr en puerto 8083 por defecto)

* Después de correr la aplicación y en caso de error por conflicto de ejecución puerto 8080 realizar los siguientes comandos por consola CMD 

	-netstat -ao | find "8080"
	
	-ctrl+c
	
	-Taskkill /PID  nroTarea /F
	
	O también
	
	-netstat -ano | findstr :8080
	
	-tskill nroTarea

- Con esto ya realizado, volver a correr la base de datos con "StartDatabase" de Oracle y correr la aplicación nuevamente
