package org.example.grpc;

import formulariorn.ServicioCreacionGrpc;
import formulariorn.ServicioListadoGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.Scanner;

public class javaServidor {
        public static void main(String[] args) throws IOException, InterruptedException{
            Server server = ServerBuilder.forPort(9090)
                    .addService(new ListadoService())
                    .addService(new CreacionService())
                    .build();
            server.start();
            System.out.println("Servidor iniciado en el puerto " + server.getPort());

            javaCliente.main(new String[] {null});
            server.awaitTermination();




        }

}
