package com.microservice.hrpayroll.servicies;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.hrpayroll.entities.Payment;
import com.microservice.hrpayroll.entities.Worker;


@Service
public class PaymentService {
	
	@Value("${hr-worker.host}") // para pegar o valor da propriedade definida no application.properties
	private String workerHost;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// método para instanciar um pagamento
	public Payment getPayment(long workerId, int days) {
		// para passar os parametros
		Map<String, String> uriVariables = new HashMap<>();
		// inserindo o parametro na id na lista de parametros
		uriVariables.put("id", "" + workerId);
		
		// fazendo uma requisição para uma API externa usando RestTemplate(hr-worker)
		Worker worker = restTemplate.getForObject(workerHost + "/workers/{id}", Worker.class, uriVariables);
		
		
		return new Payment(worker.getName(), worker.getDailyIncome(), days); // instanciando um pagamento mockado (alguns dados)
	}

}
