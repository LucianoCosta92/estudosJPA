package app;

import java.math.BigDecimal;
import java.nio.channels.Pipe.SourceChannel;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

import domain.Veiculo;
import factory.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class App {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int op = -1;
		
		do {
			try {
				System.out.println();
				System.out.println("1 - Cadastrar");
				System.out.println("2 - Listar");
				System.out.println("3 - Buscar por ID");
				System.out.println("4 - Busca por inicial");
				System.out.println("5 - Excluir");
				System.out.println("6 - Alterar");
				System.out.println("0 - Sair");
				System.out.print("Escolha: ");

				if (scan.hasNextInt()) {
					op = scan.nextInt();
				} else {
					System.out.println("\nErro: entrada inválida!");
					scan.next();
					continue; // reinicia o loop
				}
				
				switch (op) {
				case 1:
					cadastrar();
					break;
				case 2:
					listar();
					break;
				case 3:
					buscarID();
					break;
				case 4:
					buscaTeste();
					break;
				case 5:
					deletar();
					break;
				case 6:
					alterar();
					break;
				case 0:
					System.out.println("\nFim do programa!");
					break;
				default:
					System.out.println("Opção inválida!");
					break;
				}
			} catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
			}
		} while (op != 0);
		
		scan.close();
		JpaUtil.close(); // O EntityManagerFactory deve ser fechado apenas uma vez, geralmente no encerramento da aplicação
	}
	
	@SuppressWarnings("unchecked")
	private static void limpaBuffer(Scanner scan) {
		if (scan.hasNextLine()) {
			scan.nextLine();
		}
	}
	
	public static void cadastrar() {
		EntityManager manager = JpaUtil.getEntityManager();
		Scanner entrada = new Scanner(System.in);
		
		try {
			System.out.print("Fabricante: ");
			String f = entrada.nextLine();
			System.out.print("Modelo: ");
			String m = entrada.nextLine();
			System.out.print("Ano de fabricação: ");
			int af = 0;
			if (entrada.hasNextInt()) {
				af = entrada.nextInt();
			} else {
				System.out.println("Erro: entrada inválida!");
				entrada.next();
				return;
			}
			System.out.print("Ano do modelo: ");
			int am = 0;
			if (entrada.hasNextInt()) {
				am = entrada.nextInt();
			} else {
				System.out.println("Erro: entrada inválida!");
				entrada.next();
				return;
			}
			System.out.print("Valor: ");
			Double v = 0.0;
			if (entrada.hasNextDouble()) {
				v = entrada.nextDouble();
			} else {
				System.out.println("Erro: entrada inválida!");
				entrada.next();
				return;
			}
			
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			
			Veiculo veiculo = new Veiculo();
			veiculo.setFabricante(f);
			veiculo.setModelo(m);
			veiculo.setAnoFabricacao(af);
			veiculo.setAnoModelo(am);
			veiculo.setValor(new BigDecimal(v));
			
			manager.persist(veiculo);
			tx.commit();
			
			System.out.println("Veículo cadastrado com Sucesso!");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}
	
	public static void listar() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
											// usar o nome da classe
			Query query = manager.createQuery("select v from Veiculo v"); // JPQL (Java Persistence Query Language)
			@SuppressWarnings("unchecked")
			List<Veiculo> veiculos = query.getResultList();
			
			for (Veiculo v : veiculos) {
				System.out.println();
				System.out.println("Código: " + v.getCodigo());
				System.out.println("Fabricante: " + v.getFabricante());
				System.out.println("Modelo: " + v.getModelo());
				System.out.println("Ano de fabricação: " + v.getAnoFabricacao());
				System.out.println("Ano do modelo: " + v.getAnoModelo());
				System.out.println("Valor: R$ " + v.getValor());
			}
		} catch (Exception e) {
			System.out.println("Erro ao listar: " + e.getMessage());
		} finally {
			if (manager.isOpen()) {
				manager.close();
			}
		}
	}
	
	public static void buscarID() {
		EntityManager manager = JpaUtil.getEntityManager();
		Scanner entrada = new Scanner(System.in);
		
		try {
			Long id = 0L;
			System.out.print("Digite o ID a ser buscado: ");
			id = entrada.nextLong();
			
			Veiculo veiculo = manager.find(Veiculo.class, id);
			if (veiculo == null) {
				System.out.println("\nNenhum veículo encontrado com esse Id!");
				return;
			}
			System.out.println();
			System.out.println("Código: " + veiculo.getCodigo());
			System.out.println("Fabricante: " + veiculo.getFabricante());
			System.out.println("Modelo: " + veiculo.getModelo());
			System.out.println("Ano de fabricação: " + veiculo.getAnoFabricacao());
			System.out.println("Ano do modelo: " + veiculo.getAnoModelo());
			System.out.println("Valor: R$ " + veiculo.getValor());
			
		} catch (Exception e) {
			System.out.println("Erro ao fazer busca: " + e.getMessage());
		} finally {
			manager.close();
		}

	}
	
	public static void buscaTeste() {
		EntityManager manager = JpaUtil.getEntityManager();
		Scanner entrada = new Scanner(System.in);
		
		try {
			String fabricante;
			System.out.print("Digite a inicial do fabricante do veículo que deseja buscar: ");
			fabricante = entrada.nextLine();
			
			Query query = manager.createQuery("select v from Veiculo v where v.fabricante like concat (:fabricante, '%')").setParameter("fabricante", fabricante);
			@SuppressWarnings("unchecked")
			List<Veiculo> veiculos = query.getResultList();
			
			for (Veiculo v : veiculos) {
				System.out.println();
				System.out.println("Código: " + v.getCodigo());
				System.out.println("Fabricante: " + v.getFabricante());
				System.out.println("Modelo: " + v.getModelo());
				System.out.println("Ano de fabricação: " + v.getAnoFabricacao());
				System.out.println("Ano do modelo: " + v.getAnoModelo());
				System.out.println("Valor: R$ " + v.getValor());
			}
			
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		} finally {
			manager.close();
		}
		
	}
	
	public static void deletar() {
		
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		Scanner entrada = new Scanner(System.in);
		
		try {
			System.out.print("Digite o ID do veículo que deseja deletar: ");
			int id = entrada.nextInt();
			
			tx.begin();
			Veiculo veiculo = manager.find(Veiculo.class, id);
			Optional<Veiculo> optionalVeiculo = Optional.of(veiculo);
			optionalVeiculo.ifPresent(v -> manager.remove(v));
			
			/*if (veiculo == null) {
				System.out.println("\nNenhum veículo encontrado com esse ID!");
				return;
			}*/
			
			// manager.remove(veiculo);
			tx.commit();
			
			System.out.println(veiculo.getModelo() + " excluído com sucesso!");
			
		} catch (Exception e) {
			System.out.println("Erro ao tentar excluir: " + e.getMessage());
		} finally {
			manager.close();
		}
		
	}
	
	public static void alterar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		Scanner entrada = new Scanner(System.in);
		try {
			System.out.print("Digite o ID do veículo que deseja editar: ");
			int id = entrada.nextInt();
			
			tx.begin();
			Veiculo veiculo = manager.find(Veiculo.class, id);
			
			System.out.println();
			System.out.println("Código: " + veiculo.getCodigo());
			System.out.println("Fabricante: " + veiculo.getFabricante());
			System.out.println("Modelo: " + veiculo.getModelo());
			System.out.println("Ano de fabricação: " + veiculo.getAnoFabricacao());
			System.out.println("Ano do modelo: " + veiculo.getAnoModelo());
			System.out.println("Valor: R$ " + veiculo.getValor());
			System.out.println();
			
			limpaBuffer(entrada);
			System.out.print("Fabricante: ");
			String f = entrada.nextLine();
			System.out.print("Modelo: ");
			String m = entrada.nextLine();
			System.out.print("Ano de fabricação: ");
			int af = 0;
			if (entrada.hasNextInt()) {
				af = entrada.nextInt();
			} else {
				System.out.println("Erro: entrada inválida!");
				entrada.next();
				return;
			}
			System.out.print("Ano do modelo: ");
			int am = 0;
			if (entrada.hasNextInt()) {
				am = entrada.nextInt();
			} else {
				System.out.println("Erro: entrada inválida!");
				entrada.next();
				return;
			}
			System.out.print("Valor: ");
			Double v = 0.0;
			if (entrada.hasNextDouble()) {
				v = entrada.nextDouble();
			} else {
				System.out.println("Erro: entrada inválida!");
				entrada.next();
				return;
			}
			
			veiculo.setFabricante(f);
			veiculo.setModelo(m);
			veiculo.setAnoFabricacao(af);
			veiculo.setAnoModelo(am);
			veiculo.setValor(new BigDecimal(v));
			
			tx.commit();
			System.out.println(veiculo.getModelo() + " alterado com sucesso!");
			
		} catch (Exception e) {
			System.out.println("Erro ao tentar editar: " + e.getMessage());
		} finally {
			manager.close();
		}
		
	}
}
