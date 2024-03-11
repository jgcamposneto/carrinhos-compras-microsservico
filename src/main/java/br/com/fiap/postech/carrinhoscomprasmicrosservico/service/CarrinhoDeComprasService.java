package br.com.fiap.postech.carrinhoscomprasmicrosservico.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.postech.carrinhoscomprasmicrosservico.entity.CarrinhoDeCompras;
import br.com.fiap.postech.carrinhoscomprasmicrosservico.entity.ItemPedido;
import br.com.fiap.postech.carrinhoscomprasmicrosservico.repository.CarrinhosDeComprasRepository;

@Service
public class CarrinhoDeComprasService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CarrinhosDeComprasRepository carrinhosDeComprasRepository;
	
	public CarrinhoDeCompras criarCarrinho(CarrinhoDeCompras carrinho) {
		
		boolean produtosDisponiveis = verificarDisponibilidadeProdutos(carrinho.getItens());
				
		if(!produtosDisponiveis) {
			throw new NoSuchElementException("Um ou mais produtos não estão disponíveis");
		}

		BigDecimal valorTotal = calcularValorTotal(carrinho.getItens());
		carrinho.setValorTotal(valorTotal);

		return carrinhosDeComprasRepository.save(carrinho);
	}
	
	private boolean verificarDisponibilidadeProdutos(List<ItemPedido> itens) {
		
		for (ItemPedido item : itens) {
			
			Long idProduto = item.getIdProduto();
			int quantidadeItem = item.getQuantidade();
			
			ResponseEntity<String> response = restTemplate.getForEntity(
					"http://localhost:8081/produtos/{id}",
					String.class,
					idProduto
			);

			if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new NoSuchElementException("Produto não encontrado!");
			} else {
				try {
					JsonNode produtoJsonNode = objectMapper.readTree(response.getBody());
					int quantidadeEstoque = produtoJsonNode.get("quantidadeEstoque").asInt();
					
					if(quantidadeEstoque < quantidadeItem) {
						return false;
					}
					
				} catch (IOException e) {
				}
			}
		}
		
		return true;
	}

	private BigDecimal calcularValorTotal(List<ItemPedido> itens) {
		BigDecimal valorTotal = BigDecimal.ZERO;

		for (ItemPedido item : itens) {
			Long idProduto = item.getIdProduto();
			int quantidade = item.getQuantidade();

			ResponseEntity<String> response = restTemplate.getForEntity(
					"http://localhost:8081/produtos/{id}",
					String.class,
					idProduto
			);

			if(response.getStatusCode() == HttpStatus.OK) {
                try {
                    JsonNode produtoJsonNode = objectMapper.readTree(response.getBody());
					BigDecimal preco = produtoJsonNode.get("preco").decimalValue();
					valorTotal = valorTotal.add((preco.multiply(BigDecimal.valueOf(quantidade))));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

            }

		}
		return valorTotal;
	}
}
