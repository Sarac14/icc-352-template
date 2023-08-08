package org.example.servicios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
//import org.example.entidades.Agente;
import org.example.entidades.Agente;
import org.example.entidades.Formulario;
import org.example.util.NoExisteAgenteException;
import org.example.util.TablasMongo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ServicioForm {
    private static ServicioForm instancia;
    private static ServicioAgente agenteService = ServicioAgente.getInstancia();

    private MongoDbConexion mongoDbConexion;


    /**
     * Constructor privado.
     */
    private ServicioForm(){
        //
        mongoDbConexion = MongoDbConexion.getInstance();
        mongoDbConexion.getBaseDatosForm();

    }

    public static ServicioForm getInstancia(){
        if(instancia==null){
            instancia = new ServicioForm();
        }
        return instancia;
    }



    public List<Formulario> listarFormulario(){
        List<Formulario> lista = new ArrayList<>();

        //
        MongoCollection<Document> formularios = mongoDbConexion.getBaseDatosForm().getCollection(TablasMongo.formulario.getValor());

        //Consultando todos los elementos.
        MongoCursor<Document> iterator = formularios.find().iterator();
        while (iterator.hasNext()){

            //obteniendo el documento
            Document next = iterator.next();

            //Encapsulando la información
            Formulario formulario = new Formulario();
            formulario.setId(next.getObjectId("_id").toHexString());
            formulario.setSector(next.getString("sector"));
            formulario.setNombre(next.getString("nombre"));
            formulario.setNivelEscolar(next.getString("nivelEscolar"));
            formulario.setLatitud(next.getString("latitud"));
            formulario.setLongitud(next.getString("longitud"));
            //formulario.setAgente(next.getString("agente"));
            String agenteId = next.getString("agente");

            Agente agente = agenteService.getAgentePorId(agenteId);
            formulario.setAgente(agente);



            // Agregando a la lista.
            lista.add(formulario);
        }
        //retornando...
        return lista;
    }

    public Formulario getFormPorId(String id){
        Formulario formulario = null;
        //Conexion a Mongo.
        MongoCollection<Document> formularios = mongoDbConexion.getBaseDatosForm().getCollection(TablasMongo.formulario.getValor());

        //
        Document filtro = new Document("_id", id);
        Document first = formularios.find(filtro).first();

        //si no fue encontrado retorna null.
        if(first!=null){
            formulario = new Formulario();
            formulario.setId(first.getObjectId("_id").toHexString());
            formulario.setSector(first.getString("sector"));
            formulario.setNombre(first.getString("nombre"));
            formulario.setNivelEscolar(first.getString("nivelEscolar"));
            formulario.setLongitud(first.getString("longitud"));
            formulario.setLatitud(first.getString("latitud"));
            formulario.setLatitud(first.getString("agente"));



            System.out.println("Consultado: "+formulario.toString());
        }

        //retornando.
        return formulario;
    }

    public Formulario crearForm (@NotNull Formulario formulario){
        if(getFormPorId(formulario.getId())!=null){
            System.out.println("Formulario registrado...");
            return null; //generar una excepcion...
        }

        //
        Document document = new Document("sector", formulario.getSector())
                .append("nombre", formulario.getNombre())
                .append("nivelEscolar", formulario.getNivelEscolar())
                .append("agente", formulario.getAgente().getId())
                .append("longitud",formulario.getLongitud())
                .append("latitud",formulario.getLatitud());

        //
        MongoCollection<Document> formularios = mongoDbConexion.getBaseDatosForm().getCollection(TablasMongo.formulario.getValor());
        //
        InsertOneResult insertOneResult = formularios.insertOne(document);
        //
        System.out.println("Insertar: "+insertOneResult.getInsertedId()+", Acknowledged:"+insertOneResult.wasAcknowledged());

        return formulario;
    }

    public Formulario acrutalizarForm (@NotNull Formulario formulario){
        Formulario tmp = getFormPorId(formulario.getId());

        if(tmp == null){//no existe, no puede se actualizado
            throw new NoExisteAgenteException("No Existe el formulario: "+formulario.getId());
        }

        //Actualizando Documento.
        MongoCollection<Document> formularios = mongoDbConexion.getBaseDatosForm().getCollection(TablasMongo.formulario.getValor());

        //
        Document filtro = new Document("_id", new ObjectId(formulario.getId()));
        //
        //
        Document document = new Document("agente", formulario.getAgente().getId())
                .append("nombre", formulario.getNombre())
                .append("sector", formulario.getSector())
                .append("nivelEscolar",formulario.getNivelEscolar())
                .append("longitud",formulario.getLongitud())
                .append("latitud",formulario.getLatitud())
                .append("_id", new ObjectId(formulario.getId()));
        //
        formularios.findOneAndUpdate(filtro, new Document("$set", document));

        return formulario;
    }

    public boolean elimanandoForm(String id){
        //
        Formulario formPorId = getFormPorId(id);
        //Actualizando Documento.
        MongoCollection<Document> formularios = mongoDbConexion.getBaseDatosForm().getCollection(TablasMongo.formulario.getValor());
        //
        Document filtro = new Document("_id", new ObjectId(formPorId.getId()));
        //
        return formularios.findOneAndDelete(filtro) !=null;
    }
}

