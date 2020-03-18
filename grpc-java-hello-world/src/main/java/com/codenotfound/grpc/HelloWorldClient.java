package com.codenotfound.grpc;

import javax.annotation.PostConstruct;

import com.codenotfound.rpc.Greeting;
import com.codenotfound.rpc.HelloWorldServiceGrpc;
import com.codenotfound.rpc.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Component
public class HelloWorldClient {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(HelloWorldClient.class);

  private HelloWorldServiceGrpc.HelloWorldServiceBlockingStub helloWorldServiceBlockingStub;

  {
    ManagedChannel managedChannel = ManagedChannelBuilder
            .forAddress("localhost", 6565).usePlaintext().build();

    helloWorldServiceBlockingStub =
            HelloWorldServiceGrpc.newBlockingStub(managedChannel);
  }
  public static void main(String [] args){
    HelloWorldClient hh=new HelloWorldClient();
    hh.sayHello("11111111","22222");
  }

  @PostConstruct
  private void init() {
    ManagedChannel managedChannel = ManagedChannelBuilder
        .forAddress("localhost", 6565).usePlaintext().build();

    helloWorldServiceBlockingStub =
        HelloWorldServiceGrpc.newBlockingStub(managedChannel);
  }

  public String sayHello(String firstName, String lastName) {
    Person person = Person.newBuilder().setFirstName(firstName)
        .setLastName(lastName).build();
    LOGGER.info("client sending {}", person);

    Greeting greeting =
        helloWorldServiceBlockingStub.sayHello(person);
    LOGGER.info("client received {}", greeting);

    return greeting.getMessage();
  }
}
