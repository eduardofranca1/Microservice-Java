package com.microservice.hrpayroll.servicies;

import org.springframework.stereotype.Service;

import com.microservice.hrpayroll.entities.Payment;

@Service
public class PaymentService {
	
	// método para instanciar um pagamento
	public Payment getPayment(long workerId, int days) {
		return new Payment("Bob", 200.0, days); // instanciando um pagamento mockado (alguns dados)
	}

}
