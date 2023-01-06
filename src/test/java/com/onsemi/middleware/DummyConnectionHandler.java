package com.onsemi.middleware;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.util.Properties;

import net.snowflake.client.core.SFBaseResultSet;
import net.snowflake.client.core.SFBaseSession;
import net.snowflake.client.core.SFBaseStatement;
import net.snowflake.client.jdbc.SFBaseFileTransferAgent;
import net.snowflake.client.jdbc.SFConnectionHandler;
import net.snowflake.client.jdbc.SnowflakeBaseResultSet;
import net.snowflake.client.jdbc.SnowflakeResultSetV1;
import net.snowflake.client.jdbc.SnowflakeSQLException;

public class DummyConnectionHandler implements SFConnectionHandler {
	private SFBaseStatement mocked;

	public DummyConnectionHandler(SFBaseStatement mocked) {
		this.mocked = mocked;
	}

	@Override
	public boolean supportsAsyncQuery() {
		return false;
	}

	@Override
	public void initializeConnection(String url, Properties info) throws SQLException {

	}

	@Override
	public SFBaseSession getSFSession() {
		return null;
	}

	@Override
	public SFBaseStatement getSFStatement() throws SQLException {
		return mocked;
	}

	@Override
	public ResultSet createResultSet(String queryID, Statement statement) throws SQLException {
		return null;
	}

	@Override
	public SnowflakeBaseResultSet createResultSet(SFBaseResultSet resultSet, Statement statement) throws SQLException {
		return new SnowflakeResultSetV1(resultSet, statement);
	}

	@Override
	public SnowflakeBaseResultSet createAsyncResultSet(SFBaseResultSet resultSet, Statement statement) throws SQLException {
		return null;
	}

	@Override
	public SFBaseFileTransferAgent getFileTransferAgent(String command, SFBaseStatement statement)
			throws SQLNonTransientConnectionException, SnowflakeSQLException {
		return null;
	}
}
