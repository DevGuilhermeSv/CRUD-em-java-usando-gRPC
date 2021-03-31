package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.Car;
import proto.CarList;
import proto.CarRequestId;
import proto.CarServiceGrpc;

public class Client {

	public static void main(String[] args) {
		Client main = new Client();
		main.run();
	}

	private void run() {
		// TODO Auto-generated method stub
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
        CarServiceGrpc.CarServiceBlockingStub carClient = CarServiceGrpc.newBlockingStub(channel);
 
        // CREATE USER
        Car car = Car.newBuilder().setNome("Monza").setAnoModelo("1999").setFabricacao("1998").setPreco("1200,00").build();
        Car createUserResponse = carClient.create(car);
        System.out.println(createUserResponse.toString());
 
        String userId = createUserResponse.getId();
        System.out.println("user ID: "+userId);
        
        // Get USER
        Car getCarResponse = carClient.get(CarRequestId.newBuilder().setId(userId).build());
        System.out.println(getCarResponse.toString());
 
        // DELETE USER
       // DeleteUserResponse deleteUserResponse = userClient
      //          .deleteUser(DeleteUserRequest.newBuilder().setUserId(userId).build());
      //  System.out.println(deleteUserResponse.getUserId());
 
        // LIST USERS
        CarList listCarResponse = carClient.list(null);
        System.out.println(listCarResponse.getCarList());
	}
}
