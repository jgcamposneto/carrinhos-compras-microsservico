package br.com.fiap.postech.carrinhoscomprasmicrosservico.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "carrinhos")
@Entity(name = "carrinhos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CarrinhoDeCompras {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 private UUID idUsuario;
	 
	 @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
	 private Set<ItemPedido> itens = new HashSet<>();
	 
	 private BigDecimal valorTotal;
	
}
