package com.microservice.hrpayroll.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.hrpayroll.entities.Payment;
import com.microservice.hrpayroll.entities.Worker;
import com.microservice.hrpayroll.feignclients.WorkerFeignClient;

@Service
public class PaymentService {

	@Autowired
	private WorkerFeignClient workerFeignClient;

	// método para instanciar um pagamento
	public Payment getPayment(long workerId, int days) {

		// fazendo uma requisição para uma API externa usando Feign(hr-worker)
		Worker worker = workerFeignClient.findById(workerId).getBody();

		return new Payment(worker.getName(), worker.getDailyIncome(), days); // instanciando um pagamento mockado
																				// (alguns dados)
	}

}
