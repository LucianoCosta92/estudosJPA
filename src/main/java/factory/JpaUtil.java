package factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
	private static EntityManagerFactory factory;
	
	/* Blocos estáticos são blocos de código que são executados antes da execução 
	 * do construtor de um objecto. Tudo o que estiver dentro desse bloco de código 
	 * vai ser executado apenas no momento em que o ClassLoader carregar essa classe 
	 * na memoria, ou seja, na teoria é executado apenas uma vez. */
	static {
		factory = Persistence.createEntityManagerFactory("veiculos-jpa");
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public static void close() {
		factory.close();
	}
}
