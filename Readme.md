# API Validadora de mutantes

* Se definen las configuraciones de uso dentro del archivo application.config.
* Se ejecuta la aplicación inicializando el MutanValidatorApplication.java.
* Se valida el funcionamiento en Motor MySQL. Se define como base principal "DNADB".

## Endpoints


### Validador de Mutante

| POST                     | Description                       |
|:----------------------------|:----------------------------------|
| `/mutant/`      | Devuelve el resultado de la verificacion del ADN recibido |


###### Request Body
    
    {
    "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
    }
    
###### Responses
* 200 - OK "Mutant"
* 403 - Forbidden "Human"



### Ratio Mutante/Humano

####
| GET                     | Description                       |
|:----------------------------|:----------------------------------|
| `/stats`| Devuelve los contadores correspondientes a la cantidad de mutantes, humanos y el ratio mutantes/humanos al momento de la solicitud |


###### Response body
    {
        "count_mutant_dna": "17",
        "count_human_dna": "6",
        "ratio" : "0.352941"
    }
    
###### Responses

* 200 - OK


### Consideraciones

*  Se utiliza una conexión Redis para el manejo de datos estadísticos externo a la base de datos con el fin de evitar la saturación de consultas manteniendo la posibilidad de utilizar el servicio en multiples instancias.
    
*  Se realiza al inicio del servicio una primera sincronización de datos desde base a hacia redis y posteriormente se actualiza la información en relación con cada nuevo registro insertado.
    
*  Se 

## Puntos a mejorar

1. Debido a inconvenientes el framework de relacion a base de datos, los datos para la sincronizacion inicial con redis (Resultado/Cantidad) 
    se consiguen mediante dos consultas separadas. Esto debe ser unificado a una sola consulta que arme el objeto para la sincronizacion.
    
2. A fin de mejorar la consistencia estadisitica, se debe crear un proceso de sincronizacion periodica 
    que evite desfases en tiempos prolongados o la necesidad de reiniciar una instancia de la API.
    
3. Se debe agregar una trazabilidad persistente sea en archivo o base de datos sobre el comportamiento de la API.

4. Se puede agregar la insercion de registros erroneos, hoy no contemplados en base de datos, con fin de establecer metricas que permitan monitorear la devolucion de resultados validos en todo momento.

5. Por motivos de tiempo e inconvenientes en la creacion de una instancia redis para testeo, deben ser agregadas las pruebas unitarias respectivas al servicio RedisSyncService.
