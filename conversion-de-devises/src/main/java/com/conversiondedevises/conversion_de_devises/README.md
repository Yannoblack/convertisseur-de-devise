# API de Conversion de Devises

## Description

Cette API permet de convertir un montant d'une devise source vers une devise cible en utilisant les taux de change récupérés en temps réel depuis [Exchangerate-API](https://www.exchangerate-api.com/).

## Prérequis

- Java 21 (ou la version que vous utilisez)
- Maven
- Une clé API valide de [Exchangerate-API](https://www.exchangerate-api.com/signup)

## Configuration

1.  Clonez ce dépôt.
2.  Créez un fichier `application.properties` dans le répertoire `src/main/resources/`.
3.  Ajoutez les propriétés suivantes dans `src/main/resources/application.properties` en remplaçant `VOTRE_CLE_API_EXCHANGERATE` par votre clé API réelle :


L'application démarrera par défaut sur le port 8080.Utilisation de l'APIEndpoint de Conversion•URL : /convert•Méthode : POST•Description : Convertit un montant d'une devise à une autre.RequêteHeaders :•Content-Type: application/jsonCorps (JSON) :Kotlin## Lancement de l'application

Vous pouvez lancer l'application en utilisant Maven :•deviseSource (String, requis) : Code ISO de la devise source (ex: "USD", "EUR", "XAF").•deviseCible (String, requis) : Code ISO de la devise cible.•montant (BigDecimal, requis) : Montant à convertir.RéponseSuccès (200 OK) :KotlinL'application démarrera par défaut sur le port `8080`.

## Utilisation de l'API

### Endpoint de Conversion

-   **URL :** `/convert`
-   **Méthode :** `POST`
-   **Description :** Convertit un montant d'une devise à une autre.

#### Requête

**Headers :**
-   `Content-Type: application/json`

**Corps (JSON) :**•montantConverti (BigDecimal) : Le montant après conversion.•tauxDeChangeUtilise (BigDecimal) : Le taux de change qui a été appliqué pour la conversion.Erreur (ex: 4xx, 5xx) :La réponse d'erreur contiendra des détails sur le problème rencontré.Kotlin-   `deviseSource` (String, requis) : Code ISO de la devise source (ex: "USD", "EUR", "XAF").
-   `deviseCible` (String, requis) : Code ISO de la devise cible.
-   `montant` (BigDecimal, requis) : Montant à convertir.

#### Réponse

**Succès (200 OK) :**Documentation SwaggerUne fois l'application lancée, la documentation Swagger UI est disponible à l'adresse suivante : http://localhost:8080/swagger-ui.htmlVous pouvez utiliser Swagger UI pour tester interactivement l'API.Technologies utilisées•Spring Boot•Spring WebFlux (pour WebClient)•Maven•Exchangerate-API (pour les taux de change)•Swagger/OpenAPI (pour la documentation d'API)



conversion-controller


POST
/convert

Parameters
Try it out
Reset
No parameters

Request body

application/json
Example Value
Schema
{
  "deviseSource": "ZAR",
  "deviseCible": "USD",
  "montant": 50000
}



Responses
Curl

curl -X 'POST' \
  'http://localhost:8080/convert' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "deviseSource": "ZAR",
  "deviseCible": "USD",
  "montant": 50000
}'

Request URL
http://localhost:8080/convert
Server response
Code	Details
200	
Response body
Download
{
  "montantConverti": 2823.5,

  "tauxDeChangeUtilise": 0.05647
}
Response headers
 connection: keep-alive 

 content-type: application/json 

 date: Thu,12 Jun 2025 15:47:50 GMT 

 keep-alive: timeout=60 

 transfer-encoding: chunked 


Schemas

ConversionRequest{

deviseSource	string

deviseCible	string

montant	number
}

ConversionResponse{

montantConverti	number

tauxDeChangeUtilise	number

}