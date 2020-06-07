# ControlTareas

Prerequisitos

-Base de datos Oracle 11g o superior

-IDE (Neatbeans, IntelliJ o similares)

-Maben

Procedimiento para arrancar la aplicacion

1.- Ejecutar Script que se encuentran dentro de la carpeta BD en orden secuencial (en caso de ya tener creada la base de datos, Ejecutar solo script 2,3,4)

2.- Una vez descargado el proyecto en local, compilar en IntelliJ (Build ctrl+F9) y correr aplicacion.

3.- ingresar a Swagger a trtaves de la ruta local http://localhost:8083/swagger-ui.html (Aplicacion configurada para correr en puerto 8083 por defecto)

* Despues de correr la aplicacion y en caso de error por conflicto de ejecuccion  puerto 8080 realizar los siguientes comandos por consola CMD 

	-netstat -ao | find "8080"
	
	-ctrl+c
	
	-Taskkill /PID  nroTarea /F
	
	O tambien
	
	-netstat -ano | findstr :8080
	
	-tskill nroTarea

- Con esto ya realizado, volver a correr la base de datos con "StartDatabase" de oracle
- Una vez realizado esto correr nuevamente la aplicacion
