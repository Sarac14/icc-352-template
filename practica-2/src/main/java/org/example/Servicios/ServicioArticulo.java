package org.example.Servicios;

import org.example.Colecciones.Articulos;
import org.example.Colecciones.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicioArticulo {

    private static  ServicioArticulo instancia;
    private List<Articulos> ListaArticulos = new ArrayList<>();

    private ServicioArticulo(){
        LocalDate date = LocalDate.now();
        Usuario autor = new Usuario();
        autor = ServicioUsuario.buscarUsuarioPorUsername("admin"); //AQUI LO DEBO CAMBIAR POR EL USUARIO QUE ESTE LOGEADO
        ListaArticulos.add(new Articulos(1,"Ejemplo", "Mucha informacion importante",autor,date));

    }

    public static ServicioArticulo getInstancia(){
        if(instancia==null){
            instancia = new ServicioArticulo();
        }
        return instancia;
    }

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

}
