package server;

import java.util.HashMap;
import java.util.Map;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import proto.Car;
import proto.CarList;

import proto.CarRequestId;
import proto.CarServiceGrpc.CarServiceImplBase;
import proto.Empty;

public class CarServiceImpl extends CarServiceImplBase {
	  private Map<Integer, Car> carMap = new HashMap<Integer, Car>();
	    private int i = 1;
	@Override
	public void list(Empty request, StreamObserver<CarList> responseObserver) {
		System.out.println("List cars");
		CarList lista = CarList.newBuilder().addAllCar(carMap.values()).build();
        responseObserver.onNext(lista);
        responseObserver.onCompleted();
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void get(CarRequestId request, StreamObserver<Car> responseObserver) {
		System.out.println("Get car");
        if (carMap.containsKey(Integer.parseInt(request.getId()) )) {
            Car car = carMap.get(Integer.parseInt(request.getId()));
            
            responseObserver.onNext(car);
            responseObserver.onCompleted();
        } else {
            System.out.println("Car not found");
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
	}

	@Override
	public void create(Car request, StreamObserver<Car> responseObserver) {
		System.out.println("Create car");
		Car car = request;
				
				
			car = car.toBuilder().setAnoModelo(request.getAnoModelo())
			.setFabricacao(request.getFabricacao())
			.setId(String.valueOf(i))
			.setNome(request.getNome())
			.setPreco(request.getPreco())
			.build();
		
		responseObserver.onNext(car);
		responseObserver.onCompleted();
		
		carMap.put(i, car);
		i++;
		
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void update(Car request, StreamObserver<Car> responseObserver) {
		
		if (carMap.containsKey(Integer.parseInt(request.getId()))) {
            carMap.replace(Integer.parseInt(request.getId()) , request);
            System.out.println("Car was updated");
            responseObserver.onNext(request);
            responseObserver.onCompleted();
        } else {
            System.out.println("Car not found");
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
		
		super.update(request, responseObserver);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void delete(CarRequestId request, StreamObserver<Empty> responseObserver) {
		if (carMap.containsKey(Integer.parseInt(request.getId()))) {
            carMap.remove(Integer.parseInt(request.getId()));
            System.out.println("Car was deleted");
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } else {
            System.out.println("Car not found");
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
	}

}
