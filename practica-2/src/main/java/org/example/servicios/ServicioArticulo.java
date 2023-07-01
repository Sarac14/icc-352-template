package org.example.servicios;

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
        List<String> ListaEtiquetas = new ArrayList<>();
        ListaEtiquetas.add("Perro");
        ListaEtiquetas.add("Gato");

        List<String> ListaEtiquetas2 = new ArrayList<>();
        ListaEtiquetas2.add("ensalada");
        ListaEtiquetas2.add("pollo");



        autor = ServicioUsuario.buscarUsuarioPorUsername("admin"); //AQUI LO DEBO CAMBIAR POR EL USUARIO QUE ESTE LOGEADO
        Articulos art1 = new Articulos(1,"Ejemplo", "Mucha informacion importante",autor,date);
        ListaArticulos.add(art1);
        Articulos art2 = new Articulos(2,"COMIDA", "Computadora, computador u ordenador1 es una máquina electrónica digital programable" +
                " que ejecuta una serie de comandos para procesar los datos de entrada, obteniendo convenientemente información que posteriormente se " +
                "envía a las unidades de salida. Una computadora está compuesta por numerosos y diversos circuitos integrados y varios elementos de apoyo, " +
                "extensión y accesorios, que en conjunto pueden ejecutar tareas diversas con suma rapidez y bajo el control de un programa (software) " +
                "La constituyen dos partes esenciales, el hardware, que es su estructura física (circuitos electrónicos, cables, gabinete, teclado, ratón, etc.), " +
                "y el software, que es su parte intangible (programas, datos, información, documentación, etc) Desde el punto de vista funcional es una máquina que posee, " +
                "al menos, una unidad central de procesamiento (CPU), una unidad de memoria y otra de entrada/salida (periférico). Los periféricos de " +
                "entrada permiten el ingreso de datos, la CPU se encarga de su procesamiento (operaciones aritmético-lógicas) y los dispositivos de salida los comunican a los " +
                "medios externos. Es así, que la computadora recibe datos, los procesa y emite la información resultante, la que luego puede ser interpretada, " +
                "almacenada, transmitida a otra máquina o dispositivo o sencillamente impresa; todo ello a criterio de un operador o usuario y bajo el control de un " +
                "programa de computación.",autor,date);
        ListaArticulos.add(art2);
        art1.setListaEtiqueta(ListaEtiquetas);
        art2.setListaEtiqueta(ListaEtiquetas2);



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

    public boolean autenticarArticulo(long nuevoId, String titulo, String cuerpo) {
        if (titulo != null && !titulo.isEmpty() && cuerpo != null && !cuerpo.isEmpty()) {
            return true;
        }
        return false;
    }
}
