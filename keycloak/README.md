# Keycloak

## Keycloak on Docker

A Keycloak Docker container can be started with the following command: 

    docker run -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=password -p 8080:8080 jboss/keycloak

This will start the Keycloak container on port 8080 and additionally create an admin user called "admin" and a password simply called "password".
After starting the container you can access the interface on localhost:8080 at "Administration Console" and login as admin user.

## Importing Realm and User Data

You'll be greeted with an "empty" Keycloak. We can fill it up with existing data by importing a new "Realm" which consists of several environmental settings and includes some pre-made user data entries.

To import our MTS realm, hover over the top left part of the admin console that says "Master" and click on "Add realm". Afterwards, click on "import" and select the "mts_realm.json" file.
This will import the Realm settings, two roles:

    - waiter
    - manager

And three users:

    - waiter:waiter (role: waiter)
    - manager:manager (role: manager)
    - user:user (no role)