package domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tab_veiculo")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"fabricante", "modelo", "anoFabricacao", "anoModelo", "valor"}) // exclue campos do Equals e HashCode, exceto codigo
@ToString
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // PK auto_increment
	private Long codigo;
	
	@Column(length = 60, nullable = false) // not null
	private String fabricante;
	
	@Column(length = 60, nullable = false) // not null
	private String modelo;
	
	@Column(name = "ano_fabricacao", nullable = false) // not null
	private Integer anoFabricacao;
	
	@Column(name = "ano_modelo", nullable = false) // not null
	private Integer anoModelo;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal valor;
	
	
}
