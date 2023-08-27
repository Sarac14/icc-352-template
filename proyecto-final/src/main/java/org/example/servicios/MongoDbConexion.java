package org.example.servicios;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDbConexion {

    private static MongoDbConexion instance;
    private MongoClient mongoClient;
    private String DB_NOMBRE;

    private MongoDbConexion(){

    }

    public static MongoDbConexion getInstance(){
        if(instance == null){
            instance = new MongoDbConexion();
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public MongoDatabase getBaseDatos1(){

        if(mongoClient==null) {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String URL_MONGODB = "mongodb+srv://hrsp0001:NkpsDmMq1G8aFqXM@cluster0.d8u7pkk.mongodb.net/";
            DB_NOMBRE = "ProyectoFinal";
            mongoClient = MongoClients.create(URL_MONGODB);
        }

        //Retomando la conexión
        MongoDatabase database = mongoClient.getDatabase(DB_NOMBRE);
        database.runCommand(new Document("ping", 1));
        System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

        //
        return database;
    }

    /*public MongoDatabase getBaseDatosForm(){

        if(mongoClient==null) {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String URL_MONGODB = processBuilder.environment().get("mongodb+srv://smct0001:GM5CLLRlyZ9KAfOy@cluster0.gfgkzyd.mongodb.net/");
            DB_NOMBRE = processBuilder.environment().get("crudAgente");
            mongoClient = MongoClients.create(URL_MONGODB);
        }

        //Retomando la conexión
        MongoDatabase database = mongoClient.getDatabase(DB_NOMBRE);
        database.runCommand(new Document("ping", 2));
        System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

        //
        return database;
    }*/

    /**
     *
     */
    public void cerrar(){
        mongoClient.close();
        mongoClient = null;
    }

}
