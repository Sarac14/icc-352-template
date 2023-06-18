package org.example.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.Colecciones.Articulos;
import org.example.Colecciones.Comentario;
import org.example.entidades.Articulo;

import java.util.ArrayList;
import java.util.List;

public class ServicioArticulo extends GestionDb<Articulo> {
    //private static ServicioComentario servicioComentario = ServicioComentario.getInstancia();

    private static  ServicioArticulo instancia;
    private ServicioArticulo() {
        super(Articulo.class);
    }

    public static ServicioArticulo getInstancia(){
        if(instancia==null){
            instancia = new ServicioArticulo();
        }
        return instancia;
    }

    public List<Articulo> findAllByNombre(String titulo){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select a from Articulo a where a.titulo like :titulo");
        query.setParameter("titulo", titulo+"%");
        List<Articulo> lista = query.getResultList();
        return lista;
    }

    public void editarArticulo(Articulos articulo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(articulo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Articulo> consultaNativa(){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from Articulo ", Articulo.class);
        //query.setParameter("nombre", apellido+"%");
        List<Articulo> lista = query.getResultList();
        return lista;
    }

    //-----------------------FUNCIONES QUE SON USAN ORM-----------------------------------------------------
    private List<Articulos> ListaArticulos = new ArrayList<>();
    private Articulos artActual = null;

    public Articulos getArtActual() {
        return artActual;
    }

    public void setArtActual(Articulos artActual) {
        this.artActual = artActual;
    }

    /*private ServicioArticulo(){
        LocalDate date = LocalDate.now();
        Usuario autor = new Usuario();
        List<String> ListaEtiquetas = new ArrayList<>();
        ListaEtiquetas.add("Perro");
        ListaEtiquetas.add("Gato");

        List<String> ListaEtiquetas2 = new ArrayList<>();
        ListaEtiquetas2.add("Tanjiro");
        ListaEtiquetas2.add("Nezuko");


        autor = ServicioUsuario.buscarUsuarioPorUsername("sara"); //AQUI LO DEBO CAMBIAR POR EL USUARIO QUE ESTE LOGEADO
        ListaArticulos.add(new Articulos(1,"Ejemplo", "Mucha informacion importante",autor,date,ListaEtiquetas));

        Usuario autor2 = ServicioUsuario.buscarUsuarioPorUsername("admin"); //AQUI LO DEBO CAMBIAR POR EL USUARIO QUE ESTE LOGEADO
        Articulos art2 = new Articulos(2,"Domingo", "Final de Kimetsu",autor2,date,ListaEtiquetas2);
        ListaArticulos.add(art2);
         Comentario comentario = new Comentario(123,"Muy buen anime",autor2,art2);
        Comentario comentario2 = new Comentario(1234,"KK de anime",autor,art2);

        agregarComentario(art2, comentario);
        agregarComentario(art2, comentario2);



    }*/

    public List<Articulos> getListaArticulos() {
        return ListaArticulos;
    }

    public void setListaProductos(List<Articulos> ListaArticulos) {
        this.ListaArticulos = ListaArticulos;
    }

    //CREAR
    public Articulos crearArticulo(Articulos articulo) {

        Articulos tmp = getArticuloPorID(articulo.getId());
        if(tmp!=null) {
            System.out.println("Alerta: Articulo ya registrado");
            return null;
        }
        System.out.println("Alerta: Producto registrado correctamente");
        ListaArticulos.add(articulo);
        return articulo;
    }



    // ACTUALIZAR
    public Articulos actualizarArticulo(Articulos articulo) {
        Articulos tmp = getArticuloPorID(articulo.getId());
        if(tmp == null) {
            System.out.println("ERROR");
            return null;
        }
        System.out.println("Articulo actualizado correctamente");
        tmp.actualizar(articulo);
        return articulo;
    }

    // LISTAR
    public Articulos getArticuloPorID(long id) {
        return ListaArticulos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

    }




    public void agregarComentario(Articulos articulo, Comentario comentario) {
        if (articulo != null && comentario != null) {
            List<Comentario> listaComentarios = articulo.getListaComentarios();
            if (listaComentarios == null) {
                listaComentarios = new ArrayList<>();
                articulo.setListaComentarios(listaComentarios);
            }
            listaComentarios.add(comentario);
        } else {
            System.out.println("ERROR Articulo o comentario nulo");

        }
    }

    public Boolean autenticarArticulo(long id, String titulo, String cuerpo) {
        Articulos tmp = getArticuloPorID(id);
        if(titulo != null && cuerpo != null) {
            return true;
        }
        return false;
    }

    public boolean borrarComentario(long id, Articulos articulo) {
        Comentario tmp = ServicioComentario.getComentarioPorID(id);
        if(tmp==null) {
            System.out.println("ERROR");
            return false;
        }
        List<Comentario> listaComentarios = articulo.getListaComentarios();
        listaComentarios.remove(tmp);
        System.out.println("Comentario eliminado");
        return true;
    }

}



