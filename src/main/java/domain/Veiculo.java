package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_veiculo")
public class Veiculo implements Serializable {
	/* Serializable = salva o estado atual dos objetos em arquivos em formato binário para o seu computador, 
	sendo assim esse estado poderá ser recuperado posteriormente recriando o objeto em memória 
	assim como ele estava no momento da sua serialização.*/
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
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Integer getAnoFabricacao() {
		return anoFabricacao;
	}
	public void setAnoFabricacao(Integer anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}
	public Integer getAnoModelo() {
		return anoModelo;
	}
	public void setAnoModelo(Integer anoModelo) {
		this.anoModelo = anoModelo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Veiculo other = (Veiculo) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	@Override
	public String toString() {
		return "Veiculo [codigo=" + codigo + ", fabricante=" + fabricante + ", modelo=" + modelo + ", anoFabricacao="
				+ anoFabricacao + ", anoModelo=" + anoModelo + ", valor=" + valor + "]";
	}
	
	
	
}
