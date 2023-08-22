package formulariorn;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 *a
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: FormularioRn.proto")
public final class ServicioListadoGrpc {

  private ServicioListadoGrpc() {}

  public static final String SERVICE_NAME = "formulariorn.ServicioListado";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<formulariorn.FormularioRn.ListarFormsRequest,
      formulariorn.FormularioRn.ListarFormsResponse> getListarFormulariosMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListarFormularios",
      requestType = formulariorn.FormularioRn.ListarFormsRequest.class,
      responseType = formulariorn.FormularioRn.ListarFormsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<formulariorn.FormularioRn.ListarFormsRequest,
      formulariorn.FormularioRn.ListarFormsResponse> getListarFormulariosMethod() {
    io.grpc.MethodDescriptor<formulariorn.FormularioRn.ListarFormsRequest, formulariorn.FormularioRn.ListarFormsResponse> getListarFormulariosMethod;
    if ((getListarFormulariosMethod = ServicioListadoGrpc.getListarFormulariosMethod) == null) {
      synchronized (ServicioListadoGrpc.class) {
        if ((getListarFormulariosMethod = ServicioListadoGrpc.getListarFormulariosMethod) == null) {
          ServicioListadoGrpc.getListarFormulariosMethod = getListarFormulariosMethod =
              io.grpc.MethodDescriptor.<formulariorn.FormularioRn.ListarFormsRequest, formulariorn.FormularioRn.ListarFormsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListarFormularios"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  formulariorn.FormularioRn.ListarFormsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  formulariorn.FormularioRn.ListarFormsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServicioListadoMethodDescriptorSupplier("ListarFormularios"))
              .build();
        }
      }
    }
    return getListarFormulariosMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServicioListadoStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServicioListadoStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServicioListadoStub>() {
        @java.lang.Override
        public ServicioListadoStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServicioListadoStub(channel, callOptions);
        }
      };
    return ServicioListadoStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServicioListadoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServicioListadoBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServicioListadoBlockingStub>() {
        @java.lang.Override
        public ServicioListadoBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServicioListadoBlockingStub(channel, callOptions);
        }
      };
    return ServicioListadoBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServicioListadoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServicioListadoFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServicioListadoFutureStub>() {
        @java.lang.Override
        public ServicioListadoFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServicioListadoFutureStub(channel, callOptions);
        }
      };
    return ServicioListadoFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   *a
   * </pre>
   */
  public static abstract class ServicioListadoImplBase implements io.grpc.BindableService {

    /**
     */
    public void listarFormularios(formulariorn.FormularioRn.ListarFormsRequest request,
        io.grpc.stub.StreamObserver<formulariorn.FormularioRn.ListarFormsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListarFormulariosMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListarFormulariosMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                formulariorn.FormularioRn.ListarFormsRequest,
                formulariorn.FormularioRn.ListarFormsResponse>(
                  this, METHODID_LISTAR_FORMULARIOS)))
          .build();
    }
  }

  /**
   * <pre>
   *a
   * </pre>
   */
  public static final class ServicioListadoStub extends io.grpc.stub.AbstractAsyncStub<ServicioListadoStub> {
    private ServicioListadoStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServicioListadoStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServicioListadoStub(channel, callOptions);
    }

    /**
     */
    public void listarFormularios(formulariorn.FormularioRn.ListarFormsRequest request,
        io.grpc.stub.StreamObserver<formulariorn.FormularioRn.ListarFormsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListarFormulariosMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *a
   * </pre>
   */
  public static final class ServicioListadoBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServicioListadoBlockingStub> {
    private ServicioListadoBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServicioListadoBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServicioListadoBlockingStub(channel, callOptions);
    }

    /**
     */
    public formulariorn.FormularioRn.ListarFormsResponse listarFormularios(formulariorn.FormularioRn.ListarFormsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListarFormulariosMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *a
   * </pre>
   */
  public static final class ServicioListadoFutureStub extends io.grpc.stub.AbstractFutureStub<ServicioListadoFutureStub> {
    private ServicioListadoFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServicioListadoFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServicioListadoFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<formulariorn.FormularioRn.ListarFormsResponse> listarFormularios(
        formulariorn.FormularioRn.ListarFormsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListarFormulariosMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LISTAR_FORMULARIOS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServicioListadoImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServicioListadoImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LISTAR_FORMULARIOS:
          serviceImpl.listarFormularios((formulariorn.FormularioRn.ListarFormsRequest) request,
              (io.grpc.stub.StreamObserver<formulariorn.FormularioRn.ListarFormsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ServicioListadoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServicioListadoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return formulariorn.FormularioRn.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ServicioListado");
    }
  }

  private static final class ServicioListadoFileDescriptorSupplier
      extends ServicioListadoBaseDescriptorSupplier {
    ServicioListadoFileDescriptorSupplier() {}
  }

  private static final class ServicioListadoMethodDescriptorSupplier
      extends ServicioListadoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServicioListadoMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ServicioListadoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServicioListadoFileDescriptorSupplier())
              .addMethod(getListarFormulariosMethod())
              .build();
        }
      }
    }
    return result;
  }
}
