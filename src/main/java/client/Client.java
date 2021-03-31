package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.Car;
import proto.CarList;
import proto.CarRequestId;
import proto.CarServiceGrpc;
import javax.swing.*;

public class Client {

	public static void main(String[] args) {
		Client main = new Client();
		main.run();
	}

	private void run() {
		 //TODO Auto-generated method stub
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
		CarServiceGrpc.CarServiceBlockingStub carClient = CarServiceGrpc.newBlockingStub(channel);

		 // CREATE CAR
		 String name = JOptionPane.showInputDialog("Nome do Carro: ");
		 String anoModelo = JOptionPane.showInputDialog("Ano do Modelo: ");
		 String Fabricacao = JOptionPane.showInputDialog("Ano de Fabricação: ");
		 String preco = JOptionPane.showInputDialog("Preço do Carro: ");
		Car car = Car.newBuilder().setNome(name).setAnoModelo(anoModelo).setFabricacao(Fabricacao).setPreco(preco).build();
		Car createUserResponse = carClient.create(car);
		System.out.println(createUserResponse.toString());

		String userId = createUserResponse.getId();
		System.out.println("user ID: "+userId);

		 // Get USER
		Car getCarResponse = carClient.get(CarRequestId.newBuilder().setId(userId).build());
		System.out.println(getCarResponse.toString());

		

		// LIST USERS
		 CarList listCarResponse = carClient.list(null);
		 System.out.println(listCarResponse.getCarList());  

		 //UPDATE CAR
		Car newCar = Car.newBuilder().setNome("Palio").setId(userId).build();
		 newCar = carClient.update(newCar);
		 System.out.println("New car: \n"+ newCar.toString());

		 // DELETE CAR
		carClient.delete(CarRequestId.newBuilder().setId(userId).build());
		System.out.println("Car Deleted");
	}
}
