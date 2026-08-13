package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPInitListener implements ServletContextListener {

	private static final String JDBC_PROPERTIES_PATH = "/WEB-INF/jdbc.properties";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties prop = loadJdbcProperties(sce);
		loadJDBCDriver(prop);
		initConnectionPool(prop);
	}

	private Properties loadJdbcProperties(ServletContextEvent sce) {
		Properties prop = new Properties();
		try (InputStream in = sce.getServletContext().getResourceAsStream(JDBC_PROPERTIES_PATH)) {
			if (in == null) {
				throw new RuntimeException(
						"JDBC 설정 파일이 없습니다: " + JDBC_PROPERTIES_PATH
								+ " → WEB-INF/jdbc.properties.example 을 복사해 jdbc.properties 로 만든 뒤 "
								+ "본인 DB 계정으로 수정하세요.");
			}
			prop.load(in);
			return prop;
		} catch (IOException e) {
			throw new RuntimeException("JDBC 설정 로드 실패: " + JDBC_PROPERTIES_PATH, e);
		}
	}

	private void loadJDBCDriver(Properties prop) {
		String driverClass = prop.getProperty("jdbcdriver");
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("fail to load JDBC Driver", ex);
		}
	}

	private void initConnectionPool(Properties prop) {
		try {
			String jdbcUrl = prop.getProperty("jdbcUrl");
			String username = prop.getProperty("dbUser");
			String pw = prop.getProperty("dbPass");

			ConnectionFactory connFactory = new DriverManagerConnectionFactory(jdbcUrl, username, pw);

			PoolableConnectionFactory poolableConnFactory = new PoolableConnectionFactory(connFactory, null);

			String validationQuery = prop.getProperty("validationQuery");
			if (validationQuery != null && !validationQuery.isEmpty()) {
				poolableConnFactory.setValidationQuery(validationQuery);
			}

			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			poolConfig.setTestWhileIdle(true);

			int minIdle = getIntProperty(prop, "minIdle", 5);
			poolConfig.setMinIdle(minIdle);

			int maxTotal = getIntProperty(prop, "maxTotal", 50);
			poolConfig.setMaxTotal(maxTotal);

			GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnFactory,
					poolConfig);
			poolableConnFactory.setPool(connectionPool);

			// 톰캣 드라이버와의 ClassCastException 충돌을 우회하기 위해
			// 드라이버 매니저에서 가져오지 않고 직접 PoolingDriver 인스턴스를 생성하여 등록합니다.
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = new PoolingDriver();

			String poolName = prop.getProperty("poolName");
			driver.registerPool(poolName, connectionPool);

			// 드라이버 매니저에 명시적으로 등록하여 ConnectionProvider가 찾을 수 있도록 합니다.
			DriverManager.registerDriver(driver);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int getIntProperty(Properties prop, String propName, int defaultValue) {
		String value = prop.getProperty(propName);
		if (value == null)
			return defaultValue;
		return Integer.parseInt(value);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 자원 해제 시 필요한 로직 구현
	}
}
