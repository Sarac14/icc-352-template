package formulariorn;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: FormularioRn.proto")
public final class ServicioCreacionGrpc {

  private ServicioCreacionGrpc() {}

  public static final String SERVICE_NAME = "formulariorn.ServicioCreacion";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<formulariorn.FormularioRn.CrearFormularioRequest,
      formulariorn.FormularioRn.CrearFormularioResponse> getCrearFormularioMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CrearFormulario",
      requestType = formulariorn.FormularioRn.CrearFormularioRequest.class,
      responseType = formulariorn.FormularioRn.CrearFormularioResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<formulariorn.FormularioRn.CrearFormularioRequest,
      formulariorn.FormularioRn.CrearFormularioResponse> getCrearFormularioMethod() {
    io.grpc.MethodDescriptor<formulariorn.FormularioRn.CrearFormularioRequest, formulariorn.FormularioRn.CrearFormularioResponse> getCrearFormularioMethod;
    if ((getCrearFormularioMethod = ServicioCreacionGrpc.getCrearFormularioMethod) == null) {
      synchronized (ServicioCreacionGrpc.class) {
        if ((getCrearFormularioMethod = ServicioCreacionGrpc.getCrearFormularioMethod) == null) {
          ServicioCreacionGrpc.getCrearFormularioMethod = getCrearFormularioMethod =
              io.grpc.MethodDescriptor.<formulariorn.FormularioRn.CrearFormularioRequest, formulariorn.FormularioRn.CrearFormularioResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CrearFormulario"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  formulariorn.FormularioRn.CrearFormularioRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  formulariorn.FormularioRn.CrearFormularioResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServicioCreacionMethodDescriptorSupplier("CrearFormulario"))
              .build();
        }
      }
    }
    return getCrearFormularioMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServicioCreacionStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServicioCreacionStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServicioCreacionStub>() {
        @java.lang.Override
        public ServicioCreacionStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServicioCreacionStub(channel, callOptions);
        }
      };
    return ServicioCreacionStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServicioCreacionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServicioCreacionBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServicioCreacionBlockingStub>() {
        @java.lang.Override
        public ServicioCreacionBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServicioCreacionBlockingStub(channel, callOptions);
        }
      };
    return ServicioCreacionBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServicioCreacionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServicioCreacionFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServicioCreacionFutureStub>() {
        @java.lang.Override
        public ServicioCreacionFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServicioCreacionFutureStub(channel, callOptions);
        }
      };
    return ServicioCreacionFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ServicioCreacionImplBase implements io.grpc.BindableService {

    /**
     */
    public void crearFormulario(formulariorn.FormularioRn.CrearFormularioRequest request,
        io.grpc.stub.StreamObserver<formulariorn.FormularioRn.CrearFormularioResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCrearFormularioMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCrearFormularioMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                formulariorn.FormularioRn.CrearFormularioRequest,
                formulariorn.FormularioRn.CrearFormularioResponse>(
                  this, METHODID_CREAR_FORMULARIO)))
          .build();
    }
  }

  /**
   */
  public static final class ServicioCreacionStub extends io.grpc.stub.AbstractAsyncStub<ServicioCreacionStub> {
    private ServicioCreacionStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServicioCreacionStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServicioCreacionStub(channel, callOptions);
    }

    /**
     */
    public void crearFormulario(formulariorn.FormularioRn.CrearFormularioRequest request,
        io.grpc.stub.StreamObserver<formulariorn.FormularioRn.CrearFormularioResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCrearFormularioMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ServicioCreacionBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServicioCreacionBlockingStub> {
    private ServicioCreacionBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServicioCreacionBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServicioCreacionBlockingStub(channel, callOptions);
    }

    /**
     */
    public formulariorn.FormularioRn.CrearFormularioResponse crearFormulario(formulariorn.FormularioRn.CrearFormularioRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCrearFormularioMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ServicioCreacionFutureStub extends io.grpc.stub.AbstractFutureStub<ServicioCreacionFutureStub> {
    private ServicioCreacionFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServicioCreacionFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServicioCreacionFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<formulariorn.FormularioRn.CrearFormularioResponse> crearFormulario(
        formulariorn.FormularioRn.CrearFormularioRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCrearFormularioMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREAR_FORMULARIO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServicioCreacionImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServicioCreacionImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREAR_FORMULARIO:
          serviceImpl.crearFormulario((formulariorn.FormularioRn.CrearFormularioRequest) request,
              (io.grpc.stub.StreamObserver<formulariorn.FormularioRn.CrearFormularioResponse>) responseObserver);
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

  private static abstract class ServicioCreacionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServicioCreacionBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return formulariorn.FormularioRn.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ServicioCreacion");
    }
  }

  private static final class ServicioCreacionFileDescriptorSupplier
      extends ServicioCreacionBaseDescriptorSupplier {
    ServicioCreacionFileDescriptorSupplier() {}
  }

  private static final class ServicioCreacionMethodDescriptorSupplier
      extends ServicioCreacionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServicioCreacionMethodDescriptorSupplier(String methodName) {
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
      synchronized (ServicioCreacionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServicioCreacionFileDescriptorSupplier())
              .addMethod(getCrearFormularioMethod())
              .build();
        }
      }
    }
    return result;
  }
}
