package mil.dds.anet.database.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import mil.dds.anet.beans.Group;

public class GroupMapper implements ResultSetMapper<Group> {

	@Override
	public Group map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Group g = new Group();
		g.setId(r.getInt("id"));
		g.setName(r.getString("name"));
		return g;
	}

}