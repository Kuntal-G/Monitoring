/*package com.external.monitoring.plugin.impl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.external.monitoring.plugin.BaseMonitorPlugin;

public class DatabaseMonitoringPlugin extends BaseMonitorPlugin {

	public DatabaseMonitoringPlugin(String name, String description, CountDownLatch latch, String[] parameters) {
		super(name, description, latch, parameters);
	}

	@Override
	protected String execute(String[] parameters) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final Map<String, String> map = new HashMap<String, String>();
		map.put("URL", parameters[0]);
		map.put("User", parameters[1]);
		try {
			Driver driver = new oracle.jdbc.driver.OracleDriver();
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(parameters[0], parameters[1], parameters[2]);
			statement = connection.prepareStatement("select * from dual");
			result = statement.executeQuery();
			while (result.next()) {
				break;
			}
			return asHtmlTable(map);
		} catch (Exception e) {
			throw new Exception(asHtmlTable(map));

		} finally {
			try {
				if (result != null) {
					result.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException SqlException) {

			}
		}

	}
}
*/