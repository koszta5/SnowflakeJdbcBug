package net.snowflake.client.jdbc

import com.onsemi.middleware.DummyConnectionHandler
import net.snowflake.client.core.SFBaseResultSet
import net.snowflake.client.core.SFBaseStatement
import net.snowflake.client.core.SFResultSetMetaData
import net.snowflake.client.core.SFStatementType
import spock.lang.Specification
import spock.lang.Subject

import java.sql.ResultSet

class SnowflakeStatementV1Test extends Specification {

    @Subject
    private SnowflakeStatementV1 statement


    def setup(){
        def connection = Mock(SnowflakeConnectionV1)

        def sfBaseStatement = Mock(SFBaseStatement)
        def handler = new DummyConnectionHandler(sfBaseStatement);

        connection.getHandler() >> {
            return handler
        }
        statement = new SnowflakeStatementV1(connection, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);

        sfBaseStatement.execute(_, _, _) >> {

            def baseResultSet = Mock(SFBaseResultSet)
            def metaData = Mock(SFResultSetMetaData)
            baseResultSet.getMetaData() >> {
                return metaData
            }
            def type = SFStatementType.SELECT
            baseResultSet.getStatementType() >> {
                return type
            }
            return baseResultSet

        }

    }

    def "When execute is called multiple times ResultSet is NOT removed from openResultSets"(){
        when:
            int i = 0
            for (i = 0; i < 10 ; i++){
                statement.execute("select 1");
                def resultSet = statement.getResultSet();
                resultSet.close();
            }
        then:
            statement.openResultSets.size() == 0
    }
}
