package kademlia.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */

public final class KademliaServiceGrpc {

  private KademliaServiceGrpc() {}

  public static final String SERVICE_NAME = "kademlia.grpc.KademliaService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<pingMessage,
      pingAnswer> METHOD_PING =
      io.grpc.MethodDescriptor.<pingMessage, pingAnswer>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kademlia.grpc.KademliaService", "Ping"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pingMessage.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pingAnswer.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<storeMessage,
      storeAnswer> METHOD_STORE =
      io.grpc.MethodDescriptor.<storeMessage, storeAnswer>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kademlia.grpc.KademliaService", "Store"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              storeMessage.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              storeAnswer.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<findNodeMessage,
      findNodeAnswer> METHOD_FIND_NODE =
      io.grpc.MethodDescriptor.<findNodeMessage, findNodeAnswer>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kademlia.grpc.KademliaService", "FindNode"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              findNodeMessage.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              findNodeAnswer.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<findValueMessage,
      findValueAnswer> METHOD_FIND_VALUE =
      io.grpc.MethodDescriptor.<findValueMessage, findValueAnswer>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kademlia.grpc.KademliaService", "FindValue"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              findValueMessage.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              findValueAnswer.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static KademliaServiceStub newStub(io.grpc.Channel channel) {
    return new KademliaServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static KademliaServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new KademliaServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static KademliaServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new KademliaServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class KademliaServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void ping(pingMessage request,
                     io.grpc.stub.StreamObserver<pingAnswer> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PING, responseObserver);
    }

    /**
     */
    public void store(storeMessage request,
                      io.grpc.stub.StreamObserver<storeAnswer> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_STORE, responseObserver);
    }

    /**
     */
    public void findNode(findNodeMessage request,
                         io.grpc.stub.StreamObserver<findNodeAnswer> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_FIND_NODE, responseObserver);
    }

    /**
     */
    public void findValue(findValueMessage request,
                          io.grpc.stub.StreamObserver<findValueAnswer> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_FIND_VALUE, responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_PING,
            asyncUnaryCall(
              new MethodHandlers<
                pingMessage,
                pingAnswer>(
                  this, METHODID_PING)))
          .addMethod(
            METHOD_STORE,
            asyncUnaryCall(
              new MethodHandlers<
                storeMessage,
                storeAnswer>(
                  this, METHODID_STORE)))
          .addMethod(
            METHOD_FIND_NODE,
            asyncUnaryCall(
              new MethodHandlers<
                findNodeMessage,
                findNodeAnswer>(
                  this, METHODID_FIND_NODE)))
          .addMethod(
            METHOD_FIND_VALUE,
            asyncUnaryCall(
              new MethodHandlers<
                findValueMessage,
                findValueAnswer>(
                  this, METHODID_FIND_VALUE)))
          .build();
    }
  }

  /**
   */
  public static final class KademliaServiceStub extends io.grpc.stub.AbstractStub<KademliaServiceStub> {
    private KademliaServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private KademliaServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected KademliaServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new KademliaServiceStub(channel, callOptions);
    }

    /**
     */
    public void ping(pingMessage request,
                     io.grpc.stub.StreamObserver<pingAnswer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PING, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void store(storeMessage request,
                      io.grpc.stub.StreamObserver<storeAnswer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_STORE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findNode(findNodeMessage request,
                         io.grpc.stub.StreamObserver<findNodeAnswer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_FIND_NODE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findValue(findValueMessage request,
                          io.grpc.stub.StreamObserver<findValueAnswer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_FIND_VALUE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class KademliaServiceBlockingStub extends io.grpc.stub.AbstractStub<KademliaServiceBlockingStub> {
    private KademliaServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private KademliaServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected KademliaServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new KademliaServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public pingAnswer ping(pingMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PING, getCallOptions(), request);
    }

    /**
     */
    public storeAnswer store(storeMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_STORE, getCallOptions(), request);
    }

    /**
     */
    public findNodeAnswer findNode(findNodeMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_FIND_NODE, getCallOptions(), request);
    }

    /**
     */
    public findValueAnswer findValue(findValueMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_FIND_VALUE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class KademliaServiceFutureStub extends io.grpc.stub.AbstractStub<KademliaServiceFutureStub> {
    private KademliaServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private KademliaServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected KademliaServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new KademliaServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pingAnswer> ping(
        pingMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PING, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<storeAnswer> store(
        storeMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_STORE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<findNodeAnswer> findNode(
        findNodeMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_FIND_NODE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<findValueAnswer> findValue(
        findValueMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_FIND_VALUE, getCallOptions()), request);
    }
  }

  private static final int METHODID_PING = 0;
  private static final int METHODID_STORE = 1;
  private static final int METHODID_FIND_NODE = 2;
  private static final int METHODID_FIND_VALUE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final KademliaServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(KademliaServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PING:
          serviceImpl.ping((pingMessage) request,
              (io.grpc.stub.StreamObserver<pingAnswer>) responseObserver);
          break;
        case METHODID_STORE:
          serviceImpl.store((storeMessage) request,
              (io.grpc.stub.StreamObserver<storeAnswer>) responseObserver);
          break;
        case METHODID_FIND_NODE:
          serviceImpl.findNode((findNodeMessage) request,
              (io.grpc.stub.StreamObserver<findNodeAnswer>) responseObserver);
          break;
        case METHODID_FIND_VALUE:
          serviceImpl.findValue((findValueMessage) request,
              (io.grpc.stub.StreamObserver<findValueAnswer>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class KademliaServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Kademlia.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (KademliaServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new KademliaServiceDescriptorSupplier())
              .addMethod(METHOD_PING)
              .addMethod(METHOD_STORE)
              .addMethod(METHOD_FIND_NODE)
              .addMethod(METHOD_FIND_VALUE)
              .build();
        }
      }
    }
    return result;
  }
}
