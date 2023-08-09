package org.example.servicios;



import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;
import org.example.entidades.Agente;
import org.example.util.NoExisteAgenteException;
import org.example.util.TablasMongo;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ServicioAgente {
    private static ServicioAgente instancia;
    private MongoDbConexion mongoDbConexion;



    /**
     * Constructor privado.
     */
    private ServicioAgente(){
        //
        mongoDbConexion = MongoDbConexion.getInstance();
        mongoDbConexion.getBaseDatos1();

    }

    public static ServicioAgente getInstancia(){
        if(instancia==null){
            instancia = new ServicioAgente();
        }
        return instancia;
    }



    public List<Agente> listarAgente(){
        List<Agente> lista = new ArrayList<>();

        //
        MongoCollection<Document> agentes = mongoDbConexion.getBaseDatos1().getCollection(TablasMongo.agente.getValor());

        //Consultando todos los elementos.
        MongoCursor<Document> iterator = agentes.find().iterator();
        while (iterator.hasNext()){

            //obteniendo el documento
            Document next = iterator.next();

            //Encapsulando la información
            Agente agente = new Agente();
            agente.setId(next.getObjectId("_id").toHexString());
            agente.setPassword(next.getString("password"));
            agente.setNombre(next.getString("nombre"));
            agente.setUsuario(next.getString("usuario"));
            agente.setRol(next.getString("role"));

            // Agregando a la lista.
            lista.add(agente);
        }
        //retornando...
        return lista;
    }

    public Agente getAgentePorUsuario(String usuario){
        Agente agente = null;
        //Conexion a Mongo.
        MongoCollection<Document> estudiantes = mongoDbConexion.getBaseDatos1().getCollection(TablasMongo.agente.getValor());

        //
        Document filtro = new Document("usuario", usuario);
        Document first = estudiantes.find(filtro).first();

        //si no fue encontrado retorna null.
        if(first!=null){
            agente = new Agente();
            agente.setId(first.getObjectId("_id").toHexString());
            agente.setPassword(first.getString("password"));
            agente.setNombre(first.getString("nombre"));
            agente.setUsuario(first.getString("usuario"));
            agente.setRol(first.getString("role"));

            System.out.println("Consultado: "+agente.toString());
        }

        //retornando.
        return agente;
    }

    public Agente crearAgente(@NotNull Agente agente){
        if(getAgentePorUsuario(agente.getUsuario())!=null){
            System.out.println("Estudiante registrado...");
            return null; //generar una excepcion...
        }

        //
        Document document = new Document("usuario", agente.getUsuario())
                .append("nombre", agente.getNombre())
                .append("password", agente.getPassword())
                .append("role", agente.getRol());

        //
        MongoCollection<Document> agentes = mongoDbConexion.getBaseDatos1().getCollection(TablasMongo.agente.getValor());
        //
        InsertOneResult insertOneResult = agentes.insertOne(document);
        //
        System.out.println("Insertar: "+insertOneResult.getInsertedId()+", Acknowledged:"+insertOneResult.wasAcknowledged());

        return agente;
    }

    public Agente actualizarAgente(@NotNull Agente agente){
        Agente tmp = getAgentePorUsuario(agente.getUsuario());

        if(tmp == null){//no existe, no puede se actualizado
            throw new NoExisteAgenteException("No Existe el estudiante: "+agente.getUsuario());
        }

        //Actualizando Documento.
        MongoCollection<Document> agentes = mongoDbConexion.getBaseDatos1().getCollection(TablasMongo.agente.getValor());

        //
        Document filtro = new Document("_id", new ObjectId(agente.getId()));
        //
        //
        Document document = new Document("usuario", agente.getUsuario())
                .append("nombre", agente.getNombre())
                .append("password", agente.getPassword())
                .append("role",agente.getRol())
                .append("_id", new ObjectId(agente.getId()));
        //
        agentes.findOneAndUpdate(filtro, new Document("$set", document));

        return agente;
    }

    public boolean eliminandoAgente(String usuario){
        //
        Agente agentePorUsuario = getAgentePorUsuario(usuario);
        //Actualizando Documento.
        MongoCollection<Document> agentes = mongoDbConexion.getBaseDatos1().getCollection(TablasMongo.agente.getValor());
        //
        Document filtro = new Document("_id", new ObjectId(agentePorUsuario.getId()));
        //
        return agentes.findOneAndDelete(filtro) !=null;
    }

    public Agente getAgentePorId(String agenteId) {
        Agente agente = null;
        //Conexion a Mongo.
        MongoCollection<Document> estudiantes = mongoDbConexion.getBaseDatos1().getCollection(TablasMongo.agente.getValor());

        //
        Document filtro = new Document("agenteId", agenteId);
        Document first = estudiantes.find(filtro).first();

        //si no fue encontrado retorna null.
        if(first!=null){
            agente = new Agente();
            agente.setId(first.getObjectId("_id").toHexString());
            agente.setPassword(first.getString("password"));
            agente.setNombre(first.getString("nombre"));
            agente.setUsuario(first.getString("usuario"));
            agente.setRol(first.getString("role"));

            System.out.println("Consultado: "+agente.toString());
        }

        //retornando.
        return agente;
    }

    public Agente autenticarUsuario(String username, String password) {
        MongoCollection<Document> agentes = mongoDbConexion.getBaseDatos1().getCollection(TablasMongo.agente.getValor());

        Document usuarioDoc = agentes.find(new Document("usuario", username)).first();

        if (usuarioDoc != null) {
            String contrasenaAgente = usuarioDoc.getString("password");

            if (contrasenaAgente.equals(password)) {
                Agente usuario = new Agente();
                usuario.setUsuario(username);
                usuario.setPassword(password);
                return usuario;
            }
        }

        return null;
    }
}
