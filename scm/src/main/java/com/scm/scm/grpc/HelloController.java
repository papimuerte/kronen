package com.scm.scm.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloController extends HelloServiceGrpc.HelloServiceImplBase {

  @Override
  public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
    String greeting = "Hello " + request.getName() + " from Gorang Madaan Java";

    HelloResponse response = HelloResponse.newBuilder()
        .setMessage(greeting)
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}